package com.pungwe.cms.modules.node.controllers.structure;

import com.pungwe.cms.core.annotations.ui.MenuItem;
import com.pungwe.cms.core.entity.EntityDefinition;
import com.pungwe.cms.core.field.controller.AbstractFieldEditController;
import com.pungwe.cms.core.form.element.FormElement;
import com.pungwe.cms.core.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.concurrent.Callable;

/**
 * Created by ian on 25/03/2016.
 */
@MenuItem(
		menu = "system",
		name = "add_field",
		parent = "admin.structure.content-types.fields",
		title = "Add a field",
		pattern = true,
		task = true
)
@Controller
@RequestMapping("/admin/structure/content-types/{nodeType}/fields/add")
public class AddNodeTypeFieldController extends AbstractFieldEditController<EntityDefinition> {

	@RequestMapping(method = RequestMethod.GET)
	public Callable<String> get(Model model) {
		return () -> "node_type/add_field";
	}

	@ModelAttribute("title")
	public String title() {
		return "Add a field";
	}

	@ModelAttribute("nodeType")
	public String nodeType() {
		return Utils.getRequestPathVariable("nodeType");
	}

	@Override
	protected void buildInternal(FormElement<EntityDefinition> element) {
	}

	@Override
	public String getFormId() {
		return "add_entity_field_form";
	}
}
