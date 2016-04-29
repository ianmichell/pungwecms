/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.block.controller;

import com.pungwe.cms.core.annotations.stereotypes.Block;
import com.pungwe.cms.core.block.BlockConfig;
import com.pungwe.cms.core.block.BlockDefinition;
import com.pungwe.cms.core.block.services.BlockManagementService;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.form.controller.AbstractFormController;
import com.pungwe.cms.core.form.element.FormElement;
import com.pungwe.cms.core.form.element.TextElement;
import com.pungwe.cms.core.system.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.pungwe.cms.core.utils.Utils.getRequestPathVariable;
import static com.pungwe.cms.core.utils.Utils.translate;

public abstract class AbstractBlockEditController extends AbstractFormController<BlockConfig> {

    @Autowired
    protected BlockManagementService blockManagementService;

    @Override
    public void build(FormElement<BlockConfig> element) {

        BlockConfig blockConfig = blockConfig();

        String blockName = blockConfig.getName();
        String region = blockConfig.getRegion();

        Optional<BlockDefinition> blockDefinition = blockManagementService.getBlockDefinitionByName(blockName);

        if (!blockDefinition.isPresent()) {
            throw new ResourceNotFoundException(translate("Could not find block: %s", blockName));
        }

        Optional<Block> blockInfo = blockManagementService.getBlockInfoByBlockName(blockName);

        if (!blockInfo.isPresent()) {
            throw new ResourceNotFoundException(translate("Could not find block: %s", blockName));
        }

        TextElement title = new TextElement();
        title.setHtmlId("add-block-title");
        title.setName("title");
        title.setLabel(translate("Block Title"));
        title.setDefaultValue(blockInfo.get().label());
        title.setRequired(true);
        element.addContent(title);

        List<RenderedElement> settingsForm = new ArrayList<>();
        blockDefinition.get().buildSettingsForm(settingsForm, blockConfig.getSettings());

        element.addContent(settingsForm);

    }

    protected abstract BlockConfig blockConfig();

    protected abstract void submit(FormElement<BlockConfig> form, Errors errors);
}
