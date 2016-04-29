package com.pungwe.cms.core.form;

import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.form.element.FormElement;
import com.pungwe.cms.core.form.element.LabelElement;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

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
	List<String> getErrors();
	void addError(String... error);

    List<ElementValidator> getValidators();
    void setValidators(List<ElementValidator> validators);
    void addValidator(ElementValidator... validators);

	void validate();
}
