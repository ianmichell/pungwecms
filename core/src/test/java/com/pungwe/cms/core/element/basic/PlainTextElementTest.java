package com.pungwe.cms.core.element.basic;

import com.pungwe.cms.core.config.BaseApplicationConfig;
import com.pungwe.cms.core.theme.functions.TemplateFunctions;
import com.pungwe.cms.test.AbstractWebTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.junit.Assert.assertEquals;

/**
 * Created by ian on 17/02/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(BaseApplicationConfig.class)
public class PlainTextElementTest extends AbstractWebTest {

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	ViewResolver viewResolver;

	@Autowired(required = false)
	LocaleResolver localeResolver;

	@Test
	public void testPlainTextElement() throws Exception {
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
		MockHttpServletRequest request = new MockHttpServletRequest();
		PlainTextElement element = new PlainTextElement("Text");
		String html = functions.render(request, element);
		assertEquals("Text incorrect", "Text", html);
	}

	@Test
	public void testDefautConstructor() throws Exception {
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
		MockHttpServletRequest request = new MockHttpServletRequest();
		PlainTextElement element = new PlainTextElement();
		element.setText("Text");
		String html = functions.render(request, element);
		assertEquals("Text incorrect", "Text", html);
	}

	@Test
	public void testDefautConstructorExcludedAttributes() throws Exception {
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);
		MockHttpServletRequest request = new MockHttpServletRequest();
		PlainTextElement element = new PlainTextElement();
		element.setText("Text");
		String html = functions.render(request, element);
		assertEquals("Text incorrect", "Text", html);
		assertEquals("Attributes should be an empty list", 0, element.excludedAttributes().size());
	}
}
