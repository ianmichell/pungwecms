/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.form.element;

import com.pungwe.cms.config.TestConfig;
import com.pungwe.cms.core.config.BaseApplicationConfig;
import com.pungwe.cms.core.element.basic.PlainTextElement;
import com.pungwe.cms.core.theme.functions.TemplateFunctions;
import com.pungwe.cms.test.AbstractWebTest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
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
public class ButtonElementTest extends AbstractWebTest {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    ViewResolver viewResolver;

    @Autowired(required = false)
    LocaleResolver localeResolver;

    TemplateFunctions templateFunctions;

    @Before
    public void setupTemplateFunctions() {
        templateFunctions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
    }

    @Test
    public void testButtonType() {
        assertEquals(ButtonElement.ButtonType.BUTTON, ButtonElement.ButtonType.valueOf("BUTTON"));
        assertEquals(ButtonElement.ButtonType.RESET, ButtonElement.ButtonType.valueOf("RESET"));
        assertEquals(ButtonElement.ButtonType.SUBMIT, ButtonElement.ButtonType.valueOf("SUBMIT"));
    }

    @Test
    public void testWithDefaultConstructorNoType() throws Exception {

        ButtonElement buttonElement = new ButtonElement();
        buttonElement.addContent("Button");
        buttonElement.setValue("value");

        String output = templateFunctions.render(new MockHttpServletRequest(), buttonElement);

        Document doc = Jsoup.parse(output);

        assertEquals(ButtonElement.ButtonType.BUTTON, buttonElement.getType());
        assertEquals("button", doc.select("button").attr("type"));
        assertEquals("Button", doc.select("button").html());
        assertEquals("value", doc.select("button").val());
    }

    @Test
    public void testWithDefaultConstructor() throws Exception {

        ButtonElement buttonElement = new ButtonElement();
        buttonElement.addContent("Button");
        buttonElement.setValue("value");
        buttonElement.setType(ButtonElement.ButtonType.SUBMIT);

        String output = templateFunctions.render(new MockHttpServletRequest(), buttonElement);

        Document doc = Jsoup.parse(output);

        assertEquals(ButtonElement.ButtonType.SUBMIT, buttonElement.getType());
        assertEquals("submit", doc.select("button").attr("type"));
        assertEquals("Button", doc.select("button").html());
        assertEquals("value", doc.select("button").val());
    }

    @Test
    public void testWithButtonType() throws Exception {

        ButtonElement buttonElement = new ButtonElement(ButtonElement.ButtonType.BUTTON);
        buttonElement.addContent("Button");
        buttonElement.setValue("value");

        String output = templateFunctions.render(new MockHttpServletRequest(), buttonElement);

        Document doc = Jsoup.parse(output);

        assertEquals(ButtonElement.ButtonType.BUTTON, buttonElement.getType());
        assertEquals("button", doc.select("button").attr("type"));
        assertEquals("Button", doc.select("button").html());
        assertEquals("value", doc.select("button").val());
    }

    @Test
    public void testWithSubmitType() throws Exception {

        ButtonElement buttonElement = new ButtonElement(ButtonElement.ButtonType.SUBMIT);
        buttonElement.addContent("Button");
        buttonElement.setValue("value");

        String output = templateFunctions.render(new MockHttpServletRequest(), buttonElement);

        Document doc = Jsoup.parse(output);

        assertEquals(ButtonElement.ButtonType.SUBMIT, buttonElement.getType());
        assertEquals("submit", doc.select("button").attr("type"));
        assertEquals("Button", doc.select("button").html());
        assertEquals("value", doc.select("button").val());
    }

    @Test
    public void testWithResetType() throws Exception {

        ButtonElement buttonElement = new ButtonElement(ButtonElement.ButtonType.RESET);
        buttonElement.addContent("Button");
        buttonElement.setValue("value");

        String output = templateFunctions.render(new MockHttpServletRequest(), buttonElement);

        Document doc = Jsoup.parse(output);

        assertEquals(ButtonElement.ButtonType.RESET, buttonElement.getType());
        assertEquals("reset", doc.select("button").attr("type"));
        assertEquals("Button", doc.select("button").html());
        assertEquals("value", doc.select("button").val());
    }

    @Test
    public void testWithStringConstructor() throws Exception {

        ButtonElement buttonElement = new ButtonElement(ButtonElement.ButtonType.SUBMIT, "Button");
        buttonElement.setValue("value");

        String output = templateFunctions.render(new MockHttpServletRequest(), buttonElement);

        Document doc = Jsoup.parse(output);

        assertEquals(ButtonElement.ButtonType.SUBMIT, buttonElement.getType());
        assertEquals("submit", doc.select("button").attr("type"));
        assertEquals("Button", doc.select("button").html());
        assertEquals("value", doc.select("button").val());
    }

    @Test
    public void testWithRenderedElementConstructor() throws Exception {

        ButtonElement buttonElement = new ButtonElement(ButtonElement.ButtonType.SUBMIT, new PlainTextElement("Button"));
        buttonElement.setValue("value");

        String output = templateFunctions.render(new MockHttpServletRequest(), buttonElement);

        Document doc = Jsoup.parse(output);

        assertEquals(ButtonElement.ButtonType.SUBMIT, buttonElement.getType());
        assertEquals("submit", doc.select("button").attr("type"));
        assertEquals("Button", doc.select("button").html());
        assertEquals("value", doc.select("button").val());
    }
}
