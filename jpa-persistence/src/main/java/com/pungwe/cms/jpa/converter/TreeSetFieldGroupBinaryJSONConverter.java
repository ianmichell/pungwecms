package com.pungwe.cms.jpa.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.entity.FieldGroupConfig;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;

import javax.persistence.Converter;
import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by ian on 24/03/2016.
 */
@Converter
public class TreeSetFieldGroupBinaryJSONConverter extends TreeSetBinaryJSONConverter {

	@Override
	public SortedSet<?> convertToEntityAttribute(byte[] dbData) {
		try {
			return mapper.readValue(dbData, new TypeReference<TreeSet<FieldGroupConfig>>() {});
		} catch (IOException ex) {
			throw new ConversionFailedException(TypeDescriptor.forObject(dbData), TypeDescriptor.valueOf(TreeSet.class), "Could not convert value to json", ex);
		}
	}
}
