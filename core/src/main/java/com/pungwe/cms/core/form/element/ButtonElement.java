package com.pungwe.cms.core.form.element;

import com.pungwe.cms.core.annotations.ui.ThemeInfo;
import com.pungwe.cms.core.element.ContentRenderedElement;
import com.pungwe.cms.core.element.RenderedElement;
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
@ThemeInfo("form/button")
public class ButtonElement extends AbstractFormRenderedElement implements ContentRenderedElement {

	List<RenderedElement> content;

	public enum ButtonType {
		BUTTON, SUBMIT, RESET
	}

	protected ButtonType type;

	public ButtonElement() {

	}

	public ButtonElement(ButtonType type) {
		this.type = type;
	}

	public ButtonElement(ButtonType type, String... content) {
		this(type, Arrays.asList(content).stream().map(s -> new PlainTextElement(s)).collect(Collectors.toList()));
	}

	public ButtonElement(ButtonType type, RenderedElement... content) {
		this(type, Arrays.asList(content));
	}

	public ButtonElement(ButtonType type, List<RenderedElement> content) {
		this.type = type;
		this.setContent(content);
	}

	@Override
	@ModelAttribute("content")
	public List<RenderedElement> getContent() {
		if (content == null) {
			content = new ArrayList<>();
		}
		return content;
	}

	@Override
	public void setContent(List<RenderedElement> content) {
		List<RenderedElement> copyOfContent = new ArrayList<>(content.size());
		copyOfContent.addAll(content);
		this.content = copyOfContent;
	}

	@Override
	public void addContent(List<RenderedElement> renderedElement) {
		getContent().addAll(renderedElement);
	}


	public ButtonType getType() {

        if (type == null) {
            type = ButtonType.BUTTON;
        }

		return type;
	}

	@ModelAttribute("type")
	public String getTypeAsString() {
		return getType().name().toLowerCase();
	}

	public void setType(ButtonType type) {
		this.type = type;
	}
}
