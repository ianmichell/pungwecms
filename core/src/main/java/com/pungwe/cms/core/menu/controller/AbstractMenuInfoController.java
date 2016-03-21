package com.pungwe.cms.core.menu.controller;

import com.pungwe.cms.core.form.controller.AbstractFormController;
import com.pungwe.cms.core.form.element.FormElement;
import com.pungwe.cms.core.form.element.InputButtonRenderedElement;
import com.pungwe.cms.core.form.element.SingleSelectListRenderedElement;
import com.pungwe.cms.core.form.element.StringRenderedElement;
import com.pungwe.cms.core.utils.Utils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestAttributes;

import java.util.List;
import java.util.Locale;

/**
 * Created by ian on 19/03/2016.
 */
public abstract class AbstractMenuInfoController extends AbstractFormController {

	@Override
	public void build(FormElement element) {
		StringRenderedElement title = new StringRenderedElement();
		title.setLabel("Title");
		title.setName("title");
		title.setPlaceholder("Menu Title");
		title.setSize(20);
		title.setRequired(true);

		StringRenderedElement description = new StringRenderedElement();
		description.setName("description");
		description.setPlaceholder("Administrative description");
		description.setSize(100);
		description.setLabel("Description");

		// Language is populated from LocaleResolver
		final SingleSelectListRenderedElement language = new SingleSelectListRenderedElement();
		language.setLabel("Language");
		language.setName("language");
		final Locale currentLocale = LocaleContextHolder.getLocale();
		List<Locale> locales = Utils.getSortedLocales(currentLocale);
		locales.stream().forEach(locale -> {
			language.addOption(locale.getDisplayName(currentLocale), locale.getLanguage());
		});
		language.setDefaultValue(currentLocale.getLanguage());
		// Create Form
		element.setMethod(RequestMethod.POST.name());
		element.addContent(title, description, language, new InputButtonRenderedElement(InputButtonRenderedElement.InputButtonType.SUBMIT, "Save"));

	}

	@Override
	public void validate(FormElement form, Errors errors) {
		if (StringUtils.isEmpty(form.getValue("title", 0)) && form.isRequiredField("title", 0)) {
			errors.rejectValue("fields[" + form.getFormFieldIndex("title", 0) + "].value", "empty_title", "Please provide a menu title");
			form.getField("title", 0).setError(true);
		}
		if (StringUtils.isEmpty(form.getValue("description", 0)) && form.isRequiredField("description", 0)) {
			errors.rejectValue("fields[" + form.getFormFieldIndex("description", 0) + "].value", "empty_description", "Please provide a description of your menu");
			form.getField("description", 0).setError(true);
		}
		if (StringUtils.isEmpty(form.getValue("language", 0)) && form.isRequiredField("language", 0)) {
			errors.rejectValue("fields[" + form.getFormFieldIndex("language", 0) + "].value", "empty_language", "Please select a language");
			form.getField("language", 0).setError(true);
		}
	}

	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(value = BindException.class)
	public void bindException() {
	}
}
