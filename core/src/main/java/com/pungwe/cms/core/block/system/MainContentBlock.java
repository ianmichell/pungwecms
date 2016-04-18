package com.pungwe.cms.core.block.system;

import com.pungwe.cms.core.annotations.stereotypes.Block;
import com.pungwe.cms.core.block.BlockDefinition;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.basic.PlainTextElement;
import com.pungwe.cms.core.element.model.ModelAndViewElement;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * Created by ian on 05/03/2016.
 */
@Block(value = "main_content_block", label = "Main Content Block", category = "Default")
public class MainContentBlock implements BlockDefinition {

	@Override
	public Map<String, Object> getDefaultSettings() {
		return new HashMap<>();
	}

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
			} else {
				elements.add(new PlainTextElement(item.toString()));
			}
		}
	}

	@Override
	public void buildSettingsForm(List<RenderedElement> elements, Map<String, Object> settings) {
		// No need for a configuration form here
	}

}
