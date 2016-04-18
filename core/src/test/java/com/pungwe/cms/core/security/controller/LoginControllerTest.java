package com.pungwe.cms.core.security.controller;

import com.pungwe.cms.config.TestConfig;
import com.pungwe.cms.core.annotations.stereotypes.Block;
import com.pungwe.cms.core.block.impl.BlockConfigImpl;
import com.pungwe.cms.core.block.services.BlockConfigServiceImpl;
import com.pungwe.cms.core.block.services.BlockManagementService;
import com.pungwe.cms.core.block.system.PageTitleBlock;
import com.pungwe.cms.core.config.BaseApplicationConfig;
import com.pungwe.cms.core.module.services.ModuleManagementService;
import com.pungwe.cms.test.AbstractControllerTest;
import com.pungwe.cms.test.AbstractWebTest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by 917903 on 18/04/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({TestConfig.class, BaseApplicationConfig.class})
@WebAppConfiguration("src/main/resources")
public class LoginControllerTest extends AbstractControllerTest {

    @Test
    public void testLoginControllerRender() throws Exception {
        MvcResult result = mockMvc.perform(get("/login")).andExpect(request().asyncStarted()).andReturn();
        result.getAsyncResult();

        MvcResult finalResult = mockMvc
                .perform(
                        asyncDispatch(
                        result
                        )
                )
                .andExpect(
                        status().isOk()
                ).andExpect(
                        content().contentType("text/html;charset=UTF-8"))
                .andExpect(
                        view().name("system/html")
                ).andReturn();
        String content = finalResult.getResponse().getContentAsString();
        Document doc = Jsoup.parse(content);
        assertEquals("Login", doc.select("html head title").text());
        assertEquals("Login", doc.select("html body h1").html());
        assertEquals(1, doc.select("input[name=username[0].value]").size());
        assertEquals(1, doc.select("input[name=password[0].value]").size());
        assertEquals(1, doc.select("input[name=submit]").size());
        assertEquals("text", doc.select("input[name=username[0].value]").attr("type"));
        assertEquals("password", doc.select("input[name=password[0].value]").attr("type"));
        assertEquals("submit", doc.select("input[name=submit]").attr("type"));
    }

}
