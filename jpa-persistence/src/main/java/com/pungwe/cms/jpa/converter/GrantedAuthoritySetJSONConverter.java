/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.jpa.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Converter
public class GrantedAuthoritySetJSONConverter implements AttributeConverter<Set<SimpleGrantedAuthority>, byte[]> {

    protected final SmileFactory factory;
    protected final ObjectMapper mapper;

    public GrantedAuthoritySetJSONConverter() {
        factory = new SmileFactory();
        mapper = new ObjectMapper(factory);
    }

    @Override
    public byte[] convertToDatabaseColumn(Set<SimpleGrantedAuthority> attribute) {
        try {
            return mapper.writeValueAsBytes(attribute.stream().map(a -> a.getAuthority()).collect(Collectors.toSet()));
        } catch (JsonProcessingException ex) {
            throw new ConversionFailedException(TypeDescriptor.forObject(attribute),
                    TypeDescriptor.valueOf(LinkedHashSet.class), "Could not convert value to json", ex);
        }
    }

    @Override
    public Set<SimpleGrantedAuthority> convertToEntityAttribute(byte[] dbData) {
        try {
            Set<String> authorities = mapper.readValue(dbData, new TypeReference<LinkedHashSet<String>>() {});
            return authorities.stream().map(s -> new SimpleGrantedAuthority(s)).collect(Collectors.toSet());
        } catch (IOException ex) {
            throw new ConversionFailedException(TypeDescriptor.forObject(dbData), TypeDescriptor.valueOf(TreeSet.class),
                    "Could not convert value from json", ex);
        }
    }
}
