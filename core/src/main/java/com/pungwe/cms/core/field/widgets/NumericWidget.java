package com.pungwe.cms.core.field.widgets;

import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.field.FieldWidget;
import com.pungwe.cms.core.form.Form;
import com.pungwe.cms.core.form.FormState;

import java.util.List;
import java.util.Map;

/**
 * Created by ian on 10/01/2016.
 */
public class NumericWidget implements FieldWidget {

	@Override
	public String getName() {
		return null;
	}

	@Override
	public void buildWidgetForm(List<RenderedElement> elements, FieldConfig field, int delta, Form form, FormState sate) {

	}

	@Override
	public void buildWidgetSettingsForm(List<RenderedElement> elements, Form form, FormState state, Map<String, Object> settings) {

	}

	@Override
	public boolean supports(String fieldType) {
		return false;
	}

}