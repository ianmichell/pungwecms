/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.element.model;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({TestConfig.class, BaseApplicationConfig.class})
@WebAppConfiguration("src/main/resources")
public class ModelAndViewElementTest extends AbstractWebTest {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    ViewResolver viewResolver;

    @Autowired(required = false)
    LocaleResolver localeResolver;

    @Test
    public void testDefaultConstructor() throws Exception {
        TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("content", "Hello World");
        modelAndView.setViewName("model_and_view_test");

        ModelAndViewElement element = new ModelAndViewElement();
        element.setContent(modelAndView);

        assertTrue(element.excludedAttributes().isEmpty()); // line coverage

        String output = functions.render(new MockHttpServletRequest(), element);
        Document document = Jsoup.parse(output);
        assertEquals("Hello World", document.body().text());
    }

    @Test
    public void testModelAndViewConstructor() throws Exception {
        TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("content", "Hello World");
        modelAndView.setViewName("model_and_view_test");

        ModelAndViewElement element = new ModelAndViewElement(modelAndView);

        assertTrue(element.excludedAttributes().isEmpty()); // line coverage

        String output = functions.render(new MockHttpServletRequest(), element);
        Document document = Jsoup.parse(output);
        assertEquals("Hello World", document.body().text());
    }
}
