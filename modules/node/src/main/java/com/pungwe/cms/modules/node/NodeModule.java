package com.pungwe.cms.modules.node;

import com.pungwe.cms.core.annotations.Hook;
import com.pungwe.cms.core.annotations.Module;
import com.pungwe.cms.core.entity.EntityDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ian on 13/12/2015.
 */
@Module(value = "node", includePackages = {"com.pungwe.cms.modules.node"})
public class NodeModule {

    @Hook("entity_info")
    public List<EntityDefinition> createContentTypes() {
        return new ArrayList<>();
    }
}
