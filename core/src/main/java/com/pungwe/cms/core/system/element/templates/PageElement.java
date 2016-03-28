package com.pungwe.cms.core.system.element.templates;

import com.pungwe.cms.core.annotations.stereotypes.ThemeRegion;
import com.pungwe.cms.core.annotations.ui.ThemeInfo;
import com.pungwe.cms.core.element.AbstractRenderedElement;
import com.pungwe.cms.core.element.RenderedElement;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.*;

/**
 * Created by ian on 24/02/2016.
 */
@ThemeInfo("system/page")
public class PageElement extends AbstractRenderedElement {

	public static final Map<String, String> DEFAULT_REGIONS = new LinkedHashMap<>();

	static {
		DEFAULT_REGIONS.put("header", "Header");
		DEFAULT_REGIONS.put("help", "Help");
		DEFAULT_REGIONS.put("primary_menu", "Primary Menu");
		DEFAULT_REGIONS.put("secondary_menu", "Secondary Menu");
		DEFAULT_REGIONS.put("breadcrumb", "Breadcrumb");
		DEFAULT_REGIONS.put("highlighted", "Highlighted");
		DEFAULT_REGIONS.put("sidebar_first", "First Sidebar");
		DEFAULT_REGIONS.put("content", "Content");
		DEFAULT_REGIONS.put("sidebar_second", "Second Sidebar");
		DEFAULT_REGIONS.put("footer", "Footer");
	}

	protected Map<String, List<RenderedElement>> regions = new LinkedHashMap<>();

	@ModelAttribute("regions")
	public Map<String, List<RenderedElement>> getRegions() {
		return regions;
	}

	public void addRegion(String region, List<RenderedElement> content) {
		regions.put(region, content);
	}

	public void addRegion(String region, RenderedElement content) {
		if (!regions.containsKey(region)) {
			addRegion(region, Arrays.asList(content));
			return;
		}
		List<RenderedElement> renderedElements = regions.get(region);
		if (renderedElements == null) {
			addRegion(region, Arrays.asList(content));
			return;
		}
		renderedElements.add(content);
	}

	@Override
	protected Collection<String> excludedAttributes() {
		return new LinkedList<>();
	}
}
