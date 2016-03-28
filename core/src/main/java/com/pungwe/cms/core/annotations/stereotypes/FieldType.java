package com.pungwe.cms.core.annotations.stereotypes;

import com.pungwe.cms.core.field.FieldFormatterDefinition;
import com.pungwe.cms.core.field.FieldWidgetDefinition;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by ian on 10/01/2016.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
@Documented
public @interface FieldType {

	@AliasFor(annotation = Component.class, value="value")
	String value() default "";

	String label();

	String category();

	Class<? extends FieldWidgetDefinition> defaultWidget();

	Class<? extends FieldFormatterDefinition> defaultFormatter();
}
