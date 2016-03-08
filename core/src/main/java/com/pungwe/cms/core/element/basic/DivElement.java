package com.pungwe.cms.core.element.basic;

import com.pungwe.cms.core.annotations.ThemeInfo;
import com.pungwe.cms.core.element.AbstractRenderedElement;
import com.pungwe.cms.core.element.RenderedElement;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ian on 08/03/2016.
 */
@ThemeInfo("basic/div")
public class DivElement extends AbstractRenderedElement {

	protected List<RenderedElement> content;

	@ModelAttribute("content")
	public List<RenderedElement> getContent() {
		if (content == null) {
			content = new LinkedList<>();
		}
		return content;
	}

	public void setContent(List<RenderedElement> content) {
		this.content = content;
	}

	public void addContent(RenderedElement... content) {
		addContent(Arrays.asList(content));
	}

	public void addContent(Collection<RenderedElement> content) {
		getContent().addAll(content);
	}

	@Override
	protected Collection<String> excludedAttributes() {
		return new LinkedList<>();
	}
}
