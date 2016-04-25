/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.system.admin;

import com.pungwe.cms.config.TestConfig;
import com.pungwe.cms.core.config.BaseApplicationConfig;
import com.pungwe.cms.test.AbstractControllerTest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({TestConfig.class, BaseApplicationConfig.class})
@WebAppConfiguration("src/main/resources")
public class AdminControllerTest extends AbstractControllerTest {

    @Test
    public void testIndex() throws Exception {

        MvcResult result = mockMvc.perform(get("/admin")).andExpect(request().asyncStarted()).andReturn();
        result.getAsyncResult();

        mockMvc.perform(asyncDispatch(result)).andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin/dashboard"));
    }

    @Test
    public void testDashboard() throws Exception {

        MvcResult result = mockMvc.perform(get("/admin/dashboard")).andExpect(request().asyncStarted()).andReturn();
        result.getAsyncResult();

        MvcResult finalResult = mockMvc.perform(asyncDispatch(result)).andExpect(status().isOk()).andExpect(content().contentType("text/html;charset=UTF-8")).andExpect(view().name("system/html")).andReturn();
        String content = finalResult.getResponse().getContentAsString();
        System.out.println(content);
        Document doc = Jsoup.parse(content);

        // Check basic markup, we can't cover everything without loads of changes to this test
        // but we can get an approximation of what's being returned pretty easily.
        assertEquals("Dashboard", doc.select("head title").text());
        assertEquals("Dashboard", doc.select("h1").html());
    }

    @Test
    public void testStructure() throws Exception {

        MvcResult result = mockMvc.perform(get("/admin/structure")).andExpect(request().asyncStarted()).andReturn();
        result.getAsyncResult();

        MvcResult finalResult = mockMvc.perform(asyncDispatch(result)).andExpect(status().isOk()).andExpect(content().contentType("text/html;charset=UTF-8")).andExpect(view().name("system/html")).andReturn();
        String content = finalResult.getResponse().getContentAsString();
        System.out.println(content);
        Document doc = Jsoup.parse(content);

        // Check basic markup, we can't cover everything without loads of changes to this test
        // but we can get an approximation of what's being returned pretty easily.
        assertEquals("Structure", doc.select("head title").text());
        assertEquals("Structure", doc.select("h1").html());

    }

    @Test
    public void testConfiguration() throws Exception {

        MvcResult result = mockMvc.perform(get("/admin/configuration")).andExpect(request().asyncStarted()).andReturn();
        result.getAsyncResult();

        MvcResult finalResult = mockMvc.perform(asyncDispatch(result)).andExpect(status().isOk()).andExpect(content().contentType("text/html;charset=UTF-8")).andExpect(view().name("system/html")).andReturn();
        String content = finalResult.getResponse().getContentAsString();
        System.out.println(content);
        Document doc = Jsoup.parse(content);

        // Check basic markup, we can't cover everything without loads of changes to this test
        // but we can get an approximation of what's being returned pretty easily.
        assertEquals("Configuration", doc.select("head title").text());
        assertEquals("Configuration", doc.select("h1").html());

    }

    @Test
    public void testSystemReporting() throws Exception {

        MvcResult result = mockMvc.perform(get("/admin/reporting/system")).andExpect(request().asyncStarted()).andReturn();
        result.getAsyncResult();

        MvcResult finalResult = mockMvc.perform(asyncDispatch(result)).andExpect(status().isOk()).andExpect(content().contentType("text/html;charset=UTF-8")).andExpect(view().name("system/html")).andReturn();
        String content = finalResult.getResponse().getContentAsString();
        System.out.println(content);
        Document doc = Jsoup.parse(content);

        // Check basic markup, we can't cover everything without loads of changes to this test
        // but we can get an approximation of what's being returned pretty easily.
        assertEquals("Reports - System", doc.select("head title").text());
        assertEquals("Reports - System", doc.select("h1").html());

    }

    @Test
    public void testReporting() throws Exception {

        MvcResult result = mockMvc.perform(get("/admin/reporting")).andExpect(request().asyncStarted()).andReturn();
        result.getAsyncResult();

        MvcResult finalResult = mockMvc.perform(asyncDispatch(result)).andExpect(status().isOk()).andExpect(content().contentType("text/html;charset=UTF-8")).andExpect(view().name("system/html")).andReturn();
        String content = finalResult.getResponse().getContentAsString();
        System.out.println(content);
        Document doc = Jsoup.parse(content);

        // Check basic markup, we can't cover everything without loads of changes to this test
        // but we can get an approximation of what's being returned pretty easily.
        assertEquals("Reports", doc.select("head title").text());
        assertEquals("Reports", doc.select("h1").html());

    }

}
