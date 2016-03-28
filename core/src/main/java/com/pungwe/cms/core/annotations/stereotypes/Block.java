package com.pungwe.cms.core.annotations.stereotypes;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ian on 29/02/2016.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface Block {

    /**
     * Name of the block.
     *
     * @return the name of the block
     */
    @AliasFor(annotation = Component.class, attribute = "value") String value();

    /**
     * Block Label.
     *
     * @return the label of the block
     */
    String label();

    /**
     * The block category name.
     *
     * @return the name of the block category or an empty string
     * if not specified
     */
    String category() default "";
}
