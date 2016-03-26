package com.pungwe.cms.modules.node.controllers.structure;

import com.pungwe.cms.core.annotations.ui.MenuItem;
import com.pungwe.cms.core.entity.EntityDefinition;
import com.pungwe.cms.core.entity.controller.AbstractEntityTypeEditController;
import com.pungwe.cms.core.form.element.FormElement;
import com.pungwe.cms.core.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.concurrent.Callable;

/**
 * Created by 917903 on 22/03/2016.
 */
@MenuItem(
		menu = "system",
		name = "edit",
		parent = "admin.structure.content-types",
		title = "Edit",
		pattern = true,
		task = true
)
@Controller
@RequestMapping("/admin/structure/content-types/{nodeType}/edit")
public class EditNodeTypeController extends AbstractEntityTypeEditController {

	@ModelAttribute("nodeType")
	public String nodeType(@PathVariable("nodeType") String nodeType) {
		return nodeType;
	}

	@Override
	protected void buildInternal(FormElement<EntityDefinition> element) {
		final String nodeType = Utils.getRequestPathVariable("nodeType");
		final EntityDefinition entityDefinition = entityDefinitionService.get("node", nodeType);
		element.setTargetObject(entityDefinition);

		// Set field values
		element.setValue("title", 0, entityDefinition.getTitle());
		element.setValue("description", 0, entityDefinition.getDescription());

		// Remove the bundle field
		element.hideField("bundle", 0);

		// Add the submit handler
		element.addSubmitHandler((form, model) -> {
			String title = String.valueOf(form.getField("title", 0));
			String desc = String.valueOf(form.getField("description", 0));

			entityDefinition.setTitle(title);
			entityDefinition.setDescription(desc);
		});
	}

	@Override
	public String getFormId() {
		return "edit_node_type";
	}

	@ModelAttribute("title")
	public String title() {
		return "Edit Content Type";
	}

	@RequestMapping(method = RequestMethod.GET)
	public Callable<String> edit(@PathVariable("nodeType") String nodeType, Model model) {
		return () -> {
			return "node_type/edit";
		};
	}

	@RequestMapping(method = RequestMethod.POST)
	public Callable<String> submit(@PathVariable("nodeType") String nodeType, Model model, @ModelAttribute("form") FormElement<EntityDefinition> form, BindingResult result) {
		return () -> {
			if (result.hasErrors()) {
				return "node_type/edit";
			}
			form.getSubmitHandlers().forEach(formSubmitHandler -> {
				if (form.isRebuildRequired()) {
					return;
				}
				formSubmitHandler.submit(form, model.asMap());
			});

			if (form.isRebuildRequired()) {
				return "node_type/edit";
			}
			return "redirect:/admin/structure/content-types/" + nodeType + "/edit";
		};
	}
}
