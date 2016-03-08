package com.pungwe.cms.core.element.basic;

import com.pungwe.cms.core.annotations.ui.ThemeInfo;
import com.pungwe.cms.core.element.AbstractRenderedElement;
import com.pungwe.cms.core.element.HeaderRenderedElement;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by ian on 03/03/2016.
 */
@ThemeInfo("basic/meta")
public class MetaElement extends AbstractRenderedElement implements HeaderRenderedElement {

	protected String charset;
	protected String content;
	protected String httpEquiv;
	protected String name;

	public MetaElement() {

	}

	public MetaElement(String name, String content) {
		this.content = content;
		this.name = name;
	}

	@ModelAttribute("charset")
	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	@ModelAttribute("content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@ModelAttribute("httpEquiv")
	public String getHttpEquiv() {
		return httpEquiv;
	}

	public void setHttpEquiv(String httpEquiv) {
		this.httpEquiv = httpEquiv;
	}

	@ModelAttribute("name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	protected Collection<String> excludedAttributes() {
		return Arrays.asList("charset", "content", "http-equiv", "name", "scheme");
	}

}
