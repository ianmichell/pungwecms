package com.pungwe.cms.jpa.menu.services;

import com.pungwe.cms.core.annotations.ui.MenuItem;
import com.pungwe.cms.core.menu.MenuConfig;
import com.pungwe.cms.core.menu.services.MenuConfigService;
import com.pungwe.cms.jpa.menu.MenuConfigImpl;
import com.pungwe.cms.jpa.menu.repository.MenuConfigRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.lang.annotation.Documented;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ian on 09/03/2016.
 */
@Service
public class JPAMenuConfigService implements MenuConfigService<MenuConfigImpl> {

	@Autowired
	protected MenuConfigRepository menuConfigRepository;

	@Override
	public MenuConfigImpl newInstance() {
		return new MenuConfigImpl();
	}

	@Override
	public MenuConfigImpl newInstance(String menu, String parent, String name, String title, String description, boolean external, String target, String url, int weight, boolean pattern, boolean task) {
		MenuConfigImpl config = newInstance();
        StringBuilder id = new StringBuilder().append(menu).append(".");
        if (StringUtils.isNotBlank(parent)) {
            id.append(parent).append(".");
        }
        id.append(name);
		config.setId(id.toString());
		config.setMenu(menu);
		config.setParent(parent);
		config.setName(name);
		config.setTitle(title);
		config.setDescription(description);
		config.setExternal(external);
		config.setTarget(target);
		config.setUrl(url);
		config.setWeight(weight);
        config.setPattern(pattern);
        config.setTask(task);

		StringBuilder menuPath = new StringBuilder();
		if (StringUtils.isNotEmpty(parent)) {
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
		menuConfigRepository.save(menuItems.stream().map(menuConfig -> (MenuConfigImpl) menuConfig).collect(Collectors.toList()));
	}

	@Override
	@Cacheable("menu.menuTreeForUrl")
	public List<MenuConfigImpl> menuTreeForUrl(String menu, String url) {

		List<MenuConfigImpl> tree = new LinkedList<>();

		Optional<MenuConfigImpl> leaf = menuConfigRepository.findOneByMenuAndUrl(menu, url);
		if (leaf.isPresent()) {
			tree.add(leaf.get());
		} else {
			return tree;
		}

		Optional<MenuConfigImpl> parent = menuConfigRepository.findOneByMenuAndPath(menu, leaf.get().getParent());

		while (parent.isPresent()) {
			tree.add(parent.get());
			parent = menuConfigRepository.findOneByMenuAndPath(menu, parent.get().getParent());
		}
		Collections.reverse(tree);
		return tree;
	}

	@Cacheable("menu.getTopLevelMenuItems")
	@Override
	public List<MenuConfigImpl> getTopLevelMenuItems(String menu) {
		return menuConfigRepository.findAllTopLevelItemsByMenu(menu);
	}

	@Cacheable("menu.getMenuItems")
	@Override
	public List<MenuConfigImpl> getMenuItems(String menu, String parent) {
		return menuConfigRepository.findAllByMenuAndParent(menu, parent);
	}

	@Override
	public List<MenuConfig> getMenuItemsByParent(String menu, String path, boolean task) {
		return menuConfigRepository.findAllByMenuAndParentAndTask(menu, path, task);
	}
}
