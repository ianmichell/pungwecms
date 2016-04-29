/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.rest;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.services.RenderedElementService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;
import java.util.Map;

public class RenderedElementConverter implements Converter<RenderedElement, Map> {

    public RenderedElementConverter() {
    }

    @Override
    public Map convert(RenderedElement value) {
        try {
            ModelAndView modelAndView = new RenderedElementService().convertToModelAndView(value);
            if (modelAndView == null) {
                return null;
            }
            Map map = new LinkedHashMap<>();
            map.putAll(modelAndView.getModel());
            map.put("#theme", modelAndView.getViewName());
            return map;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return typeFactory.constructType(RenderedElement.class);
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return typeFactory.constructType(Map.class);
    }
}
