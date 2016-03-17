package com.pungwe.cms.core.element.basic;

import com.pungwe.cms.core.annotations.ui.ThemeInfo;
import com.pungwe.cms.core.element.AbstractContentElement;
import com.pungwe.cms.core.element.AbstractRenderedElement;
import com.pungwe.cms.core.element.RenderedElement;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ian on 16/03/2016.
 */
@ThemeInfo("basic/paragraph")
public class ParagraphElement extends AbstractContentElement {

	public ParagraphElement() {

	}

	public ParagraphElement(RenderedElement... content) {
		setContent(content);
	}

	public ParagraphElement(List<RenderedElement> content) {
		setContent(content);
	}

	@Override
	protected Collection<String> excludedAttributes() {
		return new LinkedList<>();
	}
}
