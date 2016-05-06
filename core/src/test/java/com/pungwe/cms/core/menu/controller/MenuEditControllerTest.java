/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.menu.controller;

import com.pungwe.cms.config.TestConfig;
import com.pungwe.cms.core.config.BaseApplicationConfig;
import com.pungwe.cms.core.menu.MenuInfo;
import com.pungwe.cms.core.menu.impl.MenuInfoServiceImpl;
import com.pungwe.cms.core.menu.services.MenuManagementService;
import com.pungwe.cms.test.AbstractControllerTest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({TestConfig.class, BaseApplicationConfig.class})
@WebAppConfiguration("src/main/resources")
public class MenuEditControllerTest extends AbstractControllerTest {

    @Autowired
    MenuManagementService menuManagementService;

    @Autowired
    MenuInfoServiceImpl menuInfoService;

    @Autowired
    LocaleResolver localeResolver;

    @Test
    public void testGetInvalidMenu() throws Exception {
        menuInfoService.menus.clear();

        MvcResult result = mockMvc.perform(get("/admin/structure/menu/edit/invalid/en")).andExpect(status().isNotFound()).andReturn();
    }

    @Test
    public void testGetForm() throws Exception {

        menuInfoService.menus.clear();

        MenuInfo info = menuManagementService.createMenu("My Menu", "My Menu Description", "en");

        MvcResult result = mockMvc.perform(get("/admin/structure/menu/edit/" + info.getId() + "/en")).andExpect(request().asyncStarted()).andReturn();
        result.getAsyncResult();

        MvcResult finalResult = mockMvc.perform(asyncDispatch(result)).andExpect(status().isOk()).andExpect(content().contentType("text/html;charset=UTF-8")).andExpect(view().name("system/html")).andReturn();
        String content = finalResult.getResponse().getContentAsString();
        System.out.println(content);
        Document doc = Jsoup.parse(content);

        assertEquals("Edit Menu", doc.select("head title").text());
        assertEquals("Edit Menu", doc.select(".page_title_block h1").html());

        // Labels
        assertEquals("Title", doc.select("label[for=edit_menu_form_title]").html());
        assertEquals("Description", doc.select("label[for=edit_menu_form_description]").html());

        // Input types
        assertEquals("text", doc.select("#edit_menu_form_title").attr("type"));
        assertEquals("text", doc.select("#edit_menu_form_description").attr("type"));

        // Input Place holders
        assertEquals("Menu Title", doc.select("#edit_menu_form_title").attr("placeholder"));
        assertEquals("Administrative Description", doc.select("#edit_menu_form_description").attr("placeholder"));

        // Input Value
        assertEquals("My Menu", doc.select("#edit_menu_form_title").val());
        assertEquals("My Menu Description", doc.select("#edit_menu_form_description").val());
    }

    @Test
    public void testPostForm() throws Exception {

        menuInfoService.menus.clear();

        MenuInfo info = menuManagementService.createMenu("My Menu", "My Menu Description", "en");

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.put("title[0].value", Arrays.asList("My Menu FR"));
        formData.put("description[0].value", Arrays.asList("My Menu Description FR"));

        MvcResult result = mockMvc.perform(post("/admin/structure/menu/edit/" + info.getId() + "/en").params(formData))
                .andExpect(request().asyncStarted()).andReturn();
        result.getAsyncResult();

        MvcResult finalResult = mockMvc.perform(asyncDispatch(result)).andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin/structure/menu/edit/" + info.getId() + "/en"))
                .andReturn();

        assertEquals(1, menuInfoService.menus.size());
    }

    @Test
    public void testPostFormMissingTitle() throws Exception {

        menuInfoService.menus.clear();

        MenuInfo info = menuManagementService.createMenu("My Menu", "My Menu Description", "en");

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.put("title[0].value", Arrays.asList(""));
        formData.put("description[0].value", Arrays.asList("My Menu Description fr"));

        MvcResult result = mockMvc.perform(post("/admin/structure/menu/edit/" + info.getId() + "/en").params(formData))
                .andExpect(request().asyncStarted()).andReturn();
        result.getAsyncResult();

        MvcResult finalResult = mockMvc.perform(asyncDispatch(result)).andExpect(status().isOk()).andExpect(content().contentType("text/html;charset=UTF-8")).andExpect(view().name("system/html")).andReturn();
        String content = finalResult.getResponse().getContentAsString();
        System.out.println(content);
        Document doc = Jsoup.parse(content);

        assertEquals(1, menuInfoService.menus.size());

        assertEquals("Edit Menu", doc.select("head title").text());
        assertEquals("Edit Menu", doc.select(".page_title_block h1").html());

        // Labels
        assertEquals("Title", doc.select("label[for=edit_menu_form_title]").html());
        assertEquals("Description", doc.select("label[for=edit_menu_form_description]").html());

        // Input types
        assertEquals("text", doc.select("#edit_menu_form_title").attr("type"));
        assertEquals("text", doc.select("#edit_menu_form_description").attr("type"));

        // Input Place holders
        assertEquals("Menu Title", doc.select("#edit_menu_form_title").attr("placeholder"));
        assertEquals("Administrative Description", doc.select("#edit_menu_form_description").attr("placeholder"));

        // Input Value
        assertEquals("", doc.select("#edit_menu_form_title").val());
        assertEquals("My Menu Description fr", doc.select("#edit_menu_form_description").val());

        // Check for the error block!
        assertEquals("Field is required", doc.select(".status-message.error-message li:nth-child(1)").html());
    }

    @Test
    public void testPostFormMissingDescription() throws Exception {

        menuInfoService.menus.clear();

        MenuInfo info = menuManagementService.createMenu("My Menu", "My Menu Description", "en");

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.put("title[0].value", Arrays.asList("My Menu fr"));

        MvcResult result = mockMvc.perform(post("/admin/structure/menu/edit/" + info.getId() + "/en").params(formData))
                .andExpect(request().asyncStarted()).andReturn();
        result.getAsyncResult();

        MvcResult finalResult = mockMvc.perform(asyncDispatch(result)).andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin/structure/menu/edit/" + info.getId() + "/en"))
                .andReturn();

        assertEquals(1, menuInfoService.menus.size());
    }
}
