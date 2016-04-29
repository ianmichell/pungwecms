package com.pungwe.cms.core.module.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pungwe.cms.core.block.config.BlockSystemConfig;
import com.pungwe.cms.core.element.services.RenderedElementService;
import com.pungwe.cms.core.field.config.FieldAPIConfig;
import com.pungwe.cms.core.form.processors.FormHandlerMappingPostProcessor;
import com.pungwe.cms.core.menu.controller.MenuAddController;
import com.pungwe.cms.core.menu.controller.MenuEditController;
import com.pungwe.cms.core.menu.controller.MenuLinksController;
import com.pungwe.cms.core.menu.controller.MenuListController;
import com.pungwe.cms.core.menu.services.MenuManagementService;
import com.pungwe.cms.core.module.controller.ModuleManagementController;
import com.pungwe.cms.core.security.config.SecurityConfig;
import com.pungwe.cms.core.security.controller.LoginController;
import com.pungwe.cms.core.security.controller.UserAdminstrationController;
import com.pungwe.cms.core.security.service.PermissionService;
import com.pungwe.cms.core.system.admin.AdminController;
import com.pungwe.cms.core.system.install.InstallController;
import com.pungwe.cms.core.system.interceptors.HtmlPageBuilderInterceptor;
import com.pungwe.cms.core.system.services.HtmlWrapperService;
import com.pungwe.cms.core.system.services.PageBuilderService;
import com.pungwe.cms.core.theme.PungweJtwigViewResolver;
import com.pungwe.cms.core.theme.controller.ThemeManagementController;
import com.pungwe.cms.core.theme.functions.TemplateFunctions;
import com.pungwe.cms.core.utils.services.StatusMessageService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.web.*;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

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
        SecurityConfig.class,
        // Server Properties / Property Source
        ServerPropertiesAutoConfiguration.class,
        PropertyPlaceholderAutoConfiguration.class,
        // Jackson
        JacksonAutoConfiguration.class,
        // Aop
        AopAutoConfiguration.class,
        // Caching
        CacheAutoConfiguration.class,
        // Fields
        FieldAPIConfig.class,
        // Blocks
        BlockSystemConfig.class,
})
@EnableWebMvc
@EnableCaching
public class ModuleContextConfig extends WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ServerProperties server;

    @Autowired
    private WebMvcProperties webMvcProperties;

    @Autowired(required = false)
    private MultipartConfigElement multipartConfig;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(htmlPageBuilderInterceptor());
    }

    @Bean
    public FormHandlerMappingPostProcessor formHandlerMappingPostProcessor() {
        return new FormHandlerMappingPostProcessor();
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new SessionLocaleResolver();
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("/translation/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public ContentNegotiatingViewResolver viewResolver(BeanFactory beanFactory) {
        ContentNegotiatingViewResolver contentNegotiatingViewResolver = new ContentNegotiatingViewResolver();
        contentNegotiatingViewResolver.setContentNegotiationManager(beanFactory.getBean(ContentNegotiationManager.class));
        contentNegotiatingViewResolver.setViewResolvers(Arrays.asList(pungweViewResolver()));
        contentNegotiatingViewResolver.setDefaultViews(Arrays.asList(mappingJackson2JsonView()));
        contentNegotiatingViewResolver.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return contentNegotiatingViewResolver;
    }

    @Bean
    public ObjectMapper jsonObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper;
    }

    @Bean
    public MappingJackson2JsonView mappingJackson2JsonView() {
        MappingJackson2JsonView mappingJackson2JsonView = new MappingJackson2JsonView(jsonObjectMapper());
        return mappingJackson2JsonView;
    }

    @Bean
    public ViewResolver pungweViewResolver() {
        PungweJtwigViewResolver resolver = new PungweJtwigViewResolver("classpath:templates/", ".twig");
        resolver.configuration().render().functionRepository().include(new TemplateFunctions(applicationContext, resolver, localeResolver()));
        return resolver;
    }

    //================================================
    // Services
    //================================================

    @Bean
    public HtmlWrapperService htmlWrapperService() {
        return new HtmlWrapperService();
    }

    @Bean
    public RenderedElementService renderedElementService() {
        return new RenderedElementService();
    }

    @Bean
    public MenuManagementService menuManagementService() {
        return new MenuManagementService();
    }

    @Bean
    public PageBuilderService pageBuilderService() {
        return new PageBuilderService();
    }

    @Bean
    public PermissionService permissionService() {
        return new PermissionService();
    }

    @Bean
    public StatusMessageService statusMessageService() {
        return new StatusMessageService();
    }

    //================================================
    // Interceptors
    //================================================

    @Bean
    public HtmlPageBuilderInterceptor htmlPageBuilderInterceptor() {
        return new HtmlPageBuilderInterceptor();
    }

    //================================================
    // Controllers
    //================================================

    @Bean
    public MenuAddController menuAddController() {
        return new MenuAddController();
    }

    @Bean
    public MenuEditController menuEditController() {
        return new MenuEditController();
    }

    @Bean
    public MenuLinksController menuLinksController() {
        return new MenuLinksController();
    }

    @Bean
    public MenuListController menuListController() {
        return new MenuListController();
    }

    @Bean
    public ModuleManagementController moduleManagementController() {
        return new ModuleManagementController();
    }

    @Bean
    public UserAdminstrationController userAdminstrationController() {
        return new UserAdminstrationController();
    }

    @Bean
    public AdminController adminController() {
        return new AdminController();
    }

    @Bean
    public ThemeManagementController themeManagementController() {
        return new ThemeManagementController();
    }

    @Bean
    public InstallController installController() {
        return new InstallController();
    }

    @Bean
    public LoginController loginController() {
        return new LoginController();
    }

}
