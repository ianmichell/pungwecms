package com.pungwe.cms.core.element.basic;

import com.pungwe.cms.core.annotations.ui.ThemeInfo;
import com.pungwe.cms.core.element.AbstractRenderedElement;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by ian on 03/03/2016.
 */
@ThemeInfo("basic/script")
public class ScriptElement extends AbstractRenderedElement {

	protected String type;
	protected String src;
	protected String content;

	public ScriptElement() {
	}

	public ScriptElement(String src, String type) {
		this.src = src;
		this.type = type;
	}

	@ModelAttribute("type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@ModelAttribute("src")
	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	@ModelAttribute("content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	protected Collection<String> excludedAttributes() {
		return Arrays.asList("type", "src");
	}
}
