package com.pungwe.cms.core.form;

import org.springframework.validation.Errors;

/**
 * Created by ian on 08/01/2016.
 */
public interface FormState<T> {

    T getFormData();

    void setFormData(T data);

    Errors getErrors();

    void setErrors(Errors errors);

    boolean needsRebuild();

    void setRebuild(boolean rebuild);

}
