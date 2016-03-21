package com.pungwe.cms.core.element;

import com.pungwe.cms.core.element.basic.PlainTextElement;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ian on 17/03/2016.
 */
public abstract class AbstractContentElement extends AbstractRenderedElement implements ContentRenderedElement {

	protected List<RenderedElement> content;

	@ModelAttribute("content")
	public List<RenderedElement> getContent() {
		if (content == null) {
			content = new LinkedList<>();
		}
		return content;
	}

	public void setContent(List<RenderedElement> content) {
		if (content == null) {
			this.content = null;
		}
		List<RenderedElement> copyOfContent = new ArrayList<>(content.size());
		copyOfContent.addAll(content);
		this.content = copyOfContent;
	}

	public void addContent(List<RenderedElement> renderedElement) {
		getContent().addAll(renderedElement);
	}
}
