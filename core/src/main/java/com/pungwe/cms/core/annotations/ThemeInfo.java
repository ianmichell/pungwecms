package com.pungwe.cms.core.annotations;

/**
 * Created by ian on 12/01/2016.
 */
public @interface ThemeInfo {
    String value();
    String template() default "";
    String[] parameters() default {};
}
