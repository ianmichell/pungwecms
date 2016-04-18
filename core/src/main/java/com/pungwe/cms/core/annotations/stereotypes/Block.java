package com.pungwe.cms.core.annotations.stereotypes;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Ian Michell on 29/02/2016.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface Block {

    /**
     * Name of the block. This will default to spring's default bean name.
     *
     * @return the name of the block
     */
    @AliasFor(annotation = Component.class, attribute = "value") String value() default "";

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

    /**
     * Dynamic block. This means that the bean can be retrieved, but it won't show
     * as an independent block, rather the Theme or Module needs to define it dynamically
     *
     * @return true if the block is dynamic
     */
    boolean dynamic() default false;
}
