package com.pungwe.cms.core.field.controller;

import com.pungwe.cms.core.annotations.stereotypes.FieldType;
import com.pungwe.cms.core.field.services.FieldTypeManagementService;
import com.pungwe.cms.core.form.controller.AbstractFormController;
import com.pungwe.cms.core.form.element.FormElement;
import com.pungwe.cms.core.form.element.SingleSelectListElement;
import com.pungwe.cms.core.form.element.StringElement;
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
		StringElement label = new StringElement();
		label.setLabel("Label");
		label.setName("label");
		element.addContent(label);

		final SingleSelectListElement fieldType = new SingleSelectListElement();
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

	protected abstract void buildInternal(FormElement<T> element);

	@Override
	public void validate(FormElement<T> form, Errors errors) {

	}
}
