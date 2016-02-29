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
	public void testLabelElementDefaultConstructorWithString() throws Exception {

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
	public void testLabelElementDefaultConstructorWithElement() throws Exception {

		// Get the ability to render stuff
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

		LabelElement element = new LabelElement();
		element.setContent(new PlainTextElement("My Label"));
		element.setHtmlId("label");

		String output = functions.render(new MockHttpServletRequest(), element);
		Document doc = Jsoup.parse(output);
		assertEquals("Label test is not correct", "My Label", doc.body().getElementById("label").text());
	}

	@Test
	public void testLabelElementRemoveForAttribute() throws Exception {

		StringElement stringElement = new StringElement();
		stringElement.setHtmlId("string");
		// Get the ability to render stuff
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

		LabelElement element = new LabelElement();
		element.setContent(new PlainTextElement("My Label"));
		element.setHtmlId("label");
		element.setForElement(stringElement);
		element.addAttribute("for", "blah");

		String output = functions.render(new MockHttpServletRequest(), element);
		Document doc = Jsoup.parse(output);
		assertEquals("Label test is not correct", "My Label", doc.body().getElementById("label").text());
		assertEquals("For attribute is not correct", element.getForElement().getHtmlId(), doc.getElementById("label").attr("for"));
	}

	@Test
	public void testLabelElementStringConstructor() throws Exception {

		// Get the ability to render stuff
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

		LabelElement element = new LabelElement("My Label");
		element.setHtmlId("label");

		String output = functions.render(new MockHttpServletRequest(), element);
		Document doc = Jsoup.parse(output);
		assertEquals("Label text is not correct", "My Label", doc.body().getElementById("label").text());
	}

	@Test
	public void testLabelElementElementConstructor() throws Exception {

		// Get the ability to render stuff
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

		LabelElement element = new LabelElement(new PlainTextElement("My Label"));
		element.setHtmlId("label");

		String output = functions.render(new MockHttpServletRequest(), element);
		Document doc = Jsoup.parse(output);
		assertEquals("Label text is not correct", "My Label", doc.body().getElementById("label").text());
	}
}
