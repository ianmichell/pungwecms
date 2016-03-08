package com.pungwe.cms.core.annotations;

import org.springframework.context.annotation.Scope;
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
	@AliasFor(annotation = Component.class, attribute = "value")
	String value();
	String label();
	String category() default "";
}
