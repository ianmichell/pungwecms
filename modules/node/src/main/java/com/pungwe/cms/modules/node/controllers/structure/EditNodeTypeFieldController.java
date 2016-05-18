package com.pungwe.cms.modules.node.controllers.structure;

import com.pungwe.cms.core.annotations.ui.MenuItem;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.entity.EntityDefinition;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.entity.services.EntityDefinitionService;
import com.pungwe.cms.core.field.FieldWidgetDefinition;
import com.pungwe.cms.core.field.controller.AbstractFieldEditController;
import com.pungwe.cms.core.field.services.FieldTypeManagementService;
import com.pungwe.cms.core.form.element.FieldsetElement;
import com.pungwe.cms.core.form.element.FormElement;
import com.pungwe.cms.core.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

import static com.pungwe.cms.core.utils.Utils.translate;

/**
 * Created by ian on 25/03/2016.
 */
@MenuItem(
		menu = "system",
		name = "edit_field",
		parent = "admin.structure.content-types.fields",
		title = "Edit",
		pattern = true,
		task = true
)
@Controller
@RequestMapping("/admin/structure/content-types/{nodeType}/fields/{fieldName}/edit")
public class EditNodeTypeFieldController extends AbstractFieldEditController<FieldConfig> {

	@Override
	protected void buildInternal(FormElement<FieldConfig> element) {
		// Can't change the field type once it's set as this can cause issues in the db!
		element.hideField("field_type", 0);

		String nodeType = Utils.getRequestPathVariable("nodeType");
		String field = Utils.getRequestPathVariable("fieldName");

		EntityDefinition entityDefinition = entityDefinitionService.get("node", nodeType);
		Optional<FieldConfig> fieldConfig = entityDefinition.getFieldByName(field);
		if (!fieldConfig.isPresent()) {
            return;
		}

        element.setTargetObject(fieldConfig.get());

        element.setValue("label", 0, fieldConfig.get().getLabel());
		// Add the other options potentially... Like widget form
        final FieldWidgetDefinition<?> widgetDefinition = fieldTypeManagementService.getWidgetDefinition(fieldConfig.get().getWidget());
        // Build the widget form
        List<RenderedElement> elements = new ArrayList<>();
        widgetDefinition.buildWidgetForm(elements, fieldConfig.get(), null, 0);

        FieldsetElement widgetForm = new FieldsetElement();
        widgetForm.setLegend(translate("Default Value"));
        widgetForm.addContent(elements);

        element.addContent(widgetForm);

        // Add submit handler
        element.addSubmitHandler(((form, variables) -> {

        }));
	}

	@ModelAttribute("title")
	public String title() {
		return translate("Edit");
	}

	@ModelAttribute("nodeType")
	public String nodeType() {
		return Utils.getRequestPathVariable("nodeType");
	}

	@ModelAttribute("fieldName")
	public String fieldName() {
		return Utils.getRequestPathVariable("fieldName");
	}

	@Override
	public String getFormId() {
		return "edit_entity_field_form";
	}

	@RequestMapping(method = RequestMethod.GET)
	public Callable<String> get(Model model) {
		return () -> "node_type/edit_field";
	}
}
