/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.block.builder;

import com.pungwe.cms.config.TestConfig;
import com.pungwe.cms.core.annotations.stereotypes.Block;
import com.pungwe.cms.core.block.services.BlockManagementService;
import com.pungwe.cms.core.block.system.MainContentBlock;
import com.pungwe.cms.core.config.BaseApplicationConfig;
import com.pungwe.cms.core.element.basic.PlainTextElement;
import com.pungwe.cms.core.element.model.ModelAndViewElement;
import com.pungwe.cms.core.system.element.templates.PageElement;
import com.pungwe.cms.core.theme.services.ThemeManagementService;
import com.pungwe.cms.test.AbstractWebTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({TestConfig.class, BaseApplicationConfig.class})
@WebAppConfiguration("src/main/resources")
public class BlockPageBuilderTest extends AbstractWebTest {

    @Autowired
    BlockManagementService blockManagementService;

    @Autowired
    ThemeManagementService themeManagementService;

    @Autowired
    BlockPageBuilder blockPageBuilder;

    @Test
    public void testWithContentAttribute() throws Exception {

        final PageElement pageElement = new PageElement();

        Map<String, Object> model = new HashMap<>();
        model.put("content", new PlainTextElement("Content"));
        blockPageBuilder.build(new MockHttpServletRequest(), pageElement, model);

        assertFalse(pageElement.getRegions().get("content").isEmpty());
        assertTrue(pageElement.getRegions().get("content").get(0) instanceof PlainTextElement);
        assertEquals("Content", ((PlainTextElement) pageElement.getRegions().get("content").get(0)).getText());
    }

    @Test
    public void testWithContentBlockAndWithoutContentAttribute() throws Exception {

        final PageElement pageElement = new PageElement();

        Map<String, Object> model = new HashMap<>();
        blockPageBuilder.build(new MockHttpServletRequest(), pageElement, model);

        assertTrue(pageElement.getRegions().get("content").isEmpty());
    }

    @Test
    public void testWithoutContentAttribute() throws Exception {

        final PageElement pageElement = new PageElement();

        Map<String, Object> model = new HashMap<>();
        blockPageBuilder.build(new MockHttpServletRequest(), pageElement, model);

        assertTrue(pageElement.getRegions().get("content").isEmpty());
    }

    @Test
    public void testWithoutMainContentBlock() throws Exception {

        blockManagementService.removeBlock(themeManagementService.getDefaultThemeName(), MainContentBlock.class.getAnnotation(Block.class).value());

        final PageElement pageElement = new PageElement();

        Map<String, Object> model = new HashMap<>();
        model.put("content", new PlainTextElement("Content"));
        blockPageBuilder.build(new MockHttpServletRequest(), pageElement, model);

        assertFalse(pageElement.getRegions().get("content").isEmpty());
        assertTrue(pageElement.getRegions().get("content").get(0) instanceof PlainTextElement);
        assertEquals("Content", ((PlainTextElement) pageElement.getRegions().get("content").get(0)).getText());
    }

    @Test
    public void testWithoutMainContentBlockModelAndView() throws Exception {

        blockManagementService.removeBlock(themeManagementService.getDefaultThemeName(), MainContentBlock.class.getAnnotation(Block.class).value());

        final PageElement pageElement = new PageElement();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("content", "Content");
        modelAndView.setViewName("test");

        Map<String, Object> model = new HashMap<>();
        model.put("content", modelAndView);
        blockPageBuilder.build(new MockHttpServletRequest(), pageElement, model);

        assertFalse(pageElement.getRegions().get("content").isEmpty());
        assertTrue(pageElement.getRegions().get("content").get(0) instanceof ModelAndViewElement);
        assertEquals("Content", ((ModelAndViewElement) pageElement.getRegions().get("content").get(0)).getContent().getModel().get("content"));
    }

    @Test
    public void testWithoutMainContentBlockString() throws Exception {

        blockManagementService.removeBlock(themeManagementService.getDefaultThemeName(), MainContentBlock.class.getAnnotation(Block.class).value());

        final PageElement pageElement = new PageElement();

        Map<String, Object> model = new HashMap<>();
        model.put("content", "Content");
        blockPageBuilder.build(new MockHttpServletRequest(), pageElement, model);

        assertFalse(pageElement.getRegions().get("content").isEmpty());
        assertTrue(pageElement.getRegions().get("content").get(0) instanceof PlainTextElement);
        assertEquals("Content", ((PlainTextElement) pageElement.getRegions().get("content").get(0)).getText());
    }

    @Test
    public void testWithoutMainContentBlockNull() throws Exception {

        blockManagementService.removeBlock(themeManagementService.getDefaultThemeName(), MainContentBlock.class.getAnnotation(Block.class).value());

        final PageElement pageElement = new PageElement();

        Map<String, Object> model = new HashMap<>();
        model.put("content", null);
        blockPageBuilder.build(new MockHttpServletRequest(), pageElement, model);

        assertTrue(pageElement.getRegions().get("content").isEmpty());
    }

    @Test
    public void testWithoutMainContentBlockOrContent() throws Exception {

        blockManagementService.removeBlock(themeManagementService.getDefaultThemeName(), MainContentBlock.class.getAnnotation(Block.class).value());

        final PageElement pageElement = new PageElement();

        Map<String, Object> model = new HashMap<>();
        blockPageBuilder.build(new MockHttpServletRequest(), pageElement, model);

        assertTrue(pageElement.getRegions().get("content").isEmpty());
    }
}
