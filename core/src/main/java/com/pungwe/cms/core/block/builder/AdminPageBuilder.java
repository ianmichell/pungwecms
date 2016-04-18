/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.block.builder;

import com.pungwe.cms.core.annotations.stereotypes.Block;
import com.pungwe.cms.core.block.BlockConfig;
import com.pungwe.cms.core.block.BlockDefinition;
import com.pungwe.cms.core.block.services.BlockManagementService;
import com.pungwe.cms.core.block.system.MainContentBlock;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.system.builder.PageBuilder;
import com.pungwe.cms.core.system.element.templates.PageElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class AdminPageBuilder implements PageBuilder {

    @Autowired
    private BlockManagementService blockManagementService;

    @Override
    public void build(HttpServletRequest request, PageElement page, Map<String, Object> model) {

        // Fetch the relevant block config for the current theme...
        Map<String, List<BlockConfig>> blocksByRegion = blockManagementService.listAllAdminBlocks();

        blocksByRegion.entrySet().forEach(entry -> {
            List<RenderedElement> elements = new LinkedList<RenderedElement>();
            entry.getValue().stream().forEach(blockConfig -> {
                // Don't need block config for this...
                Optional<BlockDefinition> blockDefinition = blockManagementService.getBlockDefinitionByName(blockConfig.getName());
                if (!blockDefinition.isPresent()) {
                    return;
                }
                RenderedElement blockElement = blockManagementService.buildBlockElement(blockConfig, blockDefinition.get(), model);
                if (blockElement != null) {
                    elements.add(blockElement);
                }
            });
            page.addRegion(entry.getKey(), elements);
        });
    }
}
