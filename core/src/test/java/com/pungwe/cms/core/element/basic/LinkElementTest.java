package com.pungwe.cms.core.element.basic;

/**
 * Created by ian on 17/02/2016.
 */

import com.pungwe.cms.core.config.BaseApplicationConfig;
import com.pungwe.cms.core.theme.functions.TemplateFunctions;
import com.pungwe.cms.test.AbstractWebTest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(BaseApplicationConfig.class)
@WebAppConfiguration("src/main/resources")
public class LinkElementTest extends AbstractWebTest {

	@Autowired
	ViewResolver viewResolver;

	@Autowired(required = false)
	LocaleResolver localeResolver;

	@Test
	public void testRenderLinkDefaultConstructor() throws Exception {
		TemplateFunctions functions = new TemplateFunctions(viewResolver, localeResolver);
		LinkElement link = new LinkElement();
		link.setHtmlId("link");
		link.setTitle("Link title");
		link.setHref("http://www.example.com");
		link.setContent(new PlainTextElement("Link Text"));
		String output = functions.render(new MockHttpServletRequest(), link);
		Document doc = Jsoup.parse(output);
		assertTrue("Document does not have href", doc.body().getElementById("link").hasAttr("href"));
		assertEquals("HREF is not http://www.example.com", "http://www.example.com", doc.body().getElementById("link").attr("href"));
	}

	@Test
	public void testRenderLinkHrefAndContent() throws Exception {
		TemplateFunctions functions = new TemplateFunctions(viewResolver, localeResolver);
		LinkElement link = new LinkElement("Title", "http://www.example.com", new PlainTextElement("Link"));
		link.setHtmlId("link");
		String output = functions.render(new MockHttpServletRequest(), link);
		Document doc = Jsoup.parse(output);
		assertTrue("Document does not have href", doc.body().getElementById("link").hasAttr("href"));
		assertEquals("HREF is not http://www.example.com", "http://www.example.com", doc.body().getElementById("link").attr("href"));
	}
}
