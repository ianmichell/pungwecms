package com.pungwe.cms.core.annotations;

import com.pungwe.cms.core.field.FieldFormatter;
import com.pungwe.cms.core.field.FieldWidget;

/**
 * Created by ian on 10/01/2016.
 */
public @interface FieldType {
    String name();
    String label();
    String category();
    Class<? extends FieldWidget> defaultWidget();
    Class<? extends FieldFormatter> defaultFormatter();
}
