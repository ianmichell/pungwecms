package com.pungwe.cms.core.form.validation;

import com.pungwe.cms.core.form.ElementValidator;
import com.pungwe.cms.core.form.FormRenderedElement;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.pungwe.cms.core.utils.Utils.translate;

/**
 * Created by ian on 06/05/2016.
 */
public class MachineNameValidator implements ElementValidator {

    @Override
    public void validate(FormRenderedElement element) {
        String value = (String)element.getValue();
        if (value == null || value.length() == 0) {
            return;
        }
        Pattern p = Pattern.compile("^[\\w\\d]+[\\w\\d_\\-]+$");
        Matcher matcher = p.matcher(value);
        if (!matcher.matches()) {
            element.addError(translate("Field can only contain letters, numbers and either \"_\" or \"-\" " +
                    "and must start with a letter or number"));
        }
    }

}
