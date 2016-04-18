/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.utils.services;

import com.pungwe.cms.config.TestConfig;
import com.pungwe.cms.core.config.BaseApplicationConfig;
import com.pungwe.cms.test.AbstractWebTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FlashMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({TestConfig.class, BaseApplicationConfig.class})
@WebAppConfiguration("src/main/resources")
public class StatusMessageServiceTest extends AbstractWebTest {

    @Autowired
    StatusMessageService statusMessageService;

    @Test
    public void testAddSuccessMessage() {
        FlashMap flashMap = new FlashMap();
        MockHttpServletRequest request = new MockHttpServletRequest(RequestMethod.GET.name(), "/");
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.setAttribute(DispatcherServlet.OUTPUT_FLASH_MAP_ATTRIBUTE, flashMap);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        statusMessageService.addSuccessStatusMessage("Success!");

        assertEquals("Success!", ((Set)flashMap.get(StatusMessageService.SUCCESS_MESSAGE_KEY)).iterator().next());
    }

    @Test
    public void testAddWarningMessage() {
        FlashMap flashMap = new FlashMap();
        MockHttpServletRequest request = new MockHttpServletRequest(RequestMethod.GET.name(), "/");
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.setAttribute(DispatcherServlet.OUTPUT_FLASH_MAP_ATTRIBUTE, flashMap);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        statusMessageService.addWarningStatusMessage("Warning!");

        assertEquals("Warning!", ((Set)flashMap.get(StatusMessageService.WARNING_MESSAGE_KEY)).iterator().next());
    }

    @Test
    public void testAddErrorMessage() {
        FlashMap flashMap = new FlashMap();
        MockHttpServletRequest request = new MockHttpServletRequest(RequestMethod.GET.name(), "/");
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.setAttribute(DispatcherServlet.OUTPUT_FLASH_MAP_ATTRIBUTE, flashMap);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        statusMessageService.addErrorStatusMessage("Error!");

        assertEquals("Error!", ((Set)flashMap.get(StatusMessageService.ERROR_MESSAGE_KEY)).iterator().next());
    }
}
