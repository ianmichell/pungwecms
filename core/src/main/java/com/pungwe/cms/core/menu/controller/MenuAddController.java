package com.pungwe.cms.core.menu.controller;

import com.pungwe.cms.core.annotations.ui.MenuItem;
import com.pungwe.cms.core.form.FormRenderedElement;
import com.pungwe.cms.core.form.FormState;
import com.pungwe.cms.core.form.controller.AbstractFormController;
import com.pungwe.cms.core.form.element.*;
import com.pungwe.cms.core.menu.MenuInfo;
import com.pungwe.cms.core.menu.services.MenuManagementService;
import com.pungwe.cms.core.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditor;
import java.text.Collator;
import java.util.*;
import java.util.List;
import java.util.concurrent.Callable;

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
		return "Add Menu";
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
			redirectAttributes.addFlashAttribute("message.success", "Success! You've created a new menu: " + form.getValue("title", 0));
			return "redirect:/admin/structure/menu";
		};
	}

	@Override
	public String getFormId() {
		return "add_menu_form";
	}
}
