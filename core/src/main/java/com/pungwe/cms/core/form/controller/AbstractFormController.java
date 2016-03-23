package com.pungwe.cms.core.form.controller;

import com.pungwe.cms.core.form.Form;
import com.pungwe.cms.core.form.FormRenderedElement;
import com.pungwe.cms.core.form.FormState;
import com.pungwe.cms.core.form.element.FormElement;
import com.pungwe.cms.core.utils.services.HookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ian on 19/03/2016.
 */
public abstract class AbstractFormController implements Form {

	@Autowired
	protected HookService hookService;

	@ModelAttribute("form")
	public FormElement buildForm() throws InvocationTargetException, IllegalAccessException {
		Assert.hasText(getFormId());
		FormElement element = new FormElement();
		element.setName(getFormId());
		build(element);
		hookService.executeHook("form_alter", getFormId(), element);
		return element;
	}

	protected abstract void buildInternal(FormElement element);

	@InitBinder("form")
	public void initBinder(final WebDataBinder webDataBinder) {
		webDataBinder.setValidator(new Validator() {
			@Override
			public boolean supports(Class<?> clazz) {
				return FormElement.class.isAssignableFrom(clazz);
			}

			@Override
			public void validate(Object target, Errors errors) {
				AbstractFormController.this.validate(((FormElement)target), errors);
			}
		});
	}
}
