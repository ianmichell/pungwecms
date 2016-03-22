package com.pungwe.cms.modules.node.controllers.structure;

import com.pungwe.cms.core.annotations.ui.MenuItem;
import com.pungwe.cms.core.element.basic.AnchorElement;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.Callable;

/**
 * Created by ian on 11/01/2016.
 */
@Controller
@RequestMapping("/admin/structure/content-types")
public class ManageNodeTypesController {

	@Autowired
	protected EntityDefinitionService entityDefinitionService;

	// FIXME: Inject a hook service, this will allow module developers to modify this module
	@MenuItem(
			menu = "system",
			name = "content-types",
			parent = "admin.structure",
			title = "Content Types",
			description = "Manage your content types"
	)
	@RequestMapping(method = RequestMethod.GET)
	public Callable<String> list(Model model, @RequestParam(value = "page", defaultValue = "1") int pageNumber, @RequestParam(value = "max", defaultValue = "25") int maxRows) {
		return () -> {
			Page<EntityDefinition> entities = entityDefinitionService.list("node_type", new PageRequest(pageNumber, maxRows));

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
			model.addAttribute("actions", new AnchorElement(
					"Add a new content type",
					"/admin/structure/content-types/add",
					new PlainTextElement("Add a content type")
			));

			// Table of entity types
			model.addAttribute("content", table);

			return "node_type/list";
		};
	}

	@RequestMapping(value = "/delete/{id}")
	public Callable<String> delete(@PathVariable("id") String id) {
		return () -> {
			return "redirect:/admin/structure/content-types";
		};
	}

    /* Not a selectable item */
    @MenuItem(
            menu = "system",
            name = "delete",
            parent = "admin.structure.content-types",
            title = "Confirm Deletion",
            pattern = true
    )
	@RequestMapping(value = "/delete_confirm/{id}")
	public Callable<String> deleteConfirm(@PathVariable("id") String id, Model model) {
		return () -> {
			return "node_type/delete_confirm";
		};
	}
}
