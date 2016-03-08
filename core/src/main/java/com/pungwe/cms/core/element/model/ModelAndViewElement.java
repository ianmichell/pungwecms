package com.pungwe.cms.core.element.model;

import com.pungwe.cms.core.annotations.ThemeInfo;
import com.pungwe.cms.core.element.AbstractRenderedElement;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Used to wrap a ModelAndView where RenderedElements are required...
 *
 * Created by ian on 05/03/2016.
 */
@ThemeInfo("model/model_and_view")
public class ModelAndViewElement extends AbstractRenderedElement {

	public ModelAndView content;

	public ModelAndViewElement() {

	}

	public ModelAndViewElement(ModelAndView content) {
		this.content = content;
	}

	@ModelAttribute("content")
	public ModelAndView getContent() {
		return content;
	}

	public void setContent(ModelAndView content) {
		this.content = content;
	}

	@Override
	protected Collection<String> excludedAttributes() {
		return new LinkedList<>();
	}
}
