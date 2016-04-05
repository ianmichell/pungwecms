package com.pungwe.cms.core.annotations.stereotypes;

import com.pungwe.cms.core.annotations.system.ModuleDependency;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ian on 07/12/2015.
 * <p>
 * Decorates the main module class. Used to define the main
 * entry point for a module.</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Configuration
@EnableWebMvc
public @interface Module {
    /**
     * <p>This is used for events loading and defining the config file.
     * Should not contain spaces.</p>
     *
     * <p>If it's left empty the class name will be used by default
     * (if it's called MyModule, the module name by default will
     * be my_module).</p>
     *
     * @return The machine name of the module
     */
    String name() default "";

    /**
     * The module label used for administration purposes.
     *
     * @return the label for the given module
     */
    String label() default "";

    /**
     * The administrative description of what the module does.
     *
     * @return the description for the module
     */
    String description() default "";

    /**
     * <p>An array of module dependencies. These dependencies are enabled when
     * this module is turned on. If they do not exist then they will
     * not be turned on.</p>
     *
     * @return an array of dependencies for this module
     */
    ModuleDependency[] dependencies() default {};
}
