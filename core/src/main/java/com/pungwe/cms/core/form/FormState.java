package com.pungwe.cms.core.form;

import org.springframework.beans.AbstractNestablePropertyAccessor;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.NotWritablePropertyException;
import org.springframework.validation.Errors;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ian on 08/01/2016.
 */
public class FormState {

	protected Map<String, Object> formData;
	protected Errors errors;
	protected boolean rebuild;

	public Map<String, Object> getFormData() {
		if (formData == null) {
			formData = new HashMap<>();
		}
		return formData;
	}

	public void setFormData(Map<String, Object> formData) {
		this.formData = formData;
	}

	public Errors getErrors() {
		return errors;
	}

	public void setErrors(Errors errors) {
		this.errors = errors;
	}

	public boolean isRebuild() {
		return rebuild;
	}

	public void setRebuild(boolean rebuild) {
		this.rebuild = rebuild;
	}

}
