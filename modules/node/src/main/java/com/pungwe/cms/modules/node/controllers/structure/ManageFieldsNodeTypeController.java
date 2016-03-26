package com.pungwe.cms.modules.node.controllers.structure;

import com.pungwe.cms.core.annotations.ui.MenuItem;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.basic.*;
import com.pungwe.cms.core.entity.EntityDefinition;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.entity.controller.AbstractEntityTypeFieldController;
import com.pungwe.cms.core.field.services.FieldTypeManagementService;
import com.pungwe.cms.core.form.element.FormElement;
import com.pungwe.cms.core.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.SortedSet;
import java.util.concurrent.Callable;

/**
 * Created by 917903 on 22/03/2016.
 */
@MenuItem(
        menu = "system",
        name = "fields",
        parent = "admin.structure.content-types",
        title = "Manage Fields",
        pattern = true,
        task = true
)
@Controller
@RequestMapping("/admin/structure/content-types/{nodeType}/fields")
public class ManageFieldsNodeTypeController extends AbstractEntityTypeFieldController {

	@Autowired
	protected FieldTypeManagementService fieldTypeManagementService;

	@ModelAttribute("nodeType")
	public String getNodeType() {
		return Utils.getRequestPathVariable("nodeType");
	}

    @ModelAttribute("title")
    public String title() {
        return "Manage Fields";
    }

	@Override
	protected void build(final TableElement element) {
		String id = Utils.getRequestPathVariable("nodeType");
		EntityDefinition entityDefinition = entityDefinitionService.get("node", id);

		SortedSet<FieldConfig> fields = entityDefinition.getFields();
		fields.stream().forEach(fieldConfig -> {
			ListElement operations = new UnorderedListElement();
			operations.addClass("entity-operations");
			buildOperations(getNodeType(), fieldConfig.getName(), operations);

			element.addRow(
					new TableElement.Column(fieldConfig.getLabel()),
					new TableElement.Column(fieldConfig.getName()),
					new TableElement.Column(getFieldWidgetLabel(fieldConfig.getFieldType())),
					new TableElement.Column(operations)
			);
		});
	}

	private String getFieldWidgetLabel(String name) {
		return fieldTypeManagementService.getFieldTypeLabel(name);
	}

	@Override
	protected void buildOperations(String bundle, String field, ListElement operations) {
		ListElement.ListItem edit = new ListElement.ListItem(new AnchorElement("Edit Field", "/admin/structure/content-types/" + bundle + "/fields/" + field + "/edit", "Edit"));
		edit.addClass("default-operation");
		operations.addItem(edit);
	}

	@Override
	protected void buildActions(List<RenderedElement> elements) {
		String id = Utils.getRequestPathVariable("nodeType");
		elements.add(new AnchorElement(
				"Add a new field",
				"/admin/structure/content-types/" + id + "/fields/add",
				new PlainTextElement("Add a field")
		));
	}

	@RequestMapping(method = RequestMethod.GET)
    public Callable<String> fields(Model model) {
        return () -> {
            return "node_type/fields";
        };
    }
}
