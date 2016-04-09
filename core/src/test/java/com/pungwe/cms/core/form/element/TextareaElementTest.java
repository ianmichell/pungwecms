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

/**
 * Created by ian on 25/02/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({TestConfig.class, BaseApplicationConfig.class})
@WebAppConfiguration("src/main/resources")
public class TextareaElementTest extends AbstractWebTest {

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	ViewResolver viewResolver;

	@Autowired(required = false)
	LocaleResolver localeResolver;

	@Test
	public void testTextareaElementWithDefaultValue() throws Exception {

		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

		TextareaElement element = new TextareaElement();
		element.setHtmlId("string");
		element.setDefaultValue("Default Value");
		element.setName("string");
		element.setLabel("String");
		element.setRows(5);
		element.setColumns(10);

		String output = functions.render(new MockHttpServletRequest(), element);
		System.out.println(output);
		Document doc = Jsoup.parse(output);
		assertEquals("Name does not match", "string[0].value", doc.getElementById("string").attr("name"));
		assertEquals("Name does not match", "5", doc.getElementById("string").attr("rows"));
		assertEquals("Name does not match", "10", doc.getElementById("string").attr("cols"));
		assertEquals("Textarea Value does not match", "Default Value", doc.getElementById("string").text());
		assertEquals("Label for attribute does not match element id", element.getHtmlId(), doc.select("label").first().attr("for"));
		assertEquals("Label value does not match", "String", doc.select("label").first().text());
	}

	@Test
	public void testTextareaElementWithValue() throws Exception {

		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

		TextareaElement element = new TextareaElement();
		element.setHtmlId("string");
		element.setDefaultValue("Default Value");
		element.setName("string");
		element.setLabel("String");
		element.setValue("not default");

		String output = functions.render(new MockHttpServletRequest(), element);
		Document doc = Jsoup.parse(output);
		assertEquals("Name does not match", "string[0].value", doc.getElementById("string").attr("name"));
		assertEquals("Textarea Value does not match", "not default", doc.getElementById("string").text());
		assertEquals("Label for attribute does not match element id", element.getHtmlId(), doc.select("label").first().attr("for"));
		assertEquals("Label value does not match", "String", doc.select("label").first().text());
	}

	@Test
	public void testTextareaElementWithNoAttributes() throws Exception {

		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

		TextareaElement element = new TextareaElement();
		element.setValue("Test");
		element.setName("string");
		element.setLabel("String");
		element.setRows(20);
		element.setColumns(80);

		String output = functions.render(new MockHttpServletRequest(), element);
		System.out.println(output);
		Document doc = Jsoup.parse(output);

		assertEquals("Attributes has content", "", element.getAttributesAsString());
		assertEquals("Name does not match", "string[0].value", doc.select("textarea").first().attr("name"));
		assertEquals("String Value does not match", "Test", doc.select("textarea").first().text());
		assertEquals("Textarea element rows is not 20", "20", doc.select("textarea").first().attr("rows"));
		assertEquals("Textarea element columns is not 80", "80", doc.select("textarea").first().attr("cols"));
	}
}
