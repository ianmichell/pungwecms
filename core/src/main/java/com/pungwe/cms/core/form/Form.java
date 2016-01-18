package com.pungwe.cms.core.form;

import com.pungwe.cms.core.element.RenderedElement;

import java.util.List;

/**
 * Created by ian on 08/01/2016.
 */
public interface Form<T> {

	String getFormId();

	void buildForm(List<RenderedElement> form, FormState<T> formState);

	void validateForm(List<RenderedElement> form, FormState<T> state);

	void submitForm(List<RenderedElement> form, FormState<T> state);
}
