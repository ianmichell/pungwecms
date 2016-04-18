/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.utils.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.pungwe.cms.core.utils.Utils.*;

@Service
public class StatusMessageService {

    public static final String SUCCESS_MESSAGE_KEY = "status.message.success";
    public static final String WARNING_MESSAGE_KEY = "status.message.warning";
    public static final String ERROR_MESSAGE_KEY = "status.message.error";

    public void addSuccessStatusMessage(String message, Object... args) {
        Set<String> messages = new LinkedHashSet<>();
        String messageToAdd = translate(message, args);
        if (!containsFlashAttribute(SUCCESS_MESSAGE_KEY)) {
            messages.add(messageToAdd);
            setFlashAttribute(SUCCESS_MESSAGE_KEY, messages);
            return;
        }
        Object messagesFromMap = getFlashAttribute(SUCCESS_MESSAGE_KEY);
        if (messagesFromMap instanceof Collection) {
            messages.addAll((Collection) messagesFromMap);
        } else if (messagesFromMap != null) {
            messages.add(messagesFromMap.toString());
        }
        messages.add(messageToAdd);
        // Set the new attribute...
        setFlashAttribute(SUCCESS_MESSAGE_KEY, messages);
    }

    public void addErrorStatusMessage(String message, Object... args) {
        Set<String> messages = new LinkedHashSet<>();
        String messageToAdd = translate(message, args);
        if (!containsFlashAttribute(ERROR_MESSAGE_KEY)) {
            messages.add(messageToAdd);
            setFlashAttribute(ERROR_MESSAGE_KEY, messages);
            return;
        }
        Object messagesFromMap = getFlashAttribute(ERROR_MESSAGE_KEY);
        if (messagesFromMap instanceof Collection) {
            messages.addAll((Collection) messagesFromMap);
        } else if (messagesFromMap != null) {
            messages.add(messagesFromMap.toString());
        }
        messages.add(messageToAdd);
        // Set the new attribute...
        setFlashAttribute(ERROR_MESSAGE_KEY, messages);
    }

    public void addWarningStatusMessage(String message, Object... args) {
        Set<String> messages = new LinkedHashSet<>();
        String messageToAdd = translate(message, args);
        if (!containsFlashAttribute(WARNING_MESSAGE_KEY)) {
            messages.add(messageToAdd);
            setFlashAttribute(WARNING_MESSAGE_KEY, messages);
            return;
        }
        Object messagesFromMap = getFlashAttribute(WARNING_MESSAGE_KEY);
        if (messagesFromMap instanceof Collection) {
            messages.addAll((Collection) messagesFromMap);
        } else if (messagesFromMap != null) {
            messages.add(messagesFromMap.toString());
        }
        messages.add(messageToAdd);
        // Set the new attribute...
        setFlashAttribute(WARNING_MESSAGE_KEY, messages);
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
