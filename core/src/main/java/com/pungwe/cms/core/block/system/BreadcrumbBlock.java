package com.pungwe.cms.core.block.system;

import com.pungwe.cms.core.annotations.Block;
import com.pungwe.cms.core.block.BlockDefinition;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.form.Form;
import com.pungwe.cms.core.form.FormState;

import java.util.List;
import java.util.Map;

/**
 * Created by ian on 05/03/2016.
 */
@Block(value = "breadcrumb", category = "System", label = "Breadcrumb Block")
public class BreadcrumbBlock implements BlockDefinition {

	@Override
	public void build(List<RenderedElement> elements, Map<String, Object> settings, Map<String, Object> variables) {

	}

	@Override
	public void buildSettingsForm(List<RenderedElement> elements, Form form, FormState state) {
		// Build a great settings form here
	}
}
