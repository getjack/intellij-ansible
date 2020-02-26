package com.github.getjackx.intellij.plugins.ansible.psi;

import com.intellij.psi.PsiNamedElement;

/**
 * Entity - identifier with arguments
 */
public interface NeonEntity extends NeonValue, PsiNamedElement {
    NeonArray getArgs();
}
