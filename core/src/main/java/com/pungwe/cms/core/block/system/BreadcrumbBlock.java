package com.pungwe.cms.core.block.system;

import com.pungwe.cms.core.annotations.stereotypes.Block;
import com.pungwe.cms.core.block.BlockDefinition;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.basic.AnchorElement;
import com.pungwe.cms.core.element.basic.ListElement;
import com.pungwe.cms.core.element.basic.OrderedListElement;
import com.pungwe.cms.core.element.basic.PlainTextElement;
import com.pungwe.cms.core.element.model.ModelAndViewElement;
import com.pungwe.cms.core.menu.MenuConfig;
import com.pungwe.cms.core.menu.services.MenuManagementService;
import com.pungwe.cms.core.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
        String currentPath = Utils.getRequestPath();

        if (Utils.hasRequestPathVariable()) {
            currentPath = Utils.getRequestPathVariablePattern();
        }

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
                anchor.setHref(Utils.processUrlVariables(Utils.getRequestContextPath() + "/" + menuConfig.getUrl().replaceAll("^/", ""), uriVariables));
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
    public void buildSettingsForm(List<RenderedElement> elements, Map<String, Object> settings) {
        // Build a great settings form here
    }
}
