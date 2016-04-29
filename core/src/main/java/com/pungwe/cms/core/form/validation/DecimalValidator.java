package com.pungwe.cms.core.form.validation;

import com.pungwe.cms.core.form.ElementValidator;
import com.pungwe.cms.core.form.FormRenderedElement;
import com.pungwe.cms.core.form.element.AbstractFormRenderedElement;
import com.pungwe.cms.core.form.element.FormElement;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;

import static com.pungwe.cms.core.utils.Utils.translate;

/**
 * Created by 917903 on 22/04/2016.
 */
public class DecimalValidator implements ElementValidator {

    @Override
    public void validate(FormRenderedElement element) {
        if (StringUtils.isNotBlank((String) element.getValue())) {
            try {
                Double.valueOf((String)element.getValue());
            } catch (NumberFormatException ex) {
                element.addError(translate("Please provide a valid number"));
            }
        }
    }

}
