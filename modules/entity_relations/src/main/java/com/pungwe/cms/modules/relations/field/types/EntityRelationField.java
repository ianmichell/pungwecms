package com.pungwe.cms.modules.relations.field.types;

import com.pungwe.cms.core.annotations.stereotypes.FieldType;
import com.pungwe.cms.modules.relations.field.formatters.EntityRelationFormatter;
import com.pungwe.cms.modules.relations.field.widgets.EntityRelationWidget;

/**
 * Created by ian on 09/05/2016.
 */
@FieldType(
        value="entity_relation_type",
        category = "System",
        label = "Entity Relation",
        defaultWidget = EntityRelationWidget.class,
        defaultFormatter = EntityRelationFormatter.class
)
public class EntityRelationField {
}
