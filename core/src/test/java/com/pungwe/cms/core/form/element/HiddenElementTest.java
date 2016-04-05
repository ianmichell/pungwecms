package com.pungwe.cms.core.form.element;

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
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;

import static org.junit.Assert.assertEquals;

/**
 * Created by ian on 25/02/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({TestConfig.class, BaseApplicationConfig.class})
@WebAppConfiguration("src/main/resources")
public class HiddenElementTest extends AbstractWebTest {

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	ViewResolver viewResolver;

	@Autowired(required = false)
	LocaleResolver localeResolver;

	// FIXME: Test name, as it should be element_name[delta]
	@Test
	public void testHiddenElementTest() throws Exception {

		// Get the ability to render stuff
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

		HiddenRenderedElement element = new HiddenRenderedElement();
		element.setHtmlId("hidden");
		element.setDefaultValue("Default Value");
		element.setName("hidden");

		String output = functions.render(new MockHttpServletRequest(), element);
		Document doc = Jsoup.parse(output);
		assertEquals("Name does not match", "hidden[0].value", doc.getElementById("hidden").attr("name"));
		assertEquals("Hidden Value does not match", "Default Value", doc.getElementById("hidden").attr("value"));
	}
}
