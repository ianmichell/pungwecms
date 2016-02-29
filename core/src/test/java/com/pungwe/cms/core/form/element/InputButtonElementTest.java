package com.pungwe.cms.core.form.element;

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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by ian on 27/02/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(BaseApplicationConfig.class)
@WebAppConfiguration("src/main/resources")
public class InputButtonElementTest extends AbstractWebTest {

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	ViewResolver viewResolver;

	@Autowired(required = false)
	LocaleResolver localeResolver;

	/** Code Coverage */
	@Test
	public void testEnum() throws Exception {
		InputButtonElement.InputButtonType[] values = InputButtonElement.InputButtonType.values();
		assertArrayEquals(new InputButtonElement.InputButtonType[] {InputButtonElement.InputButtonType.SUBMIT, InputButtonElement.InputButtonType.RESET, InputButtonElement.InputButtonType.BUTTON}, values);

		// Value of (why it comes up in coverage report, I don't know...
		assertEquals(InputButtonElement.InputButtonType.BUTTON, InputButtonElement.InputButtonType.valueOf("BUTTON"));
		assertEquals(InputButtonElement.InputButtonType.RESET, InputButtonElement.InputButtonType.valueOf("RESET"));
		assertEquals(InputButtonElement.InputButtonType.SUBMIT, InputButtonElement.InputButtonType.valueOf("SUBMIT"));
	}

	@Test
	public void testRenderInputButtonWithTypeConstructor() throws Exception {
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

		InputButtonElement element = new InputButtonElement(InputButtonElement.InputButtonType.SUBMIT);
		element.setName("button");
		element.setValue("My Button");

		String output = functions.render(new MockHttpServletRequest(), element);
		Document doc = Jsoup.parse(output);
		assertEquals("Button value is not correct", "My Button", doc.body().select("input[type=submit]").attr("value"));
		assertEquals("Button name is not correct", "button[0]", doc.body().select("input[type=submit]").attr("name"));
	}

	@Test
	public void testRenderInputButtonWithTypeValueConstructor() throws Exception {
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

		InputButtonElement element = new InputButtonElement(InputButtonElement.InputButtonType.SUBMIT, "My Button");
		element.setName("button");

		String output = functions.render(new MockHttpServletRequest(), element);
		Document doc = Jsoup.parse(output);
		assertEquals("Button value is not correct", "My Button", doc.body().select("input[type=submit]").attr("value"));
		assertEquals("Button name is not correct", "button[0]", doc.body().select("input[type=submit]").attr("name"));
	}

	@Test
	public void testRenderInputButtonWithTypeNameValueConstructor() throws Exception {
		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

		InputButtonElement element = new InputButtonElement(InputButtonElement.InputButtonType.SUBMIT, "button", "My Button");

		String output = functions.render(new MockHttpServletRequest(), element);
		Document doc = Jsoup.parse(output);
		assertEquals("Button value is not correct", "My Button", doc.body().select("input[type=submit]").attr("value"));
		assertEquals("Button name is not correct", "button[0]", doc.body().select("input[type=submit]").attr("name"));
	}
}
