package com.pungwe.cms.core.block.controller;

import com.pungwe.cms.core.annotations.ui.MenuItem;
import com.pungwe.cms.core.block.BlockConfig;
import com.pungwe.cms.core.block.services.BlockManagementService;
import com.pungwe.cms.core.theme.services.ThemeManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by ian on 06/03/2016.
 */
@Controller
@RequestMapping("/admin/structures/block_layout")
public class BlockLayoutController {

	@Autowired
	protected ThemeManagementService themeManagementService;

	@Autowired
	protected BlockManagementService blockManagementService;

	@MenuItem(
			menu = "system",
			name = "block-layout",
			parent = "admin.structure",
			title = "Block Layout",
			description = "Manage your block layout"
	)
	@RequestMapping(method=RequestMethod.GET)
	public Callable<String> index(final Model model) {
		return () -> {
			List<String> regions = themeManagementService.getRegionsForDefaultTheme();
			List<BlockConfig> blocks = blockManagementService.getBlockConfigForDefaultTheme();
			return "admin/block/layout";
		};
	}

}
