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
import java.util.stream.Collectors;

/**
 * Created by ian on 17/03/2016.
 */
@ThemeInfo("basic/text_format")
public class TextFormatElement extends AbstractContentElement {

	public enum Type {
		I, STRONG, MARK, CITE, P;

		public String getTagName() {
			return this.name().toLowerCase();
		}
	}

	private Type type;

	public TextFormatElement(Type type) {
		this.type = type;
	}

	public TextFormatElement(Type type, String... content) {
		this(type);
		setContent(content);
	}

	public TextFormatElement(Type type, RenderedElement... content) {
		this(type);
		setContent(content);
	}

	public TextFormatElement(Type type, List<RenderedElement> content) {
		this.type = type;
		setContent(content);
	}

	@ModelAttribute("tag")
	public String getTag() {
		return getType().getTagName();
	}

	public Type getType() {
		return type;
	}

	@Override
	protected Collection<String> excludedAttributes() {
		return new LinkedList<>();
	}
}
