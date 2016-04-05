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
public class StyleElementTest extends AbstractWebTest {

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	ViewResolver viewResolver;

	@Autowired(required = false)
	LocaleResolver localeResolver;

	@Test
	public void testDefaultConstructor() throws Exception {
		StyleElement element = new StyleElement();
		element.setType("text/css");
		element.setMedia("print");
		element.setContent("h1 { color: red; }");
		element.setHtmlId("styles");
		TemplateFunctions templateFunctions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
		String output = templateFunctions.render(new MockHttpServletRequest(), element);
		Document doc = Jsoup.parse(output);
		assertEquals("Element id is incorrect", "styles", doc.select("style").first().id());
		assertEquals("Type is wrong", "text/css", doc.select("style").first().attr("type"));
		assertEquals("Media is wrong", "print", doc.select("style").first().attr("media"));
		assertEquals("Content is wrong", element.getContent(), doc.select("style").first().html());
	}
}
