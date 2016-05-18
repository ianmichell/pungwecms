package com.pungwe.cms.modules.relations.field.formatters;

import com.pungwe.cms.core.annotations.stereotypes.FieldFormatter;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.entity.EntityInstanceId;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.field.FieldFormatterDefinition;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ian on 09/05/2016.
 */
@FieldFormatter(
        label = "Entity Relation",
        supports = "entity_relation_field",
        value = "entity_relation_formatter"
)
public class EntityRelationFormatter implements FieldFormatterDefinition<EntityInstanceId> {

    @Override
    public Map<java.lang.String, Object> getDefaultSettings() {
        return new LinkedHashMap<>();
    }

    @Override
    public void format(List<RenderedElement> elements, List<EntityInstanceId> value, FieldConfig config) {

    }

    @Override
    public void buildWidgetSettingsForm(List<RenderedElement> elements, Map<java.lang.String, Object> settings) {

    }
}
