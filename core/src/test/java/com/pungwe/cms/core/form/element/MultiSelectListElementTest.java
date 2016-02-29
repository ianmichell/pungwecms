package com.pungwe.cms.core.form.element;

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

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by ian on 25/02/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(BaseApplicationConfig.class)
@WebAppConfiguration("src/main/resources")
public class MultiSelectListElementTest extends AbstractWebTest {

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	ViewResolver viewResolver;

	@Autowired(required = false)
	LocaleResolver localeResolver;

	@Test
	public void testSelectElementWithDefaultValue() throws Exception {

		// Get the ability to render stuff
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

		MultiSelectListElement element = new MultiSelectListElement();
		element.setHtmlId("string");
		element.setDefaultValue(Arrays.asList("default value", "some value"));
		element.setName("string");
		element.setLabel("String");

		element.addOption("Some value", "some value");
		element.addOption("Default Value", "default value");
		element.addOption("Another value", "another value");
		element.addOption("Fourth Value", "fourth value");

		String output = functions.render(new MockHttpServletRequest(), element);
		Document doc = Jsoup.parse(output);
		assertEquals("Name does not match", "string[0]", doc.getElementById("string").attr("name"));
		assertEquals("Option 1 is not correct", "some value", doc.select("select option").get(0).val());
		assertEquals("Option 2 is not correct", "default value", doc.select("select option").get(1).val());
		assertEquals("Option 3 is not correct", "another value", doc.select("select option").get(2).val());
		assertEquals("Option 3 is not correct", "fourth value", doc.select("select option").get(3).val());
		assertEquals("Default Value does not match", "some value", doc.select("select option[selected]").get(0).val());
		assertEquals("Default Value does not match", "default value", doc.select("select option[selected]").get(1).val());
		assertEquals("Label for attribute does not match element id", element.getHtmlId(), doc.select("label").first().attr("for"));
		assertEquals("Label value does not match", "String", doc.select("label").first().text());
	}

	@Test
	public void testSelectElementWithValue() throws Exception {

		// Get the ability to render stuff
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

		MultiSelectListElement element = new MultiSelectListElement();
		element.setHtmlId("string");
		element.setDefaultValue(Arrays.asList("default value", "some value"));
		element.setName("string");
		element.setLabel("String");
		element.setValue(Arrays.asList("another value", "some value"));

		element.addOption("Some value", "some value");
		element.addOption("Default Value", "default value");
		element.addOption("Another value", "another value");
		element.addOption("Fourth Value", "fourth value");

		String output = functions.render(new MockHttpServletRequest(), element);
		Document doc = Jsoup.parse(output);
		assertEquals("Name does not match", "string[0]", doc.getElementById("string").attr("name"));
		assertEquals("Option 1 is not correct", "some value", doc.select("select option").get(0).val());
		assertEquals("Option 2 is not correct", "default value", doc.select("select option").get(1).val());
		assertEquals("Option 3 is not correct", "another value", doc.select("select option").get(2).val());
		assertEquals("Option 3 is not correct", "fourth value", doc.select("select option").get(3).val());
		assertEquals("Default Value does not match", "some value", doc.select("select option[selected]").get(0).val());
		assertEquals("Default Value does not match", "another value", doc.select("select option[selected]").get(1).val());
		assertEquals("Label for attribute does not match element id", element.getHtmlId(), doc.select("label").first().attr("for"));
		assertEquals("Label value does not match", "String", doc.select("label").first().text());
	}

}
