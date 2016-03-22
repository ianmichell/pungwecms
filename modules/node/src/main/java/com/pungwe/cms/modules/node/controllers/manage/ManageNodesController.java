package com.pungwe.cms.modules.node.controllers.manage;

import com.pungwe.cms.core.annotations.ui.MenuItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Callable;

/**
 * Created by ian on 17/03/2016.
 */
@Controller
@RequestMapping("/admin/content")
@MenuItem(menu="system", parent="admin", name="content", title="Manage Content", description = "Create, edit and review content for your website", weight = -200)
public class ManageNodesController {

	@RequestMapping(method=RequestMethod.GET)
	public Callable<String> index(HttpServletRequest request, Model model) {
		return () -> {
			model.addAttribute("title", "Manage Content");
			return "manage_node/index";
		};
	}
}
