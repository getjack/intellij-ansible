package com.github.getjackx.intellij.plugins.ansible.psi;

import com.intellij.psi.PsiNamedElement;

/**
 * Key-value pair, part of NeonHash
 */
public interface NeonKeyValPair extends PsiNamedElement {
    // key
    NeonKey getKey();

    String getKeyText();

    // value
    NeonValue getValue();

    String getValueText();
}
