package com.github.getjackx.intellij.plugins.ansible.reference;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.util.ArrayUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.psi.YAMLKeyValue;

import java.util.ArrayList;
import java.util.List;

public class AnsiblePropertiesContributor implements ChooseByNameContributor {
    @NotNull
    @Override
    public String[] getNames(Project project, boolean includeNonProjectItems) {
        List<YAMLKeyValue> properties = AnsibleUtil.findAllProperties(project);
        List<String> names = new ArrayList<>(properties.size());
        for (YAMLKeyValue property : properties) {
            if (property.getKeyText() != null && property.getKeyText().length() > 0) {
                names.add(property.getKeyText());
            }
        }
        return ArrayUtil.toStringArray(names);
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean includeNonProjectItems) {
        // todo include non project items
        List<PsiElement> properties = AnsibleUtil.findAllProperties(project, name);
        return properties.toArray(new NavigationItem[0]);
    }
}
