package com.github.getjackx.intellij.plugins.ansible.editor;

import com.github.getjackx.intellij.plugins.ansible.psi.NeonEntity;
import com.intellij.lang.Language;
import com.intellij.psi.PsiElement;
import com.intellij.ui.breadcrumbs.BreadcrumbsProvider;
import com.github.getjackx.intellij.plugins.ansible.YamlLanguage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.psi.YAMLKeyValue;

/**
 * Breadcrumbs info about which section are we editing now (just above the editor, below tabs)
 */
public class NeonBreadcrumbsInfoProvider implements BreadcrumbsProvider {
    private final Language[] ourLanguages = {YamlLanguage.INSTANCE};

    @Override
    public Language[] getLanguages() {
        return ourLanguages;
    }

    @Override
    public boolean acceptElement(@NotNull final PsiElement e) {
        return (e instanceof YAMLKeyValue) || (e instanceof NeonEntity);
    }

    @NotNull
    @Override
    public String getElementInfo(@NotNull final PsiElement e) {
        if (e instanceof YAMLKeyValue) {
            return ((YAMLKeyValue) e).getKeyText();

        } else if (e instanceof NeonEntity) {
            final String name = ((NeonEntity) e).getName();
            return name != null ? name : "??";

        } else {
            return "??";
        }
    }

    @Override
    public String getElementTooltip(@NotNull final PsiElement e) {
        return e.toString();
    }

}
