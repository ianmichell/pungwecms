package com.pungwe.cms.core.form;

import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.form.element.LabelElement;

/**
 * Created by ian on 18/03/2016.
 */
public interface FormRenderedElement<T> extends RenderedElement {

	String getName();
	void setName(String name);

	LabelElement getLabel();
	void setLabel(LabelElement labelElement);

	int getDelta();
	void setDelta(int delta);

	T getDefaultValue();
	void setDefaultValue(T defaultValue);

	T getValue();
	void setValue(T value);

	boolean isRequired();
	void setRequired(boolean required);

	boolean hasError();
	void setError(boolean error);
}
