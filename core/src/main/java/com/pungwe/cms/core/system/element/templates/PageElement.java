package com.pungwe.cms.core.system.element.templates;

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

	public static final Map<String, String> DEFAULT_REGIONS;

	static {
        Map<String, String> map = new LinkedHashMap<>();
		map.put("header", "Header");
		map.put("help", "Help");
		map.put("primary_menu", "Primary Menu");
        map.put("secondary_menu", "Secondary Menu");
        map.put("breadcrumb", "Breadcrumb");
        map.put("highlighted", "Highlighted");
        map.put("sidebar_first", "First Sidebar");
        map.put("content", "Content");
        map.put("sidebar_second", "Second Sidebar");
        map.put("footer", "Footer");
        DEFAULT_REGIONS = Collections.unmodifiableMap(map);
	}

	protected Map<String, List<RenderedElement>> regions = new LinkedHashMap<>();

	@ModelAttribute("regions")
	public Map<String, List<RenderedElement>> getRegions() {
		return Collections.unmodifiableMap(regions);
	}

	public void addRegion(String region, List<RenderedElement> content) {
		List<RenderedElement> elements = new ArrayList<>(content.size());
		elements.addAll(content);
		regions.put(region, elements);
	}

	public void addRegion(String region, RenderedElement... content) {
		if (!regions.containsKey(region)) {
			addRegion(region, Arrays.asList(content));
			return;
		}
		List<RenderedElement> renderedElements = regions.get(region);
		renderedElements.addAll(Arrays.asList(content));
	}

	@Override
	protected Collection<String> excludedAttributes() {
		return new LinkedList<>();
	}
}
