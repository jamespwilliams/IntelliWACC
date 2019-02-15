package ms.jameswillia.intelliwacc.structview;

import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiFile;
import ms.jameswillia.intelliwacc.Icons;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class WaccRootPresentation implements ItemPresentation {
	protected final PsiFile element;

	protected WaccRootPresentation(PsiFile element) {
		this.element = element;
	}

	@Nullable
	@Override
	public Icon getIcon(boolean unused) {
		return Icons.SAMPLE_ICON;
	}

	@Nullable
	@Override
	public String getPresentableText() {
		return element.getVirtualFile().getNameWithoutExtension();
	}

	@Nullable
	@Override
	public String getLocationString() {
		return null;
	}
}
