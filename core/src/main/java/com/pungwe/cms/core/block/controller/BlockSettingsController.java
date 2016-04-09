package com.pungwe.cms.core.block.controller;

import com.pungwe.cms.core.block.services.BlockManagementService;
import com.pungwe.cms.core.theme.services.ThemeManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.concurrent.Callable;

/**
 * Created by ian on 06/03/2016.
 */
@Controller
@RequestMapping("/admin/structure/block_settings")
@Secured({"administer_blocks"})
public class BlockSettingsController {

    @Autowired
    protected ThemeManagementService themeManagementService;

    @Autowired
    protected BlockManagementService blockManagementService;

    @RequestMapping(value = "/{blockId}", method = RequestMethod.GET)
    public Callable<String> index(final Model model) {
        return () -> {
            return "admin/block/settings";
        };
    }
}
