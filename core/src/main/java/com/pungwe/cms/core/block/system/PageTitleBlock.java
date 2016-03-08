package com.pungwe.cms.core.block.system;

import com.pungwe.cms.core.annotations.Block;
import com.pungwe.cms.core.block.BlockDefinition;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.basic.HeaderElement;
import com.pungwe.cms.core.element.basic.PlainTextElement;
import com.pungwe.cms.core.form.Form;
import com.pungwe.cms.core.form.FormState;

import java.util.List;
import java.util.Map;

/**
 * Created by ian on 05/03/2016.
 */
@Block(category = "system", label = "Page Title Block", value = "page_title_block")
public class PageTitleBlock implements BlockDefinition {

	@Override
	public void build(List<RenderedElement> elements, Map<String, Object> settings, Map<String, Object> variables) {
		// If don't have the title variable, don't bother doing anything
		if (!variables.containsKey("title")) {
			return;
		}
		// FIXME: This should automatically be H1 by default, or the tag defined by the settings...
		// Add a page title...
		int level = 1;
		if (settings.containsKey("header_level") && settings.get("header_level") instanceof Integer) {
			level = (Integer)settings.get("header_level");
		}
		elements.add(new HeaderElement(level, (String)variables.get("title")));
	}

	@Override
	public void buildSettingsForm(List<RenderedElement> elements, Form form, FormState state) {
		// No need for a configuration form here
	}
}
