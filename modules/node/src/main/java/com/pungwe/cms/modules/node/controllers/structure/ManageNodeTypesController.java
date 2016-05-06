package com.pungwe.cms.modules.node.controllers.structure;

import com.pungwe.cms.core.annotations.ui.MenuItem;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.basic.AnchorElement;
import com.pungwe.cms.core.element.basic.ListElement;
import com.pungwe.cms.core.element.basic.PlainTextElement;
import com.pungwe.cms.core.element.basic.TableElement;
import com.pungwe.cms.core.entity.EntityDefinition;
import com.pungwe.cms.core.entity.controller.AbstractEntityTypeListController;
import com.pungwe.cms.core.entity.services.EntityDefinitionService;
import com.pungwe.cms.modules.node.entity.NodeEntityTypeDefinition;
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

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by ian on 11/01/2016.
 */
@Controller
@RequestMapping("/admin/structure/content-types")
public class ManageNodeTypesController extends AbstractEntityTypeListController {

	@Autowired
	protected EntityDefinitionService entityDefinitionService;

	@Autowired
	protected NodeEntityTypeDefinition nodeEntityTypeDefinition;

	// FIXME: Inject a hook service, this will allow module developers to modify this module
	@MenuItem(
			menu = "system",
			name = "content-types",
			parent = "admin.structure",
			title = "Content Types",
			description = "Manage your content types"
	)
	@RequestMapping(method = RequestMethod.GET)
	public Callable<String> list(Model model) {
		return () -> {
			return "node_type/list";
		};
	}

	@Override
	protected String getEntityType() {
		return entityDefinitionService.getEntityTypeName(nodeEntityTypeDefinition);
	}

	@Override
	protected String getTitle() {
		return "Content Types";
	}

	@Override
	protected String editUrl(String id) {
		return "/admin/structure/content-types/" + id + "/edit";
	}

	@Override
	protected void buildActions(List<RenderedElement> elements) {
		elements.add(new AnchorElement(
				"Add a new content type",
				"/admin/structure/content-types/add",
				new PlainTextElement("Add a content type")
		));
	}

	@Override
	protected void buildOperations(String bundle, ListElement operations) {
		ListElement.ListItem edit = new ListElement.ListItem(new AnchorElement("Edit Content Type", "/admin/structure/content-types/" + bundle + "/edit", "Edit"));
		edit.addClass("default-operation");
		ListElement.ListItem fields = new ListElement.ListItem(new AnchorElement("Manage Fields", "/admin/structure/content-types/" + bundle + "/fields", "Manage Fields"));
		operations.addItem(edit, fields);
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
