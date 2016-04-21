/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.form.element;

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
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({TestConfig.class, BaseApplicationConfig.class})
@WebAppConfiguration("src/main/resources")
public class CheckboxElementTest extends AbstractWebTest {
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    ViewResolver viewResolver;

    @Autowired(required = false)
    LocaleResolver localeResolver;

    @Test
    public void testCheckboxElementWithNoAttributes() throws Exception {

        TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

        CheckboxElement element = new CheckboxElement();
        element.setChecked(true);
        element.setName("string");
        element.addContent("Label");

        String output = functions.render(new MockHttpServletRequest(), element);
        Document doc = Jsoup.parse(output);
        assertEquals("Attributes has content", "", element.getAttributesAsString());
        assertEquals("Name does not match", "string[0].value", doc.select("input[type=checkbox]").first().attr("name"));
        assertEquals("String Value does not match", "Test", doc.select("input[type=checkbox]").first().attr("value"));
    }

    @Test
    public void testCheckboxElementWithDefaultValue() throws Exception {

        TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

        CheckboxElement element = new CheckboxElement();
        element.setHtmlId("string");
        element.setDefaultValue("Default Value");
        element.setName("string");
        element.addContent(new LabelElement("String"));
        element.setRequired(true);

        String output = functions.render(new MockHttpServletRequest(), element);
        Document doc = Jsoup.parse(output);
        assertEquals("Name does not match", "string[0].value", doc.getElementById("string").attr("name"));
        assertEquals("String Value does not match", "Default Value", doc.getElementById("string").attr("value"));
        assertEquals("Label for attribute does not match element id", element.getHtmlId(), doc.select("label").first().attr("for"));
        assertEquals("Label value does not match", "String", doc.select("label").first().text());
    }

    @Test
    public void testCheckboxElementWithDefaultValueAndDelta() throws Exception {

        TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

        CheckboxElement element = new CheckboxElement();
        element.setHtmlId("string");
        element.setDefaultValue("Default Value");
        element.setName("string");
        element.addContent(new LabelElement("String"));
        element.setRequired(true);
        element.setDelta(1);

        String output = functions.render(new MockHttpServletRequest(), element);
        Document doc = Jsoup.parse(output);
        assertEquals("Name does not match", "string[1].value", doc.getElementById("string").attr("name"));
        assertEquals("String Value does not match", "Default Value", doc.getElementById("string").attr("value"));
        assertEquals("Label for attribute does not match element id", element.getHtmlId(), doc.select("label").first().attr("for"));
        assertEquals("Label value does not match", "String", doc.select("label").first().text());
    }

    @Test
    public void testCheckboxElementWithValue() throws Exception {

        TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

        CheckboxElement element = new CheckboxElement();
        element.setHtmlId("string");
        element.setDefaultValue("Default Value");
        element.setName("string");
        element.addContent("String");
        element.setValue("not default");

        String output = functions.render(new MockHttpServletRequest(), element);
        Document doc = Jsoup.parse(output);
        assertEquals("Name does not match", "string[0].value", doc.getElementById("string").attr("name"));
        assertEquals("String Value does not match", "not default", doc.getElementById("string").attr("value"));
        assertEquals("Label for attribute does not match element id", element.getHtmlId(), doc.select("label").first().attr("for"));
        assertEquals("Label value does not match", "String", doc.select("label").first().text());
    }
}
