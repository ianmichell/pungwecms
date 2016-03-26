package com.pungwe.cms.modules.text.type;

import com.pungwe.cms.core.annotations.ui.FieldType;
import com.pungwe.cms.modules.text.formatter.TextFormatter;
import com.pungwe.cms.modules.text.widget.TextareaWithSummaryWidget;

/**
 * Created by ian on 25/03/2016.
 */
@FieldType(value = "text_field",
		category = "General",
		defaultFormatter = TextFormatter.class,
		defaultWidget = TextareaWithSummaryWidget.class,
		label = "Text (Formatted, with summary)")
public class TextFieldType {
}