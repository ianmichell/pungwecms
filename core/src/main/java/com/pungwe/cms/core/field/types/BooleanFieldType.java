package com.pungwe.cms.core.field.types;


import com.pungwe.cms.core.annotations.ui.FieldType;
import com.pungwe.cms.core.field.formatters.BooleanFormatter;
import com.pungwe.cms.core.field.widgets.BooleanWidget;

/**
 * Created by ian on 09/01/2016.
 */
@FieldType(
		name = "boolean",
		label = "Boolean (Yes / No)",
		category = "General",
		defaultWidget = BooleanWidget.class,
		defaultFormatter = BooleanFormatter.class
)
public class BooleanFieldType {

}
