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

/**
 * Created by ian on 03/03/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({TestConfig.class, BaseApplicationConfig.class})
public class ScriptElementTest extends AbstractWebTest {

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	ViewResolver viewResolver;

	@Autowired(required = false)
	LocaleResolver localeResolver;

	@Test
	public void testDefaultConstructorAndContent() throws Exception {
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
		ScriptElement element = new ScriptElement();
		element.setContent("var variable;");
		element.setHtmlId("myscript");
		element.setType("text/javascript");
		String output = functions.render(new MockHttpServletRequest(), element);
		Document doc = Jsoup.parse(output);
		assertEquals("Element id is incorrect", "myscript", doc.select("script").first().id());
		assertEquals("Element type is wrong", "text/javascript", doc.select("script").first().attr("type"));
		assertEquals("Element content is wrong", "var variable;", doc.select("script").first().html());
	}

	@Test
	public void testDefaultConstructor() throws Exception {
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
		ScriptElement element = new ScriptElement();
		element.setSrc("myscript.js");
		element.setHtmlId("myscript");
		element.setType("text/javascript");
		String output = functions.render(new MockHttpServletRequest(), element);
		Document doc = Jsoup.parse(output);
		assertEquals("Element id is incorrect", "myscript", doc.select("script").first().id());
		assertEquals("Element type is wrong", "text/javascript", doc.select("script").first().attr("type"));
		assertEquals("Element content is wrong", "myscript.js", doc.select("script").first().attr("src"));
	}

	@Test
	public void testConstructorTypeAndSrc() throws Exception {
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
		ScriptElement element = new ScriptElement("myscript.js", "text/javascript");
		element.setHtmlId("myscript");
		String output = functions.render(new MockHttpServletRequest(), element);
		System.out.println(output);
		Document doc = Jsoup.parse(output);
		assertEquals("Element id is incorrect", "myscript", doc.select("script").first().id());
		assertEquals("Element type is wrong", "text/javascript", doc.select("script").first().attr("type"));
		assertEquals("Element content is wrong", "myscript.js", doc.select("script").first().attr("src"));
	}
}
