// Generated from TypeChecker.g4 by ANTLR 4.4
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link TypeCheckerParser}.
 */
public interface TypeCheckerListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link TypeCheckerParser#expr4p}.
	 * @param ctx the parse tree
	 */
	void enterExpr4p(@NotNull TypeCheckerParser.Expr4pContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeCheckerParser#expr4p}.
	 * @param ctx the parse tree
	 */
	void exitExpr4p(@NotNull TypeCheckerParser.Expr4pContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeCheckerParser#expr2p}.
	 * @param ctx the parse tree
	 */
	void enterExpr2p(@NotNull TypeCheckerParser.Expr2pContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeCheckerParser#expr2p}.
	 * @param ctx the parse tree
	 */
	void exitExpr2p(@NotNull TypeCheckerParser.Expr2pContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeCheckerParser#expr3}.
	 * @param ctx the parse tree
	 */
	void enterExpr3(@NotNull TypeCheckerParser.Expr3Context ctx);
	/**
	 * Exit a parse tree produced by {@link TypeCheckerParser#expr3}.
	 * @param ctx the parse tree
	 */
	void exitExpr3(@NotNull TypeCheckerParser.Expr3Context ctx);
	/**
	 * Enter a parse tree produced by {@link TypeCheckerParser#expr4}.
	 * @param ctx the parse tree
	 */
	void enterExpr4(@NotNull TypeCheckerParser.Expr4Context ctx);
	/**
	 * Exit a parse tree produced by {@link TypeCheckerParser#expr4}.
	 * @param ctx the parse tree
	 */
	void exitExpr4(@NotNull TypeCheckerParser.Expr4Context ctx);
	/**
	 * Enter a parse tree produced by {@link TypeCheckerParser#expr1}.
	 * @param ctx the parse tree
	 */
	void enterExpr1(@NotNull TypeCheckerParser.Expr1Context ctx);
	/**
	 * Exit a parse tree produced by {@link TypeCheckerParser#expr1}.
	 * @param ctx the parse tree
	 */
	void exitExpr1(@NotNull TypeCheckerParser.Expr1Context ctx);
	/**
	 * Enter a parse tree produced by {@link TypeCheckerParser#expr2}.
	 * @param ctx the parse tree
	 */
	void enterExpr2(@NotNull TypeCheckerParser.Expr2Context ctx);
	/**
	 * Exit a parse tree produced by {@link TypeCheckerParser#expr2}.
	 * @param ctx the parse tree
	 */
	void exitExpr2(@NotNull TypeCheckerParser.Expr2Context ctx);
	/**
	 * Enter a parse tree produced by {@link TypeCheckerParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(@NotNull TypeCheckerParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeCheckerParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(@NotNull TypeCheckerParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeCheckerParser#pclass}.
	 * @param ctx the parse tree
	 */
	void enterPclass(@NotNull TypeCheckerParser.PclassContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeCheckerParser#pclass}.
	 * @param ctx the parse tree
	 */
	void exitPclass(@NotNull TypeCheckerParser.PclassContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeCheckerParser#expr5p}.
	 * @param ctx the parse tree
	 */
	void enterExpr5p(@NotNull TypeCheckerParser.Expr5pContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeCheckerParser#expr5p}.
	 * @param ctx the parse tree
	 */
	void exitExpr5p(@NotNull TypeCheckerParser.Expr5pContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeCheckerParser#expr6p}.
	 * @param ctx the parse tree
	 */
	void enterExpr6p(@NotNull TypeCheckerParser.Expr6pContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeCheckerParser#expr6p}.
	 * @param ctx the parse tree
	 */
	void exitExpr6p(@NotNull TypeCheckerParser.Expr6pContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeCheckerParser#formal}.
	 * @param ctx the parse tree
	 */
	void enterFormal(@NotNull TypeCheckerParser.FormalContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeCheckerParser#formal}.
	 * @param ctx the parse tree
	 */
	void exitFormal(@NotNull TypeCheckerParser.FormalContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeCheckerParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(@NotNull TypeCheckerParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeCheckerParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(@NotNull TypeCheckerParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeCheckerParser#string}.
	 * @param ctx the parse tree
	 */
	void enterString(@NotNull TypeCheckerParser.StringContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeCheckerParser#string}.
	 * @param ctx the parse tree
	 */
	void exitString(@NotNull TypeCheckerParser.StringContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeCheckerParser#expr6}.
	 * @param ctx the parse tree
	 */
	void enterExpr6(@NotNull TypeCheckerParser.Expr6Context ctx);
	/**
	 * Exit a parse tree produced by {@link TypeCheckerParser#expr6}.
	 * @param ctx the parse tree
	 */
	void exitExpr6(@NotNull TypeCheckerParser.Expr6Context ctx);
	/**
	 * Enter a parse tree produced by {@link TypeCheckerParser#expr5}.
	 * @param ctx the parse tree
	 */
	void enterExpr5(@NotNull TypeCheckerParser.Expr5Context ctx);
	/**
	 * Exit a parse tree produced by {@link TypeCheckerParser#expr5}.
	 * @param ctx the parse tree
	 */
	void exitExpr5(@NotNull TypeCheckerParser.Expr5Context ctx);
	/**
	 * Enter a parse tree produced by {@link TypeCheckerParser#feature}.
	 * @param ctx the parse tree
	 */
	void enterFeature(@NotNull TypeCheckerParser.FeatureContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeCheckerParser#feature}.
	 * @param ctx the parse tree
	 */
	void exitFeature(@NotNull TypeCheckerParser.FeatureContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeCheckerParser#comment}.
	 * @param ctx the parse tree
	 */
	void enterComment(@NotNull TypeCheckerParser.CommentContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeCheckerParser#comment}.
	 * @param ctx the parse tree
	 */
	void exitComment(@NotNull TypeCheckerParser.CommentContext ctx);
}