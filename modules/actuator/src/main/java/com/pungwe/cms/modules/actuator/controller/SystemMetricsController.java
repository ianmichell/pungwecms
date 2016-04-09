package com.pungwe.cms.modules.actuator.controller;

import com.pungwe.cms.core.element.basic.PlainTextElement;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.concurrent.Callable;

import static com.pungwe.cms.core.utils.Utils.translate;

/**
 * Created by ian on 14/03/2016.
 */
@Controller
@RequestMapping("/admin/reporting/system/metrics")
public class SystemMetricsController {

	@RequestMapping(method=RequestMethod.GET)
	public Callable<String> metrics(Model model) {
		return () -> {
			model.addAttribute("title", translate("Metrics"));
			model.addAttribute("content", new PlainTextElement("TODO"));
			return "actuator/metrics";
		};
	}
}
