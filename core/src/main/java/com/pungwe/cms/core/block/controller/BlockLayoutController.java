package com.pungwe.cms.core.block.controller;

import com.pungwe.cms.core.annotations.stereotypes.Block;
import com.pungwe.cms.core.annotations.ui.MenuItem;
import com.pungwe.cms.core.block.BlockConfig;
import com.pungwe.cms.core.block.services.BlockManagementService;
import com.pungwe.cms.core.element.basic.*;
import com.pungwe.cms.core.form.controller.AbstractFormController;
import com.pungwe.cms.core.form.element.*;
import com.pungwe.cms.core.form.validation.NumberValidator;
import com.pungwe.cms.core.theme.services.ThemeManagementService;
import com.pungwe.cms.core.utils.Utils;
import com.pungwe.cms.core.utils.services.StatusMessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.pungwe.cms.core.utils.Utils.translate;

/**
 * Created by ian on 06/03/2016.
 */
@MenuItem(
        menu = "system",
        name = "block-layout",
        parent = "admin.structure",
        title = "Block Layout",
        description = "Manage your block layout"
)
@Controller
@RequestMapping("/admin/structure/block_layout")
@Secured({"administer_blocks"})
public class BlockLayoutController extends AbstractFormController {

    @Autowired
    protected ThemeManagementService themeManagementService;

    @Autowired
    protected BlockManagementService blockManagementService;

    @Autowired
    protected StatusMessageService statusMessageService;

    @ModelAttribute("title")
    public String title() {
        return "Block Layout";
    }

    @RequestMapping(method = RequestMethod.GET)
    public Callable<String> index(final Model model) {
        return () -> {
            return "admin/block/layout";
        };
    }

    @RequestMapping(method = RequestMethod.POST)
    public Callable<String> save(@Valid @ModelAttribute("form") final FormElement<BlockConfig> form,
                                 BindingResult result, RedirectAttributes redirectAttributes) {
        return () -> {
            if (result.hasErrors()) {
                return "admin/block/layout";
            }
            form.submit(new LinkedHashMap<>());
            statusMessageService.addSuccessStatusMessage(redirectAttributes,
                    translate("Your changes have been applied successfully"));
            return "redirect:/admin/structure/block_layout";
        };
    }

    @Override
    public String getFormId() {
        return "block_layout_form";
    }

    @Override
    public void build(FormElement element) {
        final Map<String, String> regions = new LinkedHashMap<>();
        regions.putAll(themeManagementService.getRegionsForDefaultTheme());
        regions.put("", translate("Disabled Blocks"));
        final List<BlockConfig> blocks = blockManagementService.getBlockConfigForDefaultTheme();

        final TableElement tableElement = new TableElement();
        tableElement.addHeaderRow(
                new TableElement.Header(translate("Block")),
                new TableElement.Header(translate("Category")),
                new TableElement.Header(translate("Region")),
                new TableElement.Header(translate("Weight")),
                new TableElement.Header(translate("Operations"))
        );

        final AtomicInteger delta = new AtomicInteger(0);
        regions.forEach((k, v) -> {
            // Add Region Header
            final TableElement.Header regionHeader = new TableElement.Header();
            regionHeader.setContent(translate(v));
            regionHeader.addAttribute("colspan", "4");
            regionHeader.addAttribute("data-region", k);

            final AnchorElement placeBlockButton = new AnchorElement(translate("Place block"),
                    "/admin/structure/block_layout/select_block/" + k, new PlainTextElement(translate("Place Block")));
            placeBlockButton.addClass("button");

            final TableElement.Header placeBlockColumn = new TableElement.Header();
            placeBlockColumn.addContent(placeBlockButton);

            tableElement.addRow(regionHeader, placeBlockColumn);
            final List<BlockConfig> blocksForRegion = blocks.stream().filter(b -> k.equals(b.getRegion())
                    || (StringUtils.isBlank(k) && StringUtils.isBlank(b.getRegion()))).sorted(
                    (o1, o2) -> {
                        if (o1 == null) {
                            return -1;
                        } else if (o2 == null) {
                            return 1;
                        }
                        return Integer.compare(o1.getWeight(), o2.getWeight());
                    }).collect(Collectors.toList());
            if (blocksForRegion.isEmpty()) {
                TableElement.Column empty = new TableElement.Column();
                empty.addAttribute("colspan", "5");
                empty.addContent(new TextFormatElement(TextFormatElement.Type.I,
                        translate("No blocks in this region")));
                tableElement.addRow(empty);
            }
            blocksForRegion.forEach(b -> {
                Optional<Block> blockInfo = blockManagementService.getBlockInfoByBlockName(b.getName());
                if (!blockInfo.isPresent()) {
                    return;
                }

                int d = delta.getAndIncrement();

                // Generate columns for each one
                // Block Id
                HiddenElement blockId = new HiddenElement("blockId", String.valueOf(b.getId()));
                blockId.setDelta(d);

                // Region Selector
                SingleSelectListElement regionSelect = new SingleSelectListElement();
                regionSelect.setName("region");
                regionSelect.setDelta(d);
                regionSelect.addOption(translate("Disable"), "");
                regions.forEach((k1, v1) -> {
                    regionSelect.addOption(v1, k1);
                });
                regionSelect.setValue(b.getRegion());

                // Block Weight
                TextElement weightField = new TextElement("blockWeight", String.valueOf(b.getWeight()));
                weightField.setDelta(d);
                weightField.setSize(5);
                weightField.addValidator(new NumberValidator());

                // Columns
                TableElement.Column blockName = new TableElement.Column(
                        new SpanElement(blockInfo.get().label()), blockId);
                TableElement.Column category = new TableElement.Column(new SpanElement(blockInfo.get().category()));
                TableElement.Column region = new TableElement.Column(regionSelect);
                TableElement.Column weight = new TableElement.Column(weightField);

                AnchorElement settings = new AnchorElement();
                settings.setHref("/admin/structure/block_layout/" + b.getId() + "/settings");
                settings.setTitle(translate("Configure block settings"));
                settings.setContent(translate("Settings"));
                settings.addClass("button");

                TableElement.Column ops = new TableElement.Column(settings);

                tableElement.addRow(blockName, category, region, weight, ops);

            });
        });

        element.addContent(tableElement);

        DivElement formActions = new DivElement();
        formActions.addClass("form-actions");
        formActions.addContent(new InputButtonElement(InputButtonElement.InputButtonType.SUBMIT,
                translate("Save Layout")));

        element.addContent(formActions);

        // Submit handler
        element.addSubmitHandler((form, variables) -> {
            // Update blocks
            List<String> blockIds = (List<String>) form.getValues("blockId");
            List<String> regionValues = (List<String>) form.getValues("region");
            List<String> weights = (List<String>)form.getValues("blockWeight");

            for (int i = 0; i < blockIds.size(); i++) {
                // Both aligned based on delta
                String blockId = blockIds.get(i);
                String region = regionValues.get(i);
                String weight = weights.get(i);

                BlockConfig block = blocks.stream().filter(blockConfig -> blockConfig.getId().equals(blockId))
                        .findFirst().orElseGet(null);
                if (block != null) {
                    block.setRegion(StringUtils.isBlank(region) ? null : region);
                    if (StringUtils.isNotBlank(weight)) {
                        block.setWeight(Integer.parseInt(weight));
                    }
                }
            }
            blockManagementService.updateBlocks(blocks);
        });
    }

    @Override
    public void validate(FormElement form, Errors errors) {
        form.validate(errors);
    }
}
