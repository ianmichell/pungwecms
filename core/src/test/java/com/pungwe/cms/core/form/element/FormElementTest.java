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
public class FormElementTest extends AbstractWebTest {

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	ViewResolver viewResolver;

	@Autowired(required = false)
	LocaleResolver localeResolver;

	@Test
	public void testRenderFormElement() throws Exception {

		FormElement element = new FormElement();
		element.setHtmlId("form");
		element.setAcceptCharset("UTF-8");
		element.setAction("action");
		element.setEnctype("multipart/form-data");
		element.setMethod("post");
		element.setName("form");
		element.setTarget("_self");

		FieldsetElement fieldsetElement1 = new FieldsetElement();
		fieldsetElement1.setLegend("My Form");
		fieldsetElement1.addClass("myclass");

		StringRenderedElement child1 = new StringRenderedElement();
		child1.setHtmlId("string");
		child1.setLabel("Child 1");
		child1.setName("child");

		fieldsetElement1.addContent(child1);

		FieldsetElement fieldsetElement2 = new FieldsetElement();
		fieldsetElement2.addClass("form-actions");

		InputButtonRenderedElement child2 = new InputButtonRenderedElement(InputButtonRenderedElement.InputButtonType.SUBMIT, "Submit");
		child2.setName("submit");

		fieldsetElement2.addContent(child2);

		element.addContent(fieldsetElement1, fieldsetElement2);

		TemplateFunctions templateFunctions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

		String output = templateFunctions.render(new MockHttpServletRequest(), element);

		Document doc = Jsoup.parse(output);

		// Check attribute accessors
		assertEquals("Attribute accessor does not match", "UTF-8", element.getAcceptCharset());
		assertEquals("Attribute accessor does not match", "_self", element.getTarget());
		assertEquals("Attribute accessor does not match", "action", element.getAction());
		assertEquals("Attribute accessor does not match", "post", element.getMethod());
		assertEquals("Attribute accessor does not match", "multipart/form-data", element.getEnctype());
		assertEquals("Attribute accessor does not match", "form", element.getName());

		assertEquals("Form Element attribute accept-charset does not match", "UTF-8", doc.getElementById("form").attr("accept-charset"));
		assertEquals("Form Element attribute action does not match", "action", doc.getElementById("form").attr("action"));
		assertEquals("Form Element attribute enctype does not match", "multipart/form-data", doc.getElementById("form").attr("enctype"));
		assertEquals("Form Element attribute method does not match", "post", doc.getElementById("form").attr("method"));
		assertEquals("Form Element attribute target does not match", "_self", doc.getElementById("form").attr("target"));
		assertEquals("Form Element attribute name does not match", "form", doc.getElementById("form").attr("name"));

		assertEquals("Fieldset element text does not match", "My Form", doc.select("fieldset legend").first().text());
		assertEquals("Fieldset element class is not correct", "myclass", doc.select("fieldset").get(0).attr("class"));
		assertEquals("Fieldset child does not match", "child[0].value", doc.select("fieldset input[type=text]").get(0).attr("name"));

		assertEquals("Fieldset element class is not correct", "form-actions", doc.select("fieldset").get(1).attr("class"));
		assertEquals("Fieldset child does not match", "submit", doc.select("fieldset input[type=submit]").first().attr("name"));
	}

	@Test
	public void testRenderFormElementSetChildren() throws Exception {

		FormElement element = new FormElement();
		element.setHtmlId("form");
		element.setAcceptCharset("UTF-8");
		element.setAction("action");
		element.setEnctype("multipart/form-data");
		element.setMethod("post");
		element.setName("form");
		element.setTarget("_self");

		FieldsetElement fieldsetElement1 = new FieldsetElement();
		fieldsetElement1.setLegend("My Form");
		fieldsetElement1.addClass("myclass");

		StringRenderedElement child1 = new StringRenderedElement();
		child1.setHtmlId("string");
		child1.setLabel("Child 1");
		child1.setName("child");

		fieldsetElement1.addContent(child1);

		FieldsetElement fieldsetElement2 = new FieldsetElement();
		fieldsetElement2.addClass("form-actions");

		InputButtonRenderedElement child2 = new InputButtonRenderedElement(InputButtonRenderedElement.InputButtonType.SUBMIT, "Submit");
		child2.setName("submit");

		fieldsetElement2.addContent(child2);

		element.setContent(Arrays.asList(fieldsetElement1, fieldsetElement2));

		TemplateFunctions templateFunctions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

		String output = templateFunctions.render(new MockHttpServletRequest(), element);

		Document doc = Jsoup.parse(output);

		// Check attribute accessors
		assertEquals("Attribute accessor does not match", "UTF-8", element.getAcceptCharset());
		assertEquals("Attribute accessor does not match", "_self", element.getTarget());
		assertEquals("Attribute accessor does not match", "action", element.getAction());
		assertEquals("Attribute accessor does not match", "post", element.getMethod());
		assertEquals("Attribute accessor does not match", "multipart/form-data", element.getEnctype());
		assertEquals("Attribute accessor does not match", "form", element.getName());

		assertEquals("Form Element attribute accept-charset does not match", "UTF-8", doc.getElementById("form").attr("accept-charset"));
		assertEquals("Form Element attribute action does not match", "action", doc.getElementById("form").attr("action"));
		assertEquals("Form Element attribute enctype does not match", "multipart/form-data", doc.getElementById("form").attr("enctype"));
		assertEquals("Form Element attribute method does not match", "post", doc.getElementById("form").attr("method"));
		assertEquals("Form Element attribute target does not match", "_self", doc.getElementById("form").attr("target"));
		assertEquals("Form Element attribute name does not match", "form", doc.getElementById("form").attr("name"));

		assertEquals("Fieldset element text does not match", "My Form", doc.select("fieldset legend").first().text());
		assertEquals("Fieldset element class is not correct", "myclass", doc.select("fieldset").get(0).attr("class"));
		assertEquals("Fieldset child does not match", "child[0].value", doc.select("fieldset input[type=text]").get(0).attr("name"));

		assertEquals("Fieldset element class is not correct", "form-actions", doc.select("fieldset").get(1).attr("class"));
		assertEquals("Fieldset child does not match", "submit", doc.select("fieldset input[type=submit]").first().attr("name"));
	}
}
