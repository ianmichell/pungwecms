package com.pungwe.cms.core.menu.services;

import com.pungwe.cms.core.annotations.ui.MenuItem;
import com.pungwe.cms.core.annotations.ui.MenuItems;
import com.pungwe.cms.core.menu.MenuConfig;
import com.pungwe.cms.core.menu.MenuInfo;
import com.pungwe.cms.core.module.services.ModuleManagementService;
import com.pungwe.cms.core.system.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by ian on 08/03/2016.
 */
@Service
public class MenuManagementService {

	@Autowired
	MenuConfigService<? extends MenuConfig> menuConfigService;

	@Autowired
	MenuInfoService menuInfoService;

	@Autowired
	ModuleManagementService moduleManagementService;

	@EventListener
	public void buildSystemMenu(ContextRefreshedEvent event) {
		if (!event.getApplicationContext().getId().equalsIgnoreCase("module-application-context")) {
			return;
		}
		// Fetch the module context and parent context... We should not have an issue with casting this...
		ConfigurableApplicationContext moduleContext = (ConfigurableApplicationContext) moduleManagementService.getModuleContext();
		ConfigurableApplicationContext context = (ConfigurableApplicationContext) moduleContext.getParent();

		// Scan the application context for beans with the relevant annotations
		List<MenuConfig> systemMenu = getSystemMenuItems(context, moduleContext);
		menuConfigService.saveMenuItem(systemMenu);
	}

	private List<MenuConfig> getSystemMenuItems(ConfigurableApplicationContext... contexts) {
		final List<MenuConfig> items = new LinkedList<>();

		for (ConfigurableApplicationContext context : contexts) {
			String[] beans = context.getBeanDefinitionNames();
			for (String bean : beans) {
				Object beanObject = context.getBean(bean);
				// Check the object for the bean definition.
				final StringBuilder route = new StringBuilder();

				// Get Menu items for this class
				getMenuItemsForClass(items, beanObject.getClass(), route);

				// Get Menu Items for the methods on the class
				getMenuItemsForMethods(items, beanObject.getClass(), route);
			}
		}

		return items;
	}

	private void getMenuItemsForClass(final List<MenuConfig> items, Class<?> beanClass, final StringBuilder route) {
		RequestMapping mapping = AnnotationUtils.findAnnotation(beanClass, RequestMapping.class);
		// Find the request mapping...
		if (mapping != null) {
			// Add the base path
			String[] path = mapping.value();
			route.append(path.length > 0 ? path[0] : "");
		}

		// Find the MenuItems annotation
		MenuItems menuItemsAnnotation = AnnotationUtils.findAnnotation(beanClass, MenuItems.class);
		if (menuItemsAnnotation != null) {
			// Menu Items
			MenuItem[] menuItemAnnotations = menuItemsAnnotation.items();
			for (MenuItem item : menuItemAnnotations) {
				MenuConfig config = buildMenuItem(item, route.toString());
				if (config == null) {
					continue;
				}
				items.add(config);
			}
		}

		// Find the MenuItem annotations
		MenuItem item = AnnotationUtils.findAnnotation(beanClass, MenuItem.class);
		if (item != null) {
			// Menu Items
			MenuConfig config = buildMenuItem(item, route.toString());
			if (config == null) {
				return;
			}
			items.add(config);
		}
	}

	private void getMenuItemsForMethods(final List<MenuConfig> items, Class<?> beanClass, final StringBuilder route) {
		// this is probably not doing to work
		Method[] methods = beanClass.getMethods();
		for (Method method : methods) {
			StringBuilder methodRoute = new StringBuilder().append(route);
			boolean mapped = false;
			// Find the request mapping...
			RequestMapping mapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
			if (mapping != null) {
				// Add the base path
				String[] path = mapping.value();
				methodRoute.append(path.length > 0 ? path[0] : "");
				mapped = true;
			}

			// Menu Items
			MenuItems menuItemsAnnotation = AnnotationUtils.findAnnotation(method, MenuItems.class);
			if (mapped && menuItemsAnnotation != null) {
				// Menu Items
				MenuItem[] menuItemAnnotations = menuItemsAnnotation.items();
				for (MenuItem i : menuItemAnnotations) {
					MenuConfig config = buildMenuItem(i, methodRoute.toString());
					if (config == null) {
						continue;
					}
					items.add(config);
				}
			}

			// Find the MenuItem annotations
			MenuItem item = AnnotationUtils.findAnnotation(method, MenuItem.class);
			if (mapped && item != null) {
				// Menu Items
				MenuConfig config = buildMenuItem(item, methodRoute.toString());
				if (config == null) {
					continue;
				}
				items.add(config);
			}
		}
	}

	private MenuConfig buildMenuItem(MenuItem item, String route) {
		if (!item.menu().equalsIgnoreCase("system")) {
			return null;
		}

		if (StringUtils.isEmpty(route) && StringUtils.isEmpty(item.route())) {
			throw new IllegalArgumentException("Route cannot be empty when there is no requestmapping path");
		}

		StringBuilder path = new StringBuilder().append(route);
		if (path.toString().endsWith("/") && !StringUtils.isEmpty(item.route())) {
			path.append(item.route());
		} else if (!StringUtils.isEmpty(item.route()) && item.route().startsWith("/")) {
			path.append(item.route());
		} else if (!StringUtils.isEmpty(item.route()) && !item.route().startsWith("/")) {
			path.append("/").append(item.route());
		}

		// Add menu config
		MenuConfig config = menuConfigService.newInstance(item.menu(), item.parent(), item.name(), item.title(), item.description(), false, "_self", path.toString(), item.weight(), item.pattern(), item.task());
		return config;
	}

	public List<MenuInfo> listMenusByLanguage(String language) {
		return menuInfoService.findAllByLanguage(language);
	}

	public Optional<MenuInfo> getMenu(String id, String language) {
		return menuInfoService.getMenu(id, language);
	}

	public void saveMenuInfo(MenuInfo... menuInfo) {
		menuInfoService.save(menuInfo);
	}

	public List<MenuConfig> getMenuTreeByUrl(String menu, String url) {
		return (List<MenuConfig>) menuConfigService.menuTreeForUrl(menu, url);
	}

	public List<MenuConfig> getTopLevelMenuItems(String menu) {
		return (List<MenuConfig>)menuConfigService.getTopLevelMenuItems(menu);
	}

	public List<MenuConfig> getMenuItems(String menu, String parent) {
		return (List<MenuConfig>)menuConfigService.getMenuItems(menu, parent);
	}

	public MenuInfo createMenu(String title, String description, String language) {
		MenuInfo info = menuInfoService.newInstance(UUID.randomUUID().toString(), title, description, language);
		List<MenuInfo> result = menuInfoService.save(info);
		if (result.isEmpty()) {
			throw new RuntimeException("Could not create menu: " + title);
		}
		return result.get(0);
	}

	public MenuInfo updateMenu(String id, String title, String description, String language) {
		MenuInfo info = getMenu(id, language).orElse(null);
		if (info == null) {
			throw new ResourceNotFoundException("Menu with id: " + id + " not found");
		}
		info.setTitle(title);
		info.setDescription(description);
		info.setLanguage(language);
		List<MenuInfo> result = menuInfoService.save(info);
		if (result.isEmpty()) {
			throw new RuntimeException("Could not update menu: " + id);
		}
		return result.get(0);
	}

	public List<MenuConfig> getTasksByPath(String menu, String path) {
		return menuConfigService.getMenuItemsByParent(menu, path, true);
	}
}
