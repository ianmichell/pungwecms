package com.pungwe.cms.core.block.system;

import com.pungwe.cms.core.block.BlockDefinition;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.form.Form;
import com.pungwe.cms.core.form.FormState;

import java.util.List;
import java.util.Map;

/**
 * Created by ian on 05/03/2016.
 */
public class SecondaryMenuBlock implements BlockDefinition {

	@Override
	public void build(List<RenderedElement> elements, Map<String, Object> settings, Map<String, Object> variables) {

	}

	@Override
	public void buildSettingsForm(List<RenderedElement> elements, Form form, FormState state) {
		// No need for a configuration form here
	}

}
