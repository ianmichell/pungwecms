package com.pungwe.cms.jpa.menu.services;

import com.pungwe.cms.core.menu.MenuInfo;
import com.pungwe.cms.core.menu.services.MenuInfoService;
import com.pungwe.cms.jpa.menu.MenuInfoImpl;
import com.pungwe.cms.jpa.menu.repository.MenuInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by ian on 17/03/2016.
 */
@Service
public class JPAMenuInfoService implements MenuInfoService {

	@Autowired
	protected MenuInfoRepository menuInfoRepository;

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
		return menuInfoRepository.findAllByLanguage(language).stream().collect(Collectors.toList());
	}

	@Override
	public Optional<MenuInfo> getMenu(String id, String language) {
		return menuInfoRepository.findOneByIdAndLanguage(id, language).map(menuInfo -> menuInfo);
	}

	@Override
	@Transactional
	@CacheEvict(cacheNames = "menus", allEntries = true)
	public <T extends MenuInfo> List<MenuInfo> save(T... menuItem) {
		List<MenuInfo> results = new LinkedList<>();
		results.addAll(menuInfoRepository.save(Arrays.asList(menuItem).stream().map(t -> (MenuInfoImpl)t).collect(Collectors.toList())));
		return results;
	}
}
