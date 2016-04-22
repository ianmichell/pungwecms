package com.pungwe.cms.core.form.validation;

import com.pungwe.cms.core.form.ElementValidator;
import com.pungwe.cms.core.form.element.AbstractFormRenderedElement;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;

import static com.pungwe.cms.core.utils.Utils.translate;

/**
 * Created by 917903 on 22/04/2016.
 */
public class NumberValidator implements ElementValidator {

    @Override
    public <T extends AbstractFormRenderedElement> void validate(T element, Errors errors, int delta) {
        if (StringUtils.isNotBlank((String)element.getValue())) {
            try {
                Long.valueOf((String)element.getValue());
            } catch (NumberFormatException ex) {
                errors.rejectValue(element.getElementName(), "invalid.number", translate("Please provide a valid number"));
            }
        }
    }
}
