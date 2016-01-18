package com.pungwe.cms.core.form.element;

import com.pungwe.cms.core.element.AbstractRenderedElement;

/**
 * Created by ian on 09/01/2016.
 */
public abstract class AbstractFormElement<T> extends AbstractRenderedElement {

	protected String label;
	protected T defaultValue;
	protected int detla;
	protected boolean required;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public T getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(T defaultValue) {
		this.defaultValue = defaultValue;
	}

	public int getDetla() {
		return detla;
	}

	public void setDetla(int detla) {
		this.detla = detla;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}
}
