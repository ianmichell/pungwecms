package com.pungwe.cms.core.menu.impl;

import com.pungwe.cms.core.menu.MenuInfo;
import com.pungwe.cms.core.menu.services.MenuInfoService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by ian on 17/03/2016.
 */
@Service
public class MenuInfoServiceImpl implements MenuInfoService {

	private List<MenuInfo> menus = new LinkedList<>();

	@Override
	public MenuInfo newInstance(String id, String title, String description, String language) {
		MenuInfoImpl m = new MenuInfoImpl();
		m.setId(id);
		m.setTitle(title);
		m.setDescription(description);
		m.setLanguage(language);
		return m;
	}

	@Override
	public List<MenuInfo> findAllByLanguage(String language) {
		return menus.stream().filter(menuInfo -> menuInfo.getLanguage().equals(language)).collect(Collectors.toList());
	}

	@Override
	public Optional<MenuInfo> getMenu(String id, String language) {
		return menus.stream().filter(menuInfo -> menuInfo.getId().equals(id) && menuInfo.getLanguage().equals(language)).findFirst();
	}

	@Override
	public List<MenuInfo> save(MenuInfo... menuItem) {
		menus.addAll(Arrays.asList(menuItem));
		return Arrays.asList(menuItem);
	}
}
