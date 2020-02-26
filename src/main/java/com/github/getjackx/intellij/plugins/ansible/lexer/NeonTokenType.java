package com.github.getjackx.intellij.plugins.ansible.lexer;

import com.github.getjackx.intellij.plugins.ansible.YamlLanguage;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;


public class NeonTokenType extends IElementType {
    public NeonTokenType(@NotNull String debugName) {
        super(debugName, YamlLanguage.INSTANCE);
    }

    public String toString() {
        return "[Yaml] " + super.toString();
    }
}
