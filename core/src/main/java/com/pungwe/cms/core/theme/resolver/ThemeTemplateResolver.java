package com.pungwe.cms.core.theme.resolver;

import com.lyncode.jtwig.services.api.url.ResourceUrlResolver;

/**
 * Created by ian on 21/02/2016.
 */
public class ThemeTemplateResolver implements ResourceUrlResolver {

	@Override
	public String resolve(String prefix, String url, String suffix) {
		/*
		- Theme Management service is inject in.
		- Current theme is picked up using the current http request, so / is default theme, /admin is admin theme
		- Execute hook theme at startup, then using the hooked themes, look for a template location, otherwise default to classpath:/templates/<template name>
		- TODO: implement potentially contextual theme loading, i.e. if there is a theme bound to a specific url, then match and return the path
		 */
		// Default Action
		return prefix + url + suffix;
	}

}
