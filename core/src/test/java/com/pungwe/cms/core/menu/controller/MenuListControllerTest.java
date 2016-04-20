/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.menu.controller;

import com.pungwe.cms.config.TestConfig;
import com.pungwe.cms.core.config.BaseApplicationConfig;
import com.pungwe.cms.core.menu.impl.MenuConfigServiceImpl;
import com.pungwe.cms.core.menu.impl.MenuInfoServiceImpl;
import com.pungwe.cms.core.menu.services.MenuManagementService;
import com.pungwe.cms.test.AbstractControllerTest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.LocaleResolver;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({TestConfig.class, BaseApplicationConfig.class})
@WebAppConfiguration("src/main/resources")
public class MenuListControllerTest extends AbstractControllerTest {

    @Autowired
    MenuManagementService menuManagementService;

    @Autowired
    MenuInfoServiceImpl menuInfoService;

    @Autowired
    LocaleResolver localeResolver;

    @Test
    public void testListWithNoMenus() throws Exception {

        // Ensure the menus are totally cleared out...
        menuInfoService.menus.clear();

        MvcResult result = mockMvc.perform(get("/admin/structure/menu")).andExpect(request().asyncStarted()).andReturn();
        result.getAsyncResult();

        MvcResult finalResult = mockMvc.perform(asyncDispatch(result)).andExpect(status().isOk()).andExpect(content().contentType("text/html;charset=UTF-8")).andExpect(view().name("system/html")).andReturn();
        String content = finalResult.getResponse().getContentAsString();
        Document doc = Jsoup.parse(content);

        assertEquals("Menus", doc.select("head title").text());
        assertEquals("Menus", doc.select(".page_title_block h1").html());
        assertEquals(3, doc.select("table thead th").size());
        assertEquals("Title", doc.select("table thead th:nth-child(1)").html());
        assertEquals("Description", doc.select("table thead th:nth-child(2)").html());
        assertEquals("Operations", doc.select("table thead th:nth-child(3)").html());

        assertEquals("There are no menus defined yet...", doc.select("table tbody tr td:nth-child(1)").html());
    }

    @Test
    public void testListWithMenu() throws Exception {

        // Ensure the menus are totally cleared out...
        menuInfoService.menus.clear();

        menuManagementService.createMenu("My Menu", "My Menu Description", localeResolver.resolveLocale(new MockHttpServletRequest()).getLanguage());

        MvcResult result = mockMvc.perform(get("/admin/structure/menu")).andExpect(request().asyncStarted()).andReturn();
        result.getAsyncResult();

        MvcResult finalResult = mockMvc.perform(asyncDispatch(result)).andExpect(status().isOk()).andExpect(content().contentType("text/html;charset=UTF-8")).andExpect(view().name("system/html")).andReturn();
        String content = finalResult.getResponse().getContentAsString();
        Document doc = Jsoup.parse(content);

        assertEquals("Menus", doc.select("head title").text());
        assertEquals("Menus", doc.select(".page_title_block h1").html());
        assertEquals(3, doc.select("table thead th").size());
        assertEquals("Title", doc.select("table thead th:nth-child(1)").html());
        assertEquals("Description", doc.select("table thead th:nth-child(2)").html());
        assertEquals("Operations", doc.select("table thead th:nth-child(3)").html());

        assertEquals("My Menu", doc.select("table tbody tr:nth-child(1) td:nth-child(1)").html());
        assertEquals("My Menu Description", doc.select("table tbody tr:nth-child(1) td:nth-child(2)").html());
        assertEquals("Edit", doc.select("table tbody tr:nth-child(1) td:nth-child(3) a").html());
    }
}
