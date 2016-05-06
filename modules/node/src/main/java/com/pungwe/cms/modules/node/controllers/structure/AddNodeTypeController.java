package com.pungwe.cms.modules.node.controllers.structure;

import com.pungwe.cms.core.annotations.ui.MenuItem;
import com.pungwe.cms.core.entity.EntityDefinition;
import com.pungwe.cms.core.entity.EntityTypeDefinition;
import com.pungwe.cms.core.entity.controller.AbstractEntityTypeEditController;
import com.pungwe.cms.core.entity.services.EntityDefinitionService;
import com.pungwe.cms.core.form.element.FormElement;
import com.pungwe.cms.core.utils.services.StatusMessageService;
import com.pungwe.cms.modules.node.entity.NodeEntityTypeDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.concurrent.Callable;

import static com.pungwe.cms.core.utils.Utils.translate;

/**
 * Created by 917903 on 22/03/2016.
 */
@MenuItem(
		menu = "system",
		name = "add",
		parent = "admin.structure.content-types",
		title = "Add a new Content Type"
)
@Controller
@RequestMapping("/admin/structure/content-types/add")
public class AddNodeTypeController extends AbstractEntityTypeEditController {

	@Autowired
	protected NodeEntityTypeDefinition nodeEntityTypeDefinition;

	@Autowired
	protected StatusMessageService statusMessageService;

	@RequestMapping(method = RequestMethod.GET)
	public Callable<String> add(Model model) {
		return () -> {
			return "node_type/add";
		};
	}

	@Override
	protected void buildInternal(FormElement<EntityDefinition> element) {
		element.setTargetObject(entityDefinitionService.newInstance(nodeEntityTypeDefinition));
		element.addSubmitHandler((form, variables) -> {
			String title = (String) form.getValue("title", 0);
			String description = (String) form.getValue("description", 0);
			String bundleName = (String) form.getValue("bundle", 0);


			EntityDefinition definition = form.getTargetObject();
			definition.getId().setBundle(bundleName);
			definition.setTitle(title);
			definition.setDescription(description);
			entityDefinitionService.create(definition);
		});
	}

	@Override
	public String getFormId() {
		return "add_node_form";
	}

	@ModelAttribute("title")
	public String title() {
		return "Add a Content Type";
	}

	@RequestMapping(method = RequestMethod.POST)
	public Callable<String> add(Model model, @Valid @ModelAttribute("form") FormElement<EntityDefinition> form,
								BindingResult result, RedirectAttributes redirectAttributes) {
		return () -> {
			if (result.hasErrors()) {
				return "node_type/add";
			}
			form.getSubmitHandlers().forEach(f -> {
				f.submit(form, model.asMap());
			});
			statusMessageService.addSuccessStatusMessage(redirectAttributes, translate("Success! You added %s successfully",
					form.getTargetObject().getTitle()));
			return "redirect:/admin/structure/content-types";
		};
	}
}
