package com.pungwe.cms.core.form;

import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.form.element.FormElement;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by ian on 08/01/2016.
 */
public interface Form {

	String getFormId();

	void build(FormElement element);

	void validate(FormElement form, Errors errors);
}
