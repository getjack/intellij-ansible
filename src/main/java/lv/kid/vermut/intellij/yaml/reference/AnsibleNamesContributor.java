package lv.kid.vermut.intellij.yaml.reference;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.util.ArrayUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLValue;

import java.util.ArrayList;
import java.util.List;

public class AnsibleNamesContributor implements ChooseByNameContributor {

    @NotNull
    @Override
    public String[] getNames(Project project, boolean includeNonProjectItems) {
        List<YAMLKeyValue> names = AnsibleUtil.findNames(project);
        List<String> result = new ArrayList<>(names.size());

        result.addAll(AnsibleUtil.findRoleNames(project, AnsibleUtil.ALL));

        for (YAMLKeyValue property : names) {
            if (property.getValueText() != null && property.getValueText().length() > 0) {
                result.add(property.getValueText());
            }
        }
        return ArrayUtil.toStringArray(result);
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String key, String pattern, Project project, boolean includeNonProjectItems) {
        List<YAMLValue> names = AnsibleUtil.findNames(project, key);
        List<PsiFile> roles = AnsibleUtil.findRoles(project, key);

        List<NavigationItem> result = new ArrayList<>(names.size() + roles.size());
        result.addAll(roles);
        result.addAll(names);
        return result.toArray(new NavigationItem[0]);
    }
}
