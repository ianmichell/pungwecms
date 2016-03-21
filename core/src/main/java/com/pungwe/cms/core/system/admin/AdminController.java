package com.pungwe.cms.core.system.admin;

import com.pungwe.cms.core.annotations.ui.MenuItem;
import com.pungwe.cms.core.annotations.ui.MenuItems;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.basic.*;
import com.pungwe.cms.core.menu.MenuConfig;
import com.pungwe.cms.core.menu.services.MenuManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by ian on 16/03/2016.
 */
@Controller
@RequestMapping("/admin")
@MenuItems(items={
		@MenuItem(menu = "system", name="admin", description = "Administration Homepage", title = "Admin"),
})
public class AdminController {

	@Autowired
	MenuManagementService menuManagementService;

	@RequestMapping(method= RequestMethod.GET)
	public Callable<String> index() {
		return () -> {
			return "admin/index";
		};
	}

	@MenuItem(menu = "system", parent="admin", name="structure", description = "Manage Website Structure", title = "Structure", weight = -102)
	@RequestMapping(value="/structure", method = RequestMethod.GET)
	@Cacheable("admin.structure")
	public Callable<String> structure(HttpServletRequest request, final Model model) {
		return () -> {
			DivElement element = new DivElement();
			element.setHtmlId("admin_structure_menu");
			element.addClass("admin-list");
			List<MenuConfig> menuItems = menuManagementService.getMenuItems("system", "admin.structure");

			List<RenderedElement> menuItemElements = menuItems.stream().sorted().map(menuConfig -> {
				HeaderElement label = new HeaderElement(4, menuConfig.getTitle());
				label.addClass("item-label");
				ParagraphElement description = new ParagraphElement(new PlainTextElement(menuConfig.getDescription()));
				description.addClass("item-description");

				AnchorElement anchor = new AnchorElement();
				anchor.setTitle(menuConfig.getDescription());
				anchor.setContent(label, description);
				anchor.setTarget(menuConfig.getTarget());
				// Set the url
				Pattern p = Pattern.compile("^https?\\://|ftp\\://");
				if (p.matcher(menuConfig.getUrl()).matches()) {
					anchor.setHref(menuConfig.getUrl());
				} else {
					anchor.setHref(request.getContextPath() + "/" + menuConfig.getUrl().replaceAll("^/", ""));
				}
				return anchor;
			}).collect(Collectors.toList());
			element.setContent(menuItemElements);

			model.addAttribute("title", "Structure");
			model.addAttribute("content", element);

			return "admin/structure";
		};
	}

	@MenuItem(menu = "system", parent="admin", name="reporting", description = "Reports", title = "Reports", weight = -100)
	@RequestMapping(value="/reporting", method = RequestMethod.GET)
	@Cacheable("admin.reporting")
	public Callable<String> reporting(HttpServletRequest request, Model model) {
		return () -> {
			DivElement element = new DivElement();
			element.setHtmlId("admin_structure_menu");
			element.addClass("admin-list");
			List<MenuConfig> menuItems = menuManagementService.getMenuItems("system", "admin.reporting");

			List<RenderedElement> menuItemElements = menuItems.stream().sorted().map(menuConfig -> {
				HeaderElement label = new HeaderElement(4, menuConfig.getTitle());
				label.addClass("item-label");
				ParagraphElement description = new ParagraphElement(new PlainTextElement(menuConfig.getDescription()));
				description.addClass("item-description");

				AnchorElement anchor = new AnchorElement();
				anchor.setTitle(menuConfig.getDescription());
				anchor.setContent(label, description);
				anchor.setTarget(menuConfig.getTarget());
				// Set the url
				Pattern p = Pattern.compile("^https?\\://|ftp\\://");
				if (p.matcher(menuConfig.getUrl()).matches()) {
					anchor.setHref(menuConfig.getUrl());
				} else {
					anchor.setHref(request.getContextPath() + "/" + menuConfig.getUrl().replaceAll("^/", ""));
				}
				return anchor;
			}).collect(Collectors.toList());
			element.setContent(menuItemElements);

			model.addAttribute("title", "Reports");
			model.addAttribute("content", element);

			return "admin/reports";
		};
	}

	@MenuItem(menu = "system", parent="admin.reporting", name="system", description = "System Reports", title = "System Reports", weight = -101)
	@RequestMapping(value="/reporting/system", method = RequestMethod.GET)
	@Cacheable("admin.reporting.system")
	public Callable<String> systemReporting(HttpServletRequest request, Model model) {
		return () -> {
			DivElement element = new DivElement();
			element.setHtmlId("admin_structure_menu");
			element.addClass("admin-list");
			List<MenuConfig> menuItems = menuManagementService.getMenuItems("system", "admin.reporting.system");

			List<RenderedElement> menuItemElements = menuItems.stream().sorted().map(menuConfig -> {
				HeaderElement label = new HeaderElement(4, menuConfig.getTitle());
				label.addClass("item-label");
				ParagraphElement description = new ParagraphElement(new PlainTextElement(menuConfig.getDescription()));
				description.addClass("item-description");

				AnchorElement anchor = new AnchorElement();
				anchor.setTitle(menuConfig.getDescription());
				anchor.setContent(label, description);
				anchor.setTarget(menuConfig.getTarget());
				// Set the url
				Pattern p = Pattern.compile("^https?\\://|ftp\\://");
				if (p.matcher(menuConfig.getUrl()).matches()) {
					anchor.setHref(menuConfig.getUrl());
				} else {
					anchor.setHref(request.getContextPath() + "/" + menuConfig.getUrl().replaceAll("^/", ""));
				}
				return anchor;
			}).collect(Collectors.toList());
			element.setContent(menuItemElements);

			model.addAttribute("title", "Reports - System");
			model.addAttribute("content", element);

			return "admin/system";
		};
	}

	@MenuItem(menu = "system", parent="admin", name="configuration", description = "Manage Website Configuration", title = "Configuration", weight = -100)
	@RequestMapping(value="/configuration", method = RequestMethod.GET)
	public Callable<String> configuration(Model model) {
		return () -> {
			model.addAttribute("title", "Configuration");
			return "admin/configuration";
		};
	}
}
