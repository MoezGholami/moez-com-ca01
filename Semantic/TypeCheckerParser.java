// Generated from TypeChecker.g4 by ANTLR 4.4
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.spi.CurrencyNameProvider;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class TypeCheckerParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	AnalizerSemantic semAnalizer = AnalizerSemantic.getInstance();
	String CurClassName = null, CurrMethodName=null, CurrVarName=null, CurrMethodTypeName=null, CurrVarTypeName=null, CurrExprType=null, TempVarType;
	ArrayList<AnalizerSemantic.UnrecognizedTypeVar> VarList;
	ArrayList<Integer> CurrScopeKey;
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__18=1, T__17=2, T__16=3, T__15=4, T__14=5, T__13=6, T__12=7, T__11=8, 
		T__10=9, T__9=10, T__8=11, T__7=12, T__6=13, T__5=14, T__4=15, T__3=16, 
		T__2=17, T__1=18, T__0=19, KWCLASS=20, KWELSE=21, KWFI=22, KWIF=23, KWIN=24, 
		KWINHERITS=25, KWISVOID=26, KWLET=27, KWLOOP=28, KWPOOL=29, KWTHEN=30, 
		KWWHILE=31, KWCASE=32, KWESAC=33, KWNEW=34, KWOF=35, KWNOT=36, KWTRUE=37, 
		KWFALSE=38, TID=39, OID=40, INT=41, WS=42, OPP=43, STR=44, LINECOMMENT=45, 
		MULTICOMMENT=46;
	public static final String[] tokenNames = {
		"<INVALID>", "'.'", "'=>'", "')'", "','", "'+'", "'-'", "'*'", "'@'", 
		"':'", "'('", "'<'", "'='", "';'", "'<='", "'{'", "'/'", "'~'", "'}'", 
		"'<-'", "KWCLASS", "KWELSE", "KWFI", "KWIF", "KWIN", "KWINHERITS", "KWISVOID", 
		"KWLET", "KWLOOP", "KWPOOL", "KWTHEN", "KWWHILE", "KWCASE", "KWESAC", 
		"KWNEW", "KWOF", "KWNOT", "KWTRUE", "KWFALSE", "TID", "OID", "INT", "WS", 
		"OPP", "STR", "LINECOMMENT", "MULTICOMMENT"
	};
	public static final int
		RULE_program = 0, RULE_pclass = 1, RULE_feature = 2, RULE_formal = 3, 
			     RULE_expr1 = 4, RULE_expr2 = 5, RULE_expr2p = 6, RULE_expr3 = 7, RULE_expr4 = 8, 
			     RULE_expr4p = 9, RULE_expr5 = 10, RULE_expr5p = 11, RULE_expr6 = 12, RULE_expr6p = 13, 
			     RULE_expr = 14, RULE_comment = 15, RULE_string = 16;
	public static final String[] ruleNames = {
		"program", "pclass", "feature", "formal", "expr1", "expr2", "expr2p", 
		"expr3", "expr4", "expr4p", "expr5", "expr5p", "expr6", "expr6p", "expr", 
		"comment", "string"
	};

	@Override
		public String getGrammarFileName() { return "TypeChecker.g4"; }

	@Override
		public String[] getTokenNames() { return tokenNames; }

	@Override
		public String[] getRuleNames() { return ruleNames; }

	@Override
		public String getSerializedATN() { return _serializedATN; }

	@Override
		public ATN getATN() { return _ATN; }

	public TypeCheckerParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public List<PclassContext> pclass() {
			return getRuleContexts(PclassContext.class);
		}
		public PclassContext pclass(int i) {
			return getRuleContext(PclassContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
			public void enterRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).enterProgram(this);
			}
		@Override
			public void exitRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).exitProgram(this);
			}
	}

	public final ProgramContext program() throws RecognitionException, AnalizerSemantic.KeyWordName, AnalizerSemantic.TypeConflict ,AnalizerSemantic.SemanticError{
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(37); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
						{
							setState(34); pclass();
							setState(35); match(T__6);
						}
					}
					setState(39); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==KWCLASS );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PclassContext extends ParserRuleContext {
		public List<FeatureContext> feature() {
			return getRuleContexts(FeatureContext.class);
		}
		public List<TerminalNode> TID() { return getTokens(TypeCheckerParser.TID); }
		public FeatureContext feature(int i) {
			return getRuleContext(FeatureContext.class,i);
		}
		public TerminalNode KWINHERITS() { return getToken(TypeCheckerParser.KWINHERITS, 0); }
		public TerminalNode TID(int i) {
			return getToken(TypeCheckerParser.TID, i);
		}
		public TerminalNode KWCLASS() { return getToken(TypeCheckerParser.KWCLASS, 0); }
		public PclassContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pclass; }
		@Override
			public void enterRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).enterPclass(this);
			}
		@Override
			public void exitRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).exitPclass(this);
			}
	}

	public final PclassContext pclass() throws RecognitionException, AnalizerSemantic.KeyWordName, AnalizerSemantic.TypeConflict ,AnalizerSemantic.SemanticError{
		PclassContext _localctx = new PclassContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_pclass);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				//System.out.println("####@#CLASS DECLERATION")
				setState(42); match(KWCLASS);
				setState(43); CurClassName = match(TID).getText();
				setState(46);
				_la = _input.LA(1);
				if (_la==KWINHERITS) {
					{
						setState(44); match(KWINHERITS);
						setState(45); match(TID); //pname was deleted from here
					}
				}

				setState(48); match(T__4);
				setState(54);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==OID) {
					{
						{
							setState(49); feature();
							setState(50); match(T__6);
						}
					}
					setState(56);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(57); match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FeatureContext extends ParserRuleContext {
		public FormalContext formal(int i) {
			return getRuleContext(FormalContext.class,i);
		}
		public List<FormalContext> formal() {
			return getRuleContexts(FormalContext.class);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode TID() { return getToken(TypeCheckerParser.TID, 0); }
		public TerminalNode OID() { return getToken(TypeCheckerParser.OID, 0); }
		public FeatureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_feature; }
		@Override
			public void enterRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).enterFeature(this);
			}
		@Override
			public void exitRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).exitFeature(this);
			}
	}
	
	public final FeatureContext feature() throws RecognitionException ,AnalizerSemantic.KeyWordName, AnalizerSemantic.TypeConflict,AnalizerSemantic.SemanticError{
		FeatureContext _localctx = new FeatureContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_feature);
		int _la;
		try {
			setState(85);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
				case 1:
					enterOuterAlt(_localctx, 1);
					{
						setState(59); CurrMethodName=match(OID).getText();
						setState(60); match(T__9);
						setState(69);
						_la = _input.LA(1);
						if (_la==OID) {
							{
								setState(61); formal();
								setState(66);
								_errHandler.sync(this);
								_la = _input.LA(1);
								while (_la==T__15) {
									{
										{
											setState(62); match(T__15);
											setState(63); formal();
										}
									}
									setState(68);
									_errHandler.sync(this);
									_la = _input.LA(1);
								}
							}
						}
						
						setState(71); match(T__16);
						setState(72); match(T__10);
						setState(73); CurrMethodTypeName=match(TID).getText();
						CurrScopeKey = semAnalizer.generateNewMainScopeKey_AfterPass0();
						setState(74); match(T__4);
						setState(75); CurrExprType = expr().TypeName;
						//System.out.printf("methodType: %s && CurrExprReturned %s && CurrMethodName %s\n",CurrMethodTypeName,CurrExprType,CurrMethodName);
						semAnalizer.checkMethodReturnType(CurrMethodTypeName, CurrExprType, _ctx.getStart().getLine());
						setState(76); match(T__1);
						semAnalizer.updateKeyWhenClosingScope(CurClassName, CurrMethodName, CurrScopeKey);
					}
					break;
				case 2:
					enterOuterAlt(_localctx, 2);
					{
						String initexp;
						CurrMethodName = null;
						setState(78); CurrVarName=match(OID).getText();
						setState(79); match(T__10);
						setState(80); CurrVarTypeName=match(TID).getText();
						setState(83);
						_la = _input.LA(1);
						if (_la==T__0) {
							{
								setState(81); match(T__0);
								setState(82); initexp=expr().TypeName;
								semAnalizer.TypeOfOperation(CurrVarTypeName, "<-", initexp, CurClassName, _ctx.getStart().getLine());
							}
						}
						

					}
					break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FormalContext extends ParserRuleContext {
		public TerminalNode TID() { return getToken(TypeCheckerParser.TID, 0); }
		public TerminalNode OID() { return getToken(TypeCheckerParser.OID, 0); }
		public FormalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formal; }
		@Override
			public void enterRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).enterFormal(this);
			}
		@Override
			public void exitRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).exitFormal(this);
			}
	}

	public final FormalContext formal() throws RecognitionException {
		FormalContext _localctx = new FormalContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_formal);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(87); CurrVarName=match(OID).getText();
				setState(88); match(T__10);
				setState(89); CurrVarTypeName=match(TID).getText();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Expr1Context extends ParserRuleContext {
		String TypeName;
		public TerminalNode KWFI() { return getToken(TypeCheckerParser.KWFI, 0); }
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public TerminalNode INT() { return getToken(TypeCheckerParser.INT, 0); }
		public TerminalNode KWTRUE() { return getToken(TypeCheckerParser.KWTRUE, 0); }
		public TerminalNode KWOF() { return getToken(TypeCheckerParser.KWOF, 0); }
		public TerminalNode KWIF() { return getToken(TypeCheckerParser.KWIF, 0); }
		public TerminalNode KWFALSE() { return getToken(TypeCheckerParser.KWFALSE, 0); }
		public TerminalNode OID(int i) {
			return getToken(TypeCheckerParser.OID, i);
		}
		public TerminalNode KWWHILE() { return getToken(TypeCheckerParser.KWWHILE, 0); }
		public TerminalNode KWLOOP() { return getToken(TypeCheckerParser.KWLOOP, 0); }
		public TerminalNode KWLET() { return getToken(TypeCheckerParser.KWLET, 0); }
		public TerminalNode KWESAC() { return getToken(TypeCheckerParser.KWESAC, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> TID() { return getTokens(TypeCheckerParser.TID); }
		public TerminalNode KWIN() { return getToken(TypeCheckerParser.KWIN, 0); }
		public TerminalNode KWELSE() { return getToken(TypeCheckerParser.KWELSE, 0); }
		public TerminalNode KWNEW() { return getToken(TypeCheckerParser.KWNEW, 0); }
		public TerminalNode KWTHEN() { return getToken(TypeCheckerParser.KWTHEN, 0); }
		public List<TerminalNode> OID() { return getTokens(TypeCheckerParser.OID); }
		public TerminalNode KWCASE() { return getToken(TypeCheckerParser.KWCASE, 0); }
		public TerminalNode KWPOOL() { return getToken(TypeCheckerParser.KWPOOL, 0); }
		public TerminalNode TID(int i) {
			return getToken(TypeCheckerParser.TID, i);
		}
		public Expr1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr1; }
		@Override
			public void enterRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).enterExpr1(this);
			}
		@Override
			public void exitRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).exitExpr1(this);
			}
	}

	public final Expr1Context expr1() throws RecognitionException, AnalizerSemantic.NoClassFound_Name, AnalizerSemantic.InvalidInheritance, AnalizerSemantic.NoMethodFoundInClass, AnalizerSemantic.IllegalArguments, AnalizerSemantic.UndefinedVar, AnalizerSemantic.KeyWordName, AnalizerSemantic.TypeConflict,AnalizerSemantic.SemanticError { // done
		//System.err.println("expr1");
		String TypeName=null;
		Expr1Context _localctx = new Expr1Context(_ctx, getState());
		enterRule(_localctx, 8, RULE_expr1);
		int _la;
		try {
			setState(184);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
				case 1:
					enterOuterAlt(_localctx, 1);
					{
						String xexpr,yexpr;
						setState(91); match(KWIF);
						setState(92); semAnalizer.CheckBoolPredicate(expr().TypeName, _ctx.getStart().getLine());
						setState(93); match(KWTHEN);
						setState(94); xexpr = expr().TypeName;
						setState(95); match(KWELSE);
						setState(96); yexpr = expr().TypeName;
						setState(97); match(KWFI);
						TypeName = semAnalizer.joinOf_TypeName(xexpr, yexpr);
					}
					break;
				case 2:
					enterOuterAlt(_localctx, 2);
					{
						setState(99); match(KWWHILE);
						setState(100); semAnalizer.CheckBoolPredicate(expr().TypeName, _ctx.getStart().getLine());
						setState(101); match(KWLOOP);
						setState(102); expr();
						setState(103); match(KWPOOL);
						TypeName = "Object";
					}
					break;
				case 3:
					enterOuterAlt(_localctx, 3);
					{
						//new Scope detected (let)
						int NumberOfLetScopes=1;
						//generate some shit

						semAnalizer.updateKeyWhenOpeningScope(CurClassName, CurrMethodName, CurrScopeKey);
						setState(105); match(KWLET);
						setState(106); CurrVarName=match(OID).getText();
						setState(107); match(T__10);
						setState(108); CurrVarTypeName=match(TID).getText();
						setState(111);
						_la = _input.LA(1);
						if (_la==T__0) {
							{
								setState(109); match(T__0);
								setState(110); expr();
							}
						}

						setState(123);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==T__15) {
							{
								{
									semAnalizer.updateKeyWhenOpeningScope(CurClassName, CurrMethodName, CurrScopeKey);
									NumberOfLetScopes++;
									setState(113); match(T__15);
									setState(114); CurrVarName=match(OID).getText();
									setState(115); match(T__10);
									setState(116); CurrVarTypeName=match(TID).getText();
									setState(119);
									_la = _input.LA(1);
									if (_la==T__0) {
										{
											setState(117); match(T__0);
											setState(118); expr();
										}
									}

								}
							}
							setState(125);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(126); match(KWIN);
						setState(127); TypeName = expr().TypeName;
						for(int i=0;i<NumberOfLetScopes;++i)
							semAnalizer.updateKeyWhenClosingScope(CurClassName, CurrMethodName, CurrScopeKey);							
					}
					break;
				case 4:
					enterOuterAlt(_localctx, 4);
					{
						ArrayList<String> CaseTypeVariables = new ArrayList<String>();
						setState(128); match(KWCASE);
						setState(129); expr();
						setState(130); match(KWOF);
						setState(138); 
						_errHandler.sync(this);
						semAnalizer.updateKeyWhenOpeningScope(CurClassName, CurrMethodName, CurrScopeKey);
						_la = _input.LA(1);
						do {
							{
								{
									setState(131); CurrVarName = match(OID).getText();
									setState(132); match(T__10);
									setState(133); CurrVarTypeName = match(TID).getText();
									CaseTypeVariables.add(CurrVarTypeName);
									setState(134); match(T__17);
									setState(135); CurrExprType = expr().TypeName;
									setState(136); match(T__6);
								}
							}
							setState(140); 
							_errHandler.sync(this);
							_la = _input.LA(1);
						} while ( _la==OID );
						TypeName = semAnalizer.joinOf_TypeName(CaseTypeVariables);
						setState(142); match(KWESAC);
						semAnalizer.updateKeyWhenClosingScope(CurClassName, CurrMethodName, CurrScopeKey);
					}
					break;
				case 5:
					enterOuterAlt(_localctx, 5);
					{
						setState(144); match(T__4);
						setState(148); 
						_errHandler.sync(this);
						_la = _input.LA(1);
						do {
							{
								{
									setState(145); TypeName = expr().TypeName;  // { }
								setState(146); match(T__6);
								}
							}
							setState(150); 
							_errHandler.sync(this);
							_la = _input.LA(1);
						} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__14) | (1L << T__13) | (1L << T__9) | (1L << T__4) | (1L << T__2) | (1L << KWIF) | (1L << KWISVOID) | (1L << KWLET) | (1L << KWWHILE) | (1L << KWCASE) | (1L << KWNEW) | (1L << KWNOT) | (1L << KWTRUE) | (1L << KWFALSE) | (1L << OID) | (1L << INT) | (1L << STR))) != 0) );
						setState(152); match(T__1);
					}
					break;
				case 6:
					enterOuterAlt(_localctx, 6);
					{
						setState(154); match(T__9);	// ( )
						setState(155); TypeName = expr().TypeName;
						setState(156); match(T__16);
					}
					break;
				case 7:
					enterOuterAlt(_localctx, 7);
					{
						// OID ( expr ( expr ) + )
						ArrayList<String> FunctionVariableList = new ArrayList<String>();
						setState(158); CurrMethodName = match(OID).getText();
						setState(159); match(T__9);
						setState(168);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__14) | (1L << T__13) | (1L << T__9) | (1L << T__4) | (1L << T__2) | (1L << KWIF) | (1L << KWISVOID) | (1L << KWLET) | (1L << KWWHILE) | (1L << KWCASE) | (1L << KWNEW) | (1L << KWNOT) | (1L << KWTRUE) | (1L << KWFALSE) | (1L << OID) | (1L << INT) | (1L << STR))) != 0)) {
							{
								setState(160); FunctionVariableList.add(expr().TypeName);
								setState(165);
								_errHandler.sync(this);
								_la = _input.LA(1);
								while (_la==T__15) {
									{
										{
											setState(161); match(T__15);
											setState(162); FunctionVariableList.add(expr().TypeName);
										}
									}
									setState(167);
									_errHandler.sync(this);
									_la = _input.LA(1);
								}
							}
						}
						//System.err.println("In expr1: method: "+FunctionVariableList.toString());
						TypeName = semAnalizer.TypeNameOfMethod(null,null,CurrMethodName,FunctionVariableList,CurClassName, _ctx.getStart().getLine());
						setState(170); match(T__16);
					}
					break;
				case 8:
					enterOuterAlt(_localctx, 8);
					{
						setState(171); CurrVarName = match(OID).getText();
						setState(172); match(T__0);
						setState(173);
						TempVarType = semAnalizer.LookUpVarType(CurClassName, CurrMethodName, CurrScopeKey, CurrVarName,_ctx.getStart().getLine());
						TypeName = semAnalizer.TypeOfOperation(TempVarType,"<-",expr().TypeName,CurClassName,_ctx.getStart().getLine());
					}
					break;
				case 9:
					enterOuterAlt(_localctx, 9);
					{
						setState(174); match(KWNEW);
						setState(175);
						TypeName = semAnalizer.TypeOfOperation( match(TID).getText(),"KWNEW",null,CurClassName,_ctx.getStart().getLine());
					}
					break;
				case 10:
					enterOuterAlt(_localctx, 10);
					{
						CurrVarName = match(OID).getText();
						//System.err.println("String? >>>> "+CurrVarName);
						setState(176); TypeName = semAnalizer.LookUpVarType(CurClassName,CurrMethodName,CurrScopeKey,CurrVarName,_ctx.getStart().getLine());
						assert TypeName !=null;
					}
					break;
				case 11:
					enterOuterAlt(_localctx, 11);
					{
						setState(178);
						_la = _input.LA(1);
						if (_la==T__14 || _la==T__13) {
							{
								setState(177);
								_la = _input.LA(1);
								if ( !(_la==T__14 || _la==T__13) ) {
									_errHandler.recoverInline(this);
								}
								consume();
							}
						}
						TypeName = "Int";
						setState(180); match(INT);
					}
					break;
				case 12:
					enterOuterAlt(_localctx, 12);
					{
						setState(181); string();
						TypeName = "String";
					}
					break;
				case 13:
					enterOuterAlt(_localctx, 13);
					{
						setState(182); match(KWTRUE);
						TypeName = "Bool";
					}
					break;
				case 14:
					enterOuterAlt(_localctx, 14);
					{
						setState(183); match(KWFALSE);
						TypeName = "Bool";
					}
					break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		_localctx.TypeName = TypeName;
		return _localctx;
	}

	public static class Expr2Context extends ParserRuleContext {
		String TypeName;
		public Expr2pContext expr2p(String exprType) {
			return getRuleContext(Expr2pContext.class,0);
		}
		public Expr1Context expr1() {
			return getRuleContext(Expr1Context.class,0);
		}
		public Expr2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr2; }
		@Override
			public void enterRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).enterExpr2(this);
			}
		@Override
			public void exitRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).exitExpr2(this);
			}
	}

	public final Expr2Context expr2() throws RecognitionException , AnalizerSemantic.SemanticError { // done
		//System.err.println("expr2");
		String TypeName=null;
		Expr2Context _localctx = new Expr2Context(_ctx, getState());
		enterRule(_localctx, 10, RULE_expr2);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(186); CurrExprType = expr1().TypeName;
				assert CurrExprType != null;
				setState(187); TypeName = expr2p(CurrExprType).TypeName;
				assert TypeName !=null;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		_localctx.TypeName = TypeName;
		return _localctx;
	}

	public static class Expr2pContext extends ParserRuleContext {
		String TypeName;
		public Expr2pContext expr2p(String exprType) {
			return getRuleContext(Expr2pContext.class,0);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode TID() { return getToken(TypeCheckerParser.TID, 0); }
		public TerminalNode OID() { return getToken(TypeCheckerParser.OID, 0); }
		public Expr2pContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr2p; }
		@Override
			public void enterRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).enterExpr2p(this);
			}
		@Override
			public void exitRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).exitExpr2p(this);
			}
	}

	public final Expr2pContext expr2p(String exprType) throws RecognitionException,AnalizerSemantic.SemanticError { // done
		//System.err.println("expr2p");
		String TypeName=null;
		Expr2pContext _localctx = new Expr2pContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_expr2p);
		int _la;
		try {
			setState(209);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
				case 1:
					enterOuterAlt(_localctx, 1);
					{
						ArrayList<String> FunctionVariableList = new ArrayList<String>();
						setState(191);
						_la = _input.LA(1);
						if (_la==T__11) {
							{
								setState(189); match(T__11);
								setState(190); CurrVarTypeName = match(TID).getText();// function decleration via @
							}
						}

						setState(193); match(T__18);
						setState(194); CurrVarName = match(OID).getText();
						setState(195); match(T__9);
						setState(204);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__14) | (1L << T__13) | (1L << T__9) | (1L << T__4) | (1L << T__2) | (1L << KWIF) | (1L << KWISVOID) | (1L << KWLET) | (1L << KWWHILE) | (1L << KWCASE) | (1L << KWNEW) | (1L << KWNOT) | (1L << KWTRUE) | (1L << KWFALSE) | (1L << OID) | (1L << INT) | (1L << STR))) != 0)) {
							{
								setState(196); FunctionVariableList.add(expr().TypeName);
								setState(201);
								_errHandler.sync(this);
								_la = _input.LA(1);
								while (_la==T__15) {
									{
										{
											setState(197); match(T__15);
											setState(198); FunctionVariableList.add(expr().TypeName);
										}
									}
									setState(203);
									_errHandler.sync(this);
									_la = _input.LA(1);
								}
							}
						}

						setState(206); match(T__16);
						setState(207); TypeName = expr2p(exprType).TypeName; // Here
						
						//System.err.println("expr2: currTypeName: "+exprType+" currMethod: "+CurrVarName+" currVarTypeName: "+CurrVarTypeName+ "return TypeName: "+TypeName);
						TypeName = semAnalizer.TypeNameOfMethod(exprType,CurrVarTypeName,CurrVarName,FunctionVariableList,CurClassName, _ctx.getStart().getLine());
						
					}
					break;
				case 2:
					enterOuterAlt(_localctx, 2);
					{
						TypeName = exprType;
					}
					break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		_localctx.TypeName = TypeName;
		return _localctx;
	}

	public static class Expr3Context extends ParserRuleContext {
		String TypeName;
		public Expr2Context expr2() {
			return getRuleContext(Expr2Context.class,0);
		}
		public TerminalNode KWISVOID() { return getToken(TypeCheckerParser.KWISVOID, 0); }
		public Expr3Context expr3() {
			return getRuleContext(Expr3Context.class,0);
		}
		public Expr3Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr3; }
		@Override
			public void enterRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).enterExpr3(this);
			}
		@Override
			public void exitRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).exitExpr3(this);
			}
	}

	public final Expr3Context expr3() throws RecognitionException, AnalizerSemantic.KeyWordName, AnalizerSemantic.TypeConflict,AnalizerSemantic.SemanticError { // done
		//System.err.println("expr3");
		String TypeName=null;
		Expr3Context _localctx = new Expr3Context(_ctx, getState());
		enterRule(_localctx, 14, RULE_expr3);
		try {
			setState(216);
			switch (_input.LA(1)) {
				case T__14:
				case T__13:
				case T__9:
				case T__4:
				case KWIF:
				case KWLET:
				case KWWHILE:
				case KWCASE:
				case KWNEW:
				case KWTRUE:
				case KWFALSE:
				case OID:
				case INT:
				case STR:
					enterOuterAlt(_localctx, 1);
					{
						setState(211); TypeName = expr2().TypeName;
						//assert TypeName != null;
					}
					break;
				case T__2:
					enterOuterAlt(_localctx, 2);
					{
						setState(212); match(T__2);
						setState(213); TypeName = semAnalizer.TypeOfOperation(expr3().TypeName,"~",null,CurClassName, _ctx.getStart().getLine());
						//assert TypeName != null;
					}
					break;
				case KWISVOID:
					enterOuterAlt(_localctx, 3);
					{
						setState(214); match(KWISVOID);
						setState(215); TypeName = semAnalizer.TypeOfOperation(expr3().TypeName,"KWISVOID",null,CurClassName, _ctx.getStart().getLine());
						//assert TypeName != null;
					}
					break;
				default:
					throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		
		_localctx.TypeName = TypeName;
		return _localctx;
	}

	public static class Expr4Context extends ParserRuleContext {
		String TypeName;
		public Expr4pContext expr4p(String exprType) {
			return getRuleContext(Expr4pContext.class,0);
		}
		public Expr3Context expr3() {
			return getRuleContext(Expr3Context.class,0);
		}
		public Expr4Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr4; }
		@Override
			public void enterRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).enterExpr4(this);
			}
		@Override
			public void exitRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).exitExpr4(this);
			}
	}

	public final Expr4Context expr4() throws RecognitionException, AnalizerSemantic.KeyWordName, AnalizerSemantic.TypeConflict,AnalizerSemantic.SemanticError { // done
		//System.err.println("expr4");
		String TypeName=null;
		Expr4Context _localctx = new Expr4Context(_ctx, getState());
		enterRule(_localctx, 16, RULE_expr4);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(218); CurrExprType = expr3().TypeName;
				//assert CurrExprType!=null;
				setState(219); TypeName = expr4p(CurrExprType).TypeName;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		_localctx.TypeName = TypeName;
		return _localctx;
	}

	public static class Expr4pContext extends ParserRuleContext {
		String TypeName;
		public Expr4pContext expr4p(String exprType) {
			return getRuleContext(Expr4pContext.class,0);
		}
		public Expr3Context expr3() {
			return getRuleContext(Expr3Context.class,0);
		}
		public Expr4pContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr4p; }
		@Override
			public void enterRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).enterExpr4p(this);
			}
		@Override
			public void exitRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).exitExpr4p(this);
			}
	}

	public final Expr4pContext expr4p(String exprType) throws RecognitionException, AnalizerSemantic.KeyWordName, AnalizerSemantic.TypeConflict,AnalizerSemantic.SemanticError { // done
		//System.err.println("expr4p");
		String TypeName=null;
		Expr4pContext _localctx = new Expr4pContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_expr4p);
		try {
			setState(230);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
				case 1:
					enterOuterAlt(_localctx, 1);
					{
						setState(221); match(T__3);
						setState(222); CurrExprType = expr3().TypeName;
						setState(223); TypeName = expr4p(semAnalizer.TypeOfOperation(exprType,"/",CurrExprType,CurClassName, _ctx.getStart().getLine())).TypeName;
					}
					break;
				case 2:
					enterOuterAlt(_localctx, 2);
					{
						setState(225); match(T__12);
						setState(226); CurrExprType = expr3().TypeName;
						setState(227); TypeName = expr4p(semAnalizer.TypeOfOperation(exprType,"*",CurrExprType,CurClassName, _ctx.getStart().getLine())).TypeName;
					}
					break;
				case 3:
					enterOuterAlt(_localctx, 3);
					{
						TypeName = exprType;
					}
					break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		_localctx.TypeName = TypeName;
		return _localctx;
	}

	public static class Expr5Context extends ParserRuleContext {
		String TypeName;
		public Expr5pContext expr5p(String exprType) {
			return getRuleContext(Expr5pContext.class,0);
		}
		public Expr4Context expr4() {
			return getRuleContext(Expr4Context.class,0);
		}
		public Expr5Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr5; }
		@Override
			public void enterRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).enterExpr5(this);
			}
		@Override
			public void exitRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).exitExpr5(this);
			}
	}

	public final Expr5Context expr5() throws RecognitionException, AnalizerSemantic.KeyWordName, AnalizerSemantic.TypeConflict,AnalizerSemantic.SemanticError { // done
		//System.err.println("expr5");
		String TypeName=null;
		Expr5Context _localctx = new Expr5Context(_ctx, getState());
		enterRule(_localctx, 20, RULE_expr5);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(232); CurrExprType = expr4().TypeName;
				//assert CurrExprType!=null;
				setState(233); TypeName = expr5p(CurrExprType).TypeName;
				
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		_localctx.TypeName = TypeName;
		return _localctx;
	}

	public static class Expr5pContext extends ParserRuleContext {
		String TypeName;
		public Expr5pContext expr5p(String exprType) {
			return getRuleContext(Expr5pContext.class,0);
		}
		public Expr4Context expr4() {
			return getRuleContext(Expr4Context.class,0);
		}
		public Expr5pContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr5p; }
		@Override
			public void enterRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).enterExpr5p(this);
			}
		@Override
			public void exitRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).exitExpr5p(this);
			}
	}

	public final Expr5pContext expr5p(String exprType) throws RecognitionException,AnalizerSemantic.KeyWordName, AnalizerSemantic.TypeConflict,AnalizerSemantic.SemanticError { //done
		//System.err.println("expr5p");
		String TypeName=null;
		Expr5pContext _localctx = new Expr5pContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_expr5p);
		try {
			setState(244);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
				case 1:
					enterOuterAlt(_localctx, 1);
					{
						setState(235); match(T__14);
						setState(236); CurrExprType = expr4().TypeName;
						setState(237); TypeName = expr5p(semAnalizer.TypeOfOperation(exprType,"+",CurrExprType,CurClassName, _ctx.getStart().getLine())).TypeName;
					}
					break;
				case 2:
					enterOuterAlt(_localctx, 2);
					{
						setState(239); match(T__13);
						setState(240); CurrExprType = expr4().TypeName;
						setState(241); TypeName = expr5p(semAnalizer.TypeOfOperation(exprType,"-",CurrExprType,CurClassName, _ctx.getStart().getLine())).TypeName;
					}
					break;
				case 3:
					enterOuterAlt(_localctx, 3);
					{
						TypeName = exprType;
					}
					break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		_localctx.TypeName = TypeName;
		return _localctx;
	}

	public static class Expr6Context extends ParserRuleContext {
		String TypeName;
		public Expr5Context expr5() {
			return getRuleContext(Expr5Context.class,0);
		}
		public Expr6pContext expr6p(String exprType) {
			return getRuleContext(Expr6pContext.class,0);
		}
		public Expr6Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr6; }
		@Override
			public void enterRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).enterExpr6(this);
			}
		@Override
			public void exitRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).exitExpr6(this);
			}
	}

	public final Expr6Context expr6() throws RecognitionException, AnalizerSemantic.KeyWordName, AnalizerSemantic.TypeConflict,AnalizerSemantic.SemanticError { //done
		//System.err.println("expr6");
		String TypeName=null;
		Expr6Context _localctx = new Expr6Context(_ctx, getState());
		enterRule(_localctx, 24, RULE_expr6);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(246); CurrExprType = expr5().TypeName;
				assert(CurrExprType!=null);
				setState(247); TypeName = expr6p(CurrExprType).TypeName;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		_localctx.TypeName = TypeName;
		return _localctx;
	}

	public static class Expr6pContext extends ParserRuleContext {
		String TypeName;
		public Expr5Context expr5() {
			return getRuleContext(Expr5Context.class,0);
		}
		public Expr6pContext expr6p(String exprType) {
			return getRuleContext(Expr6pContext.class,0);
		}
		public Expr6pContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr6p; }
		@Override
			public void enterRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).enterExpr6p(this);
			}
		@Override
			public void exitRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).exitExpr6p(this);
			}
	}

	public final Expr6pContext expr6p(String exprType) throws RecognitionException, AnalizerSemantic.KeyWordName, AnalizerSemantic.TypeConflict,AnalizerSemantic.SemanticError { // done
		//System.err.println("expr6p");
		String TypeName=null;
		Expr6pContext _localctx = new Expr6pContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_expr6p);
		try {
			setState(262);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
				case 1:
					enterOuterAlt(_localctx, 1);
					{
						setState(249); match(T__8);
						setState(250); CurrExprType = expr5().TypeName;
						setState(251); TypeName = expr6p(semAnalizer.TypeOfOperation(exprType,"<",CurrExprType,CurClassName, _ctx.getStart().getLine())).TypeName;
					}
					break;
				case 2:
					enterOuterAlt(_localctx, 2);
					{
						setState(253); match(T__5);
						setState(254); CurrExprType = expr5().TypeName;
						setState(255); TypeName = expr6p(semAnalizer.TypeOfOperation(exprType,"<=",CurrExprType,CurClassName, _ctx.getStart().getLine())).TypeName;
					}
					break;
				case 3:
					enterOuterAlt(_localctx, 3);
					{
						setState(257); match(T__7);
						setState(258); CurrExprType = expr5().TypeName;
						setState(259); TypeName = expr6p(semAnalizer.TypeOfOperation(exprType,"=",CurrExprType,CurClassName, _ctx.getStart().getLine())).TypeName;
					}
					break;
				case 4:
					enterOuterAlt(_localctx, 4);
					{
						TypeName = exprType;
					}
					break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		_localctx.TypeName = TypeName;
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext { 
		String TypeName;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode KWNOT() { return getToken(TypeCheckerParser.KWNOT, 0); }
		public Expr6Context expr6() {
			return getRuleContext(Expr6Context.class,0);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
			public void enterRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).enterExpr(this);
			}
		@Override
			public void exitRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).exitExpr(this);
			}
	}

	public final ExprContext expr() throws RecognitionException, AnalizerSemantic.KeyWordName, AnalizerSemantic.TypeConflict,AnalizerSemantic.SemanticError { //done
		//System.err.println("expr");
		String TypeName=null;
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_expr);
		try {
			setState(267);
			switch (_input.LA(1)) {
				case T__14:
				case T__13:
				case T__9:
				case T__4:
				case T__2:
				case KWIF:
				case KWISVOID:
				case KWLET:
				case KWWHILE:
				case KWCASE:
				case KWNEW:
				case KWTRUE:
				case KWFALSE:
				case OID:
				case INT:
				case STR:
					enterOuterAlt(_localctx, 1);
					{
						setState(264); TypeName = expr6().TypeName;
					}
					break;
				case KWNOT:
					enterOuterAlt(_localctx, 2);
					{
						setState(265); match(KWNOT);
						setState(266); expr();
						TypeName = "Bool";
					}
					break;
				default:
					throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		_localctx.TypeName = TypeName;
		return _localctx;
	}

	public static class CommentContext extends ParserRuleContext {
		public TerminalNode LINECOMMENT() { return getToken(TypeCheckerParser.LINECOMMENT, 0); }
		public TerminalNode MULTICOMMENT() { return getToken(TypeCheckerParser.MULTICOMMENT, 0); }
		public CommentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comment; }
		@Override
			public void enterRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).enterComment(this);
			}
		@Override
			public void exitRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).exitComment(this);
			}
	}

	public final CommentContext comment() throws RecognitionException {
		CommentContext _localctx = new CommentContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_comment);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(269);
				_la = _input.LA(1);
				if ( !(_la==LINECOMMENT || _la==MULTICOMMENT) ) {
					_errHandler.recoverInline(this);
				}
				consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StringContext extends ParserRuleContext {
		public TerminalNode STR() { return getToken(TypeCheckerParser.STR, 0); }
		public StringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string; }
		@Override
			public void enterRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).enterString(this);
			}
		@Override
			public void exitRule(ParseTreeListener listener) {
				if ( listener instanceof TypeCheckerListener ) ((TypeCheckerListener)listener).exitString(this);
			}
	}

	public final StringContext string() throws RecognitionException {
		StringContext _localctx = new StringContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_string);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(271); match(STR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\60\u0114\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\3\2\3\2\3\2\6\2(\n\2\r\2\16\2)\3\3\3\3\3\3\3\3\3\3\5\3\61\n\3\3\3\3\3"+
		"\3\3\3\3\7\3\67\n\3\f\3\16\3:\13\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\7\4C\n"+
		"\4\f\4\16\4F\13\4\5\4H\n\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\5\4V\n\4\5\4X\n\4\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6r\n\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\5\6z\n\6\7\6|\n\6\f\6\16\6\177\13\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\6\6\u008d\n\6\r\6\16\6\u008e\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\6\6\u0097\n\6\r\6\16\6\u0098\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\7\6\u00a6\n\6\f\6\16\6\u00a9\13\6\5\6\u00ab\n\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\5\6\u00b5\n\6\3\6\3\6\3\6\3\6\5\6\u00bb\n\6\3"+
		"\7\3\7\3\7\3\b\3\b\5\b\u00c2\n\b\3\b\3\b\3\b\3\b\3\b\3\b\7\b\u00ca\n\b"+
		"\f\b\16\b\u00cd\13\b\5\b\u00cf\n\b\3\b\3\b\3\b\5\b\u00d4\n\b\3\t\3\t\3"+
		"\t\3\t\3\t\5\t\u00db\n\t\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\3\13\3\13\5\13\u00e9\n\13\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\5\r\u00f7\n\r\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\3\17\3\17\3\17\3\17\3\17\5\17\u0109\n\17\3\20\3\20\3\20\5\20\u010e"+
		"\n\20\3\21\3\21\3\22\3\22\3\22\2\2\23\2\4\6\b\n\f\16\20\22\24\26\30\32"+
		"\34\36 \"\2\4\3\2\7\b\3\2/\60\u012c\2\'\3\2\2\2\4+\3\2\2\2\6W\3\2\2\2"+
		"\bY\3\2\2\2\n\u00ba\3\2\2\2\f\u00bc\3\2\2\2\16\u00d3\3\2\2\2\20\u00da"+
		"\3\2\2\2\22\u00dc\3\2\2\2\24\u00e8\3\2\2\2\26\u00ea\3\2\2\2\30\u00f6\3"+
		"\2\2\2\32\u00f8\3\2\2\2\34\u0108\3\2\2\2\36\u010d\3\2\2\2 \u010f\3\2\2"+
		"\2\"\u0111\3\2\2\2$%\5\4\3\2%&\7\17\2\2&(\3\2\2\2\'$\3\2\2\2()\3\2\2\2"+
		")\'\3\2\2\2)*\3\2\2\2*\3\3\2\2\2+,\b\3\1\2,-\7\26\2\2-\60\7)\2\2./\7\33"+
		"\2\2/\61\7)\2\2\60.\3\2\2\2\60\61\3\2\2\2\61\62\3\2\2\2\628\7\21\2\2\63"+
		"\64\5\6\4\2\64\65\7\17\2\2\65\67\3\2\2\2\66\63\3\2\2\2\67:\3\2\2\28\66"+
		"\3\2\2\289\3\2\2\29;\3\2\2\2:8\3\2\2\2;<\7\24\2\2<\5\3\2\2\2=>\7*\2\2"+
		">G\7\f\2\2?D\5\b\5\2@A\7\6\2\2AC\5\b\5\2B@\3\2\2\2CF\3\2\2\2DB\3\2\2\2"+
		"DE\3\2\2\2EH\3\2\2\2FD\3\2\2\2G?\3\2\2\2GH\3\2\2\2HI\3\2\2\2IJ\7\5\2\2"+
		"JK\7\13\2\2KL\7)\2\2LM\7\21\2\2MN\5\36\20\2NO\7\24\2\2OX\3\2\2\2PQ\7*"+
		"\2\2QR\7\13\2\2RU\7)\2\2ST\7\25\2\2TV\5\36\20\2US\3\2\2\2UV\3\2\2\2VX"+
		"\3\2\2\2W=\3\2\2\2WP\3\2\2\2X\7\3\2\2\2YZ\7*\2\2Z[\7\13\2\2[\\\7)\2\2"+
		"\\\t\3\2\2\2]^\7\31\2\2^_\5\36\20\2_`\7 \2\2`a\5\36\20\2ab\7\27\2\2bc"+
		"\5\36\20\2cd\7\30\2\2d\u00bb\3\2\2\2ef\7!\2\2fg\5\36\20\2gh\7\36\2\2h"+
		"i\5\36\20\2ij\7\37\2\2j\u00bb\3\2\2\2kl\7\35\2\2lm\7*\2\2mn\7\13\2\2n"+
		"q\7)\2\2op\7\25\2\2pr\5\36\20\2qo\3\2\2\2qr\3\2\2\2r}\3\2\2\2st\7\6\2"+
		"\2tu\7*\2\2uv\7\13\2\2vy\7)\2\2wx\7\25\2\2xz\5\36\20\2yw\3\2\2\2yz\3\2"+
		"\2\2z|\3\2\2\2{s\3\2\2\2|\177\3\2\2\2}{\3\2\2\2}~\3\2\2\2~\u0080\3\2\2"+
		"\2\177}\3\2\2\2\u0080\u0081\7\32\2\2\u0081\u00bb\5\36\20\2\u0082\u0083"+
		"\7\"\2\2\u0083\u0084\5\36\20\2\u0084\u008c\7%\2\2\u0085\u0086\7*\2\2\u0086"+
		"\u0087\7\13\2\2\u0087\u0088\7)\2\2\u0088\u0089\7\4\2\2\u0089\u008a\5\36"+
		"\20\2\u008a\u008b\7\17\2\2\u008b\u008d\3\2\2\2\u008c\u0085\3\2\2\2\u008d"+
		"\u008e\3\2\2\2\u008e\u008c\3\2\2\2\u008e\u008f\3\2\2\2\u008f\u0090\3\2"+
		"\2\2\u0090\u0091\7#\2\2\u0091\u00bb\3\2\2\2\u0092\u0096\7\21\2\2\u0093"+
		"\u0094\5\36\20\2\u0094\u0095\7\17\2\2\u0095\u0097\3\2\2\2\u0096\u0093"+
		"\3\2\2\2\u0097\u0098\3\2\2\2\u0098\u0096\3\2\2\2\u0098\u0099\3\2\2\2\u0099"+
		"\u009a\3\2\2\2\u009a\u009b\7\24\2\2\u009b\u00bb\3\2\2\2\u009c\u009d\7"+
		"\f\2\2\u009d\u009e\5\36\20\2\u009e\u009f\7\5\2\2\u009f\u00bb\3\2\2\2\u00a0"+
		"\u00a1\7*\2\2\u00a1\u00aa\7\f\2\2\u00a2\u00a7\5\36\20\2\u00a3\u00a4\7"+
		"\6\2\2\u00a4\u00a6\5\36\20\2\u00a5\u00a3\3\2\2\2\u00a6\u00a9\3\2\2\2\u00a7"+
		"\u00a5\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8\u00ab\3\2\2\2\u00a9\u00a7\3\2"+
		"\2\2\u00aa\u00a2\3\2\2\2\u00aa\u00ab\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac"+
		"\u00bb\7\5\2\2\u00ad\u00ae\7*\2\2\u00ae\u00af\7\25\2\2\u00af\u00bb\5\36"+
		"\20\2\u00b0\u00b1\7$\2\2\u00b1\u00bb\7)\2\2\u00b2\u00bb\7*\2\2\u00b3\u00b5"+
		"\t\2\2\2\u00b4\u00b3\3\2\2\2\u00b4\u00b5\3\2\2\2\u00b5\u00b6\3\2\2\2\u00b6"+
		"\u00bb\7+\2\2\u00b7\u00bb\5\"\22\2\u00b8\u00bb\7\'\2\2\u00b9\u00bb\7("+
		"\2\2\u00ba]\3\2\2\2\u00bae\3\2\2\2\u00bak\3\2\2\2\u00ba\u0082\3\2\2\2"+
		"\u00ba\u0092\3\2\2\2\u00ba\u009c\3\2\2\2\u00ba\u00a0\3\2\2\2\u00ba\u00ad"+
		"\3\2\2\2\u00ba\u00b0\3\2\2\2\u00ba\u00b2\3\2\2\2\u00ba\u00b4\3\2\2\2\u00ba"+
		"\u00b7\3\2\2\2\u00ba\u00b8\3\2\2\2\u00ba\u00b9\3\2\2\2\u00bb\13\3\2\2"+
		"\2\u00bc\u00bd\5\n\6\2\u00bd\u00be\5\16\b\2\u00be\r\3\2\2\2\u00bf\u00c0"+
		"\7\n\2\2\u00c0\u00c2\7)\2\2\u00c1\u00bf\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2"+
		"\u00c3\3\2\2\2\u00c3\u00c4\7\3\2\2\u00c4\u00c5\7*\2\2\u00c5\u00ce\7\f"+
		"\2\2\u00c6\u00cb\5\36\20\2\u00c7\u00c8\7\6\2\2\u00c8\u00ca\5\36\20\2\u00c9"+
		"\u00c7\3\2\2\2\u00ca\u00cd\3\2\2\2\u00cb\u00c9\3\2\2\2\u00cb\u00cc\3\2"+
		"\2\2\u00cc\u00cf\3\2\2\2\u00cd\u00cb\3\2\2\2\u00ce\u00c6\3\2\2\2\u00ce"+
		"\u00cf\3\2\2\2\u00cf\u00d0\3\2\2\2\u00d0\u00d1\7\5\2\2\u00d1\u00d4\5\16"+
		"\b\2\u00d2\u00d4\3\2\2\2\u00d3\u00c1\3\2\2\2\u00d3\u00d2\3\2\2\2\u00d4"+
		"\17\3\2\2\2\u00d5\u00db\5\f\7\2\u00d6\u00d7\7\23\2\2\u00d7\u00db\5\20"+
		"\t\2\u00d8\u00d9\7\34\2\2\u00d9\u00db\5\20\t\2\u00da\u00d5\3\2\2\2\u00da"+
		"\u00d6\3\2\2\2\u00da\u00d8\3\2\2\2\u00db\21\3\2\2\2\u00dc\u00dd\5\20\t"+
		"\2\u00dd\u00de\5\24\13\2\u00de\23\3\2\2\2\u00df\u00e0\7\22\2\2\u00e0\u00e1"+
		"\5\20\t\2\u00e1\u00e2\5\24\13\2\u00e2\u00e9\3\2\2\2\u00e3\u00e4\7\t\2"+
		"\2\u00e4\u00e5\5\20\t\2\u00e5\u00e6\5\24\13\2\u00e6\u00e9\3\2\2\2\u00e7"+
		"\u00e9\3\2\2\2\u00e8\u00df\3\2\2\2\u00e8\u00e3\3\2\2\2\u00e8\u00e7\3\2"+
		"\2\2\u00e9\25\3\2\2\2\u00ea\u00eb\5\22\n\2\u00eb\u00ec\5\30\r\2\u00ec"+
		"\27\3\2\2\2\u00ed\u00ee\7\7\2\2\u00ee\u00ef\5\22\n\2\u00ef\u00f0\5\30"+
		"\r\2\u00f0\u00f7\3\2\2\2\u00f1\u00f2\7\b\2\2\u00f2\u00f3\5\22\n\2\u00f3"+
		"\u00f4\5\30\r\2\u00f4\u00f7\3\2\2\2\u00f5\u00f7\3\2\2\2\u00f6\u00ed\3"+
		"\2\2\2\u00f6\u00f1\3\2\2\2\u00f6\u00f5\3\2\2\2\u00f7\31\3\2\2\2\u00f8"+
		"\u00f9\5\26\f\2\u00f9\u00fa\5\34\17\2\u00fa\33\3\2\2\2\u00fb\u00fc\7\r"+
		"\2\2\u00fc\u00fd\5\26\f\2\u00fd\u00fe\5\34\17\2\u00fe\u0109\3\2\2\2\u00ff"+
		"\u0100\7\20\2\2\u0100\u0101\5\26\f\2\u0101\u0102\5\34\17\2\u0102\u0109"+
		"\3\2\2\2\u0103\u0104\7\16\2\2\u0104\u0105\5\26\f\2\u0105\u0106\5\34\17"+
		"\2\u0106\u0109\3\2\2\2\u0107\u0109\3\2\2\2\u0108\u00fb\3\2\2\2\u0108\u00ff"+
		"\3\2\2\2\u0108\u0103\3\2\2\2\u0108\u0107\3\2\2\2\u0109\35\3\2\2\2\u010a"+
		"\u010e\5\32\16\2\u010b\u010c\7&\2\2\u010c\u010e\5\36\20\2\u010d\u010a"+
		"\3\2\2\2\u010d\u010b\3\2\2\2\u010e\37\3\2\2\2\u010f\u0110\t\3\2\2\u0110"+
		"!\3\2\2\2\u0111\u0112\7.\2\2\u0112#\3\2\2\2\33)\608DGUWqy}\u008e\u0098"+
		"\u00a7\u00aa\u00b4\u00ba\u00c1\u00cb\u00ce\u00d3\u00da\u00e8\u00f6\u0108"+
		"\u010d";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
