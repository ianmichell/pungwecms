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
public class OrderedListElementTest extends AbstractWebTest {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    ViewResolver viewResolver;

    @Autowired(required = false)
    LocaleResolver localeResolver;

    @Test
    public void testDefaultConstructor() throws Exception {
        OrderedListElement element = new OrderedListElement();
        ListElement.ListItem item = new ListElement.ListItem("Test");
        element.addItem(item);

        assertTrue(element.excludedAttributes().isEmpty());
        assertTrue(item.excludedAttributes().isEmpty());

        TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
        String output = functions.render(new MockHttpServletRequest(), element);
        Document doc = Jsoup.parse(output);
        assertEquals("Test", doc.select("ol li").first().text());
    }

    @Test
    public void testListConstructor() throws Exception {
        OrderedListElement element = new OrderedListElement(Arrays.asList(new ListElement.ListItem(new PlainTextElement("Test"))));

        assertTrue(element.excludedAttributes().isEmpty());

        TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
        String output = functions.render(new MockHttpServletRequest(), element);
        Document doc = Jsoup.parse(output);
        assertEquals("Test", doc.select("ol li").first().text());
    }

    @Test
    public void testStringConstructor() throws Exception {
        OrderedListElement element = new OrderedListElement("Test");

        assertTrue(element.excludedAttributes().isEmpty());

        TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
        String output = functions.render(new MockHttpServletRequest(), element);
        Document doc = Jsoup.parse(output);
        assertEquals("Test", doc.select("ol li").first().text());
    }

    @Test
    public void testDefaultConstructorSetItems() throws Exception {
        OrderedListElement element = new OrderedListElement();
        ListElement.ListItem item = new ListElement.ListItem("Test");
        element.setItems(Arrays.asList(item));

        assertTrue(element.excludedAttributes().isEmpty());
        assertTrue(item.excludedAttributes().isEmpty());

        TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
        String output = functions.render(new MockHttpServletRequest(), element);
        Document doc = Jsoup.parse(output);
        assertEquals("Test", doc.select("ol li").first().text());
    }
}
