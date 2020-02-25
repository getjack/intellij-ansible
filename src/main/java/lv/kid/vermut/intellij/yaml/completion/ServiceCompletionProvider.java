package lv.kid.vermut.intellij.yaml.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import lv.kid.vermut.intellij.yaml.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Complete keywords
 */
public class ServiceCompletionProvider extends CompletionProvider<CompletionParameters> {
	// current element
	PsiElement curr;

	public ServiceCompletionProvider() {
		super();
	}

	@Override
	protected void addCompletions(@NotNull CompletionParameters params,
	                              ProcessingContext ctx,
	                              @NotNull CompletionResultSet results) {
		curr = params.getPosition().getOriginalElement();
		if (curr.getParent() instanceof NeonReference) {
			for (String service : getAvailableServices()) {
				results.addElement( LookupElementBuilder.create(service) );
			}
		}
	}


	/**
	 * Find all available services
	 */
	private List<String> getAvailableServices() {
		List<String> services = new LinkedList<>();

		if (curr.getContainingFile() instanceof NeonFile) getServicesFromNeonFile(services, (NeonFile) curr.getContainingFile());

		return services;
	}


	/**
	 *
	 */
	private void getServicesFromNeonFile(List<String> result, NeonFile file) {
		for(NeonSection section: file.getSections().values()) {
			// without sections, i.e. the section is actually an extension
			if (section.getName().equals("services") && (section.getValue() instanceof NeonArray)) addServiceFromNeonArray(result, (NeonArray) section.getValue());

			if (section.getValue() instanceof NeonArray) {
				HashMap<String,NeonValue> map = ((NeonArray) section.getValue()).getMap();
				if (map.containsKey("services")) addServiceFromNeonArray(result, (NeonArray) map.get("services"));
			}
		}
	}

	private void addServiceFromNeonArray(List<String> result, NeonArray hash) {
		for (NeonKey key: hash.getKeys()) {
			result.add( key.getKeyText() );
		}
	}

}
