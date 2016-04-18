package com.pungwe.cms.core.system.element.templates;

import com.pungwe.cms.config.TestConfig;
import com.pungwe.cms.core.config.BaseApplicationConfig;
import com.pungwe.cms.core.element.RenderedElement;
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

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ian on 27/02/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({TestConfig.class, BaseApplicationConfig.class})
@WebAppConfiguration("src/main/resources")
public class PageElementTest extends AbstractWebTest {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    ViewResolver viewResolver;

    @Autowired(required = false)
    LocaleResolver localeResolver;

    @Test
    public void testPageElement() throws Exception {

        TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

        PageElement pageElement = new PageElement();
        assertTrue(pageElement.excludedAttributes().isEmpty());

        pageElement.addRegion("region_list", Arrays.asList(new PlainTextElement("List")));
        pageElement.addRegion("region_single", new PlainTextElement("Single"));
        pageElement.addRegion("region_add", new PlainTextElement("Add"));
        pageElement.addRegion("region_add", new PlainTextElement(" "));
        pageElement.addRegion("region_add", new PlainTextElement("This"));

        String output = functions.render(new MockHttpServletRequest(), pageElement);
        Document doc = Jsoup.parse(output);

        assertEquals("List", doc.select(".region-region_list").text());
        assertEquals("Single", doc.select(".region-region_single").text());
        assertEquals("Add This", doc.select(".region-region_add").text());
    }
}
