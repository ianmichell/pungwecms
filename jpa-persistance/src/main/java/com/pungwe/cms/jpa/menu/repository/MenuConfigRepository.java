package com.pungwe.cms.jpa.menu.repository;

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

	@Cacheable("menu.findOneByMenuAndUrl")
	Optional<MenuConfigImpl> findOneByMenuAndUrl(String menu, String url);

	@Cacheable("menu.findOneByMenuAndPath")
	Optional<MenuConfigImpl> findOneByMenuAndPath(String menu, String path);

	@Query("FROM MenuConfigImpl WHERE parent is null")
	@Cacheable("menu.findAllTopLevelItemsByMenu")
	List<MenuConfigImpl> findAllTopLevelItemsByMenu(String menu);

	@Cacheable("menu.findAllByMenuAndParent")
	List<MenuConfigImpl> findAllByMenuAndParent(String menu, String parent);
}
