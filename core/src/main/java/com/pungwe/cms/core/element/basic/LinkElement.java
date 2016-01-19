package com.pungwe.cms.core.element.basic;

import com.pungwe.cms.core.element.AbstractRenderedElement;
import com.pungwe.cms.core.element.RenderedElement;

/**
 * Created by ian on 14/01/2016.
 */
public class LinkElement extends AbstractRenderedElement {

	protected String title;
	protected String href;
	protected String target;
	protected RenderedElement content;

	public LinkElement() {
	}

	public LinkElement(String title, String href, RenderedElement content) {
		this.title = title;
		this.href = href;
		this.content = content;
	}

	public LinkElement(String title, String href, String target, RenderedElement content) {
		this.title = title;
		this.href = href;
		this.target = target;
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

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

	@Override
	public String preProcessAttributes() {
		StringBuilder b = new StringBuilder();
		b.append(" href=\"").append(href).append("\"");
		b.append(" target=\"").append(target).append("\"");
		b.append(" title=\"").append(title).append("\"");
		b.append(super.preProcessAttributes());
		return b.toString();
	}
}
