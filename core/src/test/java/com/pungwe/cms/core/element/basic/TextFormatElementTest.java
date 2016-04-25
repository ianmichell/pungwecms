/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.element.basic;

import com.pungwe.cms.config.TestConfig;
import com.pungwe.cms.core.config.BaseApplicationConfig;
import com.pungwe.cms.core.theme.functions.TemplateFunctions;
import com.pungwe.cms.test.AbstractWebTest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({TestConfig.class, BaseApplicationConfig.class})
public class TextFormatElementTest extends AbstractWebTest {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    ViewResolver viewResolver;

    @Autowired(required = false)
    LocaleResolver localeResolver;

    @Test
    public void testListConstructor() throws Exception {
        TextFormatElement element = new TextFormatElement(TextFormatElement.Type.I, Arrays.asList(new PlainTextElement("Test")));
        assertTrue(element.excludedAttributes().isEmpty());
        assertEquals(TextFormatElement.Type.I.getTagName(), element.getTag());
        TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
        String output = functions.render(new MockHttpServletRequest(), element);
        Document doc = Jsoup.parse(output);
        assertEquals("Test", doc.select("i").first().text());
    }

    @Test
    public void testStringConstructor() throws Exception {
        TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
        TextFormatElement element = new TextFormatElement(TextFormatElement.Type.I, "Test");
        assertTrue(element.excludedAttributes().isEmpty());
        assertEquals(TextFormatElement.Type.I.getTagName(), element.getTag());
        String output = functions.render(new MockHttpServletRequest(), element);
        Document doc = Jsoup.parse(output);
        assertEquals("Test", doc.select("i").first().text());
    }

    @Test
    public void testRenderedElementConstructor() throws Exception {
        TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
        TextFormatElement element = new TextFormatElement(TextFormatElement.Type.I, new PlainTextElement("Test"));
        assertTrue(element.excludedAttributes().isEmpty());
        assertEquals(TextFormatElement.Type.I.getTagName(), element.getTag());
        String output = functions.render(new MockHttpServletRequest(), element);
        Document doc = Jsoup.parse(output);
        assertEquals("Test", doc.select("i").first().text());
    }

    @Test
    public void testTypeConstructor() throws Exception {
        TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
        TextFormatElement element = new TextFormatElement(TextFormatElement.Type.I);
        element.addContent("Test");
        assertTrue(element.excludedAttributes().isEmpty());
        assertEquals(TextFormatElement.Type.I.getTagName(), element.getTag());
        String output = functions.render(new MockHttpServletRequest(), element);
        Document doc = Jsoup.parse(output);
    }

    @Test
    public void testTagTypes() throws Exception {
        DivElement wrapper = new DivElement();

        TextFormatElement p = new TextFormatElement(TextFormatElement.Type.P, "Test");
        TextFormatElement i = new TextFormatElement(TextFormatElement.Type.I, "Test");
        TextFormatElement strong = new TextFormatElement(TextFormatElement.Type.STRONG, "Test");
        TextFormatElement cite = new TextFormatElement(TextFormatElement.Type.CITE, "Test");
        TextFormatElement mark = new TextFormatElement(TextFormatElement.Type.MARK, "Test");

        wrapper.addContent(p, i, strong, cite, mark);

        TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
        String output = functions.render(new MockHttpServletRequest(), wrapper);
        Document doc = Jsoup.parse(output);

        assertEquals("Test", doc.select("p").first().text());
        assertEquals("Test", doc.select("i").first().text());
        assertEquals("Test", doc.select("strong").first().text());
        assertEquals("Test", doc.select("cite").first().text());
        assertEquals("Test", doc.select("mark").first().text());

    }

    // Line coverage...
    @Test
    public void testEnum() throws Exception {

        for (TextFormatElement.Type type : TextFormatElement.Type.values()) {
            assertEquals(type.name().toLowerCase(), type.getTagName());
        }

        assertEquals(TextFormatElement.Type.I, TextFormatElement.Type.valueOf("I"));
        assertEquals(TextFormatElement.Type.STRONG, TextFormatElement.Type.valueOf("STRONG"));
        assertEquals(TextFormatElement.Type.CITE, TextFormatElement.Type.valueOf("CITE"));
        assertEquals(TextFormatElement.Type.MARK, TextFormatElement.Type.valueOf("MARK"));
        assertEquals(TextFormatElement.Type.P, TextFormatElement.Type.valueOf("P"));

    }
}
