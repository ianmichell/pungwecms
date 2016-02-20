package com.pungwe.cms.core.config;

//import org.springframework.boot.actuate.autoconfigure.*;

import com.pungwe.cms.core.theme.ThemeTemplateResolver;
import com.pungwe.cms.core.theme.functions.TemplateFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * Created by ian on 20/01/2016.
 */
@Configuration()
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
@EnableWebMvc
// We only want to scan the core package
@ComponentScan(basePackages = {"com.pungwe.cms.core"})
public class BaseApplicationConfig {

	@Bean
	public LocaleResolver localeResolver() {
		return new SessionLocaleResolver();
	}

	@Bean
	public ViewResolver viewResolver() {
		ThemeTemplateResolver resolver = new ThemeTemplateResolver();
		resolver.setPrefix("classpath:/templates/");
		resolver.setSuffix(".twig");
		resolver.configuration().render().functionRepository().include(new TemplateFunctions(resolver, localeResolver()));
		return resolver;
	}
}
