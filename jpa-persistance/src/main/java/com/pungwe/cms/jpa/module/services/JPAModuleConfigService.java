package com.pungwe.cms.jpa.module.services;

import com.pungwe.cms.core.annotations.Module;
import com.pungwe.cms.core.module.services.ModuleConfigService;
import com.pungwe.cms.jpa.module.ModuleConfigImpl;
import com.pungwe.cms.jpa.module.repository.ModuleConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by ian on 22/01/2016.
 */
@Service
public class JPAModuleConfigService implements ModuleConfigService<ModuleConfigImpl> {

	@Autowired
	ModuleConfigRepository moduleConfigRepository;

	@Override
	@Transactional
	public void registerModule(Class<?> entryPoint, URL moduleLocation) {

		// Create module config holder
		ModuleConfigImpl config = new ModuleConfigImpl();

		Module moduleInfo = entryPoint.getAnnotation(Module.class);

		/// Set the name of the module
		config.setName(StringUtils.isEmpty(moduleInfo.name()) ? entryPoint.getSimpleName().toLowerCase() : moduleInfo.name());
		config.setEntryPoint(entryPoint.getName()); // class and package
		config.setEnabled(false);
		config.setModuleLocation(moduleLocation.getFile());

		moduleConfigRepository.save(config);
	}

	@Override
	@Transactional
	public void removeModules(String... modules) {
		removeModules(Arrays.asList(modules));
	}

	@Override
	@Transactional
	public void removeModules(Collection<String> modules) {
		assert !modules.isEmpty();
		moduleConfigRepository.deleteByNameIn(modules);
	}

	@Override
	@Transactional
	public void setModuleEnabled(String moduleName, boolean enabled) {
		ModuleConfigImpl config = getModuleConfig(moduleName);
		config.setEnabled(enabled);
		moduleConfigRepository.save(config);
	}

	@Override
	@Transactional
	public boolean isEnabled(String module) {
		ModuleConfigImpl config = moduleConfigRepository.getOne(module);
		return config != null && config.isEnabled();
	}

	@Override
	@Transactional
	public Set<ModuleConfigImpl> listAllModules() {
		return moduleConfigRepository.findAll().stream().collect(Collectors.toSet());
	}

	@Override
	@Transactional
	public Set<ModuleConfigImpl> listEnabledModules() {
		return moduleConfigRepository.findAllByEnabled(true).stream().collect(Collectors.toSet());
	}

	@Override
	@Transactional
	public ModuleConfigImpl getModuleConfig(String module) {
		return moduleConfigRepository.findOne(module);
	}
}