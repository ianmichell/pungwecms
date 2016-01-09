package com.pungwe.cms.core.annotations;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to define a hook implementation. This can be applied directly to methods on any class. When a hook is being executed
 * methods with this annotation will be found on the class path (provided they are in modules that are enabled) and executed.
 *
 * Created by ian on 07/12/2015.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Autowired
public @interface Hook {
    String value();
}
