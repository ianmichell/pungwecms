package com.pungwe.cms.core.form.element;

import com.pungwe.cms.core.annotations.ui.ThemeInfo;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.*;

/**
 * Created by ian on 09/01/2016.
 */
@ThemeInfo("form/select")
public class SingleSelectListElement extends AbstractFormRenderedElement<String> {

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
		this.getOptions().putIfAbsent(value, label);
	}
}
