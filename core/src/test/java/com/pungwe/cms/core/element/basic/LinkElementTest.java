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
public class LinkElementTest extends AbstractWebTest {

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	ViewResolver viewResolver;

	@Autowired(required = false)
	LocaleResolver localeResolver;

	@Test
	public void testDefaultConstructor() throws Exception {
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
		LinkElement element = new LinkElement();
		element.setType("text/css");
		element.setHref("styles.css");
		element.setRel("stylesheet");
		element.setHtmlId("styles");
		String output = functions.render(new MockHttpServletRequest(), element);
		Document doc = Jsoup.parse(output);
		assertEquals("Element id is incorrect", "styles", doc.select("link").first().id());
		assertEquals("Element type is wrong", "text/css", doc.select("link").first().attr("type"));
		assertEquals("Element src is wrong", "styles.css", doc.select("link").first().attr("href"));
		assertEquals("Element rel is wrong", "stylesheet", doc.select("link").first().attr("rel"));
	}

	@Test
	public void testRelHrefTypeConstructor() throws Exception {
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
		LinkElement element = new LinkElement("stylesheet", "styles.css", "text/css");
		element.setHtmlId("styles");
		String output = functions.render(new MockHttpServletRequest(), element);
		Document doc = Jsoup.parse(output);
		assertEquals("Element id is incorrect", "styles", doc.select("link").first().id());
		assertEquals("Element type is wrong", "text/css", doc.select("link").first().attr("type"));
		assertEquals("Element src is wrong", "styles.css", doc.select("link").first().attr("href"));
		assertEquals("Element rel is wrong", "stylesheet", doc.select("link").first().attr("rel"));
	}

	@Test
	public void testRelHrefConstructor() throws Exception {
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
		LinkElement element = new LinkElement("stylesheet", "styles.css");
		element.setType("text/css");
		element.setHtmlId("styles");
		String output = functions.render(new MockHttpServletRequest(), element);
		Document doc = Jsoup.parse(output);
		assertEquals("Element id is incorrect", "styles", doc.select("link").first().id());
		assertEquals("Element type is wrong", "text/css", doc.select("link").first().attr("type"));
		assertEquals("Element src is wrong", "styles.css", doc.select("link").first().attr("href"));
		assertEquals("Element rel is wrong", "stylesheet", doc.select("link").first().attr("rel"));
	}

	@Test
	public void testHrefConstructor() throws Exception {
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
		LinkElement element = new LinkElement("styles.css");
		element.setType("text/css");
		element.setRel("stylesheet");
		element.setHtmlId("styles");
		String output = functions.render(new MockHttpServletRequest(), element);
		Document doc = Jsoup.parse(output);
		assertEquals("Element id is incorrect", "styles", doc.select("link").first().id());
		assertEquals("Element type is wrong", "text/css", doc.select("link").first().attr("type"));
		assertEquals("Element src is wrong", "styles.css", doc.select("link").first().attr("href"));
		assertEquals("Element rel is wrong", "stylesheet", doc.select("link").first().attr("rel"));
	}
}
