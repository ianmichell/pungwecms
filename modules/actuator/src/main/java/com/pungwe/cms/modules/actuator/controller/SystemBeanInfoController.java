package com.pungwe.cms.modules.actuator.controller;

import com.pungwe.cms.core.annotations.ui.MenuItem;
import com.pungwe.cms.core.element.basic.PlainTextElement;
import com.pungwe.cms.core.element.basic.TableElement;
import com.pungwe.cms.core.element.basic.UnorderedListElement;
import com.pungwe.cms.core.module.services.ModuleManagementService;
import com.pungwe.cms.core.theme.services.ThemeManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.LiveBeansView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

/**
 * Created by ian on 14/03/2016.
 */
@Controller
@RequestMapping("/admin/reporting/system/beans")
public class SystemBeanInfoController {

	@Autowired
	ModuleManagementService moduleManagementService;

	@Autowired
	ThemeManagementService themeManagementService;

	@MenuItem(
			menu = "system",
			name = "beans",
			parent = "admin.reporting.system",
			title = "Bean Information",
			description = "Displays a list of beans within each of the application contexts"
	)
	@RequestMapping(method= RequestMethod.GET)
	@Cacheable("admin.reporting.system.beans")
	public Callable<String> beans(final Model model) {
		return () -> {

			final TableElement table = new TableElement();
			table.addHeaderRow(
					new TableElement.Header(new PlainTextElement("Context")),
					new TableElement.Header(new PlainTextElement("Name")),
					new TableElement.Header(new PlainTextElement("Scope")),
					new TableElement.Header(new PlainTextElement("Dependencies"))
			);

			ApplicationContext parent = moduleManagementService.getModuleContext().getParent();
			ApplicationContext moduleContext = moduleManagementService.getModuleContext();
			List<ApplicationContext> themeContexts = themeManagementService.getThemeContextsAsList();

			createRowsFromContext(table, parent);
			createRowsFromContext(table, moduleContext);
			themeContexts.forEach(applicationContext -> {
				createRowsFromContext(table, applicationContext);
			});

			model.addAttribute("title", "Bean Information");
			model.addAttribute("content", table);
			return "actuator/beans";
		};
	}

	private void createRowsFromContext(TableElement tableElement, ApplicationContext context) {
		if (context == null || !(context instanceof ConfigurableApplicationContext)) {
			return;
		}
		ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext)context).getBeanFactory();
		for (String name : beanFactory.getBeanDefinitionNames()) {
			BeanDefinition bd = beanFactory.getBeanDefinition(name);
			String scope = bd.getScope();
			if (!StringUtils.hasText(scope)) {
				scope = BeanDefinition.SCOPE_SINGLETON;
			}
			String[] dependencies = beanFactory.getDependenciesForBean(name);
			TableElement.Column deps = null;
			if (dependencies != null && dependencies.length > 0) {
				deps = new TableElement.Column(new UnorderedListElement(dependencies));
			} else {
				deps = new TableElement.Column(new PlainTextElement(""));
			}
			TableElement.Column nameColumn = new TableElement.Column(new PlainTextElement(name.length() > 80 ? name.substring(0, 77) + "..." : name));
			nameColumn.addAttribute("title", name);
			TableElement.Header contextCol = new TableElement.Header(new PlainTextElement(getApplicationContextLabel(context.getId())));
			tableElement.addRow(
					contextCol,
					nameColumn,
					new TableElement.Column(new PlainTextElement(scope)),
					deps
			);
		}
	}

	private String getApplicationContextLabel(String id) {
		if (id.equalsIgnoreCase("parent-application-context")) {
			return "Application";
		} else if (id.equalsIgnoreCase("module-application-context")) {
			return "Module";
		} else if (id.startsWith("theme-application-context-")) {
			return "Theme";
		}
		return "NA";
	}
}
