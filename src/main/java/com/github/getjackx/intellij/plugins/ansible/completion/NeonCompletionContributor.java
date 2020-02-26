package com.github.getjackx.intellij.plugins.ansible.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.StandardPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import com.github.getjackx.intellij.plugins.ansible.reference.AnsibleReferenceContributor;
import com.github.getjackx.intellij.plugins.ansible.reference.AnsibleUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NeonCompletionContributor extends CompletionContributor {
    public NeonCompletionContributor() {
        extend(CompletionType.BASIC, StandardPatterns.instanceOf(PsiElement.class), new ServiceCompletionProvider());

        extend(CompletionType.BASIC, AnsibleReferenceContributor.roleRefPattern(),
                new CompletionProvider<CompletionParameters>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet result) {
                        final List<String> names = AnsibleUtil.findRoleNames(parameters.getPosition().getProject(), AnsibleUtil.ALL);
                        for (final String name : names) {
                            result.addElement(LookupElementBuilder.create(name));
                        }
                    }
                });

    }
}
