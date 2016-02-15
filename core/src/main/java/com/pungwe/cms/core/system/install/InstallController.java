package com.pungwe.cms.core.system.install;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.concurrent.Callable;

/**
 * Installation controller. If the CMS has not been marked as installed, then this will run.
 */
@Controller
@RequestMapping("/install")
public class InstallController {

	@RequestMapping(method= RequestMethod.GET)
	public Callable<String> index(Model model) {
		return () -> {
			return "install/index";
		};
	}


}
