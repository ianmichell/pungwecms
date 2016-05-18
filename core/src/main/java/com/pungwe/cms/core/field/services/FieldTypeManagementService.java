package com.pungwe.cms.core.field.services;

import com.pungwe.cms.core.annotations.stereotypes.FieldFormatter;
import com.pungwe.cms.core.annotations.stereotypes.FieldWidget;
import com.pungwe.cms.core.annotations.stereotypes.FieldType;
import com.pungwe.cms.core.field.FieldWidgetDefinition;
import com.pungwe.cms.core.module.services.ModuleManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;
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

	public FieldWidgetDefinition<?> getWidgetDefinition(String widgetName) {
		FieldWidgetDefinition<?> widgetDefinition = moduleManagementService.getModuleContext().getBean(widgetName, FieldWidgetDefinition.class);
		return widgetDefinition;
	}


    public FieldType getFieldType(String fieldType) {
        Optional<FieldType> result = getAllFieldTypes().stream().filter(f -> f.value().equals(fieldType)).findFirst();
        return result.orElse(null);
    }

    public String getFieldWidgetName(FieldType fieldType) {
        Class<?> widgetClass = fieldType.defaultWidget();
        FieldWidget fieldWidget = AnnotationUtils.findAnnotation(widgetClass, FieldWidget.class);
        if (fieldWidget == null) {
            return null;
        }
        return fieldWidget.value();
    }

    public String getFieldFormatterName(FieldType fieldType) {
        Class<?> widgetClass = fieldType.defaultWidget();
        FieldFormatter fieldFormatter = AnnotationUtils.findAnnotation(widgetClass, FieldFormatter.class);
        if (fieldFormatter == null) {
            return null;
        }
        return fieldFormatter.value();
    }
}
