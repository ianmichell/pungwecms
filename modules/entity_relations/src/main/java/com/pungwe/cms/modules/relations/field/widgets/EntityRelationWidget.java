package com.pungwe.cms.modules.relations.field.widgets;

import com.pungwe.cms.core.annotations.stereotypes.FieldWidget;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.entity.EntityTypeInfo;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.field.FieldWidgetDefinition;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ian on 09/05/2016.
 */
@FieldWidget(
        value = "entity_relation_widget",
        supports = "entity_relation_field",
        label = "Entity Relation"
)
public class EntityRelationWidget implements FieldWidgetDefinition<EntityTypeInfo> {
    @Override
    public Map<String, Object> getDefaultSettings() {
        return new LinkedHashMap<>();
    }

    @Override
    public void buildWidgetForm(List<RenderedElement> elements, FieldConfig field, EntityTypeInfo value, int delta) {

    }

    @Override
    public void buildWidgetSettingsForm(List<RenderedElement> elements, Map<String, Object> settings) {

    }

    @Override
    public EntityTypeInfo extractValueFromForm(FieldConfig field, Map<String, Object> values, int delta) {
        return null;
    }
}
