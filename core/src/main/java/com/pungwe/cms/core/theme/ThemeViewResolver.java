package com.pungwe.cms.core.theme;

import com.lyncode.jtwig.mvc.JtwigViewResolver;
import com.lyncode.jtwig.resource.JtwigResource;
import com.lyncode.jtwig.resource.loader.JtwigResourceResolver;
import com.lyncode.jtwig.services.api.url.ResourceUrlResolver;
import com.lyncode.jtwig.services.impl.url.ThemedResourceUrlResolver;
import com.pungwe.cms.core.theme.resolver.ThemeResourceResolver;
import com.pungwe.cms.core.theme.services.ThemeManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import javax.annotation.PostConstruct;

/**
 * Created by ian on 19/01/2016.
 */
public class ThemeViewResolver extends JtwigViewResolver {

	@Autowired
	ThemeManagementService themeManagementService;

	public ThemeViewResolver(String prefix, String suffix) {
		setPrefix(prefix);
		setSuffix(suffix);
	}

	@PostConstruct
	public void setupResourceLoader() {
		super.setResourceLoader(new ThemeResourceResolver(themeManagementService, getServletContext(), getPrefix(), getSuffix()));
	}

	@Override
	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		AbstractUrlBasedView abstractUrlBasedView = super.buildView(viewName);
		abstractUrlBasedView.setUrl(viewName);
		return abstractUrlBasedView;
	}
}
