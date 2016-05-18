package com.pungwe.cms.modules.node.controllers.structure;

import com.pungwe.cms.core.annotations.stereotypes.FieldType;
import com.pungwe.cms.core.annotations.ui.MenuItem;
import com.pungwe.cms.core.entity.EntityDefinition;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.field.FieldWidgetDefinition;
import com.pungwe.cms.core.field.controller.AbstractFieldEditController;
import com.pungwe.cms.core.form.element.FormElement;
import com.pungwe.cms.core.form.element.TextElement;
import com.pungwe.cms.core.form.validation.MachineNameValidator;
import com.pungwe.cms.core.utils.Utils;
import com.pungwe.cms.core.utils.services.StatusMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.concurrent.Callable;

import static com.pungwe.cms.core.utils.Utils.translate;

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
public class AddNodeTypeFieldController extends AbstractFieldEditController<FieldConfig> {

    @Autowired
    protected StatusMessageService statusMessageService;

	@RequestMapping(method = RequestMethod.GET)
	public Callable<String> get(Model model) {
		return () -> "node_type/add_field";
	}

	@RequestMapping(method = RequestMethod.POST)
	public Callable<String> post(Model model, @Valid @ModelAttribute("form") FormElement<FieldConfig> formElement,
                                 BindingResult result, RedirectAttributes redirectAttributes) {
		return () -> {
            if (result.hasErrors()) {
                return "node_type/add_field";
            }
            formElement.submit(model.asMap());
            redirectAttributes.addAttribute("nodeType", nodeType());
            redirectAttributes.addAttribute("fieldName", formElement.getTargetObject().getName());
            statusMessageService.addSuccessStatusMessage(redirectAttributes,
                    translate("Success you have added a new field: %s", translate(formElement.getTargetObject()
                            .getLabel())));
            return "redirect:/admin/structure/content-types/{nodeType}/fields/{fieldName}/edit";
        };
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
	protected void buildInternal(FormElement<FieldConfig> element) {

        TextElement textElement = new TextElement();
        textElement.setName("field_name");
        textElement.setSize(20);
        textElement.setHtmlId("field_name");
        textElement.setLabel(translate("Field Name"));
        textElement.addValidator(new MachineNameValidator());

        element.addContent(textElement);

		FieldConfig fieldConfig = new FieldConfig();
        element.setTargetObject(fieldConfig);
        element.addSubmitHandler(((form, variables) -> {
            FieldConfig target = form.getTargetObject();
            target.setName((String)form.getValue("field_name", 0));
            target.setLabel((String)form.getValue("label", 0));
            target.setFieldType((String)form.getValue("field_type", 0));

            // Widget and Formatter
            FieldType fieldType = fieldTypeManagementService.getFieldType(target.getFieldType());
            String defaultWidget = fieldTypeManagementService.getFieldWidgetName(fieldType);
            String defaultFormatter = fieldTypeManagementService.getFieldFormatterName(fieldType);
            target.setFormatter(defaultFormatter);
            target.setWidget(defaultWidget);

            // Fetch the entity
            String nodeType = nodeType();
            EntityDefinition<?> entityDefinition = entityDefinitionService.get("node", nodeType);
            // Add the field to the entity definition
            entityDefinition.addField(target);

            // Save the entity definition
            entityDefinitionService.update(entityDefinition);
        }));
	}

	@Override
	public String getFormId() {
		return "add_entity_field_form";
	}
}
