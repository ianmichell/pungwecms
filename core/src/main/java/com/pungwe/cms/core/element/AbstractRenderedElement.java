package com.pungwe.cms.core.element;

import com.pungwe.cms.core.form.element.AbstractFormElement;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ian on 09/01/2016.
 */
public abstract class AbstractRenderedElement implements RenderedElement {

	protected int weight;
	// FIXME: Should this be here?
	protected Map<String, String> attributes;

	@Override
	public String getHtmlId() {
		return getAttribute("id");
	}

	@Override
	public void setHtmlId(String htmlId) {
		addAttribute("id", htmlId);
	}

	@Override
	public int getWeight() {
		return weight;
	}

	@Override
	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public Map<String, String> getAttributes() {
		if (attributes == null) {
			attributes = new LinkedHashMap<>();
		}
		return attributes;
	}

	@Override
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public String getAttribute(String name) {
		return getAttributes().get(name);
	}

	public void addAttribute(String attribute, String value) {
		getAttributes().put(attribute, value);
	}

	protected abstract Collection<String> excludedAttributes();

	@ModelAttribute("attributes")
	public String getAttributesAsString() {
		String attributes = "";
		if (!getAttributes().isEmpty()) {
			attributes = " " + getAttributes().entrySet().stream().filter(e -> !excludedAttributes().contains(e.getKey())).map(e -> e.getKey() + "=\"" + e.getValue() + "\"").collect(Collectors.joining(" "));
		}
		return attributes;
	}
}
