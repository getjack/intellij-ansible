package com.github.getjackx.intellij.plugins.ansible.reference;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileTypes.FileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.indexing.FileBasedIndex;
import com.github.getjackx.intellij.plugins.ansible.file.YamlFileType;
import org.jetbrains.yaml.psi.YAMLFile;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.PatternSyntaxException;

public class AnsibleUtil {
    public static final String ALL = ".*";
    private static final Logger log = Logger.getInstance(AnsibleUtil.class);

    public static List<String> findRoleNames(final Project project, final String key) {
        final List<String> result = new ArrayList<>();
        final Collection<PsiFile> virtualFiles = findFiles(project, "/" + key + "/tasks/main.yml$");
        for (final PsiFile virtualFile : virtualFiles) {
            result.add(virtualFile.getParent().getParent().getName());
            result.add(virtualFile.getParent().getParent().getParent().getName());
        }
        return result;
    }

    public static List<PsiFile> findRoles(final Project project, final String key) {
        // Need to find also variants like role/subrole/tasks/main.yml
        return findFiles(project, "/" + key + ".*/tasks/main.yml$");
    }

    public static List<YAMLKeyValue> findAllProperties(final Project project) {
        return searchKeyPairs(project, null, null);

    }

    public static List<PsiElement> findAllProperties(final Project project, final String key) {
        final List<YAMLKeyValue> names = searchKeyPairs(project, key, null);
        final List<PsiElement> result = new ArrayList<>();
        for (final YAMLKeyValue name : names) {
            result.add(name.getKey());
        }
        return result;
    }

    public static List<YAMLKeyValue> findNames(final Project project) {
        return searchKeyPairs(project, "name", null);
    }

    public static List<YAMLValue> findNames(final Project project, final String key) {
        final List<YAMLKeyValue> names = searchKeyPairs(project, "name", key);
        final List<YAMLValue> result = new ArrayList<>();
        for (final YAMLKeyValue name : names) {
            result.add(name.getValue());
        }
        return result;
    }

    public static List<PsiFile> findFiles(final Project project, final String pattern) {
        final List<PsiFile> result = new ArrayList<>();
        try {
            Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, YamlFileType.INSTANCE, GlobalSearchScope.allScope(project));
            for (final VirtualFile virtualFile : virtualFiles) {
                if (virtualFile.getCanonicalPath() != null && virtualFile.getCanonicalPath().matches(".*" + pattern)) {
                    result.add(PsiManager.getInstance(project).findFile(virtualFile));
                }
            }

            // Include .j2 - plaintexts
            virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, FileTypes.PLAIN_TEXT, GlobalSearchScope.allScope(project));
            for (final VirtualFile virtualFile : virtualFiles) {
                if (virtualFile.getCanonicalPath() != null && virtualFile.getCanonicalPath().matches(".*" + pattern)) {
                    result.add(PsiManager.getInstance(project).findFile(virtualFile));
                }
            }
        } catch (final PatternSyntaxException ignored) {
            log.warn(ignored.getMessage());
        }
        return result;
    }

    private static List<YAMLKeyValue> searchKeyPairs(final Project project, final String key, final String value) {
        final List<YAMLKeyValue> result = new ArrayList<>();
        final Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, YamlFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (final VirtualFile virtualFile : virtualFiles) {
            final YAMLFile yamlFile = (YAMLFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (yamlFile != null) {
                final Collection<YAMLKeyValue> properties = PsiTreeUtil.findChildrenOfType(yamlFile, YAMLKeyValue.class);
                if (!properties.isEmpty()) {
                    if (key == null) {
                        result.addAll(properties);
                    } else {
                        for (final YAMLKeyValue property : properties) {
                            if (key.equals(property.getKeyText())) {
                                if (value == null) {
                                    result.add(property);
                                } else if (value.equals(property.getValueText())) {
                                    result.add(property);
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }
}
