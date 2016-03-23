package com.pungwe.cms.core.entity.controller;

import com.pungwe.cms.core.form.controller.AbstractFormController;
import com.pungwe.cms.core.form.element.FormElement;
import org.springframework.validation.Errors;

/**
 * Created by ian on 22/03/2016.
 */
public abstract class AbstractEntityTypeFieldController extends AbstractFormController {

	@Override
	public void build(FormElement element) {

	}

	@Override
	public void validate(FormElement form, Errors errors) {

	}
}
