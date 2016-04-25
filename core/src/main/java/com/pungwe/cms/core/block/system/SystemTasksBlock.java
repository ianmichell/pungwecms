package com.pungwe.cms.core.block.system;

import com.google.common.collect.Iterables;
import com.pungwe.cms.core.annotations.stereotypes.Block;
import com.pungwe.cms.core.block.BlockDefinition;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.basic.AnchorElement;
import com.pungwe.cms.core.element.basic.ListElement;
import com.pungwe.cms.core.element.basic.PlainTextElement;
import com.pungwe.cms.core.element.basic.UnorderedListElement;
import com.pungwe.cms.core.element.model.ModelAndViewElement;
import com.pungwe.cms.core.menu.MenuConfig;
import com.pungwe.cms.core.menu.services.MenuManagementService;
import com.pungwe.cms.core.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by ian on 21/03/2016.
 */
@Block(category = "system", label = "System Tasks Block", value = "system_tasks_block")
public class SystemTasksBlock implements BlockDefinition {

    private static final Logger log = LoggerFactory.getLogger(SystemTasksBlock.class);

	@Autowired
	protected MenuManagementService menuManagementService;

	@Override
	public Map<String, Object> getDefaultSettings() {
		return new HashMap<>();
	}

	@Override
	public void build(List<RenderedElement> elements, Map<String, Object> settings, Map<String, Object> variables) {

		final UnorderedListElement element = new UnorderedListElement();

		String currentPath = Utils.getRequestPath();

        Map uriVariables = new HashMap<>();
        if (variables.containsKey("content")) {
            Object content = variables.get("content");
            if (content instanceof ModelAndViewElement) {
                uriVariables.putAll(((ModelAndViewElement) content).getContent().getModel());
            } else if (content instanceof ModelAndView) {
                uriVariables.putAll(((ModelAndView) content).getModel());
            }
        } else {
            uriVariables.putAll(variables);
        }

		String menu = null;
		if (settings.containsKey("menu")) {
			menu = settings.get("menu").toString();
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

        if (menuItemsWithoutTasks.size() == 0) {
            return;
        }
		// Get the last menu item
		MenuConfig bottom = Iterables.getLast(menuItemsWithoutTasks);

		// Fetch the tasks for this menu item
		List<MenuConfig> tasks = menuManagementService.getTasksByPath(menu, bottom.getPath());

        try {
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
                    anchor.setHref(Utils.processUrlVariables(Utils.getRequestContextPath() + "/" + menuConfig.getUrl().replaceAll("^/", ""), uriVariables));
                }
                ListElement.ListItem listItem = new ListElement.ListItem(anchor);
                if (activeIds.contains(menuConfig.getId())) {
                    listItem.addClass(settings.getOrDefault("active_item_class", "active").toString());
                }
                element.addItem(listItem);
            });

            if (element.getItems().size() > 0) {
                elements.add(element);
            }
        } catch (IllegalArgumentException ex) {
            // do nothing... If all the arguments are not there, then don't display it
            log.warn("Tasks for menu item: " + bottom.getName() + " are missing their uriVariables", ex);
        }

	}

	@Override
	public void buildSettingsForm(List<RenderedElement> elements, Map<String, Object> settings) {

	}
}
