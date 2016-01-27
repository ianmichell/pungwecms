package com.pungwe.cms.jpa.module.repository;

import com.pungwe.cms.jpa.module.ModuleConfigImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Created by ian on 22/01/2016.
 */
@Repository
public interface ModuleConfigRepository extends JpaRepository<ModuleConfigImpl, String> {

	List<ModuleConfigImpl> findAllByEnabled(boolean enabled);
	List<ModuleConfigImpl> findAllByNameIn(Collection<String> name);
	Long deleteByNameIn(Collection<String> modules);
}
