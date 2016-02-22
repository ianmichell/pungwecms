package com.pungwe.cms.core.form.element;

import com.pungwe.cms.core.annotations.ThemeInfo;
import com.pungwe.cms.core.element.AbstractRenderedElement;
import com.pungwe.cms.core.element.RenderedElement;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

/**
 * Created by ian on 09/01/2016.
 */
@ThemeInfo("form/fieldset")
public class FieldSetElement extends AbstractRenderedElement {

	protected String legend;
	protected List<RenderedElement> children;

	public String getLegend() {
		return legend;
	}

	public void setLegend(String legend) {
		this.legend = legend;
	}

	@ModelAttribute("children")
	public List<RenderedElement> getChildren() {
		return children;
	}

	public void setChildren(List<RenderedElement> children) {
		this.children = children;
	}

}
