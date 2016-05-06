/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.block.controller;

import com.pungwe.cms.core.annotations.stereotypes.Block;
import com.pungwe.cms.core.annotations.ui.MenuItem;
import com.pungwe.cms.core.block.BlockConfig;
import com.pungwe.cms.core.block.BlockDefinition;
import com.pungwe.cms.core.block.services.BlockManagementService;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.form.controller.AbstractFormController;
import com.pungwe.cms.core.form.element.FormElement;
import com.pungwe.cms.core.form.element.TextElement;
import com.pungwe.cms.core.system.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

import static com.pungwe.cms.core.utils.Utils.getRequestPathVariable;
import static com.pungwe.cms.core.utils.Utils.translate;

@MenuItem(
        menu = "system",
        name = "add-block",
        parent = "admin.structure.block-layout",
        title = "Add a block",
        description = "Add a block to your layout"
)
@Controller
@RequestMapping("/admin/structure/block_layout/add_block/{region}/{blockId}")
@Secured({"administer_blocks"})
public class BlockAddController extends AbstractBlockEditController {


    @Override
    public String getFormId() {
        return "add-block-form";
    }

    @ModelAttribute("title")
    public String title() {
        return translate("Add a block");
    }

    @Override
    protected BlockConfig blockConfig() {
        BlockConfig config = blockManagementService.newInstance();
        config.setName(getRequestPathVariable("blockId"));
        config.setRegion(getRequestPathVariable("region"));
        return config;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Callable<String> get() {
        return () -> "admin/block/add";
    }
}
