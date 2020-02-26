package lv.kid.vermut.intellij.yaml.reference;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import lv.kid.vermut.intellij.yaml.YamlIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.yaml.psi.YAMLKeyValue;

import java.util.ArrayList;
import java.util.List;

public class AnsibleVariableReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
    private String key;

    public AnsibleVariableReference(final PsiElement element, final TextRange rangeInElement) {
        super(element, rangeInElement);
        key = element.getText(); // .substring(rangeInElement.getStartOffset(), rangeInElement.getEndOffset());
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        final Project project = myElement.getProject();
        final List<PsiElement> properties = AnsibleUtil.findAllProperties(project, key);
        final List<ResolveResult> results = new ArrayList<>();
        for (final PsiElement property : properties) {
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
        final List<YAMLKeyValue> properties = AnsibleUtil.findAllProperties(project);
        final List<LookupElement> variants = new ArrayList<>();
        for (final YAMLKeyValue property : properties) {
            if (property.getKey() != null && property.getKeyText().length() > 0) {
                variants.add(LookupElementBuilder.create(property).
                        withIcon(YamlIcons.FILETYPE_ICON).
                        withTypeText(property.getContainingFile().getName())
                );
            }
        }
        return variants.toArray();
    }
}
