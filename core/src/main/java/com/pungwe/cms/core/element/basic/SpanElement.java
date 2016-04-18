package com.pungwe.cms.core.element.basic;

import com.pungwe.cms.core.annotations.ui.ThemeInfo;
import com.pungwe.cms.core.element.AbstractContentElement;
import com.pungwe.cms.core.element.AbstractRenderedElement;
import com.pungwe.cms.core.element.RenderedElement;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by ian on 03/03/2016.
 */
@ThemeInfo("basic/span")
public class SpanElement extends AbstractContentElement {

	public SpanElement() {

	}

	public SpanElement(String... content) {
		setContent(content);
	}

	public SpanElement(RenderedElement... content) {
		setContent(content);
	}

	@Override
	protected Collection<String> excludedAttributes() {
		return new LinkedList<>();
	}
}
