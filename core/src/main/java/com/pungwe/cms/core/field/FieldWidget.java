package com.pungwe.cms.core.field;

import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.form.Form;
import com.pungwe.cms.core.form.FormState;

import java.util.List;

/**
 * Created by ian on 09/12/2015.
 */
public interface FieldWidget {

	String getName();

	void buildWidgetForm(List<RenderedElement> elements, FieldConfig field, int delta, Form form, FormState sate);

	void buildWidgetSettingsForm(List<RenderedElement> elements, Form form, FormState state);

	boolean supports(String fieldType);

}
