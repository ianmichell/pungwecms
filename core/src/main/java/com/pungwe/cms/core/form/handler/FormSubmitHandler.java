package com.pungwe.cms.core.form.handler;

import com.pungwe.cms.core.form.element.FormElement;

import java.util.Map;

/**
 * Created by ian on 20/03/2016.
 */
public interface FormSubmitHandler<T> {

	void submit(FormElement<T> form, Map<String, Object> variables);

}
