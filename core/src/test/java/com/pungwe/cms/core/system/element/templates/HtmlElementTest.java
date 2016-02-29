package com.pungwe.cms.core.system.element.templates;

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

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by ian on 27/02/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(BaseApplicationConfig.class)
@WebAppConfiguration("src/main/resources")
public class HtmlElementTest extends AbstractWebTest {

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	ViewResolver viewResolver;

	@Autowired(required = false)
	LocaleResolver localeResolver;

	@Test
	public void testRenderHTML() throws Exception {

		TemplateFunctions templateFunctions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

		HtmlElement element = new HtmlElement();
		element.setTitle("Title");
		element.addAttribute("class", "page");
		element.addBodyAttribute("class", "body");
		element.setCss("<link rel=\"stylsheet\" href=\"styles.css\" />");
		element.setHead("");
		element.setJsTop("<script src=\"top.js\"></script>");
		element.setJsBottom("<script src=\"bottom.js\"></script>");
		element.setPageTop("<span class=\"top\">Top</span>");
		element.setPageContent("<span class=\"content\" id=\"#main_content\">Content</span>");
		element.setPageBottom("<span class=\"bottom\">Bottom</span>");

		String output = templateFunctions.render(new MockHttpServletRequest(), element);
		Document doc = Jsoup.parse(output);
		assertEquals("title missing", "Title", doc.select("head title").first().text());
		assertEquals("top js tag missing", "top.js", doc.select("head script").first().attr("src"));
		assertEquals("css tag missing", "styles.css", doc.select("head link").first().attr("href"));
		assertEquals("bottom js tag missing", "bottom.js", doc.select("body script").first().attr("src"));
		assertEquals("html class missing", "page", doc.select("html").first().attr("class"));
		assertEquals("body class missing", "body", doc.select("body").first().attr("class"));
		assertEquals("body top span is missing", "Top", doc.select("body span[class=top]").first().text());
		assertEquals("body top span is missing", "Content", doc.select("body span[class=content]").first().text());
		assertEquals("body top span is missing", "Bottom", doc.select("body span[class=bottom]").first().text());
	}

	@Test
	public void testRenderHTMLSetAttributes() throws Exception {

		TemplateFunctions templateFunctions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

		HtmlElement element = new HtmlElement();

		Map<String, String> attributes = new HashMap<String, String>();
		Map<String, String> bodyAttributes = new HashMap<String, String>();
		attributes.put("class", "page");
		bodyAttributes.put("class", "body");
		element.setTitle("Title");
		element.setAttributes(attributes);
		element.setBodyAttributes(bodyAttributes);
		element.setCss("<link rel=\"stylsheet\" href=\"styles.css\" />");
		element.setHead("");
		element.setJsTop("<script src=\"top.js\"></script>");
		element.setJsBottom("<script src=\"bottom.js\"></script>");
		element.setPageTop("<span class=\"top\">Top</span>");
		element.setPageContent("<span class=\"content\" id=\"#main_content\">Content</span>");
		element.setPageBottom("<span class=\"bottom\">Bottom</span>");

		String output = templateFunctions.render(new MockHttpServletRequest(), element);
		Document doc = Jsoup.parse(output);
		assertEquals("title missing", "Title", doc.select("head title").first().text());
		assertEquals("top js tag missing", "top.js", doc.select("head script").first().attr("src"));
		assertEquals("css tag missing", "styles.css", doc.select("head link").first().attr("href"));
		assertEquals("bottom js tag missing", "bottom.js", doc.select("body script").first().attr("src"));
		assertEquals("html class missing", "page", doc.select("html").first().attr("class"));
		assertEquals("body class missing", "body", doc.select("body").first().attr("class"));
		assertEquals("body top span is missing", "Top", doc.select("body span[class=top]").first().text());
		assertEquals("body top span is missing", "Content", doc.select("body span[class=content]").first().text());
		assertEquals("body top span is missing", "Bottom", doc.select("body span[class=bottom]").first().text());
	}
}
