/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.annotations.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface PermissionDefinition {

    String name();
    String label();
    String description() default "";
    String[] defaultRoles() default {};
    String category() default "";
}
