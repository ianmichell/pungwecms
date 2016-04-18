/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.block.system;

import com.pungwe.cms.config.TestConfig;
import com.pungwe.cms.core.config.BaseApplicationConfig;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.basic.HeaderElement;
import com.pungwe.cms.core.element.basic.PlainTextElement;
import com.pungwe.cms.test.AbstractWebTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({TestConfig.class, BaseApplicationConfig.class})
@WebAppConfiguration("src/main/resources")
public class PageTitleBlockTest extends AbstractWebTest {

    @Autowired
    PageTitleBlock pageTitleBlock;

    @Test
    public void testEmptyTitle() throws Exception {
        List<RenderedElement> renderedElementList = new ArrayList<>();
        pageTitleBlock.build(renderedElementList, pageTitleBlock.getDefaultSettings(), new HashMap<>());
        assertTrue(renderedElementList.isEmpty());
    }

    @Test
    public void testWithTitle() throws Exception {
        List<RenderedElement> renderedElementList = new ArrayList<>();
        Map<String, Object> variables = new HashMap<>();
        variables.put("title", "Title");
        pageTitleBlock.build(renderedElementList, pageTitleBlock.getDefaultSettings(), variables);
        assertFalse(renderedElementList.isEmpty());
        assertEquals(HeaderElement.class, renderedElementList.get(0).getClass());
        assertEquals(1, ((HeaderElement) renderedElementList.get(0)).getLevel());
        assertEquals("Title", ((PlainTextElement) ((HeaderElement) renderedElementList.get(0)).getContent().get(0)).getText());
    }

    @Test
    public void testWithTitleLevel2() throws Exception {
        List<RenderedElement> renderedElementList = new ArrayList<>();
        Map<String, Object> settings = new HashMap<>();
        settings.put("header_level", 2);
        Map<String, Object> variables = new HashMap<>();
        variables.put("title", "Title");
        pageTitleBlock.build(renderedElementList, settings, variables);
        assertFalse(renderedElementList.isEmpty());
        assertEquals(HeaderElement.class, renderedElementList.get(0).getClass());
        assertEquals(2, ((HeaderElement)renderedElementList.get(0)).getLevel());
        assertEquals("Title", ((PlainTextElement)((HeaderElement) renderedElementList.get(0)).getContent().get(0)).getText());
    }
}
