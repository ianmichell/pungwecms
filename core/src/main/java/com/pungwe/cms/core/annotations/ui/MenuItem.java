package com.pungwe.cms.core.annotations.ui;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ian on 11/01/2016.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface MenuItem {

	String name();

	String title();

	String description() default "";

	String parent() default "";

	String route() default "";

	String menu();

	int weight() default 0;

    /**
     * Used for breadcrumb generation. This means that the menu item cannot be selected
     * @return
     */
    boolean pattern() default false;
}
