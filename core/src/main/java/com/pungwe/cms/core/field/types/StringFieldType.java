package com.pungwe.cms.core.field.types;

import com.pungwe.cms.core.annotations.FieldType;
import com.pungwe.cms.core.field.formatters.TextFormatter;
import com.pungwe.cms.core.field.widgets.TextfieldWidget;

/**
 * Created by ian on 09/01/2016.
 */
@FieldType(
        name = "string",
        label = "Text (Unformatted)",
        category = "General",
        defaultWidget = TextfieldWidget.class,
        defaultFormatter = TextFormatter.class
)
public class StringFieldType {
}
