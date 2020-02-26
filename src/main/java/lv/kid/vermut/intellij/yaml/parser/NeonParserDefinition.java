package lv.kid.vermut.intellij.yaml.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import lv.kid.vermut.intellij.yaml.psi.impl.NeonFileImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.YAMLParserDefinition;
import org.jetbrains.yaml.YAMLTokenTypes;
import org.jetbrains.yaml.lexer.YAMLFlexLexer;
import org.jetbrains.yaml.parser.YAMLParser;

public class NeonParserDefinition implements ParserDefinition {
	private static final TokenSet myCommentTokens = TokenSet.create(YAMLTokenTypes.COMMENT);

	@NotNull
	@Override
	public Lexer createLexer(Project project) {
		return new YAMLFlexLexer();
	}

	@Override
	public PsiParser createParser(Project project) {
		return new YAMLParser();
	}

	@Override
	public IFileElementType getFileNodeType() {
		return NeonElementTypes.FILE;
	}

	@Override
	@NotNull
	public TokenSet getWhitespaceTokens() {
		return TokenSet.create(YAMLTokenTypes.WHITESPACE);
	}

	@Override
	@NotNull
	public TokenSet getCommentTokens() {
		return myCommentTokens;
	}

	@Override
	@NotNull
	public TokenSet getStringLiteralElements() {
		return TokenSet.create(YAMLTokenTypes.SCALAR_STRING, YAMLTokenTypes.SCALAR_DSTRING, YAMLTokenTypes.TEXT);
	}

	@NotNull
	@Override
	public PsiElement createElement(final ASTNode node) {
		final YAMLParserDefinition yamlParserDefinition = new YAMLParserDefinition();
		return yamlParserDefinition.createElement(node);
	}

	@Override
	public PsiFile createFile(final FileViewProvider viewProvider) {
		return new NeonFileImpl(viewProvider);
	}

	@Override
	public SpaceRequirements spaceExistenceTypeBetweenTokens(final ASTNode left, final ASTNode right) {
		return SpaceRequirements.MAY;
	}
}
