package com.pungwe.cms.core.form;

import com.pungwe.cms.core.element.Element;

/**
 * Created by ian on 08/01/2016.
 */
public interface Form<T> {

    String getFormId();

    void buildForm(Element form, FormState<T> formState);

    void validateForm(Element form, FormState<T> state);

    void submitForm(Element form, FormState<T> state);
}
