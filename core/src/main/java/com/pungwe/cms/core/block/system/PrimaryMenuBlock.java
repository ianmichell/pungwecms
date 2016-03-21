package com.pungwe.cms.core.block.system;

import com.pungwe.cms.core.annotations.stereotypes.Block;
import com.pungwe.cms.core.annotations.ui.ThemeInfo;
import com.pungwe.cms.core.block.BlockDefinition;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.basic.*;
import com.pungwe.cms.core.form.Form;
import com.pungwe.cms.core.form.FormState;
import com.pungwe.cms.core.menu.MenuConfig;
import com.pungwe.cms.core.menu.services.MenuManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by ian on 05/03/2016.
 */
@Block(value="primary_menu_block", label="Primary Menu Block", category = "System")
@ThemeInfo("blocks/primary_menu_block")
public class PrimaryMenuBlock implements BlockDefinition {

	@Autowired
	protected MenuManagementService menuManagementService;

	@Override
	public Map<String, Object> getDefaultSettings() {
		return new HashMap<>();
	}

	@Override
	public void build(List<RenderedElement> elements, Map<String, Object> settings, Map<String, Object> variables) {
		final UnorderedListElement element = new UnorderedListElement();

		// Request
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String currentPath = request.getRequestURI().substring(request.getContextPath().length());

		String menu = null;
		if (settings.containsKey("menu")) {
			menu = settings.get("menu").toString();
		} else if (currentPath.startsWith("/admin")) {
			// Admin always starts with
			menu = "system";
		} else {
			return; // do nothing
		}

		// Set Element ID
		element.setHtmlId("primary_menu_" + menu);
		element.addClass(settings.getOrDefault("menu_class", "").toString());

		// Get the active menu item
		List<String> activeItems = menuManagementService.getMenuTreeByUrl(menu, currentPath).stream().map(activeItem -> activeItem.getId()).collect(Collectors.toList());

		// Build the menu!
		List<MenuConfig> menuItems = null;
		if (menu == "system") {
			menuItems = menuManagementService.getMenuItems(menu, "admin");
		} else {
			menuItems = menuManagementService.getTopLevelMenuItems(menu);
		}

		if (menuItems.isEmpty()) {
			return;
		}

		menuItems.stream().sorted().forEach(menuConfig -> {
			AnchorElement anchor = new AnchorElement();
			anchor.setTitle(menuConfig.getDescription());
			anchor.setContent(new PlainTextElement(menuConfig.getTitle()));
			anchor.setTarget(menuConfig.getTarget());
			// Set the url
			Pattern p = Pattern.compile("^https?\\://|ftp\\://");
			if (p.matcher(menuConfig.getUrl()).matches()) {
				anchor.setHref(menuConfig.getUrl());
			} else {
				anchor.setHref(request.getContextPath() + "/" + menuConfig.getUrl().replaceAll("^/", ""));
			}
			ListElement.ListItem listItem = new ListElement.ListItem(anchor);
			if (activeItems.contains(menuConfig.getId())) {
				listItem.addClass(settings.getOrDefault("active_item_class", "active").toString());
			}
			element.addItem(listItem);
		});

		elements.add(element);
	}

	@Override
	public void buildSettingsForm(List<RenderedElement> elements, Form form, FormState state) {
		// No need for a configuration form here
	}

}
