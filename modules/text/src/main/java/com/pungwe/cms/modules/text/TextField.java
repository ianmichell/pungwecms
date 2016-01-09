package com.pungwe.cms.modules.text;

import com.pungwe.cms.core.field.FieldType;

/**
 * Created by ian on 03/01/2016.
 */
public class TextField implements FieldType {

    @Override
    public String getName() {
        return "text";
    }

    @Override
    public String getLabel() {
        return "Text Field";
    }

    @Override
    public String getDefaultWidget() {
        return "text";
    }

    @Override
    public String getDefaultFormatter() {
        return "text";
    }

    @Override
    public String getDefaultFieldStorageType() {
        return "string";
    }
}
