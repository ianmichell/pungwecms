package com.pungwe.cms.core.annotations.stereotypes;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Ian Michell on 22/03/2016.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface EntityType {

    /**
     * Bean name for the entity type component. This should generally be
     * entity_[entity_name].
     *
     * @return the bean name for the EntityType.
     */
    @AliasFor(annotation = Component.class, attribute = "value")
    String value();

    /**
     * The entity type label.
     * @return the specified label
     */
    String label();

    /**
     * An administrative description of the related entity type.
     *
     * @return the specified description
     */
    String description() default "";
}
