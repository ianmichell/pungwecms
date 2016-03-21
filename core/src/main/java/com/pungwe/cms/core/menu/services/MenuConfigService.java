package com.pungwe.cms.core.menu.services;

import com.pungwe.cms.core.annotations.ui.MenuItem;
import com.pungwe.cms.core.menu.MenuConfig;

import java.util.Collection;
import java.util.List;

/**
 * Created by ian on 08/03/2016.
 */
public interface MenuConfigService<T extends MenuConfig> {

	T newInstance();

	T newInstance(String menu, String parent, String name, String title, String description, boolean external, String target, String url, int weight, boolean pattern);

	void saveMenuItem(T... menuItems);

	void saveMenuItem(Collection<MenuConfig> menuItems);

	/**
	 * Fetches a list of menu items for the given menu and path.
	 * @param menu the name of the menu being retrieved
	 * @param url the URL to be found
	 * @return a list of menu items from parent to child based on the URL
	 */
	List<T> menuTreeForUrl(String menu, String url);

	List<T> getTopLevelMenuItems(String menu);

	List<T> getMenuItems(String menu, String parent);
}
