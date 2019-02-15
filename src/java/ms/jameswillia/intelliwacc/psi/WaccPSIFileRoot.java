package org.antlr.jetbrains.wacc.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import org.antlr.intellij.adaptor.SymtabUtils;
import org.antlr.intellij.adaptor.psi.ScopeNode;
import org.antlr.jetbrains.wacc.Icons;
import org.antlr.jetbrains.wacc.WaccFileType;
import org.antlr.jetbrains.wacc.WaccLanguage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class WaccPSIFileRoot extends PsiFileBase implements ScopeNode {
    public WaccPSIFileRoot(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, WaccLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return WaccFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Wacc Language file";
    }

    @Override
    public Icon getIcon(int flags) {
        return Icons.SAMPLE_ICON;
    }

	/** Return null since a file scope has no enclosing scope. It is
	 *  not itself in a scope.
	 */
	@Override
	public ScopeNode getContext() {
		return null;
	}

	@Nullable
	@Override
	public PsiElement resolve(PsiNamedElement element) {
//		System.out.println(getClass().getSimpleName()+
//		                   ".resolve("+element.getName()+
//		                   " at "+Integer.toHexString(element.hashCode())+")");
		if ( element.getParent() instanceof CallSubtree ) {
			return SymtabUtils.resolve(this, WaccLanguage.INSTANCE,
			                           element, "/script/function/ID");
		}
		return SymtabUtils.resolve(this, WaccLanguage.INSTANCE,
		                           element, "/script/vardef/ID");
	}
}
