package com.pungwe.cms.core.form.element;

import com.pungwe.cms.core.config.BaseApplicationConfig;
import com.pungwe.cms.core.element.basic.PlainTextElement;
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
import static org.junit.Assert.assertTrue;

/**
 * Created by ian on 25/02/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(BaseApplicationConfig.class)
@WebAppConfiguration("src/main/resources")
public class LabelElementTest extends AbstractWebTest {

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	ViewResolver viewResolver;

	@Autowired(required = false)
	LocaleResolver localeResolver;

	@Test
	public void testHiddenElementDefaultConstructor() throws Exception {

		// Get the ability to render stuff
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

		LabelElement element = new LabelElement();
		element.setHtmlId("label");
		element.setContent("My Label");

		String output = functions.render(new MockHttpServletRequest(), element);
		Document doc = Jsoup.parse(output);
		assertEquals("Label test is not correct", "My Label", doc.body().getElementById("label").text());
	}

	@Test
	public void testHiddenElementStringConstructor() throws Exception {

		// Get the ability to render stuff
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

		LabelElement element = new LabelElement("My Label");
		element.setHtmlId("label");

		String output = functions.render(new MockHttpServletRequest(), element);
		Document doc = Jsoup.parse(output);
		assertEquals("Label test is not correct", "My Label", doc.body().getElementById("label").text());
	}

	@Test
	public void testHiddenElementElementConstructor() throws Exception {

		// Get the ability to render stuff
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

		LabelElement element = new LabelElement(new PlainTextElement("My Label"));
		element.setHtmlId("label");

		String output = functions.render(new MockHttpServletRequest(), element);
		Document doc = Jsoup.parse(output);
		assertEquals("Label test is not correct", "My Label", doc.body().getElementById("label").text());
	}
}
