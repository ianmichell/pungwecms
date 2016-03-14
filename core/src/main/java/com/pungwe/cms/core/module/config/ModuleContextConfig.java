package com.pungwe.cms.core.module.config;

import com.pungwe.cms.core.config.BaseApplicationConfig;
import com.pungwe.cms.core.system.interceptors.HtmlPageBuilderInterceptor;
import com.pungwe.cms.core.theme.PungweJtwigViewResolver;
import com.pungwe.cms.core.theme.functions.TemplateFunctions;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.*;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import javax.servlet.MultipartConfigElement;
import java.util.Arrays;

/**
 * Created by ian on 12/03/2016.
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
@ComponentScan(
		basePackages = {"com.pungwe.cms.core"},
		excludeFilters = @ComponentScan.Filter(value={Configuration.class, Service.class})
)
@EnableWebMvc
public class ModuleContextConfig extends WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private ServerProperties server;

	@Autowired
	private WebMvcProperties webMvcProperties;

	@Autowired(required = false)
	private MultipartConfigElement multipartConfig;

	@Autowired
	protected HtmlPageBuilderInterceptor htmlTemplateRenderingInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(htmlTemplateRenderingInterceptor);
	}

	@Bean
	public LocaleResolver localeResolver() {
		return new SessionLocaleResolver();
	}

	@Bean
	public DispatcherServlet dispatcherServlet() {
		return new DispatcherServlet();
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("/translation/messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean()
	public ContentNegotiatingViewResolver viewResolver(BeanFactory beanFactory) {
		ContentNegotiatingViewResolver contentNegotiatingViewResolver = new ContentNegotiatingViewResolver();
		contentNegotiatingViewResolver.setContentNegotiationManager(beanFactory.getBean(ContentNegotiationManager.class));
		contentNegotiatingViewResolver.setViewResolvers(Arrays.asList(pungweViewResolver()));
		contentNegotiatingViewResolver.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return contentNegotiatingViewResolver;
	}

	@Bean
	public ViewResolver pungweViewResolver() {
		PungweJtwigViewResolver resolver = new PungweJtwigViewResolver("classpath:templates/", ".twig");
		resolver.configuration().render().functionRepository().include(new TemplateFunctions(applicationContext, resolver, localeResolver()));
		return resolver;
	}
}
