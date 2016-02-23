package com.pungwe.cms.core.theme.resolver;

import com.lyncode.jtwig.exception.ResourceException;
import com.lyncode.jtwig.resource.ClasspathJtwigResource;
import com.lyncode.jtwig.resource.FileJtwigResource;
import com.lyncode.jtwig.resource.JtwigResource;
import com.lyncode.jtwig.resource.WebJtwigResource;
import com.lyncode.jtwig.resource.loader.JtwigResourceResolver;
import com.lyncode.jtwig.services.api.url.ResourceUrlResolver;
import com.pungwe.cms.core.theme.services.ThemeManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;

import javax.servlet.ServletContext;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static com.lyncode.jtwig.util.LocalThreadHolder.getServletRequest;

/**
 * Created by ian on 21/02/2016.
 */
public class ThemeResourceResolver implements JtwigResourceResolver {

	private final ServletContext servletContext;
	private final String suffix;
	private final String prefix;

	protected ThemeManagementService themeManagementService;

	public ThemeResourceResolver(ThemeManagementService themeManagementService, ServletContext servletContext, String prefix, String suffix) {
		this.servletContext = servletContext;
		this.themeManagementService = themeManagementService;
		this.prefix = prefix;
		this.suffix = suffix;
	}

	@Override
	public JtwigResource resolve(String viewUrl) {
		// Should be an ordered list with the theme on top...
		List<String> urls = themeManagementService.resolveViewPath(getServletRequest(), prefix, viewUrl, suffix);
		Optional<JtwigResource> resource = urls.stream().map(url -> {
			if (url.startsWith("classpath:")) {
				return new ClasspathJtwigResource(url);
			} else if (url.startsWith("file:")) {
				return new FileJtwigResource(url);
			}
			return new WebJtwigResource(servletContext, url);
		}).filter(r -> r.exists()).findFirst();
		return resource.orElse(new JtwigResource() {
			@Override
			public boolean exists() {
				return false;
			}

			@Override
			public InputStream retrieve() throws ResourceException {
				return null;
			}

			@Override
			public JtwigResource resolve(String relativePath) throws ResourceException {
				return null;
			}
		});
	}
}
