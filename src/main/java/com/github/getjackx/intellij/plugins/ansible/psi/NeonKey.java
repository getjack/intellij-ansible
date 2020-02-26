package com.github.getjackx.intellij.plugins.ansible.psi;

import com.intellij.navigation.ItemPresentation;

/**
 * Key from key-value pair
 */
public interface NeonKey extends NeonPsiElement {
    String getKeyText();

    ItemPresentation getPresentation();
}
