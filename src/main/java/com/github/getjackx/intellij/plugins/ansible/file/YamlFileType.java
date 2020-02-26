package com.github.getjackx.intellij.plugins.ansible.file;

import com.github.getjackx.intellij.plugins.ansible.Yaml;
import com.github.getjackx.intellij.plugins.ansible.YamlIcons;
import com.github.getjackx.intellij.plugins.ansible.YamlLanguage;
import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class YamlFileType extends LanguageFileType {
    public static final YamlFileType INSTANCE = new YamlFileType();
    public static final String DEFAULT_EXTENSION = "main.yml";

    protected YamlFileType() {
        super(YamlLanguage.INSTANCE);
    }

    @NotNull
    public String getName() {
        return Yaml.LANGUAGE_NAME;
    }

    @NotNull
    public String getDescription() {
        return Yaml.LANGUAGE_DESCRIPTION;
    }

    @NotNull
    public String getDefaultExtension() {
        return DEFAULT_EXTENSION;
    }

    @NotNull
    public Icon getIcon() {
        return YamlIcons.FILETYPE_ICON;
    }
}

