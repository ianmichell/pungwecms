package com.pungwe.cms.core.annotations.ui;

import com.pungwe.cms.core.field.FieldFormatterDefinition;
import com.pungwe.cms.core.field.FieldWidgetDefinition;

/**
 * Created by ian on 10/01/2016.
 */
public @interface FieldType {
	String name();

	String label();

	String category();

	Class<? extends FieldWidgetDefinition> defaultWidget();

	Class<? extends FieldFormatterDefinition> defaultFormatter();
}
