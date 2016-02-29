package com.pungwe.cms.core.form.element;

import com.pungwe.cms.core.annotations.ThemeInfo;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ian on 09/01/2016.
 */
@ThemeInfo("form/select")
public class SingleSelectListElement extends AbstractFormElement<String> {

	protected Map<String, String> options;

	public Map<String, String> getOptions() {
		if (options == null) {
			options = new LinkedHashMap<>();
		}
		return options;
	}

	@ModelAttribute("options")
	public Set<Map.Entry<String, String>> getOptionsSet() {
		return options.entrySet();
	}

	public void addOption(String label, String value) {
		this.getOptions().put(label, value);
	}

	@Override
	@ModelAttribute("attributes")
	public String getAttributesAsString() {
		// Setup value attribute
		if (getAttributes() != null && !getAttributes().isEmpty()) {
			return " " + getAttributes().entrySet().stream().filter(e -> e.getKey() != "value").map(e -> e.getKey() + "=\"" + e.getValue() + "\"").collect(Collectors.joining(" "));
		}
		return "";
	}
}
