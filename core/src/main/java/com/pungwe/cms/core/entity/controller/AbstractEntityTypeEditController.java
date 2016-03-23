package com.pungwe.cms.core.entity.controller;

import com.pungwe.cms.core.form.controller.AbstractFormController;
import com.pungwe.cms.core.form.element.FormElement;
import com.pungwe.cms.core.form.element.StringRenderedElement;
import com.pungwe.cms.core.form.element.TextareaRenderedElement;
import org.springframework.validation.Errors;

/**
 * Created by ian on 22/03/2016.
 */
public abstract class AbstractEntityTypeEditController extends AbstractFormController {

	@Override
	public void build(FormElement element) {
		// Title field
		StringRenderedElement title = new StringRenderedElement();
		title.setSize(60);
		title.setLabel("Title");
		title.setName("title");
		title.setPlaceholder("Content Type Title");
		title.setRequired(true);
		element.addContent(title);

		// Description field
		TextareaRenderedElement description = new TextareaRenderedElement();
		description.setLabel("Description");
		description.setName("description");
		description.setPlaceholder("Administrative description");
		description.setRows(5);
		description.setLabel("Description");
		element.addContent(description);

		StringRenderedElement bundleName = new StringRenderedElement();
		bundleName.setLabel("Bundle Name");
		bundleName.setSize(60);
		bundleName.setPlaceholder("Bundle Name");
		bundleName.setRequired(true);
		bundleName.setName("bundle");
		element.addContent(bundleName);

		buildInternal(element);
	}

	@Override
	public void validate(FormElement form, Errors errors) {

	}
}
