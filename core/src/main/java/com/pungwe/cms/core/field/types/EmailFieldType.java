package com.pungwe.cms.core.field.types;

import com.pungwe.cms.core.annotations.ui.FieldType;
import com.pungwe.cms.core.field.formatters.EmailFormatter;
import com.pungwe.cms.core.field.widgets.EmailWidget;

/**
 * Created by ian on 09/01/2016.
 */
@FieldType(
		name = "email",
		label = "Email Address",
		category = "General",
		defaultWidget = EmailWidget.class,
		defaultFormatter = EmailFormatter.class
)
public class EmailFieldType {
}
