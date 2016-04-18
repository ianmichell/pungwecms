package com.pungwe.cms.core.system.element.templates;

import com.pungwe.cms.config.TestConfig;
import com.pungwe.cms.core.config.BaseApplicationConfig;
import com.pungwe.cms.core.element.basic.*;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ian on 27/02/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({TestConfig.class, BaseApplicationConfig.class})
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

        // Null check
        assertTrue(element.getTitle().isEmpty());

        element.setTitle(Arrays.asList("My"));
        element.addTitle("Title");
        element.addClass("page");
        element.addBodyAttribute("class", "body");
        element.addToCss(new LinkElement("stylesheet", "styles.css"));
        element.addToHead(new MetaElement("description", "HTML Element Test"));
        element.addToJsTop(new ScriptElement("top.js", "text/javascript"));
        element.addToJsBottom(new ScriptElement("bottom.js", "text/javascript"));

        // Page Content
        SpanElement top = new SpanElement("Top");
        top.addClass("top");
        element.addToPageTop(top);

        SpanElement content = new SpanElement("Content");
        content.setHtmlId("main_content");
        content.addClass("content");
        element.addToPageContent(content);

        SpanElement bottom = new SpanElement("Bottom");
        bottom.addClass("bottom");
        element.addToPageBottom(bottom);

        String output = templateFunctions.render(new MockHttpServletRequest(), element);
        Document doc = Jsoup.parse(output);
        assertEquals("title missing", "My | Title", doc.select("head title").first().text());
        assertEquals("top js tag missing", "top.js", doc.select("head script").first().attr("src"));
        assertEquals("css tag missing", "styles.css", doc.select("head link").first().attr("href"));
        assertEquals("bottom js tag missing", "bottom.js", doc.select("body script").first().attr("src"));
        assertEquals("html class missing", "page", doc.select("html").first().attr("class"));
        assertEquals("body class missing", "body", doc.select("body").first().attr("class"));
        assertEquals("body top span is missing", "Top", doc.select("body span[class=top]").first().text());
        assertEquals("body top span is missing", "Content", doc.select("body span[class=content]").first().text());
        assertEquals("body top span is missing", "Bottom", doc.select("body span[class=bottom]").first().text());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddInvalidCSSElement() throws Exception {
        HtmlElement element = new HtmlElement();
        element.addToCss(new MetaElement("name", "description"));
    }

    @Test
    public void testRenderHTMLSetAttributes() throws Exception {

        TemplateFunctions templateFunctions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

        HtmlElement element = new HtmlElement();

        Map<String, String> attributes = new HashMap<String, String>();
        Map<String, String> bodyAttributes = new HashMap<String, String>();
        attributes.put("class", "page");
        bodyAttributes.put("class", "body");
        element.setAttributes(attributes);
        element.setBodyAttributes(bodyAttributes);

        element.setTitle(Arrays.asList("Title"));
        element.addClass("page");
        element.addBodyAttribute("class", "body");
        element.addToCss(new LinkElement("stylesheet", "styles.css"));
        element.addToCss(new StyleElement("h1 { color: red; }"));
        element.addToHead(new MetaElement("description", "HTML Element Test"));
        element.addToJsTop(new ScriptElement("top.js", "text/javascript"));
        element.addToJsBottom(new ScriptElement("bottom.js", "text/javascript"));

        // Page Content
        SpanElement top = new SpanElement("Top");
        top.addClass("top");
        element.addToPageTop(top);

        SpanElement content = new SpanElement("Content");
        content.setHtmlId("main_content");
        content.addClass("content");
        element.addToPageContent(content);

        SpanElement bottom = new SpanElement("Bottom");
        bottom.addClass("bottom");
        element.addToPageBottom(bottom);

        String output = templateFunctions.render(new MockHttpServletRequest(), element);
        Document doc = Jsoup.parse(output);
        assertEquals("title missing", "Title", doc.select("head title").first().text());
        assertEquals("top js tag missing", "top.js", doc.select("head script").first().attr("src"));
        assertEquals("css tag missing", "styles.css", doc.select("head link").first().attr("href"));
        assertEquals("style tag content is missing", "h1 { color: red; }", doc.select("style").first().html());
        assertEquals("bottom js tag missing", "bottom.js", doc.select("body script").first().attr("src"));
        assertEquals("html class missing", "page", doc.select("html").first().attr("class"));
        assertEquals("body class missing", "body", doc.select("body").first().attr("class"));
        assertEquals("body top span is missing", "Top", doc.select("body span[class=top]").first().text());
        assertEquals("body top span is missing", "Content", doc.select("body span[class=content]").first().text());
        assertEquals("body top span is missing", "Bottom", doc.select("body span[class=bottom]").first().text());
    }
}
