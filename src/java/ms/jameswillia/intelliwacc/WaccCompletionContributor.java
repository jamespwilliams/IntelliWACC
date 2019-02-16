package ms.jameswillia.intelliwacc;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import ms.jameswillia.intelliwacc.psi.VardefSubtree;
import ms.jameswillia.intelliwacc.psi.VariableRef;
import org.jetbrains.annotations.NotNull;

public class WaccCompletionContributor extends CompletionContributor {
    public WaccCompletionContributor() {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement().withLanguage(WaccLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {
                    }
                }
        );
    }
}
