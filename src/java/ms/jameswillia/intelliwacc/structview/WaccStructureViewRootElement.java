package org.antlr.jetbrains.wacc.structview;

import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class WaccStructureViewRootElement extends WaccStructureViewElement {
	public WaccStructureViewRootElement(PsiFile element) {
		super(element);
	}

	@NotNull
	@Override
	public ItemPresentation getPresentation() {
		return new WaccRootPresentation((PsiFile)element);
	}
}
