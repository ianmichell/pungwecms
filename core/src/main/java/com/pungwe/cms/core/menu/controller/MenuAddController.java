package com.pungwe.cms.core.menu.controller;

import com.pungwe.cms.core.annotations.ui.MenuItem;
import com.pungwe.cms.core.form.FormRenderedElement;
import com.pungwe.cms.core.form.element.FormElement;
import com.pungwe.cms.core.menu.MenuInfo;
import com.pungwe.cms.core.menu.services.MenuManagementService;
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
 * Created by ian on 18/03/2016.
 */
@Controller
@RequestMapping(value = "/admin/structure/menu/add")
@MenuItem(menu = "system", parent = "admin.structure.menus", name = "add", title = "Add menu", description = "Add a new menu")
public class MenuAddController extends AbstractMenuInfoController {

	@Autowired
	protected MenuManagementService menuManagementService;

	@ModelAttribute("title")
	public String getTitle() {
		return translate("Add Menu");
	}


	@Override
	protected void buildInternal(FormElement<MenuInfo> element) {
		element.addSubmitHandler((form, variables) -> {
			menuManagementService.createMenu((String)element.getValue("title", 0), (String)element.getValue("description", 0), (String)element.getValue("language", 0));
		});
	}

	@RequestMapping(method = RequestMethod.GET)
	public Callable<String> addForm(Model model) {
		return () -> {
			return "menu/add";
		};
	}

	@RequestMapping(method = RequestMethod.POST)
	public Callable<String> addSubmit(RedirectAttributes redirectAttributes, Model model, @Valid @ModelAttribute("form") FormElement<MenuInfo> form, BindingResult bindingResult) {
		return () -> {
			if (bindingResult.hasErrors()) {
				return "menu/add";
			}
			form.getSubmitHandlers().forEach(f -> {
				// Form and any variables
				f.submit(form, model.asMap());
			});
			redirectAttributes.addFlashAttribute("message.success", translate("Success! You've created a new menu: %s", form.getValue("title", 0)));
			return "redirect:/admin/structure/menu";
		};
	}

	@Override
	public String getFormId() {
		return "add_menu_form";
	}
}
