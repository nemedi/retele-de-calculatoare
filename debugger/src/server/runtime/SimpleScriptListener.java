// Generated from /Users/iulian/SimpleScript.g4 by ANTLR 4.1
package server.runtime;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SimpleScriptParser}.
 */
@SuppressWarnings("deprecation")
public interface SimpleScriptListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SimpleScriptParser#comparison}.
	 * @param ctx the parse tree
	 */
	void enterComparison(@NotNull SimpleScriptParser.ComparisonContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleScriptParser#comparison}.
	 * @param ctx the parse tree
	 */
	void exitComparison(@NotNull SimpleScriptParser.ComparisonContext ctx);

	/**
	 * Enter a parse tree produced by {@link SimpleScriptParser#decision}.
	 * @param ctx the parse tree
	 */
	void enterDecision(@NotNull SimpleScriptParser.DecisionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleScriptParser#decision}.
	 * @param ctx the parse tree
	 */
	void exitDecision(@NotNull SimpleScriptParser.DecisionContext ctx);

	/**
	 * Enter a parse tree produced by {@link SimpleScriptParser#disjunction}.
	 * @param ctx the parse tree
	 */
	void enterDisjunction(@NotNull SimpleScriptParser.DisjunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleScriptParser#disjunction}.
	 * @param ctx the parse tree
	 */
	void exitDisjunction(@NotNull SimpleScriptParser.DisjunctionContext ctx);

	/**
	 * Enter a parse tree produced by {@link SimpleScriptParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(@NotNull SimpleScriptParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleScriptParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(@NotNull SimpleScriptParser.AssignmentContext ctx);

	/**
	 * Enter a parse tree produced by {@link SimpleScriptParser#subtraction}.
	 * @param ctx the parse tree
	 */
	void enterSubtraction(@NotNull SimpleScriptParser.SubtractionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleScriptParser#subtraction}.
	 * @param ctx the parse tree
	 */
	void exitSubtraction(@NotNull SimpleScriptParser.SubtractionContext ctx);

	/**
	 * Enter a parse tree produced by {@link SimpleScriptParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(@NotNull SimpleScriptParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleScriptParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(@NotNull SimpleScriptParser.ProgramContext ctx);

	/**
	 * Enter a parse tree produced by {@link SimpleScriptParser#division}.
	 * @param ctx the parse tree
	 */
	void enterDivision(@NotNull SimpleScriptParser.DivisionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleScriptParser#division}.
	 * @param ctx the parse tree
	 */
	void exitDivision(@NotNull SimpleScriptParser.DivisionContext ctx);

	/**
	 * Enter a parse tree produced by {@link SimpleScriptParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(@NotNull SimpleScriptParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleScriptParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(@NotNull SimpleScriptParser.ConditionContext ctx);

	/**
	 * Enter a parse tree produced by {@link SimpleScriptParser#conjunction}.
	 * @param ctx the parse tree
	 */
	void enterConjunction(@NotNull SimpleScriptParser.ConjunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleScriptParser#conjunction}.
	 * @param ctx the parse tree
	 */
	void exitConjunction(@NotNull SimpleScriptParser.ConjunctionContext ctx);

	/**
	 * Enter a parse tree produced by {@link SimpleScriptParser#loop}.
	 * @param ctx the parse tree
	 */
	void enterLoop(@NotNull SimpleScriptParser.LoopContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleScriptParser#loop}.
	 * @param ctx the parse tree
	 */
	void exitLoop(@NotNull SimpleScriptParser.LoopContext ctx);

	/**
	 * Enter a parse tree produced by {@link SimpleScriptParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(@NotNull SimpleScriptParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleScriptParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(@NotNull SimpleScriptParser.StatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link SimpleScriptParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(@NotNull SimpleScriptParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleScriptParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(@NotNull SimpleScriptParser.BlockContext ctx);

	/**
	 * Enter a parse tree produced by {@link SimpleScriptParser#multiplication}.
	 * @param ctx the parse tree
	 */
	void enterMultiplication(@NotNull SimpleScriptParser.MultiplicationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleScriptParser#multiplication}.
	 * @param ctx the parse tree
	 */
	void exitMultiplication(@NotNull SimpleScriptParser.MultiplicationContext ctx);

	/**
	 * Enter a parse tree produced by {@link SimpleScriptParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(@NotNull SimpleScriptParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleScriptParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(@NotNull SimpleScriptParser.ValueContext ctx);

	/**
	 * Enter a parse tree produced by {@link SimpleScriptParser#addition}.
	 * @param ctx the parse tree
	 */
	void enterAddition(@NotNull SimpleScriptParser.AdditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleScriptParser#addition}.
	 * @param ctx the parse tree
	 */
	void exitAddition(@NotNull SimpleScriptParser.AdditionContext ctx);
}