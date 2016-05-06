package com.pungwe.cms.core.entity.controller;

import com.pungwe.cms.core.entity.EntityDefinition;
import com.pungwe.cms.core.entity.services.EntityDefinitionService;
import com.pungwe.cms.core.form.controller.AbstractFormController;
import com.pungwe.cms.core.form.element.*;
import com.pungwe.cms.core.form.validation.MachineNameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

/**
 * Created by ian on 22/03/2016.
 */
public abstract class AbstractEntityTypeEditController extends AbstractFormController<EntityDefinition> {

	@Autowired
	protected EntityDefinitionService entityDefinitionService;

	@Override
	public void build(FormElement<EntityDefinition> element) {

		FieldsetElement fieldsetElement = new FieldsetElement();
		fieldsetElement.setHtmlId(getFormId().concat("_details"));
		element.addContent(fieldsetElement);
		// Title field
		TextElement title = new TextElement();
		title.setSize(60);
		title.setLabel("Title");
		title.setName("title");
		title.setPlaceholder("Content Type Title");
		title.setRequired(true);
		fieldsetElement.addContent(title);

		// Description field
		TextareaElement description = new TextareaElement();
		description.setLabel("Description");
		description.setName("description");
		description.setPlaceholder("Administrative description");
		description.setRows(5);
		description.setLabel("Description");
		fieldsetElement.addContent(description);

		TextElement bundleName = new TextElement();
		bundleName.setLabel("Bundle Name");
		bundleName.setSize(60);
		bundleName.setPlaceholder("Bundle Name");
		bundleName.setRequired(true);
		bundleName.setName("bundle");
        bundleName.addValidator(new MachineNameValidator());
		fieldsetElement.addContent(bundleName);

		buildInternal(element);

		FieldsetElement formActions = new FieldsetElement();
		formActions.setHtmlId(getFormId().concat("_actions"));
		InputButtonElement submit = new InputButtonElement(InputButtonElement.InputButtonType.SUBMIT);
		submit.setValue("Submit");
		submit.setName("submit");

		formActions.addContent(submit);

		element.addContent(formActions);

	}

	protected abstract void buildInternal(FormElement<EntityDefinition> element);

}
