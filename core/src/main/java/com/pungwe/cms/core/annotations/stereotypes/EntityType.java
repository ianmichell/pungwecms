package com.pungwe.cms.core.annotations.stereotypes;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by 917903 on 22/03/2016.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface EntityType {
    @AliasFor(annotation = Component.class, attribute = "value")
    String value();
    String label();
    String description() default "";
}
