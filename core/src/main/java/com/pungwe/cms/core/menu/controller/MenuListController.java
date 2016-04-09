package com.pungwe.cms.core.menu.controller;

import com.pungwe.cms.core.annotations.ui.MenuItem;
import com.pungwe.cms.core.element.RenderedElement;
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
import java.util.*;
import java.util.concurrent.Callable;

import static com.pungwe.cms.core.utils.Utils.translate;

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
					new TableElement.Header(translate("Title")),
					new TableElement.Header(translate("Description")),
					new TableElement.Header(translate("Operations"))
			);

			menus.forEach(menuInfo -> {
				tableElement.addRow(
						new TableElement.Column(translate(menuInfo.getTitle())),
						new TableElement.Column(translate(menuInfo.getDescription())),
						new TableElement.Column(new AnchorElement(translate("Edit menu"), request.getContextPath() + "/admin/structure/menu/edit/" + menuInfo.getId(), "Edit"))
				);
			});

			model.addAttribute("title", "Menus");
			model.addAttribute("table", tableElement);
			return "menu/index";
		};
	}

	@ModelAttribute("actions")
	public List<RenderedElement> actions(HttpServletRequest request) {
		List<RenderedElement> elements = new ArrayList<>();
		elements.add(new AnchorElement(translate("Add a new menu"), request.getContextPath() + "/admin/structure/menu/add", translate("Add menu")));
		return elements;
	}
}
