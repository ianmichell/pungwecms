/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.block.system;

import com.pungwe.cms.config.TestConfig;
import com.pungwe.cms.core.config.BaseApplicationConfig;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.basic.AnchorElement;
import com.pungwe.cms.core.element.basic.OrderedListElement;
import com.pungwe.cms.core.element.basic.PlainTextElement;
import com.pungwe.cms.core.element.basic.UnorderedListElement;
import com.pungwe.cms.core.menu.MenuInfo;
import com.pungwe.cms.core.menu.services.MenuManagementService;
import com.pungwe.cms.test.AbstractWebTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.LocaleResolver;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({TestConfig.class, BaseApplicationConfig.class})
@WebAppConfiguration("src/main/resources")
public class MenuBlockTest extends AbstractWebTest {

    @Autowired
    MenuBlock menuBlock;

    @Autowired
    MenuManagementService menuManagementService;

    @Autowired
    LocaleResolver localeResolver;

    @Test
    public void testWithNoMenu() {
        List<RenderedElement> elements = new ArrayList<>();
        menuBlock.build(elements, new HashMap<>(), new HashMap<>());
        assertEquals(0, elements.size());
        assertEquals(0, menuBlock.getDefaultSettings().size());
    }

    @Test
    public void testWithMenu() {
        // Build a menu
        MenuInfo menuInfo = menuManagementService.createMenu("My Menu", "", "en");
        menuManagementService.saveMenuInfo(menuInfo);

        menuManagementService.createMenuItem(menuInfo.getId(), "", "home", "Homepage", "", false, "_self", "/", 0, false);

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(
                new MockHttpServletRequest(RequestMethod.GET.name(), "/")));
        RequestContextHolder.currentRequestAttributes().setAttribute(
                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE, "/", RequestAttributes.SCOPE_REQUEST);

        Map<String, Object> model = new HashMap<>();
        Map<String, Object> settings = new HashMap<>();
        settings.put("menu", menuInfo.getId());
        List<RenderedElement> elements = new LinkedList<>();
        menuBlock.build(elements, settings, model);

        assertFalse(elements.isEmpty());
        assertEquals(UnorderedListElement.class, elements.get(0).getClass());
        assertEquals("Homepage", ((PlainTextElement)((AnchorElement)((UnorderedListElement) elements.get(0))
                .getItems().get(0).getContent().get(0)).getContent().get(0)).getText());
    }

    @Test
    public void testWithEmptyMenu() {

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(
                new MockHttpServletRequest(RequestMethod.GET.name(), "/")));
        RequestContextHolder.currentRequestAttributes().setAttribute(
                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE, "/", RequestAttributes.SCOPE_REQUEST);

        Map<String, Object> model = new HashMap<>();
        Map<String, Object> settings = new HashMap<>();
        settings.put("menu", "menu");
        List<RenderedElement> elements = new LinkedList<>();
        menuBlock.build(elements, settings, model);

        assertTrue(elements.isEmpty());
    }

    @Test
    public void testWithBlankParentMenuItem() {
        // Build a menu
        MenuInfo menuInfo = menuManagementService.createMenu("My Menu", "", "en");
        menuManagementService.saveMenuInfo(menuInfo);

        menuManagementService.createMenuItem(menuInfo.getId(), "", "home", "Homepage", "", false, "_self", "/", 0, false);
        menuManagementService.createMenuItem(menuInfo.getId(), "home", "my_link", "My Link", "", false, "_self", "/my_link", 0, false);

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(
                new MockHttpServletRequest(RequestMethod.GET.name(), "/")));
        RequestContextHolder.currentRequestAttributes().setAttribute(
                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE, "/", RequestAttributes.SCOPE_REQUEST);

        Map<String, Object> model = new HashMap<>();
        Map<String, Object> settings = new HashMap<>();
        settings.put("menu", menuInfo.getId());
        settings.put("parent_menu_item", null);
        List<RenderedElement> elements = new LinkedList<>();
        menuBlock.build(elements, settings, model);

        assertFalse(elements.isEmpty());
        assertEquals(UnorderedListElement.class, elements.get(0).getClass());
        assertEquals("Homepage", ((PlainTextElement) ((AnchorElement) ((UnorderedListElement) elements.get(0))
                .getItems().get(0).getContent().get(0)).getContent().get(0)).getText());
    }

    @Test
    public void testWithParentMenuItem() {
        // Build a menu
        MenuInfo menuInfo = menuManagementService.createMenu("My Menu", "", "en");
        menuManagementService.saveMenuInfo(menuInfo);

        menuManagementService.createMenuItem(menuInfo.getId(), "", "home", "Homepage", "", false, "_self", "/", 0, false);
        menuManagementService.createMenuItem(menuInfo.getId(), "home", "my_link", "My Link", "", false, "_self", "/my_link", 0, false);

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(
                new MockHttpServletRequest(RequestMethod.GET.name(), "/")));
        RequestContextHolder.currentRequestAttributes().setAttribute(
                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE, "/", RequestAttributes.SCOPE_REQUEST);

        Map<String, Object> model = new HashMap<>();
        Map<String, Object> settings = new HashMap<>();
        settings.put("menu", menuInfo.getId());
        settings.put("parent_menu_item", "home");
        List<RenderedElement> elements = new LinkedList<>();
        menuBlock.build(elements, settings, model);

        assertFalse(elements.isEmpty());
        assertEquals(UnorderedListElement.class, elements.get(0).getClass());
        assertEquals("My Link", ((PlainTextElement)((AnchorElement)((UnorderedListElement) elements.get(0))
                .getItems().get(0).getContent().get(0)).getContent().get(0)).getText());
    }

    @Test
    public void testWithParameterUrlWithActiveParentMenuItem() {
        // Build a menu
        MenuInfo menuInfo = menuManagementService.createMenu("My Menu", "", "en");
        menuManagementService.saveMenuInfo(menuInfo);

        menuManagementService.createMenuItem(menuInfo.getId(), "", "home", "Homepage", "", false, "_self", "/", 0, false);
        menuManagementService.createMenuItem(menuInfo.getId(), "home", "my_link", "My Link", "", false, "_self", "/my_link", 0, false);
        menuManagementService.createMenuItem(menuInfo.getId(), "my_link", "parameter", "Parameter", "", false, "_self", "/{parameter}", 0, true);

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(
                new MockHttpServletRequest(RequestMethod.GET.name(), "/test")));
        RequestContextHolder.currentRequestAttributes().setAttribute(
                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE, "/test}", RequestAttributes.SCOPE_REQUEST);
        RequestContextHolder.currentRequestAttributes().setAttribute(
                HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE, "/{parameter}", RequestAttributes.SCOPE_REQUEST);

        Map<String, Object> model = new HashMap<>();
        Map<String, Object> settings = new HashMap<>();
        settings.put("menu", menuInfo.getId());
        settings.put("parent_menu_item", "home");
        List<RenderedElement> elements = new LinkedList<>();
        menuBlock.build(elements, settings, model);

        assertFalse(elements.isEmpty());
        assertEquals(UnorderedListElement.class, elements.get(0).getClass());
        assertEquals("My Link", ((PlainTextElement)((AnchorElement)((UnorderedListElement) elements.get(0))
                .getItems().get(0).getContent().get(0)).getContent().get(0)).getText());
    }
}
