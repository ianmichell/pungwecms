package com.pungwe.cms.jpa.menu.repository;

import com.pungwe.cms.core.menu.MenuInfo;
import com.pungwe.cms.jpa.menu.MenuInfoImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by ian on 17/03/2016.
 */
@Repository
public interface MenuInfoRepository extends JpaRepository<MenuInfoImpl, String> {

	@Cacheable(value="menus", key = "{ #root.methodName + '_' + #a0 + '_' + #a1 }")
	Optional<MenuInfoImpl> findOneByIdAndLanguage(String id, String language);

	@Cacheable(value="menus", key = "{ #root.methodName + ' ' + #a0 }")
	List<MenuInfo> findAllByLanguage(String language);

}
