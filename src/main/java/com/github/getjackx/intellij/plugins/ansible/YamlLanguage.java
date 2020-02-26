package com.github.getjackx.intellij.plugins.ansible;

import com.intellij.lang.Language;
import org.jetbrains.annotations.NotNull;

public class YamlLanguage extends Language {

    public static final YamlLanguage INSTANCE = new YamlLanguage();
    public static final String MIME_TYPE = "application/x-yaml";
    public static final String MIME_TYPE2 = "application/yaml";

    public YamlLanguage() {
        super("yamlEx", MIME_TYPE, MIME_TYPE2);
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "YAML/Ansible";
    }
}