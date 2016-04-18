/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.block.system;

import com.pungwe.cms.config.TestConfig;
import com.pungwe.cms.core.config.BaseApplicationConfig;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.basic.DivElement;
import com.pungwe.cms.core.element.basic.PlainTextElement;
import com.pungwe.cms.core.element.basic.TextFormatElement;
import com.pungwe.cms.core.element.model.ModelAndViewElement;
import com.pungwe.cms.core.utils.services.StatusMessageService;
import com.pungwe.cms.test.AbstractWebTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({TestConfig.class, BaseApplicationConfig.class})
@WebAppConfiguration("src/main/resources")
public class StatusMessageBlockTest extends AbstractWebTest {

    @Autowired
    StatusMessageBlock statusMessageBlock;

    @Autowired
    StatusMessageService statusMessageService;

    @Test
    public void testNullVariables() throws Exception {
        List<RenderedElement> elements = new LinkedList<>();
        statusMessageBlock.build(elements, new HashMap<>(), null);
        assertTrue(elements.isEmpty());
    }

    @Test
    public void testAddSuccessMessage() {

        Map<String, Object> flashMap = new HashMap<>();
        flashMap.put(StatusMessageService.SUCCESS_MESSAGE_KEY, new LinkedHashSet<>(Arrays.asList("Success!")));

        MockHttpServletRequest request = new MockHttpServletRequest(RequestMethod.GET.name(), "/");
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.setAttribute(DispatcherServlet.INPUT_FLASH_MAP_ATTRIBUTE, flashMap);

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        List<RenderedElement> elements = new LinkedList<>();
        statusMessageBlock.build(elements, new HashMap<>(), new HashMap<>());
        assertTrue(!elements.isEmpty());
        assertEquals(DivElement.class, elements.get(0).getClass());
        assertEquals(TextFormatElement.class, ((DivElement) elements.get(0)).getContent().get(0).getClass());
        assertEquals("Success!", ((PlainTextElement) ((TextFormatElement) ((DivElement) elements.get(0))
                .getContent().get(0)).getContent().get(0)).getText());
    }

    @Test
    public void testAddWarningMessage() {

        Map<String, Object> flashMap = new HashMap<>();
        flashMap.put(StatusMessageService.WARNING_MESSAGE_KEY, new LinkedHashSet<>(Arrays.asList("Warning!")));

        MockHttpServletRequest request = new MockHttpServletRequest(RequestMethod.GET.name(), "/");
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.setAttribute(DispatcherServlet.INPUT_FLASH_MAP_ATTRIBUTE, flashMap);

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        List<RenderedElement> elements = new LinkedList<>();
        statusMessageBlock.build(elements, new HashMap<>(), new HashMap<>());
        assertTrue(!elements.isEmpty());
        assertEquals(DivElement.class, elements.get(0).getClass());
        assertEquals(TextFormatElement.class, ((DivElement)elements.get(0)).getContent().get(0).getClass());
        assertEquals("Warning!", ((PlainTextElement)((TextFormatElement) ((DivElement) elements.get(0))
                .getContent().get(0)).getContent().get(0)).getText());
    }

    @Test
    public void testAddErrorMessage() {

        Map<String, Object> flashMap = new HashMap<>();
        flashMap.put(StatusMessageService.ERROR_MESSAGE_KEY, new LinkedHashSet<>(Arrays.asList("Error!")));

        MockHttpServletRequest request = new MockHttpServletRequest(RequestMethod.GET.name(), "/");
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.setAttribute(DispatcherServlet.INPUT_FLASH_MAP_ATTRIBUTE, flashMap);

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        List<RenderedElement> elements = new LinkedList<>();
        statusMessageBlock.build(elements, new HashMap<>(), new HashMap<>());
        assertTrue(!elements.isEmpty());
        assertEquals(DivElement.class, elements.get(0).getClass());
        assertEquals(TextFormatElement.class, ((DivElement)elements.get(0)).getContent().get(0).getClass());
        assertEquals("Error!", ((PlainTextElement)((TextFormatElement) ((DivElement) elements.get(0))
                .getContent().get(0)).getContent().get(0)).getText());
    }

    @Test
    public void testHasBindingErrorsModelAndViewElement() {
        MockHttpServletRequest request = new MockHttpServletRequest(RequestMethod.GET.name(), "/");
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.setAttribute(DispatcherServlet.INPUT_FLASH_MAP_ATTRIBUTE, new LinkedHashMap<String, Object>());
        // Set servlet attributes in request context holder...
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        // Set BindingResult
        BindingResult bindingResult = new MapBindingResult(new HashMap<String, Object>(), "form");
        bindingResult.reject(null, "My Error");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX, bindingResult);
        ModelAndViewElement modelAndViewElement = new ModelAndViewElement();
        modelAndViewElement.setContent(modelAndView);

        // Set variables
        Map<String, Object> variables = new LinkedHashMap<>();
        variables.put("content", modelAndViewElement);

        // Build block
        List<RenderedElement> elements = new LinkedList<>();
        statusMessageBlock.build(elements, new HashMap<>(), variables);
        assertTrue(!elements.isEmpty());
    }

    @Test
    public void testHasBindingErrorsModelAndView() {
        MockHttpServletRequest request = new MockHttpServletRequest(RequestMethod.GET.name(), "/");
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.setAttribute(DispatcherServlet.INPUT_FLASH_MAP_ATTRIBUTE, new LinkedHashMap<String, Object>());
        // Set servlet attributes in request context holder...
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        // Set BindingResult
        BindingResult bindingResult = new MapBindingResult(new HashMap<String, Object>(), "form");
        bindingResult.reject(null, "My Error");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX, bindingResult);

        // Set variables
        Map<String, Object> variables = new LinkedHashMap<>();
        variables.put("content", modelAndView);

        // Build block
        List<RenderedElement> elements = new LinkedList<>();
        statusMessageBlock.build(elements, new HashMap<>(), variables);
        assertTrue(!elements.isEmpty());
    }

    @Test
    public void testHasNoBindingErrorsModelAndView() {
        MockHttpServletRequest request = new MockHttpServletRequest(RequestMethod.GET.name(), "/");
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.setAttribute(DispatcherServlet.INPUT_FLASH_MAP_ATTRIBUTE, new LinkedHashMap<String, Object>());
        // Set servlet attributes in request context holder...
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        // Set variables
        Map<String, Object> variables = new LinkedHashMap<>();
        variables.put("content", new PlainTextElement("Test"));

        // Build block
        List<RenderedElement> elements = new LinkedList<>();
        statusMessageBlock.build(elements, new HashMap<>(), variables);
        assertTrue(elements.isEmpty());
    }
}
