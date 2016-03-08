package com.pungwe.cms.core.system.element.templates;

import com.pungwe.cms.core.annotations.ui.ThemeInfo;
import com.pungwe.cms.core.element.AbstractRenderedElement;
import com.pungwe.cms.core.element.HeaderRenderedElement;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.basic.LinkElement;
import com.pungwe.cms.core.element.basic.ScriptElement;
import com.pungwe.cms.core.element.basic.StyleElement;
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
	protected List<HeaderRenderedElement> head;
	protected List<String> title;
	protected List<HeaderRenderedElement> css;
	protected List<ScriptElement> jsTop;
	protected List<RenderedElement> pageTop;
	protected List<RenderedElement> pageContent;
	protected List<RenderedElement> pageBottom;
	protected List<ScriptElement> jsBottom;

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
	public List<HeaderRenderedElement> getHead() {
		if (head == null) {
			head = new LinkedList<>();
		}
		return head;
	}

	public void addToHead(HeaderRenderedElement... elements) {
		addToHead(Arrays.asList(elements));
	}

	public void addToHead(Collection<HeaderRenderedElement> elements) {
		getHead().addAll(elements);
	}

	@ModelAttribute("title")
	public List<String> getTitle() {
		if (title == null) {
			title = new LinkedList<>();
		}
		return title;
	}

	public void setTitle(List<String> title) {
		this.title = title;
	}

	public void addTitle(String title) {
		this.getTitle().add(title);
	}

	@ModelAttribute("css")
	public List<HeaderRenderedElement> getCss() {
		if (css == null) {
			css = new LinkedList<>();
		}
		return css;
	}

	public void addToCss(HeaderRenderedElement... elements) {
		addToCss(Arrays.asList(elements));
	}

	public void addToCss(Collection<HeaderRenderedElement> elements) {
		for (HeaderRenderedElement element : elements) {
			if (!(element instanceof LinkElement) && !(element instanceof StyleElement)) {
				throw new IllegalArgumentException("Invalid element type for HTML css region");
			}
			getCss().add(element);
		}
	}

	@ModelAttribute("js_top")
	public List<ScriptElement> getJsTop() {
		if (jsTop == null) {
			jsTop = new LinkedList<>();
		}
		return jsTop;
	}

	public void addToJsTop(ScriptElement... scriptElements) {
		addToJsTop(Arrays.asList(scriptElements));
	}

	public void addToJsTop(Collection<ScriptElement> scriptElements) {
		getJsTop().addAll(scriptElements);
	}

	@ModelAttribute("js_bottom")
	public List<ScriptElement> getJsBottom() {
		if (jsBottom == null) {
			jsBottom = new LinkedList<>();
		}
		return jsBottom;
	}

	public void addToJsBottom(ScriptElement... scriptElements) {
		addToJsBottom(Arrays.asList(scriptElements));
	}

	public void addToJsBottom(Collection<ScriptElement> scriptElements) {
		getJsBottom().addAll(scriptElements);
	}

	@ModelAttribute("page_top")
	public List<RenderedElement> getPageTop() {
		if (pageTop == null) {
			pageTop = new LinkedList<>();
		}
		return pageTop;
	}

	public void addToPageTop(RenderedElement... renderedElements) {
		addToPageTop(Arrays.asList(renderedElements));
	}

	public void addToPageTop(Collection<RenderedElement> renderedElements) {
		getPageTop().addAll(renderedElements);
	}

	@ModelAttribute("page_content")
	public List<RenderedElement> getPageContent() {
		if (pageContent == null) {
			pageContent = new LinkedList<>();
		}
		return pageContent;
	}

	public void addToPageContent(RenderedElement... renderedElements) {
		addToPageContent(Arrays.asList(renderedElements));
	}

	public void addToPageContent(Collection<RenderedElement> renderedElements) {
		getPageContent().addAll(renderedElements);
	}

	@ModelAttribute("page_bottom")
	public List<RenderedElement> getPageBottom() {
		if (pageBottom == null) {
			pageBottom = new LinkedList<>();
		}
		return pageBottom;
	}

	public void addToPageBottom(RenderedElement... renderedElements) {
		addToPageBottom(Arrays.asList(renderedElements));
	}

	public void addToPageBottom(Collection<RenderedElement> renderedElements) {
		getPageBottom().addAll(renderedElements);
	}

	@Override
	protected Collection<String> excludedAttributes() {
		return new LinkedList<>();
	}

}
