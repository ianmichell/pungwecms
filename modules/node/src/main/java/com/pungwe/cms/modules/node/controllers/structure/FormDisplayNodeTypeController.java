package com.pungwe.cms.modules.node.controllers.structure;

import com.pungwe.cms.core.annotations.ui.MenuItem;
import com.pungwe.cms.core.element.basic.TableElement;
import com.pungwe.cms.core.entity.EntityDefinition;
import com.pungwe.cms.core.entity.controller.AbstractEntityTypeFormDisplayController;
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
		name = "form_display",
		parent = "admin.structure.content-types",
		title = "Form Display",
		pattern = true,
		task = true
)
@Controller
@RequestMapping("/admin/structure/content-types/{nodeType}/form_display")
public class FormDisplayNodeTypeController extends AbstractEntityTypeFormDisplayController {

	@ModelAttribute("title")
	public String title() {
		return "Form Display";
	}

	@ModelAttribute("nodeType")
	public String bundle() {
		return Utils.getRequestPathVariable("nodeType");
	}

    @Override
    protected String entityType() {
        return "node";
    }

    @RequestMapping(method = RequestMethod.GET)
	public Callable<String> get(@PathVariable("nodeType") String nodeType, Model model) {
		return () -> {
			return "node_type/form";
		};
	}

	@Override
	public String getFormId() {
		return "node-form-display";
	}
}
