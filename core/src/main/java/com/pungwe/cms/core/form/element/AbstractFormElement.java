package com.pungwe.cms.core.form.element;

import com.pungwe.cms.core.element.AbstractRenderedElement;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.stream.Collectors;

/**
 * Created by ian on 09/01/2016.
 */
public abstract class AbstractFormElement<T> extends AbstractRenderedElement {

	protected LabelElement label;
	protected T defaultValue;
	protected int detla;
	protected boolean required;

	// FIXME: We should change this slightly, so that it builds up based on delta
	public String getName() {
		return getAttribute("name");
	}

	public void setName(String name) {
		addAttribute("name", name);
	}

	@ModelAttribute("label")
	public LabelElement getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = new LabelElement(label);
	}

	@ModelAttribute("defaultValue")
	public T getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(T defaultValue) {
		this.defaultValue = defaultValue;
	}

	@ModelAttribute("delta")
	public int getDetla() {
		return detla;
	}

	public void setDetla(int detla) {
		this.detla = detla;
	}

	@ModelAttribute("required")
	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	@Override
	@ModelAttribute("attributes")
	public String getAttributesAsString() {
		// Setup value attribute
		if (getAttributes() != null && !getAttributes().containsKey("value")) {
			return " value=\"" + (getDefaultValue() != null ? getDefaultValue() : "") + "\" " + getAttributes().entrySet().stream().map(e -> e.getKey() + "=\"" + e.getValue() + "\"").collect(Collectors.joining(" "));
		}
		if (getAttributes() != null && !getAttributes().isEmpty()) {
			return " " + getAttributes().entrySet().stream().map(e -> e.getKey() + "=\"" + e.getValue() + "\"").collect(Collectors.joining(" "));
		}

		return "";
	}
}
