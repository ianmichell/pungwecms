package com.pungwe.cms.modules.text;

import com.pungwe.cms.core.element.Element;
import com.pungwe.cms.core.field.FieldType;
import com.pungwe.cms.core.form.Form;
import com.pungwe.cms.core.form.FormState;

/**
 * Created by ian on 03/01/2016.
 */
public class TextField implements FieldType {

    @Override
    public String getName() {
        return "textfield";
    }

    @Override
    public String getLabel() {
        return "text.label";
    }

}
