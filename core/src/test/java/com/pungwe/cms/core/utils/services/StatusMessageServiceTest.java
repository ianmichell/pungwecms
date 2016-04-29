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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({TestConfig.class, BaseApplicationConfig.class})
@WebAppConfiguration("src/main/resources")
public class StatusMessageServiceTest extends AbstractWebTest {

    @Autowired
    StatusMessageService statusMessageService;

    @Test
    public void testAddSuccessMessage() {
        FlashMap flashMap = new FlashMap();
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        when(redirectAttributes.addFlashAttribute(anyObject(), anyObject())).then(invocation -> {
            flashMap.put((String)invocation.getArguments()[0], invocation.getArguments()[1]);
            return invocation.getMock();
        });
        when(redirectAttributes.getFlashAttributes()).then(invocation -> flashMap);
        MockHttpServletRequest request = new MockHttpServletRequest(RequestMethod.GET.name(), "/");
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.setAttribute(DispatcherServlet.OUTPUT_FLASH_MAP_ATTRIBUTE, flashMap);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        statusMessageService.addSuccessStatusMessage(redirectAttributes, "Success!");
        statusMessageService.addSuccessStatusMessage(redirectAttributes, "Success2");

        Iterator<String> it = ((Set)flashMap.get(StatusMessageService.SUCCESS_MESSAGE_KEY)).iterator();

        assertEquals("Success!", it.next());
        assertEquals("Success2", it.next());
    }

    @Test
    public void testAddWarningMessage() {
        FlashMap flashMap = new FlashMap();
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        when(redirectAttributes.addFlashAttribute(anyObject(), anyObject())).then(invocation -> {
            flashMap.put((String)invocation.getArguments()[0], invocation.getArguments()[1]);
            return invocation.getMock();
        });
        when(redirectAttributes.getFlashAttributes()).then(invocation -> flashMap);
        MockHttpServletRequest request = new MockHttpServletRequest(RequestMethod.GET.name(), "/");
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.setAttribute(DispatcherServlet.OUTPUT_FLASH_MAP_ATTRIBUTE, flashMap);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        statusMessageService.addWarningStatusMessage(redirectAttributes, "Warning!");
        statusMessageService.addWarningStatusMessage(redirectAttributes, "Warning2");

        Iterator<String> it = ((Set)flashMap.get(StatusMessageService.WARNING_MESSAGE_KEY)).iterator();

        assertEquals("Warning!", it.next());
        assertEquals("Warning2", it.next());
    }

    @Test
    public void testAddErrorMessage() {
        final FlashMap flashMap = new FlashMap();
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        when(redirectAttributes.addFlashAttribute(anyObject(), anyObject())).then(invocation -> {
            flashMap.put((String)invocation.getArguments()[0], invocation.getArguments()[1]);
            return invocation.getMock();
        });
        when(redirectAttributes.getFlashAttributes()).then(invocation -> flashMap);
        MockHttpServletRequest request = new MockHttpServletRequest(RequestMethod.GET.name(), "/");
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.setAttribute(DispatcherServlet.OUTPUT_FLASH_MAP_ATTRIBUTE, flashMap);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        statusMessageService.addErrorStatusMessage(redirectAttributes, "Error!");
        statusMessageService.addErrorStatusMessage(redirectAttributes, "Error2");

        Iterator<String> it = ((Set)flashMap.get(StatusMessageService.ERROR_MESSAGE_KEY)).iterator();

        assertEquals("Error!", it.next());
        assertEquals("Error2", it.next());
    }

    @Test
    public void testAddSuccessMessageWithExistingString() {
        FlashMap flashMap = new FlashMap();
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        when(redirectAttributes.addFlashAttribute(anyObject(), anyObject())).then(invocation -> {
            flashMap.put((String)invocation.getArguments()[0], invocation.getArguments()[1]);
            return invocation.getMock();
        });
        when(redirectAttributes.getFlashAttributes()).then(invocation -> flashMap);
        flashMap.put(StatusMessageService.SUCCESS_MESSAGE_KEY, "Success!");
        MockHttpServletRequest request = new MockHttpServletRequest(RequestMethod.GET.name(), "/");
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.setAttribute(DispatcherServlet.OUTPUT_FLASH_MAP_ATTRIBUTE, flashMap);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        statusMessageService.addSuccessStatusMessage(redirectAttributes, "Success2");

        Iterator<String> it = ((Set)flashMap.get(StatusMessageService.SUCCESS_MESSAGE_KEY)).iterator();

        assertEquals("Success!", it.next());
        assertEquals("Success2", it.next());
    }

    @Test
    public void testAddWarningMessageWithExistingString() {
        FlashMap flashMap = new FlashMap();
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        when(redirectAttributes.addFlashAttribute(anyObject(), anyObject())).then(invocation -> {
            flashMap.put((String)invocation.getArguments()[0], invocation.getArguments()[1]);
            return invocation.getMock();
        });
        when(redirectAttributes.getFlashAttributes()).then(invocation -> flashMap);
        flashMap.put(StatusMessageService.WARNING_MESSAGE_KEY, "Warning!");
        MockHttpServletRequest request = new MockHttpServletRequest(RequestMethod.GET.name(), "/");
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.setAttribute(DispatcherServlet.OUTPUT_FLASH_MAP_ATTRIBUTE, flashMap);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        statusMessageService.addWarningStatusMessage(redirectAttributes, "Warning2");

        Iterator<String> it = ((Set)flashMap.get(StatusMessageService.WARNING_MESSAGE_KEY)).iterator();

        assertEquals("Warning!", it.next());
        assertEquals("Warning2", it.next());
    }

    @Test
    public void testAddErrorMessageWithExistingString() {
        FlashMap flashMap = new FlashMap();
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        when(redirectAttributes.addFlashAttribute(anyObject(), anyObject())).then(invocation -> {
            flashMap.put((String)invocation.getArguments()[0], invocation.getArguments()[1]);
            return invocation.getMock();
        });
        when(redirectAttributes.getFlashAttributes()).then(invocation -> flashMap);
        flashMap.put(StatusMessageService.ERROR_MESSAGE_KEY, "Error!");
        MockHttpServletRequest request = new MockHttpServletRequest(RequestMethod.GET.name(), "/");
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.setAttribute(DispatcherServlet.OUTPUT_FLASH_MAP_ATTRIBUTE, flashMap);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        statusMessageService.addErrorStatusMessage(redirectAttributes, "Error2");

        Iterator<String> it = ((Set)flashMap.get(StatusMessageService.ERROR_MESSAGE_KEY)).iterator();

        assertEquals("Error!", it.next());
        assertEquals("Error2", it.next());
    }

    @Test
    public void testAddSuccessMessageWithExistingNull() {
        FlashMap flashMap = new FlashMap();
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        when(redirectAttributes.addFlashAttribute(anyObject(), anyObject())).then(invocation -> {
            flashMap.put((String)invocation.getArguments()[0], invocation.getArguments()[1]);
            return invocation.getMock();
        });
        when(redirectAttributes.getFlashAttributes()).then(invocation -> flashMap);
        flashMap.put(StatusMessageService.SUCCESS_MESSAGE_KEY, null);
        MockHttpServletRequest request = new MockHttpServletRequest(RequestMethod.GET.name(), "/");
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.setAttribute(DispatcherServlet.OUTPUT_FLASH_MAP_ATTRIBUTE, flashMap);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        statusMessageService.addSuccessStatusMessage(redirectAttributes, "Success!");
        statusMessageService.addSuccessStatusMessage(redirectAttributes, "Success2");

        Iterator<String> it = ((Set)flashMap.get(StatusMessageService.SUCCESS_MESSAGE_KEY)).iterator();

        assertEquals("Success!", it.next());
        assertEquals("Success2", it.next());
    }

    @Test
    public void testAddWarningMessageWithExistingNull() {
        FlashMap flashMap = new FlashMap();
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        when(redirectAttributes.addFlashAttribute(anyObject(), anyObject())).then(invocation -> {
            flashMap.put((String)invocation.getArguments()[0], invocation.getArguments()[1]);
            return invocation.getMock();
        });
        when(redirectAttributes.getFlashAttributes()).then(invocation -> flashMap);
        flashMap.put(StatusMessageService.WARNING_MESSAGE_KEY, null);
        MockHttpServletRequest request = new MockHttpServletRequest(RequestMethod.GET.name(), "/");
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.setAttribute(DispatcherServlet.OUTPUT_FLASH_MAP_ATTRIBUTE, flashMap);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        statusMessageService.addWarningStatusMessage(redirectAttributes, "Warning!");
        statusMessageService.addWarningStatusMessage(redirectAttributes, "Warning2");

        Iterator<String> it = ((Set)flashMap.get(StatusMessageService.WARNING_MESSAGE_KEY)).iterator();

        assertEquals("Warning!", it.next());
        assertEquals("Warning2", it.next());
    }

    @Test
    public void testAddErrorMessageWithExistingNull() {
        FlashMap flashMap = new FlashMap();
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        when(redirectAttributes.addFlashAttribute(anyObject(), anyObject())).then(invocation -> {
            flashMap.put((String)invocation.getArguments()[0], invocation.getArguments()[1]);
            return invocation.getMock();
        });
        when(redirectAttributes.getFlashAttributes()).then(invocation -> flashMap);
        flashMap.put(StatusMessageService.ERROR_MESSAGE_KEY, null);
        MockHttpServletRequest request = new MockHttpServletRequest(RequestMethod.GET.name(), "/");
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.setAttribute(DispatcherServlet.OUTPUT_FLASH_MAP_ATTRIBUTE, flashMap);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        statusMessageService.addErrorStatusMessage(redirectAttributes, "Error!");
        statusMessageService.addErrorStatusMessage(redirectAttributes, "Error2");

        Iterator<String> it = ((Set)flashMap.get(StatusMessageService.ERROR_MESSAGE_KEY)).iterator();

        assertEquals("Error!", it.next());
        assertEquals("Error2", it.next());
    }

    @Test
    public void testGetSuccessMessagesEmpty() {
        Map<String, ?> flashMap = new HashMap<>();
        MockHttpServletRequest request = new MockHttpServletRequest(RequestMethod.GET.name(), "/");
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.setAttribute(DispatcherServlet.INPUT_FLASH_MAP_ATTRIBUTE, flashMap);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        assertEquals(0, statusMessageService.getSuccessStatusMessages().size());
    }

    @Test
    public void testGetErrorMessagesEmpty() {
        Map<String, ?> flashMap = new HashMap<>();
        MockHttpServletRequest request = new MockHttpServletRequest(RequestMethod.GET.name(), "/");
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.setAttribute(DispatcherServlet.INPUT_FLASH_MAP_ATTRIBUTE, flashMap);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        assertEquals(0, statusMessageService.getErrorStatusMessages().size());
    }

    @Test
    public void testGetWarningMessagesEmpty() {
        Map<String, ?> flashMap = new HashMap<>();
        MockHttpServletRequest request = new MockHttpServletRequest(RequestMethod.GET.name(), "/");
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.setAttribute(DispatcherServlet.INPUT_FLASH_MAP_ATTRIBUTE, flashMap);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        assertEquals(0, statusMessageService.getWarningStatusMessages().size());
    }

    @Test
    public void testGetSuccessMessagesWithString() {
        Map<String, Object> flashMap = new HashMap<>();
        flashMap.put(StatusMessageService.SUCCESS_MESSAGE_KEY, "success!");
        MockHttpServletRequest request = new MockHttpServletRequest(RequestMethod.GET.name(), "/");
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.setAttribute(DispatcherServlet.INPUT_FLASH_MAP_ATTRIBUTE, flashMap);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        assertEquals(1, statusMessageService.getSuccessStatusMessages().size());
        assertEquals("success!", statusMessageService.getSuccessStatusMessages().iterator().next());
    }

    @Test
    public void testGetErrorMessagesWithString() {
        Map<String, Object> flashMap = new HashMap<>();
        flashMap.put(StatusMessageService.ERROR_MESSAGE_KEY, "error!");
        MockHttpServletRequest request = new MockHttpServletRequest(RequestMethod.GET.name(), "/");
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.setAttribute(DispatcherServlet.INPUT_FLASH_MAP_ATTRIBUTE, flashMap);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        assertEquals(1, statusMessageService.getErrorStatusMessages().size());
        assertEquals("error!", statusMessageService.getErrorStatusMessages().iterator().next());
    }

    @Test
    public void testGetWarningMessagesWithString() {
        Map<String, Object> flashMap = new HashMap<>();
        flashMap.put(StatusMessageService.WARNING_MESSAGE_KEY, "warning!");
        MockHttpServletRequest request = new MockHttpServletRequest(RequestMethod.GET.name(), "/");
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.setAttribute(DispatcherServlet.INPUT_FLASH_MAP_ATTRIBUTE, flashMap);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        assertEquals(1, statusMessageService.getWarningStatusMessages().size());
        assertEquals("warning!", statusMessageService.getWarningStatusMessages().iterator().next());
    }

    @Test
    public void testGetSuccessMessagesWithNull() {
        Map<String, Object> flashMap = new HashMap<>();
        flashMap.put(StatusMessageService.SUCCESS_MESSAGE_KEY, null);
        MockHttpServletRequest request = new MockHttpServletRequest(RequestMethod.GET.name(), "/");
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.setAttribute(DispatcherServlet.INPUT_FLASH_MAP_ATTRIBUTE, flashMap);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        assertEquals(0, statusMessageService.getSuccessStatusMessages().size());
    }

    @Test
    public void testGetErrorMessagesWithNull() {
        Map<String, Object> flashMap = new HashMap<>();
        flashMap.put(StatusMessageService.ERROR_MESSAGE_KEY, null);
        MockHttpServletRequest request = new MockHttpServletRequest(RequestMethod.GET.name(), "/");
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.setAttribute(DispatcherServlet.INPUT_FLASH_MAP_ATTRIBUTE, flashMap);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        assertEquals(0, statusMessageService.getErrorStatusMessages().size());
    }

    @Test
    public void testGetWarningMessagesWithNull() {
        Map<String, Object> flashMap = new HashMap<>();
        flashMap.put(StatusMessageService.WARNING_MESSAGE_KEY, null);
        MockHttpServletRequest request = new MockHttpServletRequest(RequestMethod.GET.name(), "/");
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.setAttribute(DispatcherServlet.INPUT_FLASH_MAP_ATTRIBUTE, flashMap);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        assertEquals(0, statusMessageService.getWarningStatusMessages().size());
    }
    @Test
    public void testGetSuccessMessagesSet() {
        FlashMap flashMap = new FlashMap();
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        when(redirectAttributes.addFlashAttribute(anyObject(), anyObject())).then(invocation -> {
            flashMap.put((String)invocation.getArguments()[0], invocation.getArguments()[1]);
            return invocation.getMock();
        });
        when(redirectAttributes.getFlashAttributes()).then(invocation -> flashMap);
        MockHttpServletRequest request = new MockHttpServletRequest(RequestMethod.GET.name(), "/");
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.setAttribute(DispatcherServlet.OUTPUT_FLASH_MAP_ATTRIBUTE, flashMap);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        statusMessageService.addSuccessStatusMessage(redirectAttributes, "Success!");
        statusMessageService.addSuccessStatusMessage(redirectAttributes, "Success2");

        request = new MockHttpServletRequest(RequestMethod.GET.name(), "/");
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.setAttribute(DispatcherServlet.INPUT_FLASH_MAP_ATTRIBUTE, flashMap);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Iterator<String> it = statusMessageService.getSuccessStatusMessages().iterator();

        assertEquals("Success!", it.next());
        assertEquals("Success2", it.next());
    }

    @Test
    public void testGetWarningMessagesSet() {
        FlashMap flashMap = new FlashMap();
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        when(redirectAttributes.addFlashAttribute(anyObject(), anyObject())).then(invocation -> {
            flashMap.put((String)invocation.getArguments()[0], invocation.getArguments()[1]);
            return invocation.getMock();
        });
        when(redirectAttributes.getFlashAttributes()).then(invocation -> flashMap);
        MockHttpServletRequest request = new MockHttpServletRequest(RequestMethod.GET.name(), "/");
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.setAttribute(DispatcherServlet.OUTPUT_FLASH_MAP_ATTRIBUTE, flashMap);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        statusMessageService.addWarningStatusMessage(redirectAttributes, "Warning!");
        statusMessageService.addWarningStatusMessage(redirectAttributes, "Warning2");

        request = new MockHttpServletRequest(RequestMethod.GET.name(), "/");
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.setAttribute(DispatcherServlet.INPUT_FLASH_MAP_ATTRIBUTE, flashMap);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Iterator<String> it = statusMessageService.getWarningStatusMessages().iterator();

        assertEquals("Warning!", it.next());
        assertEquals("Warning2", it.next());
    }

    @Test
    public void testGetErrorMessagesSet() {
        FlashMap flashMap = new FlashMap();
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        when(redirectAttributes.addFlashAttribute(anyObject(), anyObject())).then(invocation -> {
            flashMap.put((String)invocation.getArguments()[0], invocation.getArguments()[1]);
            return invocation.getMock();
        });
        when(redirectAttributes.getFlashAttributes()).then(invocation -> flashMap);
        MockHttpServletRequest request = new MockHttpServletRequest(RequestMethod.GET.name(), "/");
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.setAttribute(DispatcherServlet.OUTPUT_FLASH_MAP_ATTRIBUTE, flashMap);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        statusMessageService.addErrorStatusMessage(redirectAttributes, "Error!");
        statusMessageService.addErrorStatusMessage(redirectAttributes, "Error2");

        request = new MockHttpServletRequest(RequestMethod.GET.name(), "/");
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.setAttribute(DispatcherServlet.INPUT_FLASH_MAP_ATTRIBUTE, flashMap);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Iterator<String> it = statusMessageService.getErrorStatusMessages().iterator();

        assertEquals("Error!", it.next());
        assertEquals("Error2", it.next());
    }
}
