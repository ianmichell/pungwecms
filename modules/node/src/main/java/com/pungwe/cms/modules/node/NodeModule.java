package com.pungwe.cms.modules.node;

import com.pungwe.cms.core.annotations.Module;

/**
 * Created by ian on 13/12/2015.
 */
@Module(
        name = "node",
        description = "A module for content editing",
        includePackages = {"com.pungwe.cms.modules.node"},
        dependencies = {"text"}
)
public class NodeModule {

}
