package com.pungwe.cms.core.menu.impl;

import com.pungwe.cms.core.annotations.ui.MenuItem;
import com.pungwe.cms.core.menu.MenuConfig;
import com.pungwe.cms.core.menu.services.MenuConfigService;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.lang.annotation.Documented;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class MenuConfigServiceImpl implements MenuConfigService<MenuConfigImpl> {

	protected List<MenuConfigImpl> menuItems = new LinkedList<>();

	@Override
	public MenuConfigImpl newInstance() {
		return new MenuConfigImpl();
	}

	@Override
	public MenuConfigImpl newInstance(String menu, String parent, String name, String title, String description, boolean external, String target, String url, int weight) {
		MenuConfigImpl config = newInstance();
		config.setId(new StringBuilder().append(menu).append(".").append(parent).append(".").append(name).toString());
		config.setMenu(menu);
		config.setParent(parent);
		config.setName(name);
		config.setTitle(title);
		config.setDescription(description);
		config.setExternal(external);
		config.setTarget(target);
		config.setUrl(url);
		config.setWeight(weight);

		StringBuilder menuPath = new StringBuilder();
		if (!StringUtils.isEmpty(parent)) {
			menuPath.append(parent).append(".");
		}
		menuPath.append(name);
		config.setPath(menuPath.toString());
		return config;
	}

	@Override
	public void saveMenuItem(MenuConfigImpl... menuItems) {
		assert menuItems != null && menuItems.length > 0;
		saveMenuItem(Arrays.asList(menuItems));
	}

	@Override
	public void saveMenuItem(Collection<MenuConfig> menuItems) {
		assert menuItems != null && menuItems.iterator().hasNext();
		// Stream and cast, exception will be thrown if there is anything dodgy!
		menuItems.addAll(menuItems);
	}

	@Override
	@Cacheable("menu.menuTreeForUrl")
	public List<MenuConfigImpl> menuTreeForUrl(String menu, String url) {

		List<MenuConfigImpl> tree = new LinkedList<>();

		final Optional<MenuConfigImpl> leaf = menuItems.stream().filter(menuConfig -> menuConfig.getUrl().equals(url) && menuConfig.getMenu().equals(menu)).findFirst();
		if (leaf.isPresent()) {
			tree.add(leaf.get());
		} else {
			return tree;
		}

		Optional<MenuConfigImpl> parent = menuItems.stream().filter(menuConfig -> menuConfig.getMenu().equals(menu) && menuConfig.getMenu().equals(leaf.get().getParent())).findFirst();

		while (parent.isPresent()) {
			tree.add(parent.get());
			final String parentPath = parent.get().getParent();
			parent = menuItems.stream().filter(menuConfig -> menuConfig.getMenu().equals(menu) && menuConfig.getPath().equals(parentPath)).findFirst();
		}
		Collections.reverse(tree);
		return tree;
	}

	@Cacheable("menu.getTopLevelMenuItems")
	@Override
	public List<MenuConfigImpl> getTopLevelMenuItems(String menu) {
		return menuItems.stream().filter(menuConfig -> StringUtils.isEmpty(menuConfig.getParent())).collect(Collectors.toList());
	}

	@Cacheable("menu.getMenuItems")
	@Override
	public List<MenuConfigImpl> getMenuItems(String menu, String parent) {
		return menuItems.stream().filter(menuConfig -> menuConfig.getMenu().equals(menu) && menuConfig.getParent().equals(parent)).collect(Collectors.toList());
	}
}
