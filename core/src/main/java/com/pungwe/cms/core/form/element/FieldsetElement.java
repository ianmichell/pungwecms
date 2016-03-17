package com.pungwe.cms.core.form.element;

import com.pungwe.cms.core.annotations.ui.ThemeInfo;
import com.pungwe.cms.core.element.AbstractContentElement;
import com.pungwe.cms.core.element.AbstractRenderedElement;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.basic.PlainTextElement;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ian on 09/01/2016.
 */
@ThemeInfo("form/fieldset")
public class FieldsetElement extends AbstractContentElement {

	protected RenderedElement legend;

	@ModelAttribute("legend")
	public RenderedElement getLegend() {
		return legend;
	}

	public void setLegend(RenderedElement legend) {
		this.legend = legend;
	}

	public void setLegend(String legend) {
		setLegend(new PlainTextElement(legend));
	}

	@Override
	protected Collection<String> excludedAttributes() {
		return new LinkedList<>();
	}
}
