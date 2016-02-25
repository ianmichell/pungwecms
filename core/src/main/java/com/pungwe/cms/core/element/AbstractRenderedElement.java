package com.pungwe.cms.core.element;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ian on 09/01/2016.
 */
public abstract class AbstractRenderedElement implements RenderedElement {

	protected String name;
	protected int weight;
	// FIXME: Should this be here?
	protected Map<String, String> attributes;
	protected String theme;

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
			attributes = new HashMap<>();
		}
		return attributes;
	}

	@Override
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public void addAttribute(String attribute, String value) {
		getAttributes().put(attribute, value);
	}

}
