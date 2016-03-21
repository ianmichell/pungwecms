package com.pungwe.cms.core.form;

import com.pungwe.cms.core.form.element.AbstractFormRenderedElement;
import org.springframework.validation.Errors;

/**
 * Created by ian on 09/01/2016.
 */
public interface ElementValidator {

	<T extends AbstractFormRenderedElement> void validate(T element, Errors errors, int delta);

}
