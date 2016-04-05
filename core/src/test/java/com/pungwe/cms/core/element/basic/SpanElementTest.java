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

/**
 * Created by ian on 03/03/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({TestConfig.class, BaseApplicationConfig.class})
public class SpanElementTest extends AbstractWebTest {

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	ViewResolver viewResolver;

	@Autowired(required = false)
	LocaleResolver localeResolver;

	@Test
	public void testDefaultConstructor() throws Exception {
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
		SpanElement element = new SpanElement();
		element.setContent("Test");
		element.addClass("myclass");
		String output = functions.render(new MockHttpServletRequest(), element);
		Document doc = Jsoup.parse(output);
		assertEquals("Element class is incorrect", "myclass", doc.body().select("span").first().className());
		assertEquals("Element content is wrong", "Test", doc.body().select("span").first().text());
	}

	@Test
	 public void testWithStringConstructor() throws Exception {
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
		SpanElement element = new SpanElement("Test");
		element.addClass("myclass");
		String output = functions.render(new MockHttpServletRequest(), element);
		Document doc = Jsoup.parse(output);
		assertEquals("Element class is incorrect", "myclass", doc.body().select("span").first().className());
		assertEquals("Element content is wrong", "Test", doc.body().select("span").first().text());
	}
}
