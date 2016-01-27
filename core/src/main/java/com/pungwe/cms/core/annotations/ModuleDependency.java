package com.pungwe.cms.core.annotations;

/**
 * Created by ian on 21/01/2016.
 */
public @interface ModuleDependency {
	String value();
	String version() default "";
}
