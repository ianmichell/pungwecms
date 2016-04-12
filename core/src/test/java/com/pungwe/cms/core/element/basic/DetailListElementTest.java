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
public class DetailListElementTest extends AbstractWebTest {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    ViewResolver viewResolver;

    @Autowired(required = false)
    LocaleResolver localeResolver;

    @Test
    public void testAddItems() throws Exception {
        TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
        DetailListElement element = new DetailListElement();
        element.addItem(new DetailListElement.DTItem("Key"), new DetailListElement.DDItem("Value"));
        String output = functions.render(new MockHttpServletRequest(), element);
        Document doc = Jsoup.parse(output);
        assertEquals("Detail List DT Missing", "Key", doc.select("dl dt").first().text());
        assertEquals("Detail List DD Missing", "Value", doc.select("dl dd").first().text());
    }

    @Test
    public void testAddItemRenderedElementConstructor() throws Exception {
        TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
        DetailListElement element = new DetailListElement();
        element.addItem(new DetailListElement.DTItem(new PlainTextElement("Key")),
                new DetailListElement.DDItem(new PlainTextElement("Value")));
        String output = functions.render(new MockHttpServletRequest(), element);
        Document doc = Jsoup.parse(output);
        assertEquals("Detail List DT Missing", "Key", doc.select("dl dt").first().text());
        assertEquals("Detail List DD Missing", "Value", doc.select("dl dd").first().text());
    }

    @Test
    public void testAddItemRenderedElementListConstructor() throws Exception {
        TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
        DetailListElement element = new DetailListElement();
        element.addItem(new DetailListElement.DTItem(Arrays.asList(new PlainTextElement("Key"))),
                new DetailListElement.DDItem(Arrays.asList(new PlainTextElement("Value"))));
        String output = functions.render(new MockHttpServletRequest(), element);
        Document doc = Jsoup.parse(output);
        assertEquals("Detail List DT Missing", "Key", doc.select("dl dt").first().text());
        assertEquals("Detail List DD Missing", "Value", doc.select("dl dd").first().text());
    }

    @Test
    public void testDefaultConstructorsSetItems() throws Exception {
        TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

        DetailListElement element = new DetailListElement();
        DetailListElement.DTItem dt = new DetailListElement.DTItem();
        dt.setContent("Key");
        DetailListElement.DDItem dd = new DetailListElement.DDItem();
        dd.setContent("Value");

        element.setItems(Arrays.asList(dt, dd));

        assertTrue(element.excludedAttributes().isEmpty());
        assertTrue(dd.excludedAttributes().isEmpty());
        assertTrue(dt.excludedAttributes().isEmpty());

        String output = functions.render(new MockHttpServletRequest(), element);
        Document doc = Jsoup.parse(output);
        assertEquals("Detail List DT Missing", "Key", doc.select("dl dt").first().text());
        assertEquals("Detail List DD Missing", "Value", doc.select("dl dd").first().text());
    }
}
