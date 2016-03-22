package com.pungwe.cms.core.block.system;

import com.google.common.collect.Iterables;
import com.pungwe.cms.core.annotations.stereotypes.Block;
import com.pungwe.cms.core.annotations.ui.MenuItems;
import com.pungwe.cms.core.block.BlockDefinition;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.basic.AnchorElement;
import com.pungwe.cms.core.element.basic.ListElement;
import com.pungwe.cms.core.element.basic.PlainTextElement;
import com.pungwe.cms.core.element.basic.UnorderedListElement;
import com.pungwe.cms.core.form.Form;
import com.pungwe.cms.core.form.FormState;
import com.pungwe.cms.core.menu.MenuConfig;
import com.pungwe.cms.core.menu.services.MenuManagementService;
import com.pungwe.cms.core.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by ian on 21/03/2016.
 */
@Block(category = "system", label = "System Tasks Block", value = "system_tasks_block")
public class SystemTasksBlock implements BlockDefinition {

	@Autowired
	protected MenuManagementService menuManagementService;

	@Override
	public Map<String, Object> getDefaultSettings() {
		return new HashMap<>();
	}

	@Override
	public void build(List<RenderedElement> elements, Map<String, Object> settings, Map<String, Object> variables) {

		final UnorderedListElement element = new UnorderedListElement();

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
		element.setHtmlId("local_tasks_" + menu);
		element.addClass(settings.getOrDefault("menu_class", "").toString());

		if (Utils.hasRequestPathVariable()) {
			currentPath = Utils.getRequestPathVariablePattern();
		}

		// Get the active menu item
		List<MenuConfig> activeItems = menuManagementService.getMenuTreeByUrl(menu, currentPath).stream().collect(Collectors.toList());
		List<String> activeIds = activeItems.stream().map(menuConfig -> menuConfig.getId()).collect(Collectors.toList());
		List<MenuConfig> menuItemsWithoutTasks = activeItems.stream().filter(mc -> !mc.isTask()).collect(Collectors.toList());

		// Get the last menu item
		MenuConfig bottom = Iterables.getLast(menuItemsWithoutTasks);

		// Fetch the tasks for this menu item
		List<MenuConfig> tasks = menuManagementService.getTasksByPath(menu, bottom.getPath());

		// Add tasks to element list
		tasks.stream().sorted().forEach(menuConfig -> {
			AnchorElement anchor = new AnchorElement();
			anchor.setTitle(menuConfig.getDescription());
			anchor.setContent(new PlainTextElement(menuConfig.getTitle()));
			anchor.setTarget(menuConfig.getTarget());
			// Set the url
			Pattern p = Pattern.compile("^https?\\://|ftp\\://");
			if (p.matcher(menuConfig.getUrl()).matches()) {
				anchor.setHref(menuConfig.getUrl());
			} else {
				anchor.setHref(processUrlVariables(request.getContextPath() + "/" + menuConfig.getUrl().replaceAll("^/", ""), variables));
			}
			ListElement.ListItem listItem = new ListElement.ListItem(anchor);
			if (activeItems.contains(menuConfig.getId())) {
				listItem.addClass(settings.getOrDefault("active_item_class", "active").toString());
			}
			element.addItem(listItem);
		});

	}

	private String processUrlVariables(String url, Map<String, Object> variables) {
		return url;
	}

	@Override
	public void buildSettingsForm(List<RenderedElement> elements, Form form, FormState state) {

	}
}
