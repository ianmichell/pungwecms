package com.pungwe.cms.core.module.config;

import com.pungwe.cms.core.annotations.security.PermissionDefinition;
import com.pungwe.cms.core.annotations.security.Permissions;
import com.pungwe.cms.core.annotations.security.RoleDefinition;
import com.pungwe.cms.core.annotations.security.Roles;
import com.pungwe.cms.core.block.builder.BlockPageBuilder;
import com.pungwe.cms.core.block.controller.BlockLayoutController;
import com.pungwe.cms.core.block.controller.BlockSettingsController;
import com.pungwe.cms.core.block.services.BlockManagementService;
import com.pungwe.cms.core.block.system.*;
import com.pungwe.cms.core.element.services.RenderedElementService;
import com.pungwe.cms.core.field.services.FieldTypeManagementService;
import com.pungwe.cms.core.field.types.*;
import com.pungwe.cms.core.field.widgets.*;
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
import com.pungwe.cms.core.security.field.widget.UserDetailsWidget;
import com.pungwe.cms.core.security.service.PermissionService;
import com.pungwe.cms.core.system.admin.AdminController;
import com.pungwe.cms.core.system.install.InstallController;
import com.pungwe.cms.core.system.interceptors.HtmlPageBuilderInterceptor;
import com.pungwe.cms.core.system.services.HtmlWrapperService;
import com.pungwe.cms.core.system.services.PageBuilderService;
import com.pungwe.cms.core.theme.PungweJtwigViewResolver;
import com.pungwe.cms.core.theme.controller.ThemeManagementController;
import com.pungwe.cms.core.theme.functions.TemplateFunctions;
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
        contentNegotiatingViewResolver.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return contentNegotiatingViewResolver;
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
    public FieldTypeManagementService fieldTypeManagementService() {
        return new FieldTypeManagementService();
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
    public BlockManagementService blockManagementService() {
        return new BlockManagementService();
    }

    @Bean
    public PermissionService permissionService() {
        return new PermissionService();
    }

    //================================================
    // Components
    //================================================

    @Bean
    public BlockPageBuilder blockPageBuilder() {
        return new BlockPageBuilder();
    }

    @Bean
    public HtmlPageBuilderInterceptor htmlPageBuilderInterceptor() {
        return new HtmlPageBuilderInterceptor();
    }

    //================================================
    // Controllers
    //================================================

    @Bean
    public BlockLayoutController blockLayoutController() {
        return new BlockLayoutController();
    }

    @Bean
    public BlockSettingsController blockSettingsController() {
        return new BlockSettingsController();
    }

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

    //================================================
    // Blocks
    //================================================
    @Bean(name = "breadcrumb_block")
    public BreadcrumbBlock breadcrumbBlock() {
        return new BreadcrumbBlock();
    }

    @Bean(name = "main_content_block")
    public MainContentBlock mainContentBlock() {
        return new MainContentBlock();
    }

    @Bean(name = "page_title_block")
    public PageTitleBlock pageTitleBlock() {
        return new PageTitleBlock();
    }

    @Bean(name = "primary_menu_block")
    public PrimaryMenuBlock primaryMenuBlock() {
        return new PrimaryMenuBlock();
    }

    @Bean(name = "secondary_menu_block")
    public SecondaryMenuBlock secondaryMenuBlock() {
        return new SecondaryMenuBlock();
    }

    @Bean(name = "status_message_block")
    public StatusMessageBlock statusMessageBlock() {
        return new StatusMessageBlock();
    }

    @Bean(name = "system_tasks_block")
    public SystemTasksBlock systemTasksBlock() {
        return new SystemTasksBlock();
    }

    //================================================
    // Field Types
    //================================================

    @Bean(name = "boolean_field")
    public BooleanFieldType booleanFieldType() {
        return new BooleanFieldType();
    }

    @Bean(name = "decimal_field")
    public DecimalFieldType decimalFieldType() {
        return new DecimalFieldType();
    }

    @Bean(name = "email_field")
    public EmailFieldType emailFieldType() {
        return new EmailFieldType();
    }

    @Bean(name = "numeric_field")
    public NumericFieldType numericFieldType() {
        return new NumericFieldType();
    }

    @Bean(name = "string_field")
    public StringFieldType stringFieldType() {
        return new StringFieldType();
    }

    //================================================
    // Field Widgets
    //================================================

    @Bean(name = "boolean_widget")
    public BooleanWidget booleanWidget() {
        return new BooleanWidget();
    }

    @Bean(name = "decimal_widget")
    public DecimalWidget decimalWidget() {
        return new DecimalWidget();
    }

    @Bean(name = "email_widget")
    public EmailWidget emailWidget() {
        return new EmailWidget();
    }

    @Bean(name = "numeric_widget")
    public NumericWidget numericWidget() {
        return new NumericWidget();
    }

    @Bean(name = "textarea_widget")
    public TextareaWidget textareaWidget() {
        return new TextareaWidget();
    }

    @Bean(name = "textfield_widget")
    public TextfieldWidget textfieldWidget() {
        return new TextfieldWidget();
    }

    @Bean(name = "user_details_widget")
    public UserDetailsWidget userDetailsWidget() {
        return new UserDetailsWidget();
    }

    //================================================
    // Field Formatters
    //================================================

}
