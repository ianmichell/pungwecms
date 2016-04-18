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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({TestConfig.class, BaseApplicationConfig.class})
public class HeaderElementTest extends AbstractWebTest {


    @Autowired
    ViewResolver viewResolver;

    @Autowired(required = false)
    LocaleResolver localeResolver;

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void testLevelConstructor() throws Exception {
        TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

        HeaderElement header = new HeaderElement(1);
        header.addContent("Hello World");

        assertTrue(header.excludedAttributes().isEmpty());
        String output = functions.render(new MockHttpServletRequest(), header);
        Document doc = Jsoup.parse(output);
        assertEquals("Hello World", doc.select("h1").text());
    }

    @Test
    public void testLevelAndStringConstructor() throws Exception {
        TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

        HeaderElement header = new HeaderElement(1, "Hello World");

        String output = functions.render(new MockHttpServletRequest(), header);
        Document doc = Jsoup.parse(output);
        assertEquals("Hello World", doc.select("h1").text());
    }

    @Test
    public void testLevelAndRenderedElementConstructor() throws Exception {
        TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

        HeaderElement header = new HeaderElement(1, new PlainTextElement("Hello World"));

        String output = functions.render(new MockHttpServletRequest(), header);
        Document doc = Jsoup.parse(output);
        assertEquals("Hello World", doc.select("h1").text());
    }
}
