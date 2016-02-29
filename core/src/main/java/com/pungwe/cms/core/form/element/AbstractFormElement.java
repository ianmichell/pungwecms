package com.pungwe.cms.core.form.element;

import com.pungwe.cms.core.element.AbstractRenderedElement;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * Created by ian on 09/01/2016.
 */
public abstract class AbstractFormElement<T> extends AbstractRenderedElement {

	protected String name;
	protected LabelElement label;
	protected T defaultValue;
	protected T value;
	protected int detla;
	protected boolean required;

	// FIXME: We should change this slightly, so that it builds up based on delta
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ModelAttribute("name")
	public String getElementName() {
		return getName() + "[" + getDetla() + "]";
	}

	@ModelAttribute("label")
	public LabelElement getLabel() {
		return label;
	}

	public void setLabel(LabelElement label) {
		this.label = label;
		this.label.setForElement(this); // force this
	}

	public void setLabel(String label) {
		this.label = new LabelElement(label, this);
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

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	@ModelAttribute("value")
	public T getValueOrDefaultValue() {
		return getValue() != null ? getValue() : getDefaultValue();
	}

	@Override
	protected Collection<String> excludedAttributes() {
		return Arrays.asList("value", "name");
	}
}
