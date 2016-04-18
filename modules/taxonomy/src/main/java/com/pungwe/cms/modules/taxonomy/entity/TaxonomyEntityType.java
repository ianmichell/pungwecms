/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * )with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * Created by ian on 30/03/2016.
 */
package com.pungwe.cms.modules.taxonomy.entity;

import com.pungwe.cms.core.annotations.stereotypes.EntityType;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.entity.EntityTypeDefinition;
import com.pungwe.cms.core.entity.FieldConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EntityType(value = "taxonomy_entity_type", label = "Taxonomy", description = "Create and manage taxonomy definitions")
public class TaxonomyEntityType implements EntityTypeDefinition {

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
