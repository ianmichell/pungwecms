package com.pungwe.cms.core.element;

import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ian on 09/01/2016.
 */
public abstract class AbstractRenderedElement implements RenderedElement {

	protected boolean wrapped = false;
	protected boolean visible = true;

	protected int weight;
	// FIXME: Should this be here?
	protected Map<String, String> attributes;

	protected List<String> classes;

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
	public boolean isWrapped() {
		return wrapped;
	}

	@Override
	public void setWrapped(boolean wrapped) {
		this.wrapped = wrapped;
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

	@Override
	public String getAttribute(String name, String defaultValue) {
		return getAttributes().getOrDefault(name, defaultValue);
	}

	public void addAttribute(String attribute, String value) {
		getAttributes().put(attribute, value);
	}

	protected abstract Collection<String> excludedAttributes();

	@ModelAttribute("attributes")
	public String getAttributesAsString() {
		String attributes = "";
		if (!getAttributes().isEmpty()) {
			attributes = " " + getAttributes().entrySet().stream().filter(e -> !excludedAttributes().contains(e.getKey()) || e.getKey().equals("class")).map(e -> e.getKey() + "=\"" + e.getValue() + "\"").collect(Collectors.joining(" "));
		}
		return attributes;
	}

	@Override
	@ModelAttribute("classes")
	public List<String> getClasses() {
		if (classes == null) {
			classes = new ArrayList<>();
		}
		return classes;
	}

	@Override
	public void setClasses(List<String> classes) {
		if (classes == null) {
			this.classes = null;
			return;
		}
		List<String> copyOfClasses = new ArrayList<>();
		copyOfClasses.addAll(classes);
		this.classes = copyOfClasses;
	}

	@Override
	@ModelAttribute("element_visible")
	public boolean isVisible() {
		return visible;
	}

	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
