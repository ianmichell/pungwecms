/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.jpa.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

@Converter
public class LinkedHashBinaryJSONConverter implements AttributeConverter<Set<?>, byte[]> {

    protected SmileFactory factory;
    protected ObjectMapper mapper;

    public LinkedHashBinaryJSONConverter() {
        factory = new SmileFactory();
        mapper = new ObjectMapper(factory);
    }

    @Override
    public byte[] convertToDatabaseColumn(Set<?> attribute) {
        try {
            return mapper.writeValueAsBytes(attribute);
        } catch (JsonProcessingException ex) {
            throw new ConversionFailedException(TypeDescriptor.forObject(attribute), TypeDescriptor.valueOf(byte[].class), "Could not convert value to json", ex);
        }
    }

    @Override
    public Set<?> convertToEntityAttribute(byte[] dbData) {
        try {
            return mapper.readValue(dbData, LinkedHashSet.class);
        } catch (IOException ex) {
            throw new ConversionFailedException(TypeDescriptor.forObject(dbData), TypeDescriptor.valueOf(Set.class), "Could not convert value to json", ex);
        }
    }
}
