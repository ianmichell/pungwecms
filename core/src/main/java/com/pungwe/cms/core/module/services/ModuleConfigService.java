package com.pungwe.cms.core.module.services;


import com.pungwe.cms.core.module.ModuleConfig;

import java.net.URL;
import java.util.Collection;
import java.util.Set;

/**
 * Created by ian on 21/01/2016.
 */
public interface ModuleConfigService<M extends ModuleConfig> {

	/**
	 * Registers a module in the module registry
	 *
	 * @param entryPoint The module class
	 * @param moduleLocation The jar file the module is contained in
	 */
	M registerModule(Class<?> entryPoint, URL moduleLocation);

	void removeModules(String... modules);

	void removeModules(Collection<String> modules);

	void setModuleEnabled(String moduleName, boolean enabled);

	void setInstalled(String module, boolean installed);

	boolean isEnabled(String module);

	Set<M> listAllModules();

	Set<M> listEnabledModules();

	M getModuleConfig(String module);

}
