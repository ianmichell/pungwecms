package com.pungwe.cms.core.block.controller;

import com.pungwe.cms.core.annotations.ui.MenuItem;
import com.pungwe.cms.core.block.BlockConfig;
import com.pungwe.cms.core.block.services.BlockManagementService;
import com.pungwe.cms.core.form.element.FormElement;
import com.pungwe.cms.core.theme.services.ThemeManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.concurrent.Callable;

import static com.pungwe.cms.core.utils.Utils.getRequestPathVariable;
import static com.pungwe.cms.core.utils.Utils.translate;

/**
 * Created by ian on 06/03/2016.
 */
@MenuItem(
        menu = "system",
        name = "edit-block",
        parent = "admin.structure.block-layout",
        title = "Edit block settings",
        description = "Edit block settings"
)
@Controller
@RequestMapping("/admin/structure/block_layout/{blockId}/settings")
@Secured({"administer_blocks"})
public class BlockSettingsController extends AbstractBlockEditController {

    @Autowired
    protected ThemeManagementService themeManagementService;

    @Autowired
    protected BlockManagementService blockManagementService;

    @Override
    protected BlockConfig blockConfig() {
        return blockManagementService.getBlockConfigById(getRequestPathVariable("blockId"));
    }

    @Override
    protected void submit(FormElement<BlockConfig> form, Errors errors) {

    }

    @ModelAttribute("title")
    public String title() {
        return translate("Edit block settings");
    }

    @Override
    public String getFormId() {
        return "block-settings-form";
    }

    @RequestMapping(method = RequestMethod.GET)
    public Callable<String> index(@PathVariable("blockId") final String blockId, final Model model) {
        return () -> {
            return "admin/block/settings";
        };
    }
}
