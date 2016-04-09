/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.annotations.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Roles {
    RoleDefinition[] value();
}
