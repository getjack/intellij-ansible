package lv.kid.vermut.intellij.yaml.reference;

import com.intellij.codeInsight.completion.CompletionUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AnsibleRoleReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
    private String key;

    public AnsibleRoleReference(final PsiElement element, final TextRange rangeInElement) {
        super(element, rangeInElement);
        key = element.getText(); // .substring(rangeInElement.getStartOffset(), rangeInElement.getEndOffset());

        // It should work another way, but I can't figure out how and why
        if (key.endsWith(CompletionUtilCore.DUMMY_IDENTIFIER_TRIMMED))
            key = key.substring(0, key.length() - CompletionUtilCore.DUMMY_IDENTIFIER_TRIMMED.length());
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(final boolean incompleteCode) {
        final Project project = myElement.getProject();
        final List<PsiFile> properties = AnsibleUtil.findRoles(project, key);
        final List<ResolveResult> results = new ArrayList<>();
        for (final PsiFile property : properties) {
            results.add(new PsiElementResolveResult(property));
        }
        return results.toArray(new ResolveResult[0]);
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        final ResolveResult[] resolveResults = multiResolve(false);
        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        final Project project = myElement.getProject();
        return AnsibleUtil.findRoleNames(project, key + ".*").toArray();
    }
}
