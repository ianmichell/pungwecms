package com.pungwe.cms.modules.node.controllers;

import com.pungwe.cms.core.annotations.MenuItem;
import com.pungwe.cms.core.element.basic.LinkElement;
import com.pungwe.cms.core.element.basic.PlainTextElement;
import com.pungwe.cms.core.element.basic.TableElement;
import com.pungwe.cms.core.entity.EntityDefinition;
import com.pungwe.cms.core.entity.services.EntityDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by ian on 11/01/2016.
 */
@Controller
@RequestMapping("/admin/structure/content-types")
public class NodeTypeController {

	@Autowired
	protected EntityDefinitionService entityDefinitionService;

	// FIXME: Inject a hook service, this will allow module developers to modify this module
	@MenuItem(
			name = "content-types",
			parent = "system.admin.structure",
			title = "Content Types",
			description = "Manage your content types"
	)
	@RequestMapping(value = "/")
	public Callable<String> list(Model model, int pageNumber, int maxRows) {
		return () -> {
			Page<EntityDefinition> entities = entityDefinitionService.list("node_type", new PageRequest(pageNumber, maxRows));

			TableElement table = new TableElement();
			table.addHeaderRow(
					new TableElement.Column(new PlainTextElement("Title")),
					new TableElement.Column(new PlainTextElement("Description")),
					new TableElement.Column(new PlainTextElement("Operations"))
			);

			// Run through each record and create a table row per entity
			for (EntityDefinition entity : entities) {
				// Link the title to the edit operation for the entity
				LinkElement entityEditLink = new LinkElement(
						entity.getTitle(),
						"admin/structure/content-types/edit/" + entity.getId().getBundle(),
						new PlainTextElement(entity.getTitle())
				);
				// Add a table row for the entity
				table.addRow(
						new TableElement.Column(entityEditLink),
						new TableElement.Column(new PlainTextElement(entity.getDescription())),
						new TableElement.Column(new PlainTextElement("Operations"))
				);
			}

			// Page title
			model.addAttribute("title", "Content Types");

			// Page actions
			model.addAttribute("actions", new LinkElement(
					"Add a new content type",
					"admin/structure/content-types/add",
					new PlainTextElement("Add a content type")
			));

			// Table of entity types
			model.addAttribute("content", table);

			return "node_type_list";
		};
	}

	@MenuItem(
			name = "node.type.add",
			parent = "system.admin.structure.node.content-types",
			title = "Add a new Content Type"
	)
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public Callable<String> add(Model model) {
		return () -> {
			return "node_type_add";
		};
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Callable<String> add(Model model, BindingResult result) {
		return () -> {
			return "node_type_add";
		};
	}

	@RequestMapping(value = "/edit/{id}")
	public Callable<String> edit(@PathVariable("id") String id) {
		return () -> {
			return "node_type_edit";
		};
	}

	@RequestMapping(value = "/delete/{id}")
	public Callable<String> delete(@PathVariable("id") String id) {
		return () -> {
			return "redirect:/admin/structure/content-types";
		};
	}

	@RequestMapping(value = "/delete_confirm/{id}")
	public Callable<String> deleteConfirm(@PathVariable("id") String id, Model model) {
		return () -> {
			return "delete_confirm";
		};
	}
}
