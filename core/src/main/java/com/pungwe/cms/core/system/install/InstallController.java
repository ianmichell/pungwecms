package com.pungwe.cms.core.system.install;

import com.pungwe.cms.core.element.basic.PlainTextElement;
import com.pungwe.cms.core.element.basic.TableElement;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.concurrent.Callable;

/**
 * Installation controller. If the CMS has not been marked as installed, then this will run.
 */
@Controller
@RequestMapping("/install")
public class InstallController {

	@RequestMapping(method= RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public Callable<String> index(Model model) {
		return () -> {
			TableElement table = new TableElement();
			table.addHeaderRow(
					new TableElement.Header(new PlainTextElement("Title")),
					new TableElement.Header(new PlainTextElement("Description")),
					new TableElement.Header(new PlainTextElement("Operations"))
			);
			table.setCaption(new PlainTextElement("Table Caption"));
			table.addAttribute("class", "table table-striped");
			model.addAttribute("content", table);
			return "install/index";
		};
	}


}
