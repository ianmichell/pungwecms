package com.pungwe.cms.modules.node.controllers.content;

import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.basic.PlainTextElement;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.concurrent.Callable;

/**
 * Created by ian on 11/01/2016.
 */
@RequestMapping("/node")
public class NodeController {

	@RequestMapping(method=RequestMethod.GET)
	public Callable<String> handle(final Model model) {
		return () -> {
			model.addAttribute("content", new LinkedList<RenderedElement>());
			return "node/list";
		};
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Callable<String> handle(@PathVariable("id") String id, final Model model) {
		return () -> {
			model.addAttribute("content", new PlainTextElement("Node by id"));
			return "node/view";
		};
	}
}
