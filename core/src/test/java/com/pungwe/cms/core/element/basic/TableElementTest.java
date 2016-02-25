package com.pungwe.cms.core.element.basic;

import com.lyncode.jtwig.functions.exceptions.FunctionException;
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
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;

import static org.junit.Assert.assertEquals;

/**
 * Created by ian on 17/02/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(BaseApplicationConfig.class)
public class TableElementTest extends AbstractWebTest {

	@Autowired
	ViewResolver viewResolver;

	@Autowired(required = false)
	LocaleResolver localeResolver;

	@Autowired
	ApplicationContext applicationContext;

	@Test
	public void testCreateTableElement() throws FunctionException {

		TemplateFunctions functions = new TemplateFunctions(applicationContext, viewResolver, localeResolver);

		TableElement table = new TableElement();
		table.setCaption(new PlainTextElement("Caption"));
		table.addHeaderRow(
				new TableElement.Header(new PlainTextElement("Column 1")),
				new TableElement.Header(new PlainTextElement("Column 2")),
				new TableElement.Header(new PlainTextElement("Column 3"))
		);
		table.addRow(
				new TableElement.Column(new LinkElement("Link title", "http://www.example.com", new PlainTextElement("Link"))),
				new TableElement.Column(new PlainTextElement("Column 2 text")),
				new TableElement.Column(new PlainTextElement("Column 3 text"))
		);
		/// Add the footer
		TableElement.Column footer = new TableElement.Column(new PlainTextElement("Footer"));
		footer.addAttribute("colspan", "3");
		table.addFooterRow(footer);

		String output = functions.render(new MockHttpServletRequest(), table);
		Document doc = Jsoup.parse(output);
		assertEquals("Doesn't start with the table element", "table", doc.body().children().first().nodeName());
		assertEquals("Table doesn't have a caption", "Caption", doc.select("table caption").text());
		assertEquals("Table doesn't have three header columns", 3, doc.select("table thead tr th").size());
		assertEquals("Table doesn't have a column 1", "Column 1", doc.select("table thead tr th").get(0).text());
		assertEquals("Table doesn't have a column 2", "Column 2", doc.select("table thead tr th").get(1).text());
		assertEquals("Table doesn't have a column 3", "Column 3", doc.select("table thead tr th").get(2).text());

		assertEquals("Table doesn't have three columns", 3, doc.select("table tbody tr td").size());
		assertEquals("Table doesn't have a link in column 1", "Link", doc.select("table tbody tr td a").get(0).text());
		assertEquals("Table doesn't have a column 2", "Column 2 text", doc.select("table tbody tr td").get(1).text());
		assertEquals("Table doesn't have a column 3", "Column 3 text", doc.select("table tbody tr td").get(2).text());

		assertEquals("Table footer doesn't have one columns", 1, doc.select("table tfoot tr td").size());
		assertEquals("Table footer colspan is not 3", "3", doc.select("table tfoot tr td").attr("colspan"));
		assertEquals("Table footer doesn't have text", "Footer", doc.select("table tfoot tr td").text());
	}
}
