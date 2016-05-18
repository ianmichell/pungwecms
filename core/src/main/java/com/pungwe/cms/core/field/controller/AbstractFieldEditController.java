package com.pungwe.cms.core.field.controller;

import com.pungwe.cms.core.annotations.stereotypes.FieldType;
import com.pungwe.cms.core.entity.services.EntityDefinitionService;
import com.pungwe.cms.core.field.services.FieldTypeManagementService;
import com.pungwe.cms.core.form.controller.AbstractFormController;
import com.pungwe.cms.core.form.element.FormElement;
import com.pungwe.cms.core.form.element.InputButtonElement;
import com.pungwe.cms.core.form.element.SingleSelectListElement;
import com.pungwe.cms.core.form.element.TextElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import java.util.List;

import static com.pungwe.cms.core.utils.Utils.translate;

/**
 * Created by ian on 25/03/2016.
 */
public abstract class AbstractFieldEditController<T> extends AbstractFormController<T> {

	@Autowired
	protected FieldTypeManagementService fieldTypeManagementService;

	@Autowired
	protected EntityDefinitionService entityDefinitionService;

	@Override
	public void build(FormElement<T> element) {
		TextElement label = new TextElement();
		label.setLabel("Label");
		label.setName("label");
		element.addContent(label);

		final SingleSelectListElement fieldType = new SingleSelectListElement();
		fieldType.setName("field_type");
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

        InputButtonElement submit = new InputButtonElement(InputButtonElement.InputButtonType.SUBMIT);
        submit.setDefaultValue(translate("Save"));
        submit.addClass("button");

        element.addContent(submit);
	}

	protected abstract void buildInternal(FormElement<T> element);

}
