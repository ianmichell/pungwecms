package com.pungwe.cms.core.system.element.templates;

import com.pungwe.cms.core.annotations.ThemeInfo;
import com.pungwe.cms.core.element.AbstractRenderedElement;
import com.pungwe.cms.core.element.RenderedElement;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 *
 * Created by ian on 24/02/2016.
 */
@ThemeInfo("system/html")
public class HtmlElement extends AbstractRenderedElement {

	protected Map<String, String> bodyAttributes;
	protected List<RenderedElement> pageTop;
	protected RenderedElement page;
	protected List<RenderedElement> pageBottom;

	public Map<String, String> getBodyAttributes() {
		if (bodyAttributes == null) {
			bodyAttributes = new HashMap<>();
		}
		return bodyAttributes;
	}

	public void setBodyAttributes(Map<String, String> bodyAttributes) {
		this.bodyAttributes = bodyAttributes;
	}

	@ModelAttribute("body_attributes")
	public String getBodyAttributesAsString() {
		return " " + getAttributes().entrySet().stream().map(e -> e.getKey() + "=\"" + e.getValue() + "\"").collect(Collectors.joining(" "));
	}

	@ModelAttribute("page_top")
	public List<RenderedElement> getPageTop() {
		if (pageTop == null) {
			pageTop = new LinkedList<>();
		}
		return pageTop;
	}

	public void setPageTop(List<RenderedElement> pageTop) {
		this.pageTop = pageTop;
	}

	@ModelAttribute("page")
	public RenderedElement getPage() {
		return page;
	}

	public void setPage(RenderedElement page) {
		this.page = page;
	}

	@ModelAttribute("page_bottom")
	public List<RenderedElement> getPageBottom() {
		if (pageBottom == null) {
			pageBottom = new LinkedList<>();
		}
		return pageBottom;
	}

	public void setPageBottom(List<RenderedElement> pageBottom) {
		this.pageBottom = pageBottom;
	}

	public void addBodyAttribute(String name, String value) {
		getAttributes().put(name, value);
	}

	public void addPageTop(RenderedElement... pageTop) {
		getPageTop().addAll(Arrays.asList(pageTop));
	}

	public void addPageBottom(RenderedElement... pageBottom) {
		getPageBottom().addAll(Arrays.asList(pageBottom));
	}
}
