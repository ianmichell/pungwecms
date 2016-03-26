package com.pungwe.cms.core.field.controller;

import com.pungwe.cms.core.annotations.ui.FieldType;
import com.pungwe.cms.core.field.services.FieldTypeManagementService;
import com.pungwe.cms.core.form.controller.AbstractFormController;
import com.pungwe.cms.core.form.element.FormElement;
import com.pungwe.cms.core.form.element.SingleSelectListRenderedElement;
import com.pungwe.cms.core.form.element.StringRenderedElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import java.util.List;

/**
 * Created by ian on 25/03/2016.
 */
public abstract class AbstractFieldEditController<T> extends AbstractFormController<T> {

	@Autowired
	protected FieldTypeManagementService fieldTypeManagementService;

	@Override
	public void build(FormElement<T> element) {
		StringRenderedElement label = new StringRenderedElement();
		label.setLabel("Label");
		label.setName("label");
		element.addContent(label);

		final SingleSelectListRenderedElement fieldType = new SingleSelectListRenderedElement();
		fieldType.setName("fieldType");
		fieldType.setLabel("Field Type");
		List<FieldType> fieldTypes = fieldTypeManagementService.getAllFieldTypes();
		fieldTypes.forEach(type -> {
			if (type == null) {
				return;
			}
			fieldType.addOption(type.label(), type.value());
		});
		element.addContent(fieldType);

		buildInternal(element);
	}

	@Override
	public void validate(FormElement<T> form, Errors errors) {

	}
}
