package com.github.getjackx.intellij.plugins.ansible.editor;

import com.github.getjackx.intellij.plugins.ansible.YamlIcons;
import com.github.getjackx.intellij.plugins.ansible.psi.NeonArray;
import com.github.getjackx.intellij.plugins.ansible.psi.NeonKeyValPair;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.structureView.impl.common.PsiTreeElementBase;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.github.getjackx.intellij.plugins.ansible.psi.NeonFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLValue;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NeonStructureViewElement extends PsiTreeElementBase<PsiElement> {

    public NeonStructureViewElement(final PsiElement element) {
        super(element);
    }

    @NotNull
    @Override
    public Collection<StructureViewTreeElement> getChildrenBase() {
        final List<StructureViewTreeElement> elements = new ArrayList<>();
        final PsiElement element = getElement();

        if (element instanceof NeonFile) {
            if (element.getChildren().length == 1 && element.getChildren()[0] instanceof NeonArray) { // top level array -> show it's elements
                addArrayElements(elements, element.getChildren()[0]);

            } else { // file children directly
                addArrayElements(elements, element);
            }

        } else if (element instanceof NeonKeyValPair && ((NeonKeyValPair) element).getValue() instanceof NeonArray) {
            addArrayElements(elements, ((NeonKeyValPair) element).getValue());

        } else if (element instanceof NeonArray) {
            addArrayElements(elements, element);

        }

        return elements;
    }

    private void addArrayElements(final List<StructureViewTreeElement> elements, final PsiElement firstValue) {
        for (final PsiElement child : firstValue.getChildren()) {
            elements.add(new NeonStructureViewElement(child));
        }
    }

    @Override
    @Nullable
    public String getPresentableText() {
        final PsiElement element = getElement();
        if (element instanceof NeonFile) {
            return ((NeonFile) element).getName();

        } else if (element instanceof NeonArray) {
            return "array";

        } else if (element instanceof YAMLKeyValue) {
            return ((YAMLKeyValue) element).getKeyText();

        } else if (element instanceof YAMLValue) {
            return element.getText();

        } else {
            return "? YAML";
        }
    }

    @Override
    @Nullable
    public String getLocationString() {
        final PsiFile containingFile = getElement().getContainingFile();

        return "("
                + containingFile.getParent().getParent().getName() + "/"
                + containingFile.getParent().getName() + "/"
                + containingFile.getName() + ")";
    }

    @Nullable
    @Override
    public Icon getIcon(boolean unused) {
        return YamlIcons.FILETYPE_ICON;
    }
}
