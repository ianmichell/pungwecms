package com.pungwe.cms.modules.node.entity;

import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.entity.EntityType;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.form.FormState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ian on 09/01/2016.
 */
public class NodeEntityType implements EntityType {

    @Override
    public String getType() {
        return "node";
    }

    @Override
    public List<FieldConfig> getBaseFields() {

        List<FieldConfig> fields = new ArrayList<>();

        FieldConfig title = new FieldConfig();
        title.setName("title");
        title.setWeight(-100);
        title.setLabel("Title");
        title.setRequired(true);
        title.setCardinality(1);
        title.setWidget("string_textfield");
        title.setFormatter("string");
        title.setFieldType("string");
        title.addSetting("size", 200);

        FieldConfig body = new FieldConfig();
        body.setName("body");
        body.setWeight(0);
        body.setLabel("Body");
        body.setCardinality(1);
        body.setWidget("textarea_and_summary");
        body.setFormatter("text_default");
        body.setFieldType("text");
        body.addSetting("rows", 10);

        fields.add(title);
        fields.add(body);

        return fields;
    }

    @Override
    public void buildSettingsForm(List<RenderedElement> elements, FormState state) {
    }
}
