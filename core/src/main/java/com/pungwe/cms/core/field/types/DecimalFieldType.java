package com.pungwe.cms.core.field.types;


import com.pungwe.cms.core.annotations.FieldType;
import com.pungwe.cms.core.field.formatters.DecimalFormatter;
import com.pungwe.cms.core.field.widgets.DecimalWidget;

/**
 * Created by ian on 09/01/2016.
 */
@FieldType(
		name = "decimal",
		label = "Decimal Number",
		category = "General",
		defaultWidget = DecimalWidget.class,
		defaultFormatter = DecimalFormatter.class
)
public class DecimalFieldType {

}
