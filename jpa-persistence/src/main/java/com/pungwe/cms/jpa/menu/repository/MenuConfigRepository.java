package com.pungwe.cms.jpa.menu.repository;

import com.pungwe.cms.core.menu.MenuConfig;
import com.pungwe.cms.jpa.menu.MenuConfigImpl;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by ian on 09/03/2016.
 */
@Repository
public interface MenuConfigRepository extends JpaRepository<MenuConfigImpl, String> {

	@Cacheable(value = "menu_config", key = "#root.methodName + '_' + #a0 + '_' + #a1")
	Optional<MenuConfigImpl> findOneByMenuAndUrl(String menu, String url);

	@Cacheable(value = "menu_config", key = "#root.methodName + '_' + #a0 + '_' + #a1")
	Optional<MenuConfigImpl> findOneByMenuAndPath(String menu, String path);

	@Query("FROM MenuConfigImpl WHERE parent is null and menu = ?1")
	@Cacheable(value = "menu_config", key = "#root.methodName + '_' + #a0")
	List<MenuConfigImpl> findAllTopLevelItemsByMenu(String menu);

	@Cacheable(value = "menu_config", key = "#root.methodName + '_' + #a0 + '_' + #a1")
	List<MenuConfigImpl> findAllByMenuAndParent(String menu, String parent);

	@Cacheable(value = "menu_config", key = "#root.methodName + '_' + #a0 + '_' + #a1")
	List<MenuConfig> findAllByMenuAndParentAndTask(String menu, String path, boolean task);
}
