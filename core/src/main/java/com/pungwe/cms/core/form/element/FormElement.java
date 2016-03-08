package com.pungwe.cms.core.form.element;

import com.pungwe.cms.core.annotations.ui.ThemeInfo;
import com.pungwe.cms.core.element.AbstractRenderedElement;
import com.pungwe.cms.core.element.RenderedElement;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ian on 24/02/2016.
 */
@ThemeInfo("form/form")
public class FormElement extends AbstractRenderedElement {

	protected List<RenderedElement> children;

	public void setEnctype(String enctype) {
		addAttribute("enctype", enctype);
	}

	public String getEnctype() {
		return getAttribute("enctype");
	}

	public void setMethod(String method) {
		addAttribute("method", method);
	}

	public String getMethod() {
		return getAttribute("method");
	}

	public void setTarget(String target) {
		addAttribute("target", target);
	}

	public String getTarget() {
		return getAttribute("target");
	}

	public void setAcceptCharset(String acceptCharset) {
		addAttribute("accept-charset", acceptCharset);
	}

	public String getAcceptCharset() {
		return getAttribute("accept-charset");
	}

	public void setAction(String action) {
		addAttribute("action", action);
	}

	public String getAction() {
		return getAttribute("action");
	}

	public void setName(String name) {
		addAttribute("name", name);
	}

	public String getName() {
		return getAttribute("name");
	}

	@ModelAttribute("children")
	public List<RenderedElement> getChildren() {
		if (children == null) {
			children = new LinkedList<>();
		}
		return children;
	}

	public void setChildren(List<RenderedElement> children) {
		this.children = children;
	}

	public void addChild(RenderedElement... child) {
		getChildren().addAll(Arrays.asList(child));
	}

	@Override
	protected Collection<String> excludedAttributes() {
		return new LinkedList<>();
	}
}
