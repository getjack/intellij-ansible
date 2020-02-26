package com.github.getjackx.intellij.plugins.ansible.file;

import com.intellij.lang.Language;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.SingleRootFileViewProvider;
import org.jetbrains.annotations.NotNull;

/**
 * disables incremental reparsing
 */
public class YamlFileViewProvider extends SingleRootFileViewProvider {

    public YamlFileViewProvider(@NotNull final PsiManager psiManager, @NotNull final VirtualFile virtualFile, boolean b) {
        super(psiManager, virtualFile, b);
    }

    @Override
    public boolean supportsIncrementalReparse(@NotNull final Language language) {
        return false;
    }
}
