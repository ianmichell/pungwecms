package com.pungwe.cms.core.block.system;

import com.pungwe.cms.core.annotations.Block;
import com.pungwe.cms.core.block.BlockDefinition;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.model.ModelAndViewElement;
import com.pungwe.cms.core.form.Form;
import com.pungwe.cms.core.form.FormState;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by ian on 05/03/2016.
 */
@Block(value = "main_content_block", label = "Main Content Block", category = "Default")
public class MainContentBlock implements BlockDefinition {

	@Override
	public void build(List<RenderedElement> elements, Map<String, Object> settings, Map<String, Object> variables) {
		// Check for the content attribute
		if (!variables.containsKey("content")) {
			return;
		}
		// Get the content element...
		Object content = variables.get("content");
		for (Object item : (content instanceof Collection ? (Collection)content : Arrays.asList(content))) {
			if (item instanceof RenderedElement) {
				elements.add((RenderedElement) item);
			} else if (item instanceof ModelAndView) {
				elements.add(new ModelAndViewElement((ModelAndView) item));
			}
		}
	}

	@Override
	public void buildSettingsForm(List<RenderedElement> elements, Form form, FormState state) {
		// No need for a configuration form here
	}

}
