/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.menu.controller;

import com.pungwe.cms.config.TestConfig;
import com.pungwe.cms.core.config.BaseApplicationConfig;
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
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({TestConfig.class, BaseApplicationConfig.class})
@WebAppConfiguration("src/main/resources")
public class MenuAddControllerTest extends AbstractControllerTest {

    @Autowired
    MenuManagementService menuManagementService;

    @Autowired
    MenuInfoServiceImpl menuInfoService;

    @Autowired
    LocaleResolver localeResolver;

    @Test
    public void testGetForm() throws Exception {

        MvcResult result = mockMvc.perform(get("/admin/structure/menu/add")).andExpect(request().asyncStarted()).andReturn();
        result.getAsyncResult();

        MvcResult finalResult = mockMvc.perform(asyncDispatch(result)).andExpect(status().isOk()).andExpect(content().contentType("text/html;charset=UTF-8")).andExpect(view().name("system/html")).andReturn();
        String content = finalResult.getResponse().getContentAsString();
        System.out.println(content);
        Document doc = Jsoup.parse(content);

        assertEquals("Add Menu", doc.select("head title").text());
        assertEquals("Add Menu", doc.select(".page_title_block h1").html());

        // Labels
        assertEquals("Title", doc.select("label[for=add_menu_form_title]").html());
        assertEquals("Description", doc.select("label[for=add_menu_form_description]").html());
        assertEquals("Language", doc.select("label[for=add_menu_form_language]").html());

        // Input types
        assertEquals("text", doc.select("#add_menu_form_title").attr("type"));
        assertEquals("text", doc.select("#add_menu_form_description").attr("type"));
        assertTrue(doc.select("#add_menu_form_language").is("select"));

        // Input Place holders
        assertEquals("Menu Title", doc.select("#add_menu_form_title").attr("placeholder"));
        assertEquals("Administrative Description", doc.select("#add_menu_form_description").attr("placeholder"));

        // Default Select value
        assertEquals("en", doc.select("#add_menu_form_language option[selected]").attr("value"));
    }

    @Test
    public void testPostForm() throws Exception {

        menuInfoService.menus.clear();

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.put("title[0].value", Arrays.asList("My Menu"));
        formData.put("description[0].value", Arrays.asList("My Menu Description"));
        formData.put("language[0].value", Arrays.asList("en"));

        MvcResult result = mockMvc.perform(post("/admin/structure/menu/add").params(formData))
                .andExpect(request().asyncStarted()).andReturn();
        result.getAsyncResult();

        MvcResult finalResult = mockMvc.perform(asyncDispatch(result)).andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin/structure/menu"))
                .andReturn();

        assertEquals(1, menuInfoService.menus.size());
    }

    @Test
    public void testPostFormMissingTitle() throws Exception {

        menuInfoService.menus.clear();

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.put("description[0].value", Arrays.asList("My Menu Description"));
        formData.put("language[0].value", Arrays.asList("en"));

        MvcResult result = mockMvc.perform(post("/admin/structure/menu/add").params(formData))
                .andExpect(request().asyncStarted()).andReturn();
        result.getAsyncResult();

        MvcResult finalResult = mockMvc.perform(asyncDispatch(result)).andExpect(status().isOk()).andExpect(content().contentType("text/html;charset=UTF-8")).andExpect(view().name("system/html")).andReturn();
        String content = finalResult.getResponse().getContentAsString();
        System.out.println(content);
        Document doc = Jsoup.parse(content);

        assertEquals(0, menuInfoService.menus.size());

        assertEquals("Add Menu", doc.select("head title").text());
        assertEquals("Add Menu", doc.select(".page_title_block h1").html());

        // Labels
        assertEquals("Title", doc.select("label[for=add_menu_form_title]").html());
        assertEquals("Description", doc.select("label[for=add_menu_form_description]").html());
        assertEquals("Language", doc.select("label[for=add_menu_form_language]").html());

        // Input types
        assertEquals("text", doc.select("#add_menu_form_title").attr("type"));
        assertEquals("text", doc.select("#add_menu_form_description").attr("type"));
        assertTrue(doc.select("#add_menu_form_language").is("select"));

        // Input Place holders
        assertEquals("Menu Title", doc.select("#add_menu_form_title").attr("placeholder"));
        assertEquals("Administrative Description", doc.select("#add_menu_form_description").attr("placeholder"));

        // Default Select value
        assertEquals("en", doc.select("#add_menu_form_language option[selected]").attr("value"));

        // Input Value
        assertEquals("", doc.select("#add_menu_form_title").val());
        assertEquals("My Menu Description", doc.select("#add_menu_form_description").val());

        // Check for the error block!
        assertEquals("Please provide a menu title", doc.select(".status-message.error-message li:nth-child(1)").html());
    }

    @Test
    public void testPostFormMissingDescription() throws Exception {

        menuInfoService.menus.clear();

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.put("title[0].value", Arrays.asList("My Menu"));
        formData.put("language[0].value", Arrays.asList("en"));

        MvcResult result = mockMvc.perform(post("/admin/structure/menu/add").params(formData))
                .andExpect(request().asyncStarted()).andReturn();
        result.getAsyncResult();

        MvcResult finalResult = mockMvc.perform(asyncDispatch(result)).andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin/structure/menu"))
                .andReturn();

        assertEquals(1, menuInfoService.menus.size());
    }

    @Test
    public void testPostFormMissingLanguage() throws Exception {

        menuInfoService.menus.clear();

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.put("title[0].value", Arrays.asList("My Menu"));
        formData.put("description[0].value", Arrays.asList("My Menu Description"));

        MvcResult result = mockMvc.perform(post("/admin/structure/menu/add").params(formData))
                .andExpect(request().asyncStarted()).andReturn();
        result.getAsyncResult();

        MvcResult finalResult = mockMvc.perform(asyncDispatch(result)).andExpect(status().isOk()).andExpect(content().contentType("text/html;charset=UTF-8")).andExpect(view().name("system/html")).andReturn();
        String content = finalResult.getResponse().getContentAsString();
        System.out.println(content);
        Document doc = Jsoup.parse(content);

        assertEquals(0, menuInfoService.menus.size());

        assertEquals("Add Menu", doc.select("head title").text());
        assertEquals("Add Menu", doc.select(".page_title_block h1").html());

        // Labels
        assertEquals("Title", doc.select("label[for=add_menu_form_title]").html());
        assertEquals("Description", doc.select("label[for=add_menu_form_description]").html());
        assertEquals("Language", doc.select("label[for=add_menu_form_language]").html());

        // Input types
        assertEquals("text", doc.select("#add_menu_form_title").attr("type"));
        assertEquals("text", doc.select("#add_menu_form_description").attr("type"));
        assertTrue(doc.select("#add_menu_form_language").is("select"));

        // Input Place holders
        assertEquals("Menu Title", doc.select("#add_menu_form_title").attr("placeholder"));
        assertEquals("Administrative Description", doc.select("#add_menu_form_description").attr("placeholder"));

        // Default Select value
        assertEquals("en", doc.select("#add_menu_form_language option[selected]").attr("value"));

        // Input Value
        assertEquals("My Menu", doc.select("#add_menu_form_title").val());
        assertEquals("My Menu Description", doc.select("#add_menu_form_description").val());

        // Check for the error block!
        assertEquals("Please select a language", doc.select(".status-message.error-message li:nth-child(1)").html());
    }
}
