package com.pungwe.cms.core.theme.resolver;

import com.lyncode.jtwig.exception.ResourceException;
import com.lyncode.jtwig.resource.ClasspathJtwigResource;
import com.lyncode.jtwig.resource.FileJtwigResource;
import com.lyncode.jtwig.resource.JtwigResource;
import com.lyncode.jtwig.resource.WebJtwigResource;
import com.lyncode.jtwig.resource.loader.JtwigResourceResolver;
import com.pungwe.cms.core.theme.resource.ThemeViewResource;
import com.pungwe.cms.core.theme.services.ThemeManagementService;
import org.springframework.core.io.UrlResource;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
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
		if (viewUrl.startsWith(prefix) && viewUrl.endsWith(suffix)) {
			return getResource(viewUrl);
		}
		// Should be an ordered list with the theme on top...
		List<String> urls = themeManagementService.resolveViewPath(getServletRequest(), prefix, viewUrl, suffix);
		Optional<JtwigResource> resource = urls.stream().map(url -> {
			return getResource(url);
		}).filter(r -> r.exists()).findFirst();
		return resource.orElse(new WebJtwigResource(servletContext, viewUrl));
	}

	private JtwigResource getResource(String url) {
		if (url.startsWith("classpath:")) {
			return new ClasspathJtwigResource(url);
		} else if (url.startsWith("file:")) {
			return new ThemeViewResource(url);
		}
		return new WebJtwigResource(servletContext, url);
//		return new ThemeViewResource(url);
	}
}
