package com.pungwe.cms.core.entity.controller;

import com.pungwe.cms.core.element.basic.TableElement;
import com.pungwe.cms.core.entity.EntityDefinition;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.entity.FieldGroupConfig;
import com.pungwe.cms.core.entity.services.EntityDefinitionService;
import com.pungwe.cms.core.form.controller.AbstractFormController;
import com.pungwe.cms.core.form.element.FormElement;
import com.pungwe.cms.core.form.element.SingleSelectListElement;
import com.pungwe.cms.core.form.element.TextElement;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.TreeSet;

import static com.pungwe.cms.core.utils.Utils.translate;

/**
 * Created by ian on 10/05/2016.
 */
public abstract class AbstractEntityTypeFormDisplayController extends AbstractFormController<EntityDefinition<?>> {

    @Autowired
    protected EntityDefinitionService entityDefinitionService;

    @Override
    public void build(FormElement<EntityDefinition<?>> element) {
        TableElement tableElement = new TableElement();
        tableElement.addClass("sortable");
        tableElement.addHeaderRow(
                new TableElement.Header(translate("Field")),
                new TableElement.Header(translate("Visibility")),
                new TableElement.Header(translate("Weight")),
                new TableElement.Header(translate("Group")),
                new TableElement.Header(translate("Widget")),
                new TableElement.Header(translate("Operations"))
        );

        EntityDefinition<?> entityDefinition = entityDefinitionService.get(entityType(), bundle());
        element.setTargetObject(entityDefinition);

        final Set<FieldGroupConfig> fieldGroups = entityDefinition.getFieldGroupsWithoutParent();
        for (FieldGroupConfig fieldGroup : fieldGroups) {
            buildFieldGroupRow(fieldGroup, entityDefinition, tableElement, 0);
        }

        // List the entity fields and build up the relevant columns
        for (FieldConfig field : entityDefinition.getFieldsWithoutParent()) {
            buildFieldRow(field, tableElement, 0);
        }

        element.addContent(tableElement);
    }

    protected void buildFieldRow(final FieldConfig field, final TableElement tableElement, final int indent) {
        TableElement.Row row = new TableElement.Row();
        row.addAttribute("data-field-name", field.getName());

        // Field Label
        TableElement.Column fieldColumn = new TableElement.Column();
        fieldColumn.addContent(translate(field.getLabel()));
        fieldColumn.addAttribute("data-indent", String.valueOf(indent));
        fieldColumn.addClass("no-wrap");
        row.addColumn(fieldColumn);

        // Visibility
        TableElement.Column visibilityColumn = new TableElement.Column();
        SingleSelectListElement visibility = new SingleSelectListElement();
        visibility.setHtmlId(field.getName() + "_visibility");
        visibility.addOption(translate("Visible"), "visible");
        visibility.addOption(translate("Hidden"), "hidden");
        visibility.setName(field.getName() + "_visibility");
        String visibilityValue = field.getSettings() != null ? (String)field.getSettings().get("form_visibility")
                : "visible";
        visibility.setDefaultValue(StringUtils.isNotBlank(visibilityValue) ? visibilityValue : "visible");
        visibilityColumn.addContent(visibility);
        row.addColumn(visibilityColumn);

        // Weight
        TextElement weight = new TextElement();
        weight.setHtmlId(field.getName() + "_weight");
        weight.setName(field.getName() + "_weight");
        weight.setDefaultValue(String.format("%d", field.getWeight()));
        weight.setSize(5);
        row.addColumn(new TableElement.Column(weight));

        // Group
        row.addColumn(new TableElement.Column());

        // Widget
        row.addColumn(new TableElement.Column());

        // Operations
        row.addColumn(new TableElement.Column());

        // Add row to table
        tableElement.addRow(row);
    }

    protected void buildFieldGroupRow(final FieldGroupConfig fieldGroup, final EntityDefinition<?> entityDefinition,
                                      final TableElement tableElement, final int indent) {
        TableElement.Row row = new TableElement.Row();
        row.addAttribute("data-field-group-name", fieldGroup.getName());

        // Field Label
        TableElement.Header fieldColumn = new TableElement.Header();
        fieldColumn.addContent(translate(fieldGroup.getLabel()));
        fieldColumn.addAttribute("data-indent", String.valueOf(indent));
        fieldColumn.addClass("no-wrap");
        row.addColumn(fieldColumn);

        row.addColumn(new TableElement.Column());

        // Weight
        TextElement weight = new TextElement();
        weight.setHtmlId(fieldGroup.getName() + "_weight");
        weight.setName(fieldGroup.getName() + "_weight");
        weight.setDefaultValue(String.format("%i", fieldGroup.getWeight()));
        weight.setSize(5);
        row.addColumn(new TableElement.Column(weight));

        // Group
        row.addColumn(new TableElement.Column());

        // Widget
        row.addColumn(new TableElement.Column());

        // Operations
        row.addColumn(new TableElement.Column());

        // Add row to table
        tableElement.addRow(row);

        Set<Object> children = new TreeSet<>((o1, o2) -> {
            // No need for null checks
            int weight1 = 0, weight2 = 0;

            // O1
            if (FieldGroupConfig.class.isAssignableFrom(o1.getClass())) {
                weight1 = ((FieldGroupConfig)o1).getWeight();
            }
            if (FieldConfig.class.isAssignableFrom(o1.getClass())) {
                weight1 = ((FieldConfig)o1).getWeight();
            }

            // O2
            if (FieldGroupConfig.class.isAssignableFrom(o2.getClass())) {
                weight2 = ((FieldGroupConfig)o2).getWeight();
            }
            if (FieldConfig.class.isAssignableFrom(o2.getClass())) {
                weight2 = ((FieldConfig)o2).getWeight();
            }

            return Integer.compare(weight1, weight2);
        });

        children.addAll(entityDefinition.getFieldGroupsByParent(fieldGroup.getName()));
        children.addAll(entityDefinition.getFieldsByGroup(fieldGroup.getName()));

        for (Object child : children) {

        }
    }

    protected abstract String entityType();

    protected abstract String bundle();
}
