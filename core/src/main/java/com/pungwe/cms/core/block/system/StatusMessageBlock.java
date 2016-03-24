package com.pungwe.cms.core.block.system;

import com.pungwe.cms.core.annotations.stereotypes.Block;
import com.pungwe.cms.core.annotations.ui.ThemeInfo;
import com.pungwe.cms.core.block.BlockDefinition;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.basic.ListElement;
import com.pungwe.cms.core.element.basic.ParagraphElement;
import com.pungwe.cms.core.element.basic.UnorderedListElement;
import com.pungwe.cms.core.element.model.ModelAndViewElement;
import com.pungwe.cms.core.form.Form;
import com.pungwe.cms.core.form.FormState;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * Created by ian on 05/03/2016.
 */
@Block(value = "status_message_block", label = "Status Message Block", category = "System")
@ThemeInfo("blocks/status_message")
public class StatusMessageBlock implements BlockDefinition {

	@Override
	public Map<String, Object> getDefaultSettings() {
		return new HashMap<>();
	}

	@Override
	public void build(List<RenderedElement> elements, Map<String, Object> settings, Map<String, Object> variables) {
		if (variables == null || !variables.containsKey("content")) {
			return;
		}

		// Check for bind errors!
		AtomicBoolean hasErrors = new AtomicBoolean(false);
//		if (variables.entrySet().stream().filter(entry -> entry.getKey().startsWith(BindingResult.MODEL_KEY_PREFIX) && entry.getValue() instanceof BindingResult).findAny().isPresent()) {
		UnorderedListElement listElement = new UnorderedListElement();
		listElement.addClass("status-message");
		// Get the content element...
		Object content = variables.get("content");
		for (Object item : (content instanceof Collection ? (Collection) content : Arrays.asList(content))) {
			if (item instanceof ModelAndViewElement) {
				item = ((ModelAndViewElement) item).getContent();
			}
			if (item instanceof ModelAndView) {
				// Check for bind results
				List<BindingResult> bindingResults = ((ModelAndView) item).getModel().entrySet().stream()
						.filter(entry -> entry.getKey().startsWith(BindingResult.MODEL_KEY_PREFIX) && entry.getValue() instanceof BindingResult)
						.map(entry -> (BindingResult) entry.getValue()).collect(Collectors.toList());
				bindingResults.forEach(bindingResult -> {
					if (!hasErrors.get()) {
						hasErrors.set(bindingResult.hasErrors());
					}
					listElement.addItem(bindingResult.getAllErrors().stream().map(objectError -> new ListElement.ListItem(objectError.getDefaultMessage()))
							.collect(Collectors.toList()).toArray(new ListElement.ListItem[0]));
				});

			}
		}
		if (hasErrors.get()) {
			listElement.addClass("error-message");
		}
		if (listElement.getItems().size() > 0) {
			elements.add(listElement);
			return; // don't bother checking for other messages...
		}
//		}

		// Check for generic errors
		if (variables.containsKey("status.message.error")) {
			ParagraphElement element = new ParagraphElement();
			element.addClass("status-message", "error-message");
			element.setContent(variables.get("status.message.success").toString());
			elements.add(element);
		}

		if (variables.containsKey("status.message.warning")) {
			ParagraphElement element = new ParagraphElement();
			element.addClass("status-message", "warning-message");
			element.setContent(variables.get("status.message.success").toString());
			elements.add(element);
		}

		if (variables.containsKey("status.message.info")) {
			ParagraphElement element = new ParagraphElement();
			element.addClass("status-message", "info-message");
			element.setContent(variables.get("status.message.success").toString());
			elements.add(element);
		}

		if (variables.containsKey("status.message.success")) {
			ParagraphElement element = new ParagraphElement();
			element.addClass("status-message", "success-message");
			element.setContent(variables.get("status.message.success").toString());
			elements.add(element);
		}
	}

	@Override
	public void buildSettingsForm(List<RenderedElement> elements, Form form, FormState state) {
		// No need for a configuration form here
	}

}
