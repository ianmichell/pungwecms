/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.block.system;

import com.pungwe.cms.config.TestConfig;
import com.pungwe.cms.core.config.BaseApplicationConfig;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.basic.PlainTextElement;
import com.pungwe.cms.core.element.model.ModelAndViewElement;
import com.pungwe.cms.test.AbstractWebTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({TestConfig.class, BaseApplicationConfig.class})
@WebAppConfiguration("src/main/resources")
public class MainContentBlockTest extends AbstractWebTest {

    @Autowired
    MainContentBlock mainContentBlock;

    @Test
    public void testDefaultSettings() {
        assertTrue(mainContentBlock.getDefaultSettings().isEmpty());
    }

    @Test
    public void testWithoutContent() throws Exception {
        List<RenderedElement> renderedElementList = new ArrayList<>();
        mainContentBlock.build(renderedElementList, new HashMap<>(), new HashMap<>());
        assertTrue(renderedElementList.isEmpty());
    }

    @Test
    public void testWithString() throws Exception {
        List<RenderedElement> renderedElementList = new ArrayList<>();
        Map<String, Object> variables = new HashMap<>();
        variables.put("content", "Test");
        mainContentBlock.build(renderedElementList, new HashMap<>(), variables);
        assertFalse(renderedElementList.isEmpty());
        assertEquals(PlainTextElement.class, renderedElementList.get(0).getClass());
    }

    @Test
    public void testWithRenderedElement() throws Exception {
        List<RenderedElement> renderedElementList = new ArrayList<>();
        Map<String, Object> variables = new HashMap<>();
        variables.put("content", new PlainTextElement("Test"));
        mainContentBlock.build(renderedElementList, new HashMap<>(), variables);
        assertFalse(renderedElementList.isEmpty());
        assertEquals(PlainTextElement.class, renderedElementList.get(0).getClass());
    }

    @Test
    public void testWithRenderedElementCollection() throws Exception {
        List<RenderedElement> renderedElementList = new ArrayList<>();
        Map<String, Object> variables = new HashMap<>();
        variables.put("content", Arrays.asList(new PlainTextElement("Test")));
        mainContentBlock.build(renderedElementList, new HashMap<>(), variables);
        assertFalse(renderedElementList.isEmpty());
        assertEquals(PlainTextElement.class, renderedElementList.get(0).getClass());
    }

    @Test
    public void testWithModelAndView() throws Exception {
        List<RenderedElement> renderedElementList = new ArrayList<>();
        Map<String, Object> variables = new HashMap<>();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("content", "Test");
        modelAndView.setViewName("test");
        variables.put("content", modelAndView);
        mainContentBlock.build(renderedElementList, new HashMap<>(), variables);
        assertFalse(renderedElementList.isEmpty());
        assertEquals(ModelAndViewElement.class, renderedElementList.get(0).getClass());
    }
}
