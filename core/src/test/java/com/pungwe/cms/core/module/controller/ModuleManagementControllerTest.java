/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.module.controller;

import com.pungwe.cms.config.TestConfig;
import com.pungwe.cms.core.config.BaseApplicationConfig;
import com.pungwe.cms.core.module.services.ModuleConfigService;
import com.pungwe.cms.modules.dependency.ModuleWithDependency;
import com.pungwe.cms.modules.test.TestModule;
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

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({TestConfig.class, BaseApplicationConfig.class})
@WebAppConfiguration("src/main/resources")
public class ModuleManagementControllerTest extends AbstractControllerTest {

    @Autowired
    ModuleConfigService moduleConfigService;

    @Test
    public void testGetAndList() throws Exception {

        moduleConfigService.registerModule(TestModule.class, TestModule.class.getProtectionDomain().getCodeSource().getLocation());
        moduleConfigService.registerModule(ModuleWithDependency.class, ModuleWithDependency.class.getProtectionDomain().getCodeSource().getLocation());

        ///admin/appearance
        MvcResult result = mockMvc.perform(get("/admin/modules")).andExpect(request().asyncStarted()).andReturn();
        result.getAsyncResult();

        MvcResult finalResult = mockMvc.perform(asyncDispatch(result)).andExpect(status().isOk()).andExpect(content().contentType("text/html;charset=UTF-8")).andExpect(view().name("system/html")).andReturn();
        String content = finalResult.getResponse().getContentAsString();
        Document doc = Jsoup.parse(content);

        // Check basic markup, we can't cover everything without loads of changes to this test
        // but we can get an approximation of what's being returned pretty easily.
        assertEquals("Modules", doc.select("head title").text());
        assertEquals("Modules", doc.select(".page_title_block h1").html());
        assertEquals(3, doc.select("table thead th").size());
        assertEquals("Enabled", doc.select("table thead th:nth-child(1)").html());
        assertEquals("Name", doc.select("table thead th:nth-child(2)").html());
        assertEquals("Description", doc.select("table thead th:nth-child(3)").html());

        assertEquals("test_module", doc.select("#module_enabled_test_module").val());
        assertEquals("name[0].value", doc.select("#module_enabled_test_module").attr("name"));
        assertEquals("Test Module", doc.select("label[for=module_enabled_test_module]").html());
        assertEquals("Module Name:", doc.select("tbody tr:nth-child(1) dl dt:nth-child(1)").html());
        assertEquals("test_module", doc.select("tbody tr:nth-child(1) dl dd:nth-child(2)").html());
        assertEquals("Dependencies:", doc.select("tbody tr:nth-child(1) dl dt:nth-child(3)").html());
        assertEquals("", doc.select("tbody tr:nth-child(1) dl dd:nth-child(4)").html());

        assertEquals("module_with_dependency", doc.select("#module_enabled_module_with_dependency").val());
        assertEquals("Module With Dependency", doc.select("label[for=module_enabled_module_with_dependency]").html());
        assertEquals("Module Name:", doc.select("tbody tr:nth-child(2) dl dt:nth-child(1)").html());
        assertEquals("module_with_dependency", doc.select("tbody tr:nth-child(2) dl dd:nth-child(2)").html());
        assertEquals("Dependencies:", doc.select("tbody tr:nth-child(2) dl dt:nth-child(3)").html());
        assertEquals("Test Module", doc.select("tbody tr:nth-child(2) dl dd:nth-child(4)").html());
    }

    @Test
    public  void testPost() throws Exception {

        moduleConfigService.registerModule(TestModule.class, TestModule.class.getProtectionDomain().getCodeSource().getLocation());
        moduleConfigService.registerModule(ModuleWithDependency.class, ModuleWithDependency.class.getProtectionDomain().getCodeSource().getLocation());

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.put("name[0].value", Arrays.asList("test_module"));

        MvcResult result = mockMvc.perform(post("/admin/modules").params(parameters)).andExpect(request().asyncStarted()).andReturn();
        result.getAsyncResult();

        MvcResult finalResult = mockMvc.perform(asyncDispatch(result)).andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin/modules")).andReturn();

        // Ensure module that we just enabled was found
        assertTrue(moduleConfigService.isEnabled("test_module"));
        // Ensure that the module we did not enable is not enabled
        assertFalse(moduleConfigService.isEnabled("module_with_dependency"));

        // Ensure that the module we just enabled was found
        TestModule module = moduleManagementService.getModuleContext().getBean(TestModule.class);
    }
}
