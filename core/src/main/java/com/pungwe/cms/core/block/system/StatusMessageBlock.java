package com.pungwe.cms.core.block.system;

import com.pungwe.cms.core.annotations.stereotypes.Block;
import com.pungwe.cms.core.annotations.ui.ThemeInfo;
import com.pungwe.cms.core.block.BlockDefinition;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.basic.DivElement;
import com.pungwe.cms.core.element.basic.ListElement;
import com.pungwe.cms.core.element.basic.TextFormatElement;
import com.pungwe.cms.core.element.basic.UnorderedListElement;
import com.pungwe.cms.core.element.model.ModelAndViewElement;
import com.pungwe.cms.core.utils.services.StatusMessageService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    StatusMessageService statusMessageService;

    @Override
    public Map<String, Object> getDefaultSettings() {
        return new HashMap<>();
    }

    @Override
    public void build(List<RenderedElement> elements, Map<String, Object> settings, Map<String, Object> variables) {
        if (variables == null) {
            return;
        }

        // Check for bind errors!
        AtomicBoolean hasErrors = new AtomicBoolean(false);
        UnorderedListElement listElement = new UnorderedListElement();
        listElement.addClass("status-message");
        // Get the content element...
        Object content = variables.getOrDefault("content", new LinkedHashSet<>());
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
        if (listElement.getItems().size() > 0) {
            listElement.addClass("error-message");
            elements.add(listElement);
            return; // don't bother checking for other messages...
        }

        // Check for generic errors
        if (statusMessageService.getErrorStatusMessages().size() > 0) {
            final DivElement message = new DivElement();
            message.addClass("status-message", "error-message");
            statusMessageService.getErrorStatusMessages().forEach(statusMessage -> {
                TextFormatElement element = new TextFormatElement(TextFormatElement.Type.P);
                element.setContent(statusMessage);
                message.addContent(element);
            });
            elements.add(message);
            return;
        }

        if (statusMessageService.getWarningStatusMessages().size() > 0) {
            final DivElement message = new DivElement();
            message.addClass("status-message", "warning-message");
            statusMessageService.getWarningStatusMessages().forEach(statusMessage -> {
                TextFormatElement element = new TextFormatElement(TextFormatElement.Type.P);
                element.setContent(statusMessage);
                message.addContent(element);
            });
            elements.add(message);
            return;
        }

        if (statusMessageService.getSuccessStatusMessages().size() > 0) {
            final DivElement message = new DivElement();
            message.addClass("status-message", "success-message");
            statusMessageService.getSuccessStatusMessages().forEach(statusMessage -> {
                TextFormatElement element = new TextFormatElement(TextFormatElement.Type.P);
                element.setContent(statusMessage);
                message.addContent(element);
            });
            elements.add(message);
            return;
        }
    }

    @Override
    public void buildSettingsForm(List<RenderedElement> elements, Map<String, Object> settings) {
        // No need for a configuration form here
    }

}
