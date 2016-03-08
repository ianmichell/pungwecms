package com.pungwe.cms.core.element.basic;

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
@SpringApplicationConfiguration(BaseApplicationConfig.class)
public class MetaElementTest extends AbstractWebTest {

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	ViewResolver viewResolver;

	@Autowired(required = false)
	LocaleResolver localeResolver;

	@Test
	public void testDefaultConstructor() throws Exception {
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
		MetaElement element = new MetaElement();
		element.setName("name");
		element.setContent("content");
		String output = functions.render(new MockHttpServletRequest(), element);
		Document doc = Jsoup.parse(output);
		assertEquals("Name not right", "name", doc.select("meta").first().attr("name"));
		assertEquals("Content not right", "content", doc.select("meta").first().attr("content"));
	}

	@Test
	 public void testDefaultConstructorHttpEquivContent() throws Exception {
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
		MetaElement element = new MetaElement();
		element.setHttpEquiv("refresh");
		element.setContent("30");
		String output = functions.render(new MockHttpServletRequest(), element);
		Document doc = Jsoup.parse(output);
		assertEquals("Name not right", "refresh", doc.select("meta").first().attr("http-equiv"));
		assertEquals("Content not right", "30", doc.select("meta").first().attr("content"));
	}

	@Test
	public void testDefaultConstructorCharset() throws Exception {
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
		MetaElement element = new MetaElement();
		element.setCharset("UTF-8");
		String output = functions.render(new MockHttpServletRequest(), element);
		Document doc = Jsoup.parse(output);
		assertEquals("Name not right", "UTF-8", doc.select("meta").first().attr("charset"));
	}

	@Test
	public void testNameContentConstructor() throws Exception {
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
		MetaElement element = new MetaElement("name", "content");
		String output = functions.render(new MockHttpServletRequest(), element);
		Document doc = Jsoup.parse(output);
		assertEquals("Name not right", "name", doc.select("meta").first().attr("name"));
		assertEquals("Content not right", "content", doc.select("meta").first().attr("content"));
	}

	@Test
	public void testNameContentConstructorWithHtmlId() throws Exception {
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
		MetaElement element = new MetaElement("name", "content");
		element.setHtmlId("metaId");
		String output = functions.render(new MockHttpServletRequest(), element);
		Document doc = Jsoup.parse(output);
		assertEquals("Name not right", "name", doc.select("meta").first().attr("name"));
		assertEquals("Content not right", "content", doc.select("meta").first().attr("content"));
		assertEquals("ID not right", "metaId", doc.select("meta").first().attr("id"));
	}
}
