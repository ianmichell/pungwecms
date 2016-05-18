package com.pungwe.cms.modules.autocomplete;

import com.pungwe.cms.core.annotations.stereotypes.Module;
import com.pungwe.cms.core.annotations.system.ModuleDependency;

/**
 * Created by ian on 08/05/2016.
 */
@Module(
        name = "autocomplete",
        label = "Autocomplete Widgets",
        description = "Auto complete widgets and javascript",
        dependencies = {
                @ModuleDependency("jquery")
        }
)
public class AutoCompleteModule {
}
