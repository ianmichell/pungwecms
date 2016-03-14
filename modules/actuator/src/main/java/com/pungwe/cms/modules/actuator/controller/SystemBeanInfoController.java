package com.pungwe.cms.modules.actuator.controller;

import com.pungwe.cms.core.element.basic.PlainTextElement;
import com.pungwe.cms.core.module.services.ModuleManagementService;
import com.pungwe.cms.core.theme.services.ThemeManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.concurrent.Callable;

/**
 * Created by ian on 14/03/2016.
 */
@Controller
@RequestMapping("/admin/reporting/system/beans")
public class SystemBeanInfoController {

	@Autowired
	ModuleManagementService moduleManagementService;

	@Autowired
	ThemeManagementService themeManagementService;

	@RequestMapping(method= RequestMethod.GET)
	public Callable<String> beans(final Model model) {
		return () -> {
			model.addAttribute("title", "Bean Information");
			model.addAttribute("content", new PlainTextElement("TODO"));
			return "actuator/beans";
		};
	}

}
