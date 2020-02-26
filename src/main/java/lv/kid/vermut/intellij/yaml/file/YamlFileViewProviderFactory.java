package lv.kid.vermut.intellij.yaml.file;

import com.intellij.lang.Language;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.FileViewProviderFactory;
import com.intellij.psi.PsiManager;

public class YamlFileViewProviderFactory implements FileViewProviderFactory {
    @Override
    public FileViewProvider createFileViewProvider(final VirtualFile virtualFile, final Language language, final PsiManager psiManager, final boolean physical) {
        return new YamlFileViewProvider(psiManager, virtualFile, physical);
    }
}
