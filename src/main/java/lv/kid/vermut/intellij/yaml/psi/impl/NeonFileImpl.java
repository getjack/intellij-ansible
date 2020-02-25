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
import lv.kid.vermut.intellij.yaml.parser.NeonElementTypes;
import lv.kid.vermut.intellij.yaml.psi.NeonFile;
import lv.kid.vermut.intellij.yaml.psi.NeonSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.HashMap;

public class NeonFileImpl extends PsiFileBase implements NeonFile {
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
        return "NeonFile:" + getName();
    }

    public HashMap<String, NeonSection> getSections() {
        HashMap<String, NeonSection> ret = new HashMap<>();

        for (ASTNode node : getNode().getChildren(TokenSet.create(NeonElementTypes.KEY_VALUE_PAIR))) {
            NeonSection section = (NeonSection) node.getPsi();
            ret.put(section.getKeyText(), section);
        }

        return ret;
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
}
