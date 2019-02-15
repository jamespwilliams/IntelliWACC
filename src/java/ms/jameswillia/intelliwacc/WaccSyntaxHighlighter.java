package ms.jameswillia.intelliwacc;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor;
import org.antlr.intellij.adaptor.lexer.PSIElementTypeFactory;
import org.antlr.intellij.adaptor.lexer.TokenIElementType;
import ms.jameswillia.intelliwacc.parser.WaccLanguageLexer;
import ms.jameswillia.intelliwacc.parser.WaccLanguageParser;
import org.jetbrains.annotations.NotNull;
import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

/** A highlighter is really just a mapping from token type to
 *  some text attributes using {@link #getTokenHighlights(IElementType)}.
 *  The reason that it returns an array, TextAttributesKey[], is
 *  that you might want to mix the attributes of a few known highlighters.
 *  A {@link TextAttributesKey} is just a name for that a theme
 *  or IDE skin can set. For example, {@link com.intellij.openapi.editor.DefaultLanguageHighlighterColors#KEYWORD}
 *  is the key that maps to what identifiers look like in the editor.
 *  To change it, see dialog: Editor > Colors & Fonts > Language Defaults.
 *
 *  From <a href="http://www.jetbrains.org/intellij/sdk/docs/reference_guide/custom_language_support/syntax_highlighting_and_error_highlighting.html">doc</a>:
 *  "The mapping of the TextAttributesKey to specific attributes used
 *  in an editor is defined by the EditorColorsScheme class, and can
 *  be configured by the user if the plugin provides an appropriate
 *  configuration interface.
 *  ...
 *  The syntax highlighter returns the {@link TextAttributesKey}
 * instances for each token type which needs special highlighting.
 * For highlighting lexer errors, the standard TextAttributesKey
 * for bad characters HighlighterColors.BAD_CHARACTER can be used."
 */
public class WaccSyntaxHighlighter extends SyntaxHighlighterBase {
	private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];
	public static final TextAttributesKey ID =
		createTextAttributesKey("SAMPLE_ID", DefaultLanguageHighlighterColors.IDENTIFIER);
	public static final TextAttributesKey KEYWORD =
		createTextAttributesKey("SAMPLE_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
	public static final TextAttributesKey STRING =
		createTextAttributesKey("SAMPLE_STRING", DefaultLanguageHighlighterColors.STRING);
	public static final TextAttributesKey NUMBER =
			createTextAttributesKey("SAMPLE_NUM", DefaultLanguageHighlighterColors.NUMBER);
	public static final TextAttributesKey FUNCTIONCALL =
			createTextAttributesKey("SAMPLE_FCALL", DefaultLanguageHighlighterColors.STATIC_METHOD);
	public static final TextAttributesKey LINE_COMMENT =
		createTextAttributesKey("SAMPLE_LINE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
	public static final TextAttributesKey BLOCK_COMMENT =
		createTextAttributesKey("SAMPLE_BLOCK_COMMENT", DefaultLanguageHighlighterColors.BLOCK_COMMENT);

	static {
		PSIElementTypeFactory.defineLanguageIElementTypes(WaccLanguage.INSTANCE,
		                                                  WaccLanguageParser.tokenNames,
		                                                  WaccLanguageParser.ruleNames);
	}

	@NotNull
	@Override
	public Lexer getHighlightingLexer() {
		WaccLanguageLexer lexer = new WaccLanguageLexer(null);
		return new ANTLRLexerAdaptor(WaccLanguage.INSTANCE, lexer);
	}

	@NotNull
	@Override
	public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
		if ( !(tokenType instanceof TokenIElementType) ) return EMPTY_KEYS;
		TokenIElementType myType = (TokenIElementType)tokenType;
		int ttype = myType.getANTLRTokenType();
		TextAttributesKey attrKey;

		switch ( ttype ) {
			case WaccLanguageLexer.IDENT :
				attrKey = ID;
				break;
			case WaccLanguageLexer.INT :
			case WaccLanguageLexer.BOOL :
			case WaccLanguageLexer.CHAR :
			case WaccLanguageLexer.STRING :
			case WaccLanguageLexer.IF :
			case WaccLanguageLexer.ELSE :
			case WaccLanguageLexer.RETURN :
			case WaccLanguageLexer.PAIR :
			case WaccLanguageLexer.NEWPAIR :
			case WaccLanguageLexer.BOOL_LITER :
			case WaccLanguageLexer.THEN :
			case WaccLanguageLexer.SKIP_ :
			case WaccLanguageLexer.BEGIN :
			case WaccLanguageLexer.END :
			case WaccLanguageLexer.IS :
			case WaccLanguageLexer.FI :
			case WaccLanguageLexer.WHILE :
			case WaccLanguageLexer.DO :
			case WaccLanguageLexer.DONE :
				attrKey = KEYWORD;
				break;
			case WaccLanguageLexer.STR_LITER :
				attrKey = STRING;
				break;
			case WaccLanguageLexer.COMMENT :
				attrKey = LINE_COMMENT;
				break;
			case WaccLanguageLexer.INT_LITER :
				attrKey = NUMBER;
				break;
			case WaccLanguageLexer.CALL:
			case WaccLanguageLexer.READ :
			case WaccLanguageLexer.LEN :
			case WaccLanguageLexer.ORD:
			case WaccLanguageLexer.CHR :
			case WaccLanguageLexer.PRINTLN:
			case WaccLanguageLexer.PRINT :
			case WaccLanguageLexer.FREE :
			case WaccLanguageLexer.EXIT:
				attrKey = FUNCTIONCALL;
				break;
			default :
				return EMPTY_KEYS;
		}
		return new TextAttributesKey[] {attrKey};
	}
}
