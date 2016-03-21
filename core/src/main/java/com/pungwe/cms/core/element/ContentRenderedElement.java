package com.pungwe.cms.core.element;

import com.pungwe.cms.core.element.basic.PlainTextElement;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ian on 20/03/2016.
 */
public interface ContentRenderedElement {
	List<RenderedElement> getContent();

	default void setContent(String... content) {
		setContent(Arrays.asList(content).stream().map(s -> new PlainTextElement(s)).collect(Collectors.toList()));
	}

	default void setContent(RenderedElement... content) {
		setContent(Arrays.asList(content));
	}

	void setContent(List<RenderedElement> content);

	default void addContent(String... content) {
		addContent(Arrays.asList(content).stream().map(s -> new PlainTextElement(s)).collect(Collectors.toList()));
	}

	default void addContent(RenderedElement... content) {
		addContent(Arrays.asList(content));
	}

	void addContent(List<RenderedElement> renderedElement);
}
