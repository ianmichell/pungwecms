package com.pungwe.cms.core.entity.controller;

import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.basic.*;
import com.pungwe.cms.core.entity.EntityDefinition;
import com.pungwe.cms.core.entity.services.EntityDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ian on 22/03/2016.
 */
public abstract class AbstractEntityTypeListController {

	@Autowired
	EntityDefinitionService entityDefinitionService;

	protected abstract String getEntityType();

	@ModelAttribute("content")
	public RenderedElement content(@RequestParam(value = "page", defaultValue = "0") int pageNumber, @RequestParam(value = "max", defaultValue = "25") int maxRows) {

		Page<EntityDefinition> entities = entityDefinitionService.list(getEntityType(), new PageRequest(pageNumber, maxRows));

		TableElement table = new TableElement();
		table.addHeaderRow(
				new TableElement.Header(new PlainTextElement("Title")),
				new TableElement.Header(new PlainTextElement("Description")),
				new TableElement.Header(new PlainTextElement("Operations"))
		);
		// Run through each record and create a table row per entity
		for (EntityDefinition entity : entities) {
			// Link the title to the edit operation for the entity
			AnchorElement entityEditLink = new AnchorElement(
					entity.getTitle(),
					"/admin/structure/content-types/edit/" + entity.getId().getBundle(),
					new PlainTextElement(entity.getTitle())
			);
			ListElement operations = new UnorderedListElement();
			operations.addClass("entity-operations");
			buildOperations(entity.getId().getBundle(), operations);
			// Add a table row for the entity
			table.addRow(
					new TableElement.Column(entityEditLink),
					new TableElement.Column(new PlainTextElement(entity.getDescription())),
					new TableElement.Column(new PlainTextElement("Operations"))
			);
		}

		return table;
	}

	@ModelAttribute("title")
	public String title() {
		return getTitle();
	}

	@ModelAttribute("actions")
	public List<RenderedElement> actions() {
		List<RenderedElement> elements = new ArrayList<>();
		buildActions(elements);
		return elements;
	}

	protected abstract String getTitle();

	protected abstract void buildActions(List<RenderedElement> elements);

	protected abstract void buildOperations(String bundle, ListElement operations);
}
