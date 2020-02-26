package com.github.getjackx.intellij.plugins.ansible.reference;

import com.github.getjackx.intellij.plugins.ansible.psi.NeonJinja;
import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import com.github.getjackx.intellij.plugins.ansible.YamlLanguage;
import com.github.getjackx.intellij.plugins.ansible.psi.NeonArray;
import com.github.getjackx.intellij.plugins.ansible.psi.NeonReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLScalar;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class AnsibleReferenceContributor extends PsiReferenceContributor {
    public static PsiElementPattern.Capture<NeonReference> jinjaRefPattern() {
        return psiElement(NeonReference.class)
                .inside(NeonJinja.class)
                .withLanguage(YamlLanguage.INSTANCE);
    }

    // { role: ROLE }         OR
    // roles:
    //   -- ROLE
    public static PsiElementPattern.Capture<YAMLScalar> roleRefPattern() {
        return psiElement(YAMLScalar.class)
                .andOr(
                        psiElement().afterSibling(PlatformPatterns.psiElement(YAMLKeyValue.class).withText("role")),
                        psiElement().withSuperParent(2,
                                PlatformPatterns.psiElement(NeonArray.class).afterSibling(psiElement(YAMLKeyValue.class).withText("roles"))))
                .withLanguage(YamlLanguage.INSTANCE);
    }

    public static PsiElementPattern.Capture<YAMLScalar> srcRefPattern() {
        return psiElement(YAMLScalar.class)
                .afterSibling(psiElement(YAMLKeyValue.class).andOr(psiElement().withText("src"), psiElement().withText("include"), psiElement().withText("include_vars")))
                .withLanguage(YamlLanguage.INSTANCE);
    }

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(jinjaRefPattern(),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                        String text = element.getText();
                        if (text != null) {
                            return new PsiReference[]{new AnsibleVariableReference(element, new TextRange(0, text.length()))};
                        }
                        return AnsibleVariableReference.EMPTY_ARRAY;
                    }
                });

        registrar.registerReferenceProvider(roleRefPattern(),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                        String text = element.getText();
                        if (text != null) {
                            return new PsiReference[]{new AnsibleRoleReference(element, new TextRange(0, text.length()))};
                        }
                        return AnsibleVariableReference.EMPTY_ARRAY;
                    }
                });

        registrar.registerReferenceProvider(srcRefPattern(),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                        String text = element.getText();
                        if (text != null) {
                            return new PsiReference[]{new AnsibleFileReference(element, new TextRange(0, text.length()))};
                        }
                        return AnsibleVariableReference.EMPTY_ARRAY;
                    }
                });
    }
}
