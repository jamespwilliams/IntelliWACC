package org.antlr.jetbrains.wacc;

import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.PsiElement;
import org.antlr.intellij.adaptor.lexer.RuleIElementType;
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode;
import org.antlr.jetbrains.wacc.psi.FunctionSubtree;
import org.antlr.jetbrains.wacc.psi.IdentifierPSINode;
import org.antlr.jetbrains.wacc.psi.VardefSubtree;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static org.antlr.jetbrains.wacc.parser.WaccLanguageParser.RULE_functionCall;
import static org.antlr.jetbrains.wacc.parser.WaccLanguageParser.RULE_expr;
import static org.antlr.jetbrains.wacc.parser.WaccLanguageParser.RULE_param;
import static org.antlr.jetbrains.wacc.parser.WaccLanguageParser.RULE_func;
import static org.antlr.jetbrains.wacc.parser.WaccLanguageParser.RULE_literal;
import static org.antlr.jetbrains.wacc.parser.WaccLanguageParser.RULE_stat;
import static org.antlr.jetbrains.wacc.parser.WaccLanguageParser.RULE_declarationStat;

public class WaccFindUsagesProvider implements FindUsagesProvider {
	/** Is "find usages" meaningful for a kind of definition subtree? */
	@Override
	public boolean canFindUsagesFor(PsiElement psiElement) {
		return psiElement instanceof IdentifierPSINode || // the case where we highlight the ID in def subtree itself
			   psiElement instanceof FunctionSubtree ||   // remaining cases are for resolve() results
			   psiElement instanceof VardefSubtree;
	}

	@Nullable
	@Override
	public WordsScanner getWordsScanner() {
		return null; // null implies use SimpleWordScanner default
	}

	@Nullable
	@Override
	public String getHelpId(PsiElement psiElement) {
		return null;
	}

	/** What kind of thing is the ID node? Can group by in "Find Usages" dialog */
	@NotNull
	@Override
	public String getType(PsiElement element) {
		// The parent of an ID node will be a RuleIElementType:
		// function, vardef, formal_arg, statement, expr, call_expr, primary
		ANTLRPsiNode parent = (ANTLRPsiNode)element.getParent();
		RuleIElementType elType = (RuleIElementType)parent.getNode().getElementType();
		switch ( elType.getRuleIndex() ) {
			case RULE_functionCall :
			case RULE_func :
				return "function";
			case RULE_declarationStat :
				return "variable";
			case RULE_param :
				return "parameter";
			case RULE_stat :
			case RULE_expr :
			case RULE_literal :
				return "variable";
		}
		return "";
	}

	@NotNull
	@Override
	public String getDescriptiveName(PsiElement element) {
		return element.getText();
	}

	@NotNull
	@Override
	public String getNodeText(PsiElement element, boolean useFullName) {
		String text = element.getText();
		return text;
	}
}
