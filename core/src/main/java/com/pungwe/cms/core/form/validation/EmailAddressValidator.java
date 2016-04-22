package com.pungwe.cms.core.form.validation;

import com.pungwe.cms.core.form.ElementValidator;
import com.pungwe.cms.core.form.element.AbstractFormRenderedElement;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.validation.Errors;

import static com.pungwe.cms.core.utils.Utils.translate;

/**
 * Created by 917903 on 22/04/2016.
 */
public class EmailAddressValidator implements ElementValidator {

    @Override
    public <T extends AbstractFormRenderedElement> void validate(T element, Errors errors, int delta) {
        String value = (String)element.getValueOrDefaultValue();
        if (value == null) {
            return;
        }

        // Use the hibernate validator...
        boolean valid = new EmailValidator().isValid(value, null);

        if (!valid) {
            errors.rejectValue(element.getElementName(), "email.invalid", translate("Please provide a valid email address"));
        }
    }
}
