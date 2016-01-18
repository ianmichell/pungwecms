package com.pungwe.cms.jpa.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by ian on 06/12/2015.
 */
@Converter
public class TreeSetBinaryJSONConverter implements AttributeConverter<SortedSet<?>, byte[]> {

	@Override
	public byte[] convertToDatabaseColumn(SortedSet<?> attribute) {
		SmileFactory factory = new SmileFactory();
		ObjectMapper mapper = new ObjectMapper(factory);
		try {
			return mapper.writeValueAsBytes(attribute);
		} catch (JsonProcessingException ex) {
			throw new ConversionFailedException(TypeDescriptor.forObject(attribute), TypeDescriptor.valueOf(byte[].class), "Could not convert value to json", ex);
		}
	}

	@Override
	public SortedSet<?> convertToEntityAttribute(byte[] dbData) {
		SmileFactory factory = new SmileFactory();
		ObjectMapper mapper = new ObjectMapper(factory);
		try {
			return mapper.readValue(dbData, TreeSet.class);
		} catch (IOException ex) {
			throw new ConversionFailedException(TypeDescriptor.forObject(dbData), TypeDescriptor.valueOf(TreeSet.class), "Could not convert value to json", ex);
		}
	}
}
