package com.pungwe.cms.core.menu.controller;

import com.pungwe.cms.core.menu.services.MenuManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.Callable;

/**
 * Created by ian on 08/03/2016.
 */
@RequestMapping(value="/admin/structure/menu")
public class MenuController {

	@Autowired
	protected MenuManagementService menuManagementService;

	public Callable<String> index() {
		return () -> {
			return "menu/index";
		};
	}
}
