/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.block.controller;

import com.pungwe.cms.core.annotations.stereotypes.Block;
import com.pungwe.cms.core.block.BlockConfig;
import com.pungwe.cms.core.block.BlockDefinition;
import com.pungwe.cms.core.block.services.BlockManagementService;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.basic.DivElement;
import com.pungwe.cms.core.form.FormRenderedElement;
import com.pungwe.cms.core.form.controller.AbstractFormController;
import com.pungwe.cms.core.form.element.ButtonElement;
import com.pungwe.cms.core.form.element.FormElement;
import com.pungwe.cms.core.form.element.InputButtonElement;
import com.pungwe.cms.core.form.element.TextElement;
import com.pungwe.cms.core.security.forms.VisibilityFormBuilder;
import com.pungwe.cms.core.system.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import java.util.*;
import java.util.stream.Collectors;

import static com.pungwe.cms.core.utils.Utils.getRequestPathVariable;
import static com.pungwe.cms.core.utils.Utils.isOfType;
import static com.pungwe.cms.core.utils.Utils.translate;

public abstract class AbstractBlockEditController extends AbstractFormController<BlockConfig> {

    @Autowired
    protected BlockManagementService blockManagementService;

    @Autowired
    protected VisibilityFormBuilder visibilityFormBuilder;

    @Override
    public void build(FormElement<BlockConfig> element) {

        BlockConfig blockConfig = blockConfig();

        element.setTargetObject(blockConfig);

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

        // Build visibility form
        RenderedElement visibility = visibilityFormBuilder.buildVisibilityForm(blockConfig.getSettings());

        element.addContent(visibility);

        DivElement formActions = new DivElement();
        formActions.addClass("form-actions");

        InputButtonElement save = new InputButtonElement(InputButtonElement.InputButtonType.SUBMIT);
        save.addClass("button");
        save.setValue(translate("Save changes"));
        formActions.addContent(save);

        element.addContent(formActions);

        element.addSubmitHandler((form, variables) -> {
            final Map<String, Object> settings = new LinkedHashMap<String, Object>();

            // Get a map of fields grouped by name
            Map<String, List<FormRenderedElement<?>>> fields = form.getGroupedFields().entrySet().stream()
                    .filter(e -> {
                        Optional<FormRenderedElement<?>> field = e.getValue().stream().findFirst();
                        if (!field.isPresent() || isOfType(field.get().getClass(), InputButtonElement.class,
                                ButtonElement.class)) {
                            return false;
                        }
                        switch (e.getKey()) {
                            case "title":
                            case "region":
                                return false;
                            default:
                                return true;
                        }
                    }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            // Create settings object
            fields.forEach((k, v) -> {
                Object value = null;
                // Logic required in form.getValue(s) -> might as well use that, we'll be looping either way
                if (v.size() > 1) {
                    List<Object> values = new ArrayList<Object>(v.size());
                    values.addAll(form.getValues(k).stream().filter(fv -> fv != null).collect(Collectors.toList()));
                    if (values.isEmpty()) {
                        return;
                    }
                    value = values;
                } else {
                    value = form.getValue(k, 0);
                    if (StringUtils.isEmpty(value)) {
                        return;
                    }
                }
                // Add values to settings object
                settings.put(k, value);
            });

            // Update title
            blockConfig.setSettings(settings);
            blockConfig.setAdminTitle((String) form.getValue("title", 0));

            blockManagementService.updateBlocks(Arrays.asList(blockConfig));
        });
    }

    protected abstract BlockConfig blockConfig();
}
