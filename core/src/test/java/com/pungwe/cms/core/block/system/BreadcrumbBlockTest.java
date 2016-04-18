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
import com.pungwe.cms.core.element.model.ModelAndViewElement;
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
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({TestConfig.class, BaseApplicationConfig.class})
@WebAppConfiguration("src/main/resources")
public class BreadcrumbBlockTest extends AbstractWebTest {

    @Autowired
    protected BreadcrumbBlock breadcrumbBlock;

    @Autowired
    protected MenuManagementService menuManagementService;

    @Test
    public void testEmptyModel() throws Exception {

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(
                new MockHttpServletRequest(RequestMethod.GET.name(), "/")));

        Map<String, Object> model = new HashMap<>();
        Map<String, Object> settings = new HashMap<>();
        List<RenderedElement> elements = new LinkedList<>();
        breadcrumbBlock.build(elements, settings, model);

        assertTrue(elements.isEmpty());
    }

    @Test
    public void testWithPathAndMenuItem() throws Exception {

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
        breadcrumbBlock.build(elements, settings, model);

        assertFalse(elements.isEmpty());
        assertEquals(OrderedListElement.class, elements.get(0).getClass());
        assertEquals("Homepage", ((PlainTextElement) ((OrderedListElement) elements.get(0))
                .getItems().get(0).getContent().get(0)).getText());
    }

    @Test
    public void testWithPathAndMissingMenu() throws Exception {

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(
                new MockHttpServletRequest(RequestMethod.GET.name(), "/")));
        RequestContextHolder.currentRequestAttributes().setAttribute(
                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE, "/", RequestAttributes.SCOPE_REQUEST);

        Map<String, Object> model = new HashMap<>();
        Map<String, Object> settings = new HashMap<>();
        settings.put("menu", "my_menu");
        List<RenderedElement> elements = new LinkedList<>();
        breadcrumbBlock.build(elements, settings, model);

        assertTrue(elements.isEmpty());
    }
    @Test
    public void testWithPathAndAdminMenu() throws Exception {

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(
                new MockHttpServletRequest(RequestMethod.GET.name(), "/admin")));
        RequestContextHolder.currentRequestAttributes().setAttribute(
                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE, "/admin", RequestAttributes.SCOPE_REQUEST);

        Map<String, Object> model = new HashMap<>();
        Map<String, Object> settings = new HashMap<>();
        settings.put("menu", "my_menu");
        List<RenderedElement> elements = new LinkedList<>();
        breadcrumbBlock.build(elements, settings, model);

        assertTrue(elements.isEmpty());
    }


    @Test
    public void testWithPathAndMenuItemAndEmbeddeContent() throws Exception {

        // Build a menu
        MenuInfo menuInfo = menuManagementService.createMenu("My Menu", "", "en");
        menuManagementService.saveMenuInfo(menuInfo);

        menuManagementService.createMenuItem(menuInfo.getId(), "", "home", "Homepage", "", false, "_self", "/", 0, false);
        menuManagementService.createMenuItem(menuInfo.getId(), "home", "link", "Link", "", false, "_self", "/{link}", 0, true);

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(
                new MockHttpServletRequest(RequestMethod.GET.name(), "/link")));

        RequestContextHolder.currentRequestAttributes().setAttribute(
                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE, "/link", RequestAttributes.SCOPE_REQUEST);
        RequestContextHolder.currentRequestAttributes().setAttribute(
                HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE, "/{link}", RequestAttributes.SCOPE_REQUEST);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("link", "link");
        modelAndView.setViewName("test");

        Map<String, Object> variables = new HashMap<>();
        variables.put("content", modelAndView);
        Map<String, Object> settings = new HashMap<>();
        settings.put("menu", menuInfo.getId());

        List<RenderedElement> elements = new LinkedList<>();
        breadcrumbBlock.build(elements, settings, variables);

        assertFalse(elements.isEmpty());
        assertEquals(OrderedListElement.class, elements.get(0).getClass());
        assertEquals("Link", ((PlainTextElement)((OrderedListElement) elements.get(0))
                .getItems().get(1).getContent().get(0)).getText());
    }

    @Test
    public void testWithPathAndMenuItemAndEmbeddeContentElement() throws Exception {

        // Build a menu
        MenuInfo menuInfo = menuManagementService.createMenu("My Menu", "", "en");
        menuManagementService.saveMenuInfo(menuInfo);

        menuManagementService.createMenuItem(menuInfo.getId(), "", "home", "Homepage", "", false, "_self", "/", 0, false);
        menuManagementService.createMenuItem(menuInfo.getId(), "home", "link", "Link", "", false, "_self", "/{link}", 0, true);

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(
                new MockHttpServletRequest(RequestMethod.GET.name(), "/link")));

        RequestContextHolder.currentRequestAttributes().setAttribute(
                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE, "/link", RequestAttributes.SCOPE_REQUEST);
        RequestContextHolder.currentRequestAttributes().setAttribute(
                HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE, "/{link}", RequestAttributes.SCOPE_REQUEST);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("link", "link");
        modelAndView.setViewName("test");

        Map<String, Object> variables = new HashMap<>();
        variables.put("content", new ModelAndViewElement(modelAndView));
        Map<String, Object> settings = new HashMap<>();
        settings.put("menu", menuInfo.getId());

        List<RenderedElement> elements = new LinkedList<>();
        breadcrumbBlock.build(elements, settings, variables);

        assertFalse(elements.isEmpty());
        assertEquals(OrderedListElement.class, elements.get(0).getClass());
        assertEquals("Link", ((PlainTextElement)((OrderedListElement) elements.get(0))
                .getItems().get(1).getContent().get(0)).getText());
    }
}
