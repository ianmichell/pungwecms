package com.pungwe.cms.core.system.builder;

import com.pungwe.cms.core.system.element.templates.PageElement;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by ian on 05/03/2016.
 */
public interface PageBuilder {
	/**
	 * Builds a page using the provided page element and http request object
	 * @param request The request object to be used for parameter and uri pattern matching
	 * @param page the page element to populate
	 * @param model the model to be used by the page builder
	 */
	void build(HttpServletRequest request, PageElement page, Map<String, Object> model);
}
