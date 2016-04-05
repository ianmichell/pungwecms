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
package com.pungwe.cms.modules.taxonomy;

import com.pungwe.cms.core.annotations.stereotypes.Module;
import com.pungwe.cms.core.annotations.system.ModuleDependency;
import com.pungwe.cms.core.annotations.util.Hook;
import com.pungwe.cms.core.entity.EntityDefinition;
import com.pungwe.cms.core.entity.services.EntityDefinitionService;
import com.pungwe.cms.modules.taxonomy.entity.TaxonomyEntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import java.util.Date;

@Module(
        name = "taxonomy",
        description = "Taxonomy module for tagging content and creating categories",
        label = "Taxonomy",
        dependencies = {@ModuleDependency(
                "text"
        )}
)
@ComponentScan(value = "com.pungwe.cms.modules.taxonomy")
public class TaxonomyModule {

    @Autowired
    private EntityDefinitionService entityDefinitionService;

    @Autowired
    private TaxonomyEntityType taxonomyEntityType;

    /**
     * Installs the taxonomy module into the CMS.
     */
    @Hook("install")
    public void install() {
        EntityDefinition tags = entityDefinitionService.newInstance(taxonomyEntityType, "tags");
        tags.setTitle("Tags");
        tags.setDescription("A vocabulary of keywords / tags used in content");
        tags.setDateCreated(new Date());
        // Create the entity definition
        entityDefinitionService.create(tags);
    }

    public EntityDefinitionService getEntityDefinitionService() {
        return entityDefinitionService;
    }

    public void setEntityDefinitionService(EntityDefinitionService entityDefinitionService) {
        this.entityDefinitionService = entityDefinitionService;
    }

    public TaxonomyEntityType getTaxonomyEntityType() {
        return taxonomyEntityType;
    }

    public void setTaxonomyEntityType(TaxonomyEntityType taxonomyEntityType) {
        this.taxonomyEntityType = taxonomyEntityType;
    }
}
