package com.pungwe.cms.core.field.types;

import com.pungwe.cms.core.annotations.ui.FieldType;
import com.pungwe.cms.core.field.formatters.NumericFormatter;
import com.pungwe.cms.core.field.widgets.NumericWidget;

/**
 * Created by ian on 09/01/2016.
 */
@FieldType(
		name = "numeric",
		label = "Numeric",
		category = "General",
		defaultWidget = NumericWidget.class,
		defaultFormatter = NumericFormatter.class
)
public class NumericFieldType {
}
