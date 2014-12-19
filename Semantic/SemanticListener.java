// Generated from Semantic.g4 by ANTLR 4.4
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SemanticParser}.
 */
public interface SemanticListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SemanticParser#expr4p}.
	 * @param ctx the parse tree
	 */
	void enterExpr4p(@NotNull SemanticParser.Expr4pContext ctx);
	/**
	 * Exit a parse tree produced by {@link SemanticParser#expr4p}.
	 * @param ctx the parse tree
	 */
	void exitExpr4p(@NotNull SemanticParser.Expr4pContext ctx);
	/**
	 * Enter a parse tree produced by {@link SemanticParser#expr2p}.
	 * @param ctx the parse tree
	 */
	void enterExpr2p(@NotNull SemanticParser.Expr2pContext ctx);
	/**
	 * Exit a parse tree produced by {@link SemanticParser#expr2p}.
	 * @param ctx the parse tree
	 */
	void exitExpr2p(@NotNull SemanticParser.Expr2pContext ctx);
	/**
	 * Enter a parse tree produced by {@link SemanticParser#expr3}.
	 * @param ctx the parse tree
	 */
	void enterExpr3(@NotNull SemanticParser.Expr3Context ctx);
	/**
	 * Exit a parse tree produced by {@link SemanticParser#expr3}.
	 * @param ctx the parse tree
	 */
	void exitExpr3(@NotNull SemanticParser.Expr3Context ctx);
	/**
	 * Enter a parse tree produced by {@link SemanticParser#expr4}.
	 * @param ctx the parse tree
	 */
	void enterExpr4(@NotNull SemanticParser.Expr4Context ctx);
	/**
	 * Exit a parse tree produced by {@link SemanticParser#expr4}.
	 * @param ctx the parse tree
	 */
	void exitExpr4(@NotNull SemanticParser.Expr4Context ctx);
	/**
	 * Enter a parse tree produced by {@link SemanticParser#expr1}.
	 * @param ctx the parse tree
	 */
	void enterExpr1(@NotNull SemanticParser.Expr1Context ctx);
	/**
	 * Exit a parse tree produced by {@link SemanticParser#expr1}.
	 * @param ctx the parse tree
	 */
	void exitExpr1(@NotNull SemanticParser.Expr1Context ctx);
	/**
	 * Enter a parse tree produced by {@link SemanticParser#expr2}.
	 * @param ctx the parse tree
	 */
	void enterExpr2(@NotNull SemanticParser.Expr2Context ctx);
	/**
	 * Exit a parse tree produced by {@link SemanticParser#expr2}.
	 * @param ctx the parse tree
	 */
	void exitExpr2(@NotNull SemanticParser.Expr2Context ctx);
	/**
	 * Enter a parse tree produced by {@link SemanticParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(@NotNull SemanticParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link SemanticParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(@NotNull SemanticParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link SemanticParser#pclass}.
	 * @param ctx the parse tree
	 */
	void enterPclass(@NotNull SemanticParser.PclassContext ctx);
	/**
	 * Exit a parse tree produced by {@link SemanticParser#pclass}.
	 * @param ctx the parse tree
	 */
	void exitPclass(@NotNull SemanticParser.PclassContext ctx);
	/**
	 * Enter a parse tree produced by {@link SemanticParser#expr5p}.
	 * @param ctx the parse tree
	 */
	void enterExpr5p(@NotNull SemanticParser.Expr5pContext ctx);
	/**
	 * Exit a parse tree produced by {@link SemanticParser#expr5p}.
	 * @param ctx the parse tree
	 */
	void exitExpr5p(@NotNull SemanticParser.Expr5pContext ctx);
	/**
	 * Enter a parse tree produced by {@link SemanticParser#expr6p}.
	 * @param ctx the parse tree
	 */
	void enterExpr6p(@NotNull SemanticParser.Expr6pContext ctx);
	/**
	 * Exit a parse tree produced by {@link SemanticParser#expr6p}.
	 * @param ctx the parse tree
	 */
	void exitExpr6p(@NotNull SemanticParser.Expr6pContext ctx);
	/**
	 * Enter a parse tree produced by {@link SemanticParser#formal}.
	 * @param ctx the parse tree
	 */
	void enterFormal(@NotNull SemanticParser.FormalContext ctx);
	/**
	 * Exit a parse tree produced by {@link SemanticParser#formal}.
	 * @param ctx the parse tree
	 */
	void exitFormal(@NotNull SemanticParser.FormalContext ctx);
	/**
	 * Enter a parse tree produced by {@link SemanticParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(@NotNull SemanticParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link SemanticParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(@NotNull SemanticParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link SemanticParser#string}.
	 * @param ctx the parse tree
	 */
	void enterString(@NotNull SemanticParser.StringContext ctx);
	/**
	 * Exit a parse tree produced by {@link SemanticParser#string}.
	 * @param ctx the parse tree
	 */
	void exitString(@NotNull SemanticParser.StringContext ctx);
	/**
	 * Enter a parse tree produced by {@link SemanticParser#expr6}.
	 * @param ctx the parse tree
	 */
	void enterExpr6(@NotNull SemanticParser.Expr6Context ctx);
	/**
	 * Exit a parse tree produced by {@link SemanticParser#expr6}.
	 * @param ctx the parse tree
	 */
	void exitExpr6(@NotNull SemanticParser.Expr6Context ctx);
	/**
	 * Enter a parse tree produced by {@link SemanticParser#expr5}.
	 * @param ctx the parse tree
	 */
	void enterExpr5(@NotNull SemanticParser.Expr5Context ctx);
	/**
	 * Exit a parse tree produced by {@link SemanticParser#expr5}.
	 * @param ctx the parse tree
	 */
	void exitExpr5(@NotNull SemanticParser.Expr5Context ctx);
	/**
	 * Enter a parse tree produced by {@link SemanticParser#feature}.
	 * @param ctx the parse tree
	 */
	void enterFeature(@NotNull SemanticParser.FeatureContext ctx);
	/**
	 * Exit a parse tree produced by {@link SemanticParser#feature}.
	 * @param ctx the parse tree
	 */
	void exitFeature(@NotNull SemanticParser.FeatureContext ctx);
	/**
	 * Enter a parse tree produced by {@link SemanticParser#comment}.
	 * @param ctx the parse tree
	 */
	void enterComment(@NotNull SemanticParser.CommentContext ctx);
	/**
	 * Exit a parse tree produced by {@link SemanticParser#comment}.
	 * @param ctx the parse tree
	 */
	void exitComment(@NotNull SemanticParser.CommentContext ctx);
}