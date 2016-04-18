/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.theme.controller;

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
public class ThemeManagementControllerTest extends AbstractControllerTest {

    @Test
    public void testGetAndList() throws Exception {

        ///admin/appearance
        MvcResult result = mockMvc.perform(get("/admin/appearance")).andExpect(request().asyncStarted()).andReturn();
        result.getAsyncResult();

        MvcResult finalResult = mockMvc.perform(asyncDispatch(result)).andExpect(status().isOk()).andExpect(content().contentType("text/html;charset=UTF-8")).andExpect(view().name("system/html")).andReturn();
        String content = finalResult.getResponse().getContentAsString();
        System.out.println(content);
        Document doc = Jsoup.parse(content);

        // Check basic markup, we can't cover everything without loads of changes to this test
        // but we can get an approximation of what's being returned pretty easily.
        assertEquals("Appearance", doc.select("head title").text());
        assertEquals("Appearance", doc.select(".page_title_block h1").html());
    }
}
