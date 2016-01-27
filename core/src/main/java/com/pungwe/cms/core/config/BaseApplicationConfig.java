package com.pungwe.cms.core.config;

//import org.springframework.boot.actuate.autoconfigure.*;

import org.springframework.boot.autoconfigure.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by ian on 20/01/2016.
 */
@Configuration
@Import({
		EmbeddedServletContainerAutoConfiguration.class,
		// Web MVC
		WebMvcAutoConfiguration.class,
		HttpMessageConvertersAutoConfiguration.class,
		ErrorMvcAutoConfiguration.class,
		DispatcherServletAutoConfiguration.class,
		// Security
		SecurityAutoConfiguration.class,
		// Server Properties / Property Source
		ServerPropertiesAutoConfiguration.class,
		PropertyPlaceholderAutoConfiguration.class,
		// Jackson
		JacksonAutoConfiguration.class,
		// Aop
		AopAutoConfiguration.class,
})
// We only want to scan the core package
@ComponentScan(basePackages = "com.pungwe.cms.core")
public class BaseApplicationConfig {

}
