package lv.kid.vermut.intellij.yaml.reference;

import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.yaml.psi.YAMLKeyValue;

import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;

import static com.intellij.patterns.PlatformPatterns.psiElement;
import static lv.kid.vermut.intellij.yaml.reference.AnsibleReferenceContributor.jinjaRefPattern;

public class AnsibleVariableValuesDocumentationProvider extends AbstractDocumentationProvider {
    // TODO: Provide as project configuration
    private final String ANSIBLE_VERSION = "latest";

    @Nullable
    public String getQuickNavigateInfo(final PsiElement element, final PsiElement originalElement) {
        if (jinjaRefPattern().accepts(originalElement)) {
            // Find all values of jinja var
            final List<PsiElement> properties = AnsibleUtil.findAllProperties(originalElement.getProject(), originalElement.getText());
            final StringBuilder result = new StringBuilder();

            for (final PsiElement yamlKeyValue : properties) {
                if (yamlKeyValue.getParent() instanceof YAMLKeyValue) {
                    final YAMLKeyValue keyValPair = (YAMLKeyValue) yamlKeyValue.getParent();
                    result.append(keyValPair.getValueText()).append("<br>");
                }
            }

            return result.toString();
        }

        // Try documentation lookup for key
        if (psiElement(YAMLKeyValue.class).accepts(originalElement)) {
            final String url = MessageFormat.format("https://docs.ansible.com/ansible/" + ANSIBLE_VERSION + "/modules/{0}_module.html", ((YAMLKeyValue) originalElement).getKeyText());
            try {
                final String pageData = new Scanner(new URL(url).openStream(), "UTF-8").useDelimiter("\\A").next();
                return pageData.substring(pageData.indexOf("<div role=\"main\" class=\"document\" itemscope=\"itemscope\" itemtype=\"http://schema.org/Article\">"));
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }

    @Override
    public String generateDoc(final PsiElement element, @Nullable final PsiElement originalElement) {
        return getQuickNavigateInfo(element, element);
    }
}
