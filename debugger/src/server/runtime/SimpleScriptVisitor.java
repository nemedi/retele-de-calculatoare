// Generated from /Users/iulian/SimpleScript.g4 by ANTLR 4.1
package server.runtime;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SimpleScriptParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
@SuppressWarnings("deprecation")
public interface SimpleScriptVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link SimpleScriptParser#comparison}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparison(@NotNull SimpleScriptParser.ComparisonContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleScriptParser#decision}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecision(@NotNull SimpleScriptParser.DecisionContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleScriptParser#disjunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDisjunction(@NotNull SimpleScriptParser.DisjunctionContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleScriptParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(@NotNull SimpleScriptParser.AssignmentContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleScriptParser#subtraction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubtraction(@NotNull SimpleScriptParser.SubtractionContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleScriptParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(@NotNull SimpleScriptParser.ProgramContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleScriptParser#division}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDivision(@NotNull SimpleScriptParser.DivisionContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleScriptParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(@NotNull SimpleScriptParser.ConditionContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleScriptParser#conjunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConjunction(@NotNull SimpleScriptParser.ConjunctionContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleScriptParser#loop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoop(@NotNull SimpleScriptParser.LoopContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleScriptParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(@NotNull SimpleScriptParser.StatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleScriptParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(@NotNull SimpleScriptParser.BlockContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleScriptParser#multiplication}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplication(@NotNull SimpleScriptParser.MultiplicationContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleScriptParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(@NotNull SimpleScriptParser.ValueContext ctx);

	/**
	 * Visit a parse tree produced by {@link SimpleScriptParser#addition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddition(@NotNull SimpleScriptParser.AdditionContext ctx);
}