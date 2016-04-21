package com.pungwe.cms.core.block.controller;

import com.pungwe.cms.core.annotations.stereotypes.Block;
import com.pungwe.cms.core.annotations.ui.MenuItem;
import com.pungwe.cms.core.block.BlockConfig;
import com.pungwe.cms.core.block.services.BlockManagementService;
import com.pungwe.cms.core.element.basic.SpanElement;
import com.pungwe.cms.core.element.basic.TableElement;
import com.pungwe.cms.core.element.basic.TextFormatElement;
import com.pungwe.cms.core.form.controller.AbstractFormController;
import com.pungwe.cms.core.form.element.FormElement;
import com.pungwe.cms.core.form.element.HiddenElement;
import com.pungwe.cms.core.form.element.SingleSelectListElement;
import com.pungwe.cms.core.form.element.TextElement;
import com.pungwe.cms.core.theme.services.ThemeManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
@Controller
@RequestMapping("/admin/structure/block_layout")
@Secured({"administer_blocks"})
public class BlockLayoutController extends AbstractFormController<BlockConfig> {

    @Autowired
    protected ThemeManagementService themeManagementService;

    @Autowired
    protected BlockManagementService blockManagementService;

    @ModelAttribute("title")
    public String title() {
        return "Block Layout";
    }

    @MenuItem(
            menu = "system",
            name = "block-layout",
            parent = "admin.structure",
            title = "Block Layout",
            description = "Manage your block layout"
    )
    @RequestMapping(method = RequestMethod.GET)
    public Callable<String> index(final Model model) {
        return () -> {
            return "admin/block/layout";
        };
    }

    @Override
    public String getFormId() {
        return "block_layout_form";
    }

    @Override
    public void build(FormElement<BlockConfig> element) {
        final Map<String, String> regions = themeManagementService.getRegionsForDefaultTheme();
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
            regionHeader.setContent(v);
            regionHeader.addAttribute("colspan", "5");
            regionHeader.addAttribute("data-region", k);
            tableElement.addRow(regionHeader);
            final List<BlockConfig> blocksForRegion = blocks.stream().filter(b -> k.equals(b.getRegion())).sorted(
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
                empty.addContent(new TextFormatElement(TextFormatElement.Type.I, translate("No blocks in this region")));
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
                regions.forEach((k1, v1) -> {
                    regionSelect.addOption(v1, k1);
                });
                regionSelect.setValue(b.getRegion());

                // Block Weight
                TextElement<Integer> weightField = new TextElement<>("blockWeight", b.getWeight());
                weightField.setDelta(d);
                weightField.setSize(5);

                // Columns
                TableElement.Column blockName = new TableElement.Column(new SpanElement(blockInfo.get().label()), blockId);
                TableElement.Column category = new TableElement.Column(new SpanElement(blockInfo.get().category()));
                TableElement.Column region = new TableElement.Column(regionSelect);
                TableElement.Column weight = new TableElement.Column(weightField);
                TableElement.Column ops = new TableElement.Column("Operations");

                tableElement.addRow(blockName, category, region, weight, ops);

            });
        });

        element.addContent(tableElement);
    }

    @Override
    public void validate(FormElement<BlockConfig> form, Errors errors) {
    }
}
