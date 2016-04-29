package com.pungwe.cms.core.form;

import com.pungwe.cms.core.form.element.AbstractFormRenderedElement;
import com.pungwe.cms.core.form.element.FormElement;
import org.springframework.validation.Errors;

/**
 * Created by ian on 09/01/2016.
 */
public interface ElementValidator {

	void validate(FormRenderedElement element);

}
