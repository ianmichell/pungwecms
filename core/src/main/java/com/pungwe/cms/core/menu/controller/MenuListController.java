package com.pungwe.cms.core.menu.controller;

import com.pungwe.cms.core.annotations.ui.MenuItem;
import com.pungwe.cms.core.element.basic.AnchorElement;
import com.pungwe.cms.core.element.basic.PlainTextElement;
import com.pungwe.cms.core.element.basic.TableElement;
import com.pungwe.cms.core.form.FormState;
import com.pungwe.cms.core.form.element.*;
import com.pungwe.cms.core.menu.MenuInfo;
import com.pungwe.cms.core.menu.services.MenuManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by ian on 08/03/2016.
 */
@Controller
@RequestMapping(value="/admin/structure/menu")
public class MenuListController {

	@Autowired
	protected MenuManagementService menuManagementService;

	@Autowired
	protected LocaleResolver localeResolver;

	@MenuItem(menu="system", parent="admin.structure", name="menus", title="Menus", description = "Manage menus and menu links")
	@RequestMapping(method= RequestMethod.GET)
	public Callable<String> index(HttpServletRequest request, Model model) {
		return () -> {
			final List<MenuInfo> menus = menuManagementService.listMenusByLanguage(localeResolver.resolveLocale(request).getLanguage());

			TableElement tableElement = new TableElement();
			tableElement.addHeaderRow(
					new TableElement.Header("Title"),
					new TableElement.Header("Description"),
					new TableElement.Header("Operations")
			);

			menus.forEach(menuInfo -> {
				tableElement.addRow(
						new TableElement.Column(menuInfo.getTitle()),
						new TableElement.Column(menuInfo.getDescription()),
						new TableElement.Column(new AnchorElement("Edit menu", request.getContextPath() + "/admin/structure/menu/edit/" + menuInfo.getId(), "Edit"))
				);
			});

			model.addAttribute("title", "Menus");
			model.addAttribute("action", new AnchorElement("Add a new menu", request.getContextPath() + "/admin/structure/menu/add", "Add menu"));
			model.addAttribute("table", tableElement);
			return "menu/index";
		};
	}
}
