// Generated from /Users/iulian/SimpleScript.g4 by ANTLR 4.1
package server.runtime;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SimpleScriptLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		Relation=1, LT=2, LE=3, EQ=4, NE=5, GE=6, GT=7, IF=8, THEN=9, ELSE=10, 
		WHILE=11, DO=12, BEGIN=13, END=14, OR=15, AND=16, NOT=17, ASSIGN=18, LB=19, 
		RB=20, PLUS=21, MINUS=22, MUL=23, DIV=24, VARIABLE=25, NUMBER=26, WS=27;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"Relation", "'<'", "'<='", "'=='", "'<>'", "'>='", "'>'", "'if'", "'then'", 
		"'else'", "'while'", "'do'", "'begin'", "'end'", "'or'", "'and'", "'not'", 
		"'='", "'('", "')'", "'+'", "'-'", "'*'", "'/'", "VARIABLE", "NUMBER", 
		"WS"
	};
	public static final String[] ruleNames = {
		"Relation", "LT", "LE", "EQ", "NE", "GE", "GT", "IF", "THEN", "ELSE", 
		"WHILE", "DO", "BEGIN", "END", "OR", "AND", "NOT", "ASSIGN", "LB", "RB", 
		"PLUS", "MINUS", "MUL", "DIV", "VARIABLE", "NUMBER", "WS"
	};


	public SimpleScriptLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "SimpleScript.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 26: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\2\35\u00a0\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\3\2\3\2\3\2\3\2\3\2\3\2\5\2@\n\2\3"+
		"\3\3\3\3\4\3\4\3\4\3\5\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\t\3\t"+
		"\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\20"+
		"\3\20\3\20\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\23\3\23\3\24\3\24"+
		"\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\6\32\u008c\n\32"+
		"\r\32\16\32\u008d\3\33\6\33\u0091\n\33\r\33\16\33\u0092\3\33\3\33\6\33"+
		"\u0097\n\33\r\33\16\33\u0098\5\33\u009b\n\33\3\34\3\34\3\34\3\34\2\35"+
		"\3\3\1\5\4\1\7\5\1\t\6\1\13\7\1\r\b\1\17\t\1\21\n\1\23\13\1\25\f\1\27"+
		"\r\1\31\16\1\33\17\1\35\20\1\37\21\1!\22\1#\23\1%\24\1\'\25\1)\26\1+\27"+
		"\1-\30\1/\31\1\61\32\1\63\33\1\65\34\1\67\35\2\3\2\5\4\2C\\c|\3\2\62;"+
		"\5\2\13\f\17\17\"\"\u00a8\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2"+
		"\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2"+
		"\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3"+
		"\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2"+
		"\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67"+
		"\3\2\2\2\3?\3\2\2\2\5A\3\2\2\2\7C\3\2\2\2\tF\3\2\2\2\13I\3\2\2\2\rL\3"+
		"\2\2\2\17O\3\2\2\2\21Q\3\2\2\2\23T\3\2\2\2\25Y\3\2\2\2\27^\3\2\2\2\31"+
		"d\3\2\2\2\33g\3\2\2\2\35m\3\2\2\2\37q\3\2\2\2!t\3\2\2\2#x\3\2\2\2%|\3"+
		"\2\2\2\'~\3\2\2\2)\u0080\3\2\2\2+\u0082\3\2\2\2-\u0084\3\2\2\2/\u0086"+
		"\3\2\2\2\61\u0088\3\2\2\2\63\u008b\3\2\2\2\65\u0090\3\2\2\2\67\u009c\3"+
		"\2\2\29@\5\5\3\2:@\5\7\4\2;@\5\t\5\2<@\5\13\6\2=@\5\r\7\2>@\5\17\b\2?"+
		"9\3\2\2\2?:\3\2\2\2?;\3\2\2\2?<\3\2\2\2?=\3\2\2\2?>\3\2\2\2@\4\3\2\2\2"+
		"AB\7>\2\2B\6\3\2\2\2CD\7>\2\2DE\7?\2\2E\b\3\2\2\2FG\7?\2\2GH\7?\2\2H\n"+
		"\3\2\2\2IJ\7>\2\2JK\7@\2\2K\f\3\2\2\2LM\7@\2\2MN\7?\2\2N\16\3\2\2\2OP"+
		"\7@\2\2P\20\3\2\2\2QR\7k\2\2RS\7h\2\2S\22\3\2\2\2TU\7v\2\2UV\7j\2\2VW"+
		"\7g\2\2WX\7p\2\2X\24\3\2\2\2YZ\7g\2\2Z[\7n\2\2[\\\7u\2\2\\]\7g\2\2]\26"+
		"\3\2\2\2^_\7y\2\2_`\7j\2\2`a\7k\2\2ab\7n\2\2bc\7g\2\2c\30\3\2\2\2de\7"+
		"f\2\2ef\7q\2\2f\32\3\2\2\2gh\7d\2\2hi\7g\2\2ij\7i\2\2jk\7k\2\2kl\7p\2"+
		"\2l\34\3\2\2\2mn\7g\2\2no\7p\2\2op\7f\2\2p\36\3\2\2\2qr\7q\2\2rs\7t\2"+
		"\2s \3\2\2\2tu\7c\2\2uv\7p\2\2vw\7f\2\2w\"\3\2\2\2xy\7p\2\2yz\7q\2\2z"+
		"{\7v\2\2{$\3\2\2\2|}\7?\2\2}&\3\2\2\2~\177\7*\2\2\177(\3\2\2\2\u0080\u0081"+
		"\7+\2\2\u0081*\3\2\2\2\u0082\u0083\7-\2\2\u0083,\3\2\2\2\u0084\u0085\7"+
		"/\2\2\u0085.\3\2\2\2\u0086\u0087\7,\2\2\u0087\60\3\2\2\2\u0088\u0089\7"+
		"\61\2\2\u0089\62\3\2\2\2\u008a\u008c\t\2\2\2\u008b\u008a\3\2\2\2\u008c"+
		"\u008d\3\2\2\2\u008d\u008b\3\2\2\2\u008d\u008e\3\2\2\2\u008e\64\3\2\2"+
		"\2\u008f\u0091\t\3\2\2\u0090\u008f\3\2\2\2\u0091\u0092\3\2\2\2\u0092\u0090"+
		"\3\2\2\2\u0092\u0093\3\2\2\2\u0093\u009a\3\2\2\2\u0094\u0096\7\60\2\2"+
		"\u0095\u0097\t\3\2\2\u0096\u0095\3\2\2\2\u0097\u0098\3\2\2\2\u0098\u0096"+
		"\3\2\2\2\u0098\u0099\3\2\2\2\u0099\u009b\3\2\2\2\u009a\u0094\3\2\2\2\u009a"+
		"\u009b\3\2\2\2\u009b\66\3\2\2\2\u009c\u009d\t\4\2\2\u009d\u009e\3\2\2"+
		"\2\u009e\u009f\b\34\2\2\u009f8\3\2\2\2\b\2?\u008d\u0092\u0098\u009a";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}