package com.github.getjackx.intellij.plugins.ansible;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.github.getjackx.intellij.plugins.ansible.lexer.NeonTokenTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class YamlBraceMatcher implements PairedBraceMatcher, NeonTokenTypes {
    private static final BracePair[] PAIRS = {
            new BracePair(NEON_LBRACE_JINJA, NEON_RBRACE_JINJA, true), // {{ }}
            new BracePair(NEON_LBRACE_CURLY, NEON_RBRACE_CURLY, true),  // {}
            new BracePair(NEON_LBRACE_SQUARE, NEON_RBRACE_SQUARE, true), // []
    };

    @Override
    public BracePair[] getPairs() {
        return PAIRS;
    }

    @Override
    public boolean isPairedBracesAllowedBeforeType(@NotNull final IElementType iElementType, @Nullable final IElementType iElementType1) {
        return true;
    }

    @Override
    public int getCodeConstructStart(final PsiFile psiFile, final int openingBraceOffset) {
        return openingBraceOffset;
    }
}
