package com.pungwe.cms.core.block.system;

import com.pungwe.cms.core.annotations.stereotypes.Block;
import com.pungwe.cms.core.block.BlockDefinition;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.basic.AnchorElement;
import com.pungwe.cms.core.element.basic.ListElement;
import com.pungwe.cms.core.element.basic.OrderedListElement;
import com.pungwe.cms.core.element.basic.PlainTextElement;
import com.pungwe.cms.core.form.Form;
import com.pungwe.cms.core.form.FormState;
import com.pungwe.cms.core.menu.MenuConfig;
import com.pungwe.cms.core.menu.services.MenuManagementService;
import com.pungwe.cms.core.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by ian on 05/03/2016.
 */
@Block(value = "breadcrumb_block", category = "System", label = "Breadcrumb Block")
public class BreadcrumbBlock implements BlockDefinition {

	@Autowired
	MenuManagementService menuManagementService;

	@Override
	public Map<String, Object> getDefaultSettings() {
		return new HashMap<>();
	}

	@Override
	public void build(List<RenderedElement> elements, Map<String, Object> settings, Map<String, Object> variables) {
		final OrderedListElement element = new OrderedListElement();

		// Request
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String currentPath = request.getRequestURI().substring(request.getContextPath().length());

        if (Utils.hasRequestPathVariable()) {
            currentPath = Utils.getRequestPathVariablePattern();
        }

		String menu = null;
		if (settings.containsKey("menu")) {
			menu = settings.get("menu").toString();
		} else if (currentPath.startsWith("/admin")) {
			// Admin always starts with
			menu = "system";
		} else {
			return; // do nothing
		}
		List<MenuConfig> breadcrumb = menuManagementService.getMenuTreeByUrl(menu, currentPath);
		Iterator<MenuConfig> it = breadcrumb.iterator();
		while (it.hasNext()) {
			MenuConfig menuConfig = it.next();
			if (it.hasNext()) {
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
				element.addItem(new ListElement.ListItem(anchor));
			} else {
				element.addItem(new ListElement.ListItem(menuConfig.getTitle()));
			}
		}

		if (element.getItems().isEmpty()) {
			return;
		}

		// Set the class
		element.addClass("breadcrumb");

		// Add the breadcrumb to the element list
		elements.add(element);
	}

	@Override
	public void buildSettingsForm(List<RenderedElement> elements, Form form, FormState state) {
		// Build a great settings form here
	}
}
