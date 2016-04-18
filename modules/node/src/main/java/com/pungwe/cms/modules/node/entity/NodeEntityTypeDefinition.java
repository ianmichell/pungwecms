package com.pungwe.cms.modules.node.entity;

import com.pungwe.cms.core.annotations.stereotypes.EntityType;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.entity.EntityTypeDefinition;
import com.pungwe.cms.core.entity.FieldConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ian on 09/01/2016.
 */
@EntityType(value = "node_entity_type", label = "Content Type", description = "Content Types")
public class NodeEntityTypeDefinition implements EntityTypeDefinition {

	@Override
	public List<FieldConfig> getBaseFields() {

		List<FieldConfig> fields = new ArrayList<>();

		FieldConfig title = new FieldConfig();
		title.setName("title");
		title.setWeight(-100);
		title.setLabel("Title");
		title.setRequired(true);
		title.setCardinality(1);
		title.setWidget("textfield_widget");
		title.setFormatter("string_formatter");
		title.setFieldType("string_field");
		title.addSetting("size", 200);

		FieldConfig body = new FieldConfig();
		body.setName("body");
		body.setWeight(0);
		body.setLabel("Body");
		body.setCardinality(1);
		body.setWidget("textarea_with_summary_widget");
		body.setFormatter("text_formatter");
		body.setFieldType("text_field");
		body.addSetting("rows", 10);

		fields.add(title);
		fields.add(body);

		return fields;
	}

	@Override
	public void buildSettingsForm(List<RenderedElement> elements, Map<String, Object> settings) {
	}
}
