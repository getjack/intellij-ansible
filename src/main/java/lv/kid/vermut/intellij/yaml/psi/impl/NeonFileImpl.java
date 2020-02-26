package lv.kid.vermut.intellij.yaml.psi.impl;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;
import com.intellij.psi.tree.TokenSet;
import lv.kid.vermut.intellij.yaml.YamlLanguage;
import lv.kid.vermut.intellij.yaml.editor.NeonStructureViewElement;
import lv.kid.vermut.intellij.yaml.file.YamlFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.yaml.YAMLElementTypes;
import org.jetbrains.yaml.psi.YAMLDocument;
import org.jetbrains.yaml.psi.YAMLFile;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class NeonFileImpl extends PsiFileBase implements YAMLFile {
    public NeonFileImpl(FileViewProvider viewProvider) {
        super(viewProvider, YamlLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return YamlFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "AnsibleFile:" + getName();
    }

    @NotNull
    @Override
    public PsiReference[] getReferences() {
        return ReferenceProvidersRegistry.getReferencesFromProviders(this);
    }

    @Override
    public ItemPresentation getPresentation() {
        if (getVirtualFile().getCanonicalPath().endsWith("tasks/main.yml"))
            // Format role differently
            return new ItemPresentation() {
                @Nullable
                @Override
                public String getPresentableText() {
                    return "ROLE: " + getParent().getParent().getName();
                }

                @Nullable
                @Override
                public String getLocationString() {
                    return "(" + getParent().getParent().getParent().getParent().getName() + "/" +
                            getParent().getParent().getParent().getName()
                             + ")";
                }

                @Nullable
                @Override
                public Icon getIcon(boolean unused) {
                    return null;
                }
            };
        else
            return new NeonStructureViewElement(this);
    }

    @Override
    public List<YAMLDocument> getDocuments() {
        final ArrayList<YAMLDocument> result = new ArrayList<>();
        for (ASTNode node : getNode().getChildren(TokenSet.create(YAMLElementTypes.DOCUMENT))) {
            result.add((YAMLDocument) node.getPsi());
        }
        return result;
    }
}
