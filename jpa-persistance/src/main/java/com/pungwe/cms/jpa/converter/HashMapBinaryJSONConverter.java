package com.pungwe.cms.jpa.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import org.springframework.core.convert.ConversionException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Converts a HashMap to and from binary JSON (smile format)
 * Created by ian on 06/12/2015.
 */
@Converter()
public class HashMapBinaryJSONConverter implements AttributeConverter<Map<?, ?>, byte[]> {

    /**
     * Converts a hashmap to a smile json byte array
     * @param attribute the hashmap to be converted
     * @return a byte array json representation of the hashmap
     */
    @Override
    public byte[] convertToDatabaseColumn(Map<?, ?> attribute) {
        SmileFactory factory = new SmileFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        try {
            return mapper.writeValueAsBytes(attribute);
        } catch (JsonProcessingException ex) {
            throw new ConversionFailedException(TypeDescriptor.forObject(attribute), TypeDescriptor.valueOf(byte[].class), "Could not convert value to json", ex);
        }
    }

    /**
     * Converts a byte array back into a hashmap
     * @param dbData the json byte array
     * @return a HashMap
     */
    @Override
    public Map<?, ?> convertToEntityAttribute(byte[] dbData) {
        SmileFactory factory = new SmileFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        try {
            return mapper.readValue(dbData, HashMap.class);
        } catch (IOException ex) {
            throw new ConversionFailedException(TypeDescriptor.forObject(dbData), TypeDescriptor.valueOf(HashMap.class), "Could not convert value to json", ex);
        }
    }
}
