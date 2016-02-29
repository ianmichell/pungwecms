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
	protected String head;
	protected String title;
	protected String css;
	protected String jsTop;
	protected String pageTop;
	protected String pageContent;
	protected String pageBottom;
	protected String jsBottom;

	public Map<String, String> getBodyAttributes() {
		if (bodyAttributes == null) {
			bodyAttributes = new HashMap<>();
		}
		return bodyAttributes;
	}

	public void setBodyAttributes(Map<String, String> bodyAttributes) {
		this.bodyAttributes = bodyAttributes;
	}

	public void addBodyAttribute(String name, String value) {
		getBodyAttributes().put(name, value);
	}

	@ModelAttribute("body_attributes")
	public String getBodyAttributesAsString() {
		return " " + getBodyAttributes().entrySet().stream().map(e -> e.getKey() + "=\"" + e.getValue() + "\"").collect(Collectors.joining(" "));
	}

	@ModelAttribute("head")
	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	@ModelAttribute("title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@ModelAttribute("css")
	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	@ModelAttribute("js_top")
	public String getJsTop() {
		return jsTop;
	}

	public void setJsTop(String jsTop) {
		this.jsTop = jsTop;
	}

	@ModelAttribute("js_bottom")
	public String getJsBottom() {
		return jsBottom;
	}

	public void setJsBottom(String jsBottom) {
		this.jsBottom = jsBottom;
	}

	@ModelAttribute("page_top")
	public String getPageTop() {
		return pageTop;
	}

	public void setPageTop(String pageTop) {
		this.pageTop = pageTop;
	}

	@ModelAttribute("page_content")
	public String getPageContent() {
		return pageContent;
	}

	public void setPageContent(String pageContent) {
		this.pageContent = pageContent;
	}

	@ModelAttribute("page_bottom")
	public String getPageBottom() {
		return pageBottom;
	}

	public void setPageBottom(String pageBottom) {
		this.pageBottom = pageBottom;
	}

	@Override
	protected Collection<String> excludedAttributes() {
		return new LinkedList<>();
	}
}
