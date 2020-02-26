package com.github.getjackx.intellij.plugins.ansible.parser;

import com.intellij.psi.tree.IElementType;
import com.github.getjackx.intellij.plugins.ansible.YamlLanguage;
import org.jetbrains.annotations.NotNull;


public class NeonElementType extends IElementType {
    public NeonElementType(@NotNull String debugName) {
        super(debugName, YamlLanguage.INSTANCE);
    }

    public String toString() {
        return "[Yaml] " + super.toString();
    }
}
