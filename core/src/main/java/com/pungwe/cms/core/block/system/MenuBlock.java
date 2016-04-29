/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.block.system;

import com.pungwe.cms.core.annotations.stereotypes.Block;
import com.pungwe.cms.core.annotations.ui.ThemeInfo;
import com.pungwe.cms.core.block.BlockDefinition;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.basic.AnchorElement;
import com.pungwe.cms.core.element.basic.ListElement;
import com.pungwe.cms.core.element.basic.PlainTextElement;
import com.pungwe.cms.core.element.basic.UnorderedListElement;
import com.pungwe.cms.core.form.element.SingleSelectListElement;
import com.pungwe.cms.core.form.element.TextElement;
import com.pungwe.cms.core.menu.MenuConfig;
import com.pungwe.cms.core.menu.MenuInfo;
import com.pungwe.cms.core.menu.services.MenuManagementService;
import com.pungwe.cms.core.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.pungwe.cms.core.utils.Utils.getRequest;
import static com.pungwe.cms.core.utils.Utils.translate;

@Block(value="menu_block", label="Primary Menu Block", category = "System")
@ThemeInfo("blocks/menu_block")
public class MenuBlock implements BlockDefinition {

    @Autowired
    private MenuManagementService menuManagementService;

    @Autowired
    private LocaleResolver localeResolver;

    @Override
    public Map<String, Object> getDefaultSettings() {
        return new LinkedHashMap<>();
    }

    @Override
    public void build(List<RenderedElement> elements, Map<String, Object> settings, Map<String, Object> variables) {
        final UnorderedListElement element = new UnorderedListElement();

        // Request
        String currentPath = Utils.getRequestPath();

        String menu = null;
        if (settings.containsKey("menu")) {
            menu = settings.get("menu").toString();
        } else {
            return; // do nothing
        }

        // Set Element ID
        element.setHtmlId("menu_block_" + menu);
        element.addClass(settings.getOrDefault("menu_class", "").toString());

        if (Utils.hasRequestPathVariable()) {
            currentPath = Utils.getRequestPathVariablePattern();
        }

        // Get the active menu item
        List<String> activeItems = menuManagementService.getMenuTreeByUrl(menu, currentPath).stream().map(activeItem -> activeItem.getId()).collect(Collectors.toList());

        // Build the menu!
        List<MenuConfig> menuItems = null;
        if (settings.containsKey("parent_menu_item") && StringUtils.isNotBlank((String)settings.get("parent_menu_item"))) {
            menuItems = menuManagementService.getMenuItems(menu, (String)settings.get("parent_menu_item"));
        } else {
            menuItems = menuManagementService.getTopLevelMenuItems(menu);
        }

        if (menuItems.isEmpty()) {
            return;
        }

        menuItems.stream().filter(menuConfig -> !menuConfig.isPattern()).sorted().forEach(menuConfig -> {
            AnchorElement anchor = new AnchorElement();
            anchor.setTitle(menuConfig.getDescription());
            anchor.setContent(new PlainTextElement(menuConfig.getTitle()));
            anchor.setTarget(menuConfig.getTarget());
            // Set the url
            Pattern p = Pattern.compile("^https?\\://|ftp\\://");
            if (p.matcher(menuConfig.getUrl()).matches()) {
                anchor.setHref(menuConfig.getUrl());
            } else {
                anchor.setHref(Utils.getRequestContextPath() + "/" + menuConfig.getUrl().replaceAll("^/", ""));
            }
            ListElement.ListItem listItem = new ListElement.ListItem(anchor);
            listItem.addAttribute("data-menu-item-name", menuConfig.getName());
            if (activeItems.contains(menuConfig.getId())) {
                listItem.addClass(settings.getOrDefault("active_item_class", "active").toString());
            }
            element.addItem(listItem);
        });

        elements.add(element);
    }

    @Override
    public void buildSettingsForm(List<RenderedElement> elements, Map<String, Object> settings) {
        SingleSelectListElement menu = new SingleSelectListElement();
        menu.setName("menu");
        menu.setHtmlId("menu_block_menu_name");
        menu.setLabel(translate("Menu"));
        menu.setRequired(true);
        menu.setDefaultValue((String) settings.getOrDefault("menu", ""));
        List<MenuInfo> menuList = menuManagementService.listMenusByLanguage(localeResolver.resolveLocale(getRequest()).getLanguage());
        menuList.forEach(menuInfo -> menu.addOption(menuInfo.getTitle(), menuInfo.getId()));
        elements.add(menu);

        TextElement menuClass = new TextElement();
        menuClass.setName("menu");
        menuClass.setHtmlId("menu_block_menu_class");
        menuClass.setLabel(translate("Menu Class"));
        menuClass.setRequired(false);
        menuClass.setDefaultValue((String)settings.getOrDefault("menu_class", ""));
        elements.add(menuClass);
    }
}
