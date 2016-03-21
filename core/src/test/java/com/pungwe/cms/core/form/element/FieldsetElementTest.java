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

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by ian on 25/02/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(BaseApplicationConfig.class)
@WebAppConfiguration("src/main/resources")
public class FieldsetElementTest extends AbstractWebTest {

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	ViewResolver viewResolver;

	@Autowired(required = false)
	LocaleResolver localeResolver;

	@Test
	public void testRenderFieldset() throws Exception {
		TemplateFunctions templateFunctions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

		FieldsetElement fieldsetElement = new FieldsetElement();
		fieldsetElement.setLegend("Fieldset");
		fieldsetElement.addClass("myclass");

		String output = templateFunctions.render(new MockHttpServletRequest(), fieldsetElement);
		Document doc = Jsoup.parse(output);
		assertEquals("Fieldset element text does not match", "Fieldset", doc.select("fieldset legend").first().text());
		assertEquals("Fieldset element class is not correct", "myclass", doc.select("fieldset").first().attr("class"));
	}

	@Test
	public void testRenderFieldsetWithChildren() throws Exception {
		TemplateFunctions templateFunctions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

		FieldsetElement fieldsetElement = new FieldsetElement();
		fieldsetElement.setLegend("Fieldset");
		fieldsetElement.addClass("myclass");

		StringRenderedElement child1 = new StringRenderedElement();
		child1.setLabel("Child 1");
		child1.setName("child");

		StringRenderedElement child2 = new StringRenderedElement();
		child2.setName("child");
		child2.setLabel("Child 2");
		child2.setDelta(1);

		fieldsetElement.addContent(child1, child2);

		String output = templateFunctions.render(new MockHttpServletRequest(), fieldsetElement);
		Document doc = Jsoup.parse(output);
		assertEquals("Fieldset element text does not match", "Fieldset", doc.select("fieldset legend").first().text());
		assertEquals("Fieldset element class is not correct", "myclass", doc.select("fieldset").first().attr("class"));
		assertEquals("Fieldset child does not match", "child[0].value", doc.select("fieldset input[type=text]").get(0).attr("name"));
		assertEquals("Fieldset child does not match", "child[1].value", doc.select("fieldset input[type=text]").get(1).attr("name"));
	}

	@Test
	public void testRenderFieldsetSetChildren() throws Exception {
		TemplateFunctions templateFunctions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

		FieldsetElement fieldsetElement = new FieldsetElement();
		fieldsetElement.setLegend("Fieldset");
		fieldsetElement.addClass("myclass");

		StringRenderedElement child1 = new StringRenderedElement();
		child1.setLabel("Child 1");
		child1.setName("child");

		StringRenderedElement child2 = new StringRenderedElement();
		child2.setName("child");
		child2.setLabel("Child 2");
		child2.setDelta(1);

		fieldsetElement.setContent(Arrays.asList(child1, child2));

		String output = templateFunctions.render(new MockHttpServletRequest(), fieldsetElement);
		Document doc = Jsoup.parse(output);
		assertEquals("Fieldset element text does not match", "Fieldset", doc.select("fieldset legend").first().text());
		assertEquals("Fieldset element class is not correct", "myclass", doc.select("fieldset").first().attr("class"));
		assertEquals("Fieldset child does not match", "child[0].value", doc.select("fieldset input[type=text]").get(0).attr("name"));
		assertEquals("Fieldset child does not match", "child[1].value", doc.select("fieldset input[type=text]").get(1).attr("name"));
	}
}
