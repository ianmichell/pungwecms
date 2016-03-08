package com.pungwe.cms.core.element.basic;

import com.pungwe.cms.core.annotations.ThemeInfo;
import com.pungwe.cms.core.element.AbstractRenderedElement;
import com.pungwe.cms.core.element.HeaderRenderedElement;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by ian on 03/03/2016.
 */
@ThemeInfo("basic/link")
public class LinkElement extends AbstractRenderedElement implements HeaderRenderedElement {

	protected String rel;
	protected String href;
	protected String type;

	public LinkElement() {

	}

	public LinkElement(String href) {
		this.href = href;
	}

	public LinkElement(String rel, String href, String type) {
		this.rel = rel;
		this.href = href;
		this.type = type;
	}

	public LinkElement(String rel, String href) {
		this.rel = rel;
		this.href = href;
	}

	@ModelAttribute("rel")
	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	@ModelAttribute("href")
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	@ModelAttribute("type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	protected Collection<String> excludedAttributes() {
		return new LinkedList<>();
	}
}
