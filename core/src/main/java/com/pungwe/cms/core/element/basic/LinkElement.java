package com.pungwe.cms.core.element.basic;

import com.pungwe.cms.core.element.AbstractRenderedElement;
import com.pungwe.cms.core.element.RenderedElement;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Created by ian on 14/01/2016.
 */
public class LinkElement extends AbstractRenderedElement {

	protected RenderedElement content;

	public LinkElement() {
	}

	public LinkElement(String title, String href, RenderedElement content) {
		addAttribute("title", title);
		addAttribute("href", href);
		this.content = content;
	}

	public LinkElement(String title, String href, String target, RenderedElement content) {
		addAttribute("title", title);
		addAttribute("href", href);
		addAttribute("target", target);
		this.content = content;
	}

	public String getTitle() {
		return getAttribute("title");
	}

	public void setTitle(String title) {
		addAttribute("title", title);
	}

	public String getHref() {
		return getAttribute("href");
	}

	public void setHref(String href) {
		addAttribute("href", href);
	}

	public String getTarget() {
		return getAttribute("target");
	}

	public void setTarget(String target) {
		addAttribute("target", target);
	}

	@ModelAttribute("content")
	public RenderedElement getContent() {
		return content;
	}

	public void setContent(RenderedElement content) {
		this.content = content;
	}

	@Override
	public String getTheme() {
		return "link";
	}
}
