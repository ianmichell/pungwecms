package com.pungwe.cms.modules.relations;

import com.pungwe.cms.core.annotations.stereotypes.Module;
import com.pungwe.cms.modules.relations.field.formatters.EntityRelationFormatter;
import com.pungwe.cms.modules.relations.field.types.EntityRelationField;
import com.pungwe.cms.modules.relations.field.widgets.EntityRelationWidget;
import org.springframework.context.annotation.Bean;

/**
 * Created by ian on 09/05/2016.
 */
@Module(
        name = "entity_relations",
        label = "Entity Relations",
        description = "Entity Relationship module. Allows the linking of different entities"
)
public class EntityRelationsModule {

    @Bean(name = "entity_relation_formatter")
    public EntityRelationFormatter entityRelationFormatter() {
        return new EntityRelationFormatter();
    }

    @Bean(name = "entity_relation_widget")
    public EntityRelationWidget entityRelationWidget() {
        return new EntityRelationWidget();
    }

    @Bean(name = "entity_relation_field")
    public EntityRelationField entityRelationField() {
        return new EntityRelationField();
    }
}
