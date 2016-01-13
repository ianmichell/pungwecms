package com.pungwe.cms.core.annotations;

import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by ian on 12/01/2016.
 */
public @interface RouteCallback {

    String name();
    String title() default "";
    String path();
    RequestMethod method() default RequestMethod.GET;
}
