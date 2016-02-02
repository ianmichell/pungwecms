package com.pungwe.cms.modules.node;

import com.pungwe.cms.core.annotations.Hook;
import com.pungwe.cms.core.annotations.Module;
import com.pungwe.cms.core.annotations.ModuleDependency;

/**
 * Created by ian on 13/12/2015.
 */
@Module(
		name = "node",
		description = "A module for content editing",
		includePackages = {"com.pungwe.cms.modules.node"},
		dependencies = { @ModuleDependency("text") }
)
public class NodeModule {

	@Hook("install")
	public void install() {
		System.out.println("Installed node");
	}
}
