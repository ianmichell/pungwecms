package com.pungwe.cms.core.element;

import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by ian on 09/01/2016.
 */
public interface RenderedElement {

	String getHtmlId();

	void setHtmlId(String htmlId);

	String getName();

	void setName(String name);

	int getWeight();

	void setWeight(int weight);

	Map<String, String> getAttributes();

	void setAttributes(Map<String, String> attributes);

	String getTheme();

	default String getAttribute(String name) {
		return getAttributes() == null ? null : getAttributes().get(name);
	}

	@ModelAttribute("attributes")
	default String getAttributesAsString() {

		if (getAttributes() != null) {
			return " " + getAttributes().entrySet().stream().map(e -> e.getKey() + "=\"" + e.getValue() + "\"").collect(Collectors.joining(" "));
		}

		return "";
	}
}
