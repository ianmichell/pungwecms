package com.pungwe.cms.core.element.basic;

/**
 * Created by ian on 17/02/2016.
 */

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

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(BaseApplicationConfig.class)
@WebAppConfiguration("src/main/resources")
public class AnchorElementTest extends AbstractWebTest {

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	ViewResolver viewResolver;

	@Autowired(required = false)
	LocaleResolver localeResolver;

	@Test
	public void testRenderLinkDefaultConstructor() throws Exception {
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
		AnchorElement link = new AnchorElement();
		link.setHtmlId("link");
		link.setTitle("Link title");
		link.setHref("http://www.example.com");
		link.setContent(new PlainTextElement("Link Text"));
		String output = functions.render(new MockHttpServletRequest(), link);
		System.out.println(output);
		Document doc = Jsoup.parse(output);
		assertTrue("Document does not have href", doc.body().getElementById("link").hasAttr("href"));
		assertEquals("HREF is not http://www.example.com", "http://www.example.com", doc.body().getElementById("link").attr("href"));
	}

	@Test
	public void testRenderLinkDefaultConstructorSetAttributes() throws Exception {
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
		AnchorElement link = new AnchorElement();
		Map<String, String> attributes = new LinkedHashMap<>();
		attributes.put("id", "link");
		attributes.put("title", "Link Title");
		attributes.put("href", "http://www.example.com");
		link.setAttributes(attributes);
		link.setContent(new PlainTextElement("Link Text"));
		link.setWeight(10);
		String output = functions.render(new MockHttpServletRequest(), link);
		System.out.println(output);
		Document doc = Jsoup.parse(output);
		assertEquals(10, link.getWeight());
		assertTrue("Document does not have href", doc.body().getElementById("link").hasAttr("href"));
		assertEquals("HREF is not http://www.example.com", "http://www.example.com", doc.body().getElementById("link").attr("href"));
	}

	@Test
	public void testRenderLinkHrefAndContent() throws Exception {
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
		AnchorElement link = new AnchorElement("Title", "http://www.example.com", new PlainTextElement("Link"));
		link.setHtmlId("link");
		String output = functions.render(new MockHttpServletRequest(), link);
		Document doc = Jsoup.parse(output);
		assertTrue("Document does not have href", doc.body().getElementById("link").hasAttr("href"));
		assertEquals("HREF is not http://www.example.com", "http://www.example.com", doc.body().getElementById("link").attr("href"));
	}

	@Test
	public void testRenderLinkHrefAndContentString() throws Exception {
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
		AnchorElement link = new AnchorElement("Title", "http://www.example.com", "Link");
		link.setHtmlId("link");
		String output = functions.render(new MockHttpServletRequest(), link);
		Document doc = Jsoup.parse(output);
		assertTrue("Document does not have href", doc.body().getElementById("link").hasAttr("href"));
		assertEquals("HREF is not http://www.example.com", "http://www.example.com", doc.body().getElementById("link").attr("href"));
	}

	@Test
	public void testRenderLinkHrefTargetContentString() throws Exception {
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
		AnchorElement link = new AnchorElement("Title", "http://www.example.com", "_self", "Link");
		link.setHtmlId("link");
		String output = functions.render(new MockHttpServletRequest(), link);
		Document doc = Jsoup.parse(output);
		assertTrue("Document does not have href", doc.body().getElementById("link").hasAttr("href"));
		assertTrue("Document does not have href", doc.body().getElementById("link").hasAttr("target"));
		assertEquals("HREF is not http://www.example.com", "http://www.example.com", doc.body().getElementById("link").attr("href"));
		assertEquals("HREF is not http://www.example.com", "_self", doc.body().getElementById("link").attr("target"));
	}

	@Test
	public void testRenderLinkHrefTargetContent() throws Exception {
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
		AnchorElement link = new AnchorElement("Title", "http://www.example.com", "_self", new PlainTextElement("Link"));
		link.setHtmlId("link");
		String output = functions.render(new MockHttpServletRequest(), link);
		Document doc = Jsoup.parse(output);
		// Test Getters
		assertEquals("href does not match", "http://www.example.com", link.getHref());
		assertEquals("target does not match", "_self", link.getTarget());
		assertEquals("title does not match", "Title", link.getTitle());

		assertTrue("Document does not have href", doc.body().getElementById("link").hasAttr("href"));
		assertTrue("Document does not have href", doc.body().getElementById("link").hasAttr("target"));
		assertEquals("HREF is not http://www.example.com", "http://www.example.com", doc.body().getElementById("link").attr("href"));
		assertEquals("HREF is not http://www.example.com", "_self", doc.body().getElementById("link").attr("target"));
	}
}
