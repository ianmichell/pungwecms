/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.utils.services;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.pungwe.cms.core.utils.Utils.*;

@Service
public class StatusMessageService {

    public static final String SUCCESS_MESSAGE_KEY = "status.message.success";
    public static final String WARNING_MESSAGE_KEY = "status.message.warning";
    public static final String ERROR_MESSAGE_KEY = "status.message.error";

    public void addSuccessStatusMessage(RedirectAttributes redirectAttributes, String message, Object... args) {
        Set<String> messages = new LinkedHashSet<>();
        String messageToAdd = translate(message, args);
        if (!redirectAttributes.getFlashAttributes().containsKey(SUCCESS_MESSAGE_KEY)) {
            messages.add(messageToAdd);
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE_KEY, messages);
            return;
        }
        Object messagesFromMap = redirectAttributes.getFlashAttributes().get(SUCCESS_MESSAGE_KEY);
        if (messagesFromMap instanceof Collection) {
            messages.addAll((Collection) messagesFromMap);
        } else if (messagesFromMap != null) {
            messages.add(messagesFromMap.toString());
        }
        messages.add(messageToAdd);
        // Set the new attribute...
        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE_KEY, messages);
    }

    public void addErrorStatusMessage(RedirectAttributes redirectAttributes, String message, Object... args) {
        Set<String> messages = new LinkedHashSet<>();
        String messageToAdd = translate(message, args);
        if (!redirectAttributes.getFlashAttributes().containsKey(ERROR_MESSAGE_KEY)) {
            messages.add(messageToAdd);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, messages);
            return;
        }
        Object messagesFromMap = redirectAttributes.getFlashAttributes().get(ERROR_MESSAGE_KEY);
        if (messagesFromMap instanceof Collection) {
            messages.addAll((Collection) messagesFromMap);
        } else if (messagesFromMap != null) {
            messages.add(messagesFromMap.toString());
        }
        messages.add(messageToAdd);
        // Set the new attribute...
        redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, messages);
    }

    public void addWarningStatusMessage(RedirectAttributes redirectAttributes, String message, Object... args) {
        Set<String> messages = new LinkedHashSet<>();
        String messageToAdd = translate(message, args);
        if (!redirectAttributes.getFlashAttributes().containsKey(WARNING_MESSAGE_KEY)) {
            messages.add(messageToAdd);
            redirectAttributes.addFlashAttribute(WARNING_MESSAGE_KEY, messages);
            return;
        }
        Object messagesFromMap = redirectAttributes.getFlashAttributes().get(WARNING_MESSAGE_KEY);
        if (messagesFromMap instanceof Collection) {
            messages.addAll((Collection) messagesFromMap);
        } else if (messagesFromMap != null) {
            messages.add(messagesFromMap.toString());
        }
        messages.add(messageToAdd);
        // Set the new attribute...
        redirectAttributes.addFlashAttribute(WARNING_MESSAGE_KEY, messages);
    }

    public Set<String> getSuccessStatusMessages() {
        if (!containsFlashAttribute(SUCCESS_MESSAGE_KEY)) {
            return new LinkedHashSet<>();
        }
        Object o = getFlashAttribute(SUCCESS_MESSAGE_KEY);
        if (o != null && Collection.class.isAssignableFrom(o.getClass())) {
            Set<String> messages = new LinkedHashSet<>();
            messages.addAll((Collection)o);
            return messages;
        } else if (o != null) {
            Set<String> messages = new LinkedHashSet<>();
            messages.add(o.toString());
            return messages;
        }
        return new LinkedHashSet<>();
    }

    public Set<String> getWarningStatusMessages() {
        if (!containsFlashAttribute(WARNING_MESSAGE_KEY)) {
            return new LinkedHashSet<>();
        }
        Object o = getFlashAttribute(WARNING_MESSAGE_KEY);
        if (o != null && Collection.class.isAssignableFrom(o.getClass())) {
            Set<String> messages = new LinkedHashSet<>();
            messages.addAll((Collection)o);
            return messages;
        } else if (o != null) {
            Set<String> messages = new LinkedHashSet<>();
            messages.add(o.toString());
            return messages;
        }
        return new LinkedHashSet<>();
    }

    public Set<String> getErrorStatusMessages() {
        if (!containsFlashAttribute(ERROR_MESSAGE_KEY)) {
            return new LinkedHashSet<>();
        }
        Object o = getFlashAttribute(ERROR_MESSAGE_KEY);
        if (o != null && Collection.class.isAssignableFrom(o.getClass())) {
            Set<String> messages = new LinkedHashSet<>();
            messages.addAll((Collection)o);
            return messages;
        } else if (o != null) {
            Set<String> messages = new LinkedHashSet<>();
            messages.add(o.toString());
            return messages;
        }
        return new LinkedHashSet<>();
    }

}
