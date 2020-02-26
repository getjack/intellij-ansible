package com.github.getjackx.intellij.plugins.ansible.editor;

import com.github.getjackx.intellij.plugins.ansible.parser.NeonElementTypes;
import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilder;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.github.getjackx.intellij.plugins.ansible.lexer.NeonTokenTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;

/**
 * Fold sections in Yaml
 */
public class NeonFoldingBuilder implements FoldingBuilder, NeonTokenTypes {
    private static final TokenSet COMPOUND_VALUE = TokenSet.create(
            NeonElementTypes.COMPOUND_VALUE,
            NeonElementTypes.HASH
    );

    private static void collectDescriptors(@NotNull ASTNode node, @NotNull final List<FoldingDescriptor> descriptors) {
        final IElementType type = node.getElementType();
        final TextRange nodeTextRange = node.getTextRange();
        if ((!StringUtil.isEmptyOrSpaces(node.getText())) && (nodeTextRange.getLength() >= 2)) {
            if (type == NeonElementTypes.KEY_VALUE_PAIR) {
                final ASTNode[] children = node.getChildren(COMPOUND_VALUE);

                if ((children.length > 0) && (!StringUtil.isEmpty(children[0].getText().trim()))) {
                    descriptors.add(new FoldingDescriptor(node, nodeTextRange));
                }
            }
            if (type == NeonElementTypes.SCALAR_VALUE) {
                descriptors.add(new FoldingDescriptor(node, nodeTextRange));
            }
        }
        for (final ASTNode child : node.getChildren(null)) {
            collectDescriptors(child, descriptors);
        }
    }

    @NotNull
    public FoldingDescriptor[] buildFoldRegions(@NotNull final ASTNode astNode, @NotNull final Document document) {
        final List<FoldingDescriptor> descriptors = new LinkedList<>();
        collectDescriptors(astNode, descriptors);
        return descriptors.toArray(new FoldingDescriptor[0]);
    }

    @Nullable
    public String getPlaceholderText(@NotNull final ASTNode node) {
        final IElementType type = node.getElementType();
        if (type == NeonElementTypes.KEY_VALUE_PAIR) {
            return node.getFirstChildNode().getText();
        }
        if (type == NeonElementTypes.SCALAR_VALUE) {
            return node.getText().substring(0, 1);
        }
        return "...";
    }

    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        return false;
    }
}
