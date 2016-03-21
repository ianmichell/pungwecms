package com.pungwe.cms.core.menu.services;

import com.pungwe.cms.core.menu.MenuInfo;

import java.util.List;
import java.util.Optional;

/**
 * Created by ian on 17/03/2016.
 */
public interface MenuInfoService {

	MenuInfo newInstance(String id, String title, String description, String language);

	List<MenuInfo> findAllByLanguage(String language);

	Optional<MenuInfo> getMenu(String id, String language);

	<T extends MenuInfo> List<MenuInfo> save(T... menuItem);
}
