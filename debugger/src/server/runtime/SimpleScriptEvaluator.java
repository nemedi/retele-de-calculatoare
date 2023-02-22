package server.runtime;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.ParserRuleContext;

import common.IClientService;
import server.runtime.SimpleScriptParser.AdditionContext;
import server.runtime.SimpleScriptParser.AssignmentContext;
import server.runtime.SimpleScriptParser.BlockContext;
import server.runtime.SimpleScriptParser.ComparisonContext;
import server.runtime.SimpleScriptParser.ConditionContext;
import server.runtime.SimpleScriptParser.ConjunctionContext;
import server.runtime.SimpleScriptParser.DecisionContext;
import server.runtime.SimpleScriptParser.DisjunctionContext;
import server.runtime.SimpleScriptParser.DivisionContext;
import server.runtime.SimpleScriptParser.LoopContext;
import server.runtime.SimpleScriptParser.MultiplicationContext;
import server.runtime.SimpleScriptParser.ProgramContext;
import server.runtime.SimpleScriptParser.StatementContext;
import server.runtime.SimpleScriptParser.SubtractionContext;
import server.runtime.SimpleScriptParser.ValueContext;

public class SimpleScriptEvaluator extends SimpleScriptBaseVisitor<Optional<Object>> {

	private Map<String, Double> variables = new HashMap<String, Double>();
	private List<Integer> breakpoints;
	private volatile boolean waitForBreakpoint;
	private IClientService clientService;
	private String program;
	private static final Map<String, Function<Integer, Boolean>> RELATIONS;
	
	static {
		RELATIONS = new HashMap<String, Function<Integer, Boolean>>();
		RELATIONS.put("<", v -> v < 0);
		RELATIONS.put("<=", v -> v <= 0);
		RELATIONS.put("<>", v -> v != 0);
		RELATIONS.put("==", v -> v == 0);
		RELATIONS.put(">=", v -> v >= 0);
		RELATIONS.put(">", v -> v > 0);
	}
	
	public SimpleScriptEvaluator(String program, int[] breakpoints, IClientService clientService) {
		this.program = program;
		this.breakpoints = Arrays.stream(breakpoints).boxed().collect(Collectors.toList());
		this.clientService = clientService;
	}
	
	@Override
	public Optional<Object> visitValue(ValueContext ctx) {
		if (ctx.VARIABLE() != null) {
			return Optional.of(variables.get(ctx.VARIABLE().getText()));
		} else if (ctx.NUMBER() != null) {
			return Optional.of(Double.parseDouble(ctx.NUMBER().getText()));
		} else {
			return visit(ctx.addition());
		}
	}
	
	@Override
	public Optional<Object> visitDivision(DivisionContext ctx) {
		Double value = (Double) visit(ctx.value(0)).get();
		for (int i = 1; i < ctx.value().size(); i++) {
			value /= (Double) visit(ctx.value(i)).get();
		}
		return Optional.of(value);
	}
	
	@Override
	public Optional<Object> visitMultiplication(MultiplicationContext ctx) {
		Double value = (Double) visit(ctx.division(0)).get();
		for (int i = 1; i < ctx.division().size(); i++) {
			value *= (Double) visit(ctx.division(i)).get();
		}
		return Optional.of(value);
	}
	
	@Override
	public Optional<Object> visitSubtraction(SubtractionContext ctx) {
		Double value = (Double) visit(ctx.multiplication(0)).get();
		for (int i = 1; i < ctx.multiplication().size(); i++) {
			value -= (Double) visit(ctx.multiplication(i)).get();
		}
		return Optional.of(value);
	}
	
	@Override
	public Optional<Object> visitAddition(AdditionContext ctx) {
		Double value = (Double) visit(ctx.subtraction(0)).get();
		for (int i = 1; i < ctx.subtraction().size(); i++) {
			value += (Double) visit(ctx.subtraction(i)).get();
		}
		return Optional.of(value);
	}
	
	@Override
	public Optional<Object> visitAssignment(AssignmentContext ctx) {
		Double value = (Double) visit(ctx.addition()).get();
		variables.put(ctx.VARIABLE().getText(), value);
		return Optional.of(value);
	}
	
	@Override
	public Optional<Object> visitComparison(ComparisonContext ctx) {
		Double leftValue = (Double) visit(ctx.addition(0)).get();
		Double rightValue = (Double) visit(ctx.addition(1)).get();
		return Optional.of(RELATIONS.get(ctx.Relation().getText()).apply(leftValue.compareTo(rightValue)));
	}
	
	@Override
	public Optional<Object> visitConjunction(ConjunctionContext ctx) {
		Boolean value = (Boolean) visit(ctx.comparison(0)).get();
		for (int i = 1; i < ctx.comparison().size(); i++) {
			value &= (Boolean) visit(ctx.comparison(i)).get();
		}
		return Optional.of(value);
	}
	
	@Override
	public Optional<Object> visitDisjunction(DisjunctionContext ctx) {
		Boolean value = (Boolean) visit(ctx.conjunction(0)).get();
		for (int i = 1; i < ctx.conjunction().size(); i++) {
			value |= (Boolean) visit(ctx.conjunction(i)).get();
		}
		return Optional.of(value);
	}
	
	@Override
	public Optional<Object> visitCondition(ConditionContext ctx) {
		Boolean value = (Boolean) visit(ctx.disjunction()).get();
		if (ctx.NOT() != null) {
			return Optional.of(!value);
		} else {
			return Optional.of(value);
		}
	}
	
	@Override
	public Optional<Object> visitDecision(DecisionContext ctx) {
		if ((Boolean) visit(ctx.condition()).get()) {
			return visit(ctx.statement(0));
		} else if (ctx.ELSE() != null) {
			return visit(ctx.statement(1));
		} else {
			return Optional.empty();
		}
	}
	
	@Override
	public Optional<Object> visitLoop(LoopContext ctx) {
		while ((Boolean) visit(ctx.condition()).get()) {
			visit(ctx.statement());
		}
		return Optional.empty();
	}
	
	@Override
	public Optional<Object> visitBlock(BlockContext ctx) {
		for (int i = 0; i < ctx.statement().size(); i++) {
			visit(ctx.statement(i));
		}
		return Optional.empty();
	}
	
	@Override
	public Optional<Object> visitStatement(StatementContext ctx) {
		Optional<Object> result = Optional.empty();
		if (ctx.assignment() != null) {
			result = visit(ctx.assignment());
		} else if (ctx.decision() != null) {
			result = visit(ctx.decision());
		} else if (ctx.loop() != null) {
			result = visit(ctx.loop());
		} else if (ctx.block() != null) {
			result = visit(ctx.block());
		}
		onBreakpoint(ctx);
		return result;
	}

	@Override
	public Optional<Object> visitProgram(ProgramContext ctx) {
		for (int i = 0; i < ctx.statement().size(); i++) {
			visit(ctx.statement(i));
		}
		onEnd();
		return Optional.empty();
	}
	
	private <T extends ParserRuleContext> void onBreakpoint(T ctx) {
		int line = ctx.getStart().getLine() - 1;
		if (breakpoints.contains(line)
				&& clientService != null) {
			try {
				clientService.onBreakpoint(program, line, variables);
				waitForBreakpoint = true;
				while(waitForBreakpoint) {
				}
			} catch (RemoteException e) {
			}
		}
	}
	
	private void onEnd() {
		if (clientService != null) {
			try {
				clientService.onEnd(program);
			} catch (RemoteException e) {
			}
		}
	}
	
	public void stopDebugging() {
		waitForBreakpoint = false;
		breakpoints.clear();
	}

	public void continueDebugging() {
		waitForBreakpoint = false;
	}
}
