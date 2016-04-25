/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.block.controller;

import com.pungwe.cms.core.annotations.stereotypes.Block;
import com.pungwe.cms.core.annotations.ui.MenuItem;
import com.pungwe.cms.core.block.BlockDefinition;
import com.pungwe.cms.core.block.services.BlockManagementService;
import com.pungwe.cms.core.element.basic.AnchorElement;
import com.pungwe.cms.core.element.basic.TableElement;
import com.pungwe.cms.core.theme.services.ThemeManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.concurrent.Callable;

import static com.pungwe.cms.core.utils.Utils.translate;

@Controller
@RequestMapping("/admin/structure/block_layout/select_block/{region}")
@MenuItem(
        menu = "system",
        parent = "admin.structure.block-layout",
        name = "select-block",
        title = "Select a block", pattern = true,
        task = false
)
public class BlockPlacementController {

    @Autowired
    private BlockManagementService blockManagementService;

    @Autowired
    private ThemeManagementService themeManagementService;

    @RequestMapping(method = RequestMethod.GET)
    public Callable<String> get(final @PathVariable("region") String region, Model model) {
        return () -> {
            final TableElement element = new TableElement();
            element.addHeaderRow(translate("Name"), translate("Category"), translate("Operations"));
            List<BlockDefinition> blockDefinitions = blockManagementService.listAllBlocks();
            blockDefinitions.forEach(blockDefinition -> {

                Block info = blockManagementService.getBlockInfoForBlock(blockDefinition);

                if (info == null) {
                    return;
                }

                AnchorElement anchorElement = new AnchorElement();
                anchorElement.setTitle(translate("Place this block"));
                anchorElement.addContent(translate("Place block"));
                anchorElement.setHref("/admin/structure/block_layout/add_block/" + region + "/" + info.value());

                element.addRow(
                        new TableElement.Column(translate(info.label())),
                        new TableElement.Column(translate(info.category())),
                        new TableElement.Column(anchorElement)
                );
            });
            model.addAttribute("content", element);
            return "admin/block/select_block";
        };
    }

    @ModelAttribute("title")
    public String title() {
        return translate("Select a block");
    }
}
