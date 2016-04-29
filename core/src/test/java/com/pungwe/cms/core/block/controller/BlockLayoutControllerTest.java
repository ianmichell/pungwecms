/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.block.controller;

import com.pungwe.cms.config.TestConfig;
import com.pungwe.cms.core.block.builder.BlockPageBuilder;
import com.pungwe.cms.core.config.BaseApplicationConfig;
import com.pungwe.cms.core.system.element.templates.PageElement;
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
public class BlockLayoutControllerTest extends AbstractControllerTest {

    @Test
    public void testGetBlockLayout() throws Exception {
        MvcResult result = mockMvc.perform(get("/admin/structure/block_layout")).andExpect(request().asyncStarted()).andReturn();
        result.getAsyncResult();

        MvcResult finalResult = mockMvc.perform(asyncDispatch(result)).andExpect(status().isOk()).andExpect(content().contentType("text/html;charset=UTF-8")).andExpect(view().name("system/html")).andReturn();
        String content = finalResult.getResponse().getContentAsString();
        System.out.println(content);
        Document doc = Jsoup.parse(content);

        // Check basic markup, we can't cover everything without loads of changes to this test
        // but we can get an approximation of what's being returned pretty easily.
        assertEquals("Block Layout", doc.select("head title").text());
        assertEquals("Block Layout", doc.select(".page_title_block h1").html());
        assertEquals(5, doc.select("table thead th").size());
        assertEquals("Block", doc.select("table thead th:nth-child(1)").html());
        assertEquals("Category", doc.select("table thead th:nth-child(2)").html());
        assertEquals("Region", doc.select("table thead th:nth-child(3)").html());
        assertEquals("Weight", doc.select("table thead th:nth-child(4)").html());
        assertEquals("Operations", doc.select("table thead th:nth-child(5)").html());

        // Check the number of rows:
        assertEquals(PageElement.DEFAULT_REGIONS.size() + 1, doc.select("table tbody tr th[data-region]").size());
        assertEquals(PageElement.DEFAULT_REGIONS.get("header"), doc.select("table tbody tr th[data-region=header]").html());
        assertEquals(PageElement.DEFAULT_REGIONS.get("help"), doc.select("table tbody tr th[data-region=help]").html());
        assertEquals(PageElement.DEFAULT_REGIONS.get("breadcrumb"), doc.select("table tbody tr th[data-region=breadcrumb]").html());
        assertEquals(PageElement.DEFAULT_REGIONS.get("primary_menu"), doc.select("table tbody tr th[data-region=primary_menu]").html());
        assertEquals(PageElement.DEFAULT_REGIONS.get("secondary_menu"), doc.select("table tbody tr th[data-region=secondary_menu]").html());
        assertEquals(PageElement.DEFAULT_REGIONS.get("content"), doc.select("table tbody tr th[data-region=content]").html());
        assertEquals(PageElement.DEFAULT_REGIONS.get("sidebar_first"), doc.select("table tbody tr th[data-region=sidebar_first]").html());
        assertEquals(PageElement.DEFAULT_REGIONS.get("sidebar_second"), doc.select("table tbody tr th[data-region=sidebar_second]").html());
        assertEquals(PageElement.DEFAULT_REGIONS.get("footer"), doc.select("table tbody tr th[data-region=footer]").html());
        assertEquals(PageElement.DEFAULT_REGIONS.get("highlighted"), doc.select("table tbody tr th[data-region=highlighted]").html());

    }
}
