package com.pungwe.cms.core.annotations.stereotypes;

import com.pungwe.cms.core.annotations.system.ModuleDependency;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ian on 07/12/2015.
 * <p>
 * Decorates the main module class. Used to define the main entry point for a module.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Configuration
public @interface Module {
	/**
	 * This is used for events loading and defining the config file. Should not contain spaces.
	 * <p>
	 * If it's left empty the class name will be used by default (if it's called MyModule, the module name by default will be my_module).
	 *
	 * @return The machine name of the module
	 */
	String name() default "";

	String label() default "";

	String description() default "";

	ModuleDependency[] dependencies() default {};
}
