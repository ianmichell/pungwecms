package com.pungwe.cms.core.entity.controller;

import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.basic.ListElement;
import com.pungwe.cms.core.element.basic.TableElement;
import com.pungwe.cms.core.entity.EntityDefinition;
import com.pungwe.cms.core.entity.services.EntityDefinitionService;
import com.pungwe.cms.core.form.controller.AbstractFormController;
import com.pungwe.cms.core.form.element.FormElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ian on 22/03/2016.
 */
public abstract class AbstractEntityTypeFieldController {

	@Autowired
	protected EntityDefinitionService entityDefinitionService;

	@ModelAttribute("content")
	public TableElement content() {
		TableElement element = new TableElement();
		element.addHeaderRow(
				new TableElement.Header("Label"),
				new TableElement.Header("Field Name"),
				new TableElement.Header("Field Type"),
				new TableElement.Header("Operations")
		);

		build(element);

		return element;
	}

	@ModelAttribute("actions")
	public List<RenderedElement> actions() {
		List<RenderedElement> elements = new ArrayList<>();
		buildActions(elements);
		return elements;
	}

	protected abstract void build(TableElement element);

	protected abstract void buildActions(List<RenderedElement> elements);

	protected abstract void buildOperations(String bundle, String field, ListElement operations);
}
