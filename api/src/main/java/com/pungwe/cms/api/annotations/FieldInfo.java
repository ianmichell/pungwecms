package com.pungwe.cms.api.annotations;

import com.pungwe.cms.api.field.FieldType;

/**
 * Created by ian on 09/12/2015.
 */
public @interface FieldInfo {

    Class<? extends FieldType> fields();
}
