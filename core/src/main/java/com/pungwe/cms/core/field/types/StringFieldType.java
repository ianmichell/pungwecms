package com.pungwe.cms.core.field.types;

import com.pungwe.cms.core.annotations.ui.FieldType;
import com.pungwe.cms.core.field.formatters.StringFormatter;
import com.pungwe.cms.core.field.widgets.TextfieldWidget;

/**
 * Created by ian on 09/01/2016.
 */
@FieldType(
		value = "string_field",
		label = "Text (Unformatted)",
		category = "General",
		defaultWidget = TextfieldWidget.class,
		defaultFormatter = StringFormatter.class
)
public class StringFieldType {
}
