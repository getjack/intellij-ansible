package lv.kid.vermut.intellij.yaml.file;

import com.intellij.lang.Language;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.SingleRootFileViewProvider;
import org.jetbrains.annotations.NotNull;

/**
 * disables incremental reparsing
 */
public class YamlFileViewProvider extends SingleRootFileViewProvider {

	public YamlFileViewProvider(@NotNull PsiManager psiManager, @NotNull VirtualFile virtualFile, boolean b) {
		super(psiManager, virtualFile, b);
	}

	@Override
	public boolean supportsIncrementalReparse(@NotNull Language language) {
		return false;
	}
}
