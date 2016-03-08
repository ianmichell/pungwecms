package com.pungwe.cms.modules.dependency;

import com.pungwe.cms.core.annotations.stereotypes.Module;
import com.pungwe.cms.core.annotations.system.ModuleDependency;
import com.pungwe.cms.core.module.services.ModuleManagementService;
import com.pungwe.cms.modules.test.TestComponent;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ian on 21/01/2016.
 */
@Module(name = "module_with_dependency",
		label = "Module With Dependency",
		description = "Module with a dependency, so one can test injection",
		dependencies = @ModuleDependency("test_module"))
public class ModuleWithDependency {

	@Autowired
	ModuleManagementService managementService;

//	// Force non-required
	@Autowired(required = false)
	TestComponent component;

	public boolean isCommponentInjected() {
		return component != null;
	}
}
