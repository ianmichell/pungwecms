package com.pungwe.cms.core.field.services;

import com.pungwe.cms.core.annotations.stereotypes.FieldWidget;
import com.pungwe.cms.core.annotations.stereotypes.FieldType;
import com.pungwe.cms.core.module.services.ModuleManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ian on 25/03/2016.
 */
@Service
public class FieldTypeManagementService {

	@Autowired
	protected ModuleManagementService moduleManagementService;

	public List<FieldType> getAllFieldTypes() {
		return moduleManagementService.getModuleContext().getBeansWithAnnotation(FieldType.class).entrySet().stream().map(o -> {
			FieldType type = AnnotationUtils.findAnnotation(o.getValue().getClass(), FieldType.class);
			return type;
		}).collect(Collectors.toList());
	}

	public String getLabelForWidget(String name) {
		Object object = moduleManagementService.getModuleContext().getBean(name);
		FieldWidget fieldWidget = AnnotationUtils.findAnnotation(object.getClass(), FieldWidget.class);
		if (fieldWidget == null) {
			return null;
		}
		return fieldWidget.label();
	}

	public String getFieldTypeLabel(String name) {
		Object object = moduleManagementService.getModuleContext().getBean(name);
		FieldType fieldType = AnnotationUtils.getAnnotation(object.getClass(), FieldType.class);
		if (fieldType == null) {
			return null;
		}
		return fieldType.label();
	}
}
