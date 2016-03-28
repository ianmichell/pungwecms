package com.pungwe.cms.core.system.services;

import com.pungwe.cms.core.block.builder.BlockPageBuilder;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.module.services.ModuleManagementService;
import com.pungwe.cms.core.system.builder.PageBuilder;
import com.pungwe.cms.core.system.element.templates.PageElement;
import com.pungwe.cms.core.theme.services.ThemeManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by ian on 05/03/2016.
 */
@Service
public class PageBuilderService {

	@Autowired
	protected ThemeManagementService themeManagementService;

	@Autowired
	protected ApplicationContext applicationContext;

	// Uses the http request and the content object to load and build a page based on the URL and current theme! Just LIKE MAGIC!
	public PageElement buildPage(final HttpServletRequest request, final Map<String, Object> model) {
		Map<String, String> regions = themeManagementService.getRegionsForDefaultThemeByRequest();
		// Build the page element...
		final PageElement pageElement = new PageElement();
		for (String region : regions.keySet()) {
			pageElement.addRegion(region, new LinkedList<RenderedElement>());
		}
		// Build the page with a page builder! Defaults to BlockPageBuilder
		// FIXME: Build with a page manager.
		PageBuilder builder = applicationContext.getBean(BlockPageBuilder.class);
		builder.build(request, pageElement, model);
		return pageElement;
	}
}
