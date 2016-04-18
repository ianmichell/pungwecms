package com.pungwe.cms.themes.test;

import com.pungwe.cms.core.annotations.util.Hook;
import com.pungwe.cms.core.annotations.stereotypes.Theme;
import com.pungwe.cms.core.block.services.BlockManagementService;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ian on 13/02/2016.
 */
@Theme(name = "test_theme", label = "Test Theme", description = "This is a test theme with no parent")
public class TestTheme {

    @Autowired
    BlockManagementService blockManagementService;

    @Hook("install")
    public void install() {

        // Create a list of the default blocks that will be used...
        blockManagementService.addBlockToTheme("test_theme_page_title_block", "test_theme", "header", "page_title_block", -100, new HashMap<>());
        blockManagementService.addBlockToTheme("test_theme_breadcrumb_block", "test_theme", "breadcrumb", "breadcrumb_block", -100, breadcrumbBlockSettings());
        blockManagementService.addBlockToTheme("test_theme_status_message_block", "test_theme", "highlighted", "status_message_block", -100, new HashMap<>());
        blockManagementService.addBlockToTheme("test_theme_menu_block", "test_theme", "sidebar_first", "menu_block", -100, primaryMenuSettings());
        blockManagementService.addBlockToTheme("test_theme_system_tasks_block", "test_theme", "content", "system_tasks_block", -101, taskBlockSettings());
        blockManagementService.addBlockToTheme("test_theme_main_content_block", "test_theme", "content", "main_content_block", -100, new HashMap<>());
    }


    private Map<String, Object> primaryMenuSettings() {
        Map<String, Object> settings = new HashMap<String, Object>();
        settings.put("menu_class", "nav navbar-nav");
        settings.put("active_item_class", "active");
        settings.put("menu", "main_navigation");
        return settings;
    }

    private Map<String, Object> taskBlockSettings() {
        Map<String, Object> map = new HashMap<>();
        map.put("menu", "main_navigation");
        return map;
    }

    private Map<String, Object> breadcrumbBlockSettings() {
        Map<String, Object> map = new HashMap<>();
        map.put("menu", "main_navigation");
        return map;
    }

    @Hook("theme")
    public Map<String, String> themeHook() {
        HashMap<String, String> theme = new HashMap<>();
        theme.put("my_view", "my_theme/my_view");
        return theme;
    }
}
