package com.pungwe.cms.core.security.controller;

import com.pungwe.cms.config.TestConfig;
import com.pungwe.cms.core.config.BaseApplicationConfig;
import com.pungwe.cms.core.security.service.UserManagementService;
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

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by 917903 on 18/04/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({TestConfig.class, BaseApplicationConfig.class})
@WebAppConfiguration("src/main/resources")
public class UserAdministrationControllerTest extends AbstractControllerTest {

    @Autowired
    UserManagementService userManagementService;

    @Test
    public void testListUsers() throws Exception {

        MvcResult result = mockMvc.perform(get("/admin/people")).andExpect(request().asyncStarted()).andReturn();
        result.getAsyncResult();

        MvcResult finalResult = mockMvc.perform(asyncDispatch(result)).andExpect(status().isOk()).andExpect(content().contentType("text/html;charset=UTF-8")).andExpect(view().name("system/html")).andReturn();
        String content = finalResult.getResponse().getContentAsString();
        System.out.println(content);
        Document doc = Jsoup.parse(content);

        // Check basic markup, we can't cover everything without loads of changes to this test
        // but we can get an approximation of what's being returned pretty easily.
        assertEquals("People", doc.select("head title").text());
        assertEquals("People", doc.select("h1").html());
        assertEquals(4, doc.select("table thead th").size());
        assertEquals("Username", doc.select("table thead th:nth-child(1)").html());
        assertEquals("Status", doc.select("table thead th:nth-child(2)").html());
        assertEquals("Roles", doc.select("table thead th:nth-child(3)").html());
        assertEquals("Operations", doc.select("table thead th:nth-child(4)").html());
    }
}
