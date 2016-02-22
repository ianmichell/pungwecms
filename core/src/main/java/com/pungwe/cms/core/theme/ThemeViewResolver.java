package com.pungwe.cms.core.theme;

import com.lyncode.jtwig.mvc.JtwigViewResolver;
import com.lyncode.jtwig.resource.JtwigResource;
import com.lyncode.jtwig.resource.loader.JtwigResourceResolver;
import com.lyncode.jtwig.services.api.url.ResourceUrlResolver;
import com.lyncode.jtwig.services.impl.url.ThemedResourceUrlResolver;
import com.pungwe.cms.core.theme.services.ThemeManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import static com.lyncode.jtwig.util.LocalThreadHolder.getServletRequest;

/**
 * Created by ian on 19/01/2016.
 */
public class ThemeViewResolver extends JtwigViewResolver {

	@Autowired
	ThemeManagementService themeManagementService;

	@Override
	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		AbstractUrlBasedView abstractUrlBasedView = super.buildView(viewName);
		abstractUrlBasedView.setUrl(resolveUrl(viewName));
		return abstractUrlBasedView;
	}

	protected String resolveUrl(String viewName) {
		// Default action
		return themeManagementService.resolveViewPath(getServletRequest(), getPrefix(), viewName, getSuffix());
	}
}
