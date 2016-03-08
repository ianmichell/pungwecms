package com.pungwe.cms.modules.node;

import com.pungwe.cms.core.annotations.util.Hook;
import com.pungwe.cms.core.annotations.stereotypes.Module;
import com.pungwe.cms.core.annotations.system.ModuleDependency;

/**
 * Created by ian on 13/12/2015.
 */
@Module(
		name = "node",
		description = "A module for content editing",
		dependencies = { @ModuleDependency("text") }
)
public class NodeModule {

	@Hook("install")
	public void install() {
		System.out.println("Installed node");
	}
}
