package com.pungwe.cms.core.theme.services;

import com.pungwe.cms.core.annotations.stereotypes.Theme;
import com.pungwe.cms.core.annotations.system.ModuleDependency;
import com.pungwe.cms.core.module.services.ModuleManagementService;
import com.pungwe.cms.core.system.element.templates.PageElement;
import com.pungwe.cms.core.theme.ThemeConfig;
import com.pungwe.cms.core.utils.services.HookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static com.lyncode.jtwig.util.LocalThreadHolder.getServletRequest;

/**
 * Created by ian on 29/01/2016.
 */
@Service
public class ThemeManagementService {

    private static final Logger LOG = LoggerFactory.getLogger(ThemeManagementService.class);

    @Autowired
    private ThemeConfigService<? extends ThemeConfig> themeConfigService;

    @Autowired
    private HookService hookService;

    @Autowired
    private ModuleManagementService moduleManagementService;

    @Autowired
    private ApplicationContext rootContext;

    private Map<String, AnnotationConfigApplicationContext> themeContexts = new TreeMap<>();

    public ApplicationContext getThemeContext(String name) {
        return themeContexts.get(name);
    }

    public boolean enable(String theme) {

        if (StringUtils.isEmpty(theme)) {
            return false;
        }
        // Fetch the theme config
        ThemeConfig config = themeConfigService.getTheme(theme);
        try {
            Class<?> c = Class.forName(config.getEntryPoint());
            Theme info = c.getAnnotation(Theme.class);
            String parent = info.parent();
            if (!StringUtils.isEmpty(parent) && !themeConfigService.isEnabled(parent) && !enable(parent)) {
                LOG.error("Could not enabled parent theme: " + parent + " for theme: " + theme);
                return false;
            }
            // Enable dependencies
            moduleManagementService.enable(Arrays.asList(info.dependencies()).stream().map(moduleDependency -> {
                return moduleDependency.value();
            }).collect(Collectors.toList()));
            themeConfigService.setThemeEnabled(theme, true);
            return true;
        } catch (ClassNotFoundException ex) {
            LOG.error("Could not enable theme: " + theme, ex);
            themeConfigService.removeThemes(theme);
            return false;
        }
    }

    public void setDefaultTheme(String theme) {
        themeConfigService.setDefaultTheme(theme);
    }

    public void setDefaultAdminTheme(String theme) {
        themeConfigService.setDefaultAdminTheme(theme);
    }

    public boolean disable(String theme) {
        themeConfigService.setThemeEnabled(theme, false);
        AnnotationConfigApplicationContext ctx = themeContexts.remove(theme);
        ctx.close();
        return ctx.isActive();
    }

    public void startEnabledThemes() {

        removeMissingThemes();

        // Get a list of enabled themes
        Set<ThemeConfig> enabled = (Set<ThemeConfig>) themeConfigService.listEnabledThemes();

        // Create application contexts for the enabled themes. This is different from the module
        // context, whereby the modules share a single application context, themes need to be isolated
        // from each other in order to function correctly...
        enabled.stream().sorted((t1, t2) -> {
            try {
                // First theme class
                Class<?> c1 = Class.forName(t1.getEntryPoint());
                Theme i1 = c1.getAnnotation(Theme.class);
                // Second theme class
                Class<?> c2 = Class.forName(t2.getEntryPoint());
                Theme i2 = c2.getAnnotation(Theme.class);

                // If t1 parent is blank and t2 is not, then t1 should be before t2.
                if (StringUtils.isEmpty(i1.parent()) && !StringUtils.isEmpty(i2.parent())) {
                    return -1;
                    // If t1 has a parent and t2 does not, then it should be after t2
                } else if (!StringUtils.isEmpty(i1.parent()) && StringUtils.isEmpty(i2.parent())) {
                    return 1;
                }
                // Check if t1 is the parent of t2. If it is, then t1 should be first
                if (i1.name().equalsIgnoreCase(i2.parent())) {
                    return -1;
                    // otherwise t2 should be first if it's the parent of t1
                } else if (i2.name().equalsIgnoreCase(i1.parent())) {
                    return 1;
                    // Ensure that there is not a circular reference
                } else if (i1.name().equalsIgnoreCase(i2.parent()) && i2.name().equalsIgnoreCase(i1.parent())) {
                    throw new IllegalArgumentException("Circular reference in theme parents");
                }
                // Just sort by name by default... If none of the above, then sort by name...
                return t1.getName().compareTo(t2.getName());
            } catch (ClassNotFoundException ex) {
                return -1;
            }
        }).forEachOrdered(theme -> {
            try {
                Class<?> c = Class.forName(theme.getEntryPoint());

                // Check for an existing application context
                AnnotationConfigApplicationContext ctx = (AnnotationConfigApplicationContext) getThemeContext(theme.getName());
                if (ctx != null && ctx.isActive()) {
                    ctx.close();
                }

                // Create a new application context for the theme
                ctx = new AnnotationConfigApplicationContext();

                // Fetch the theme info
                Theme themeInfo = c.getAnnotation(Theme.class);
                ctx.setId("theme-application-context-" + themeInfo.name());

                // Find the parent application context for the theme and set it
                ApplicationContext parent = getThemeContext(themeInfo.parent());
                ctx.setParent(parent == null ? moduleManagementService.getModuleContext() : parent);

                // Register the theme entry point class
                ctx.register(c);

                // Refresh the context
                ctx.refresh();

                // Overwrite the existing theme application context
                themeContexts.put(theme.getName(), ctx);

                // Execute hook install - the theme should be installed.
                if (!theme.isInstalled()) {
                    hookService.executeHook(ctx, c, "install");
                    themeConfigService.setInstalled(theme.getName(), true);
                }

            } catch (ClassNotFoundException ex) {
                LOG.error("Could not start theme: " + theme.getName(), ex);
            } catch (IllegalAccessException ex) {
                LOG.error("Could not install theme: " + theme.getName(), ex);
            } catch (InvocationTargetException ex) {
                LOG.error("Could not install theme: " + theme.getName(), ex);
            }
        });

    }

    public void scan() {

        // Remove the themes missing from the classpath
        removeMissingThemes();

        String defaultTheme = rootContext.getEnvironment().getProperty("themes.default", "");
        String defaultAdminTheme = rootContext.getEnvironment().getProperty("themes.defaultAdmin", defaultTheme);

        ThemeConfig defaultThemeConfig = themeConfigService.getDefaultTheme();
        ThemeConfig defaultAdminThemeConfig = themeConfigService.getDefaultAdminTheme();

        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Theme.class));
        Set<BeanDefinition> modules = scanner.findCandidateComponents("*");
        modules.forEach(b -> {
            try {
                Class c = Class.forName(b.getBeanClassName());
                themeConfigService.registerTheme(c, c.getProtectionDomain().getCodeSource().getLocation());
            } catch (ClassNotFoundException e) {
                LOG.error("Could not load a module found on the class path, due to it's class not being found. This should never happen and usually means something is wrong with the environment", e);
            }
        });


        if ((defaultThemeConfig == null || !themeClassExists(defaultThemeConfig))) {
            enable(defaultTheme);
            setDefaultTheme(defaultTheme);
        }

        if ((defaultAdminThemeConfig == null || !themeClassExists(defaultAdminThemeConfig))) {
            enable(defaultAdminTheme);
            setDefaultAdminTheme(defaultAdminTheme);
        }
    }

    private boolean themeClassExists(ThemeConfig config) {
        try {
            // Just running this will check that the class exists
            Class.forName(config.getEntryPoint());
            return true;
        } catch (ClassNotFoundException ex) {
            return false;
        }
    }

    public ApplicationContext getDefaultThemeContext() {
        HttpServletRequest request = getServletRequest();
        String currentPath = request.getRequestURI().substring(request.getContextPath().length());
        // If the current path starts with /admin, then load the admin theme.
        ThemeConfig themeConfig = null;
        if (currentPath.startsWith("/admin")) {
            themeConfig = themeConfigService.getDefaultAdminTheme();
        } else {
            themeConfig = themeConfigService.getDefaultTheme();
        }

        if (themeConfig == null) {
            return null;
        }
        return getThemeContext(themeConfig.getName());
    }

    protected void removeMissingThemes() {
        Set<String> missing = themeConfigService.listAllThemes().stream().filter(t -> {
            try {
                Class<?> c = Class.forName(t.getEntryPoint());
                return !c.isAnnotationPresent(Theme.class);
            } catch (Exception ex) {
                return true;
            }
        }).map(t -> t.getName()).collect(Collectors.toSet());
        themeConfigService.removeThemes(missing);
    }

    public List<String> resolveViewPath(HttpServletRequest request, final String prefix, final String viewName, final String suffix) {
        List<String> urls = new ArrayList<>();
        urls.add(prefix + viewName + suffix);
        urls.addAll(getThemeTemplateURLSearchPath(prefix.replace(ResourceUtils.CLASSPATH_URL_PREFIX, ""), viewName, suffix));
        // Get the request path... We use a substring of this excluding the context path and the rest of the url to determine if it's admin or not.
        try {
            hookService.executeHook("theme", (c, o) -> {
                if (o instanceof Map && ((Map) o).containsKey(viewName)) {
                    URL hookLocation = c.getProtectionDomain().getCodeSource().getLocation();
                    String prefixPath = prefix.replace(ResourceUtils.CLASSPATH_URL_PREFIX, "");

                    if (ResourceUtils.isJarFileURL(hookLocation)) {
                        String url = hookLocation.toExternalForm() + ResourceUtils.JAR_URL_SEPARATOR + prefixPath + ((Map) o).get(viewName) + suffix;
                        if (!urls.contains(url)) {
                            urls.add(url);
                        }
                        // Should default to standard prefix + file
                    } else {
                        String url = prefix + ((Map) o).get(viewName) + suffix;
                        if (!urls.contains(url)) {
                            urls.add(url);
                        }
                    }
                }
            });
            // FIXME: Add custom exception here
            // Shouldn't ever happen... But you never know
        } catch (InvocationTargetException e) {
            LOG.error("Could not execute hook theme", e);
        } catch (IllegalAccessException e) {
            LOG.error("Could not execute hook theme", e);
        }
        // Reverse the collection
        Collections.reverse(urls);

        return urls;
    }

    private List<String> getThemeTemplateURLSearchPath(String prefix, String viewName, String suffix) {
        ThemeConfig config = getDefaultThemeConfigForRequest();
        if (config == null) {
            return new LinkedList<>();
        }
        URL url = null;
        try {
            if (ResourceUtils.isJarFileURL(new URL(config.getThemeLocation()))) {
                url = new URL(config.getThemeLocation() + ResourceUtils.JAR_URL_SEPARATOR + prefix.replaceAll("^/", "").replaceAll("/$", "") + "/" + config.getName() + "/" + viewName + suffix);
            } else {
                url = new URL(config.getThemeLocation() + "/" + prefix.replaceAll("^/", "").replaceAll("/$", "") + "/" + config.getName() + "/" + viewName + suffix);
            }
        } catch (MalformedURLException ex) {
            // do nothing
        }
        List<String> themePaths = new ArrayList<>(1);
        if (url != null) {
            themePaths.add(url.toExternalForm());
        }
        return themePaths;
    }

    protected ThemeConfig getDefaultThemeConfigForRequest() {
        // FIXME: Move to a method as this is done more than once...
        HttpServletRequest request = getServletRequest();
        String currentPath = request.getRequestURI().substring(request.getContextPath().length());
        // Fetch the default theme config
        ThemeConfig themeConfig = null;
        if (currentPath.startsWith("/admin")) {
            themeConfig = themeConfigService.getDefaultAdminTheme();
        } else {
            themeConfig = themeConfigService.getDefaultTheme();
        }
        return themeConfig;
    }

    public Map<String, String> getRegionsForDefaultThemeByRequest() {
        ThemeConfig themeConfig = getDefaultThemeConfigForRequest();
        return getThemeRegions(themeConfig);
    }

    public Map<String, String> getThemeRegions(String theme) {
        ThemeConfig themeConfig = themeConfigService.getTheme(theme);
        return getThemeRegions(themeConfig);
    }

    /**
     * Returns a list of the regions for the default theme, independent of current request.
     *
     * @return
     */
    public Map<String, String> getRegionsForDefaultTheme() {
        ThemeConfig config = themeConfigService.getDefaultTheme();
        return getThemeRegions(config);
    }

    protected Map<String, String> getThemeRegions(ThemeConfig themeConfig) {
        if (themeConfig != null) {
            try {
                Class<?> clazz = Class.forName(themeConfig.getEntryPoint());
                final Map<String, String> regions = new LinkedHashMap<>();
                Arrays.asList(clazz.getAnnotation(Theme.class).regions()).forEach(themeRegion -> {
                    regions.put(themeRegion.name(), themeRegion.label());
                });
                if (regions.isEmpty()) {
                    regions.putAll(PageElement.DEFAULT_REGIONS);
                }
                return regions;
            } catch (ClassNotFoundException e) {
                LOG.warn("Could not find default theme class!");
            }
        }
        return PageElement.DEFAULT_REGIONS;
    }

    public String getDefaultThemeName() {
        ThemeConfig config = themeConfigService.getDefaultTheme();
        if (config != null) {
            return config.getName();
        }
        return null;
    }

    public String getCurrentThemeNameForRequest() {
        ThemeConfig config = getDefaultThemeConfigForRequest();
        if (config == null) {
            return null;
        }
        return config.getName();
    }

    public List<ApplicationContext> getThemeContextsAsList() {
        if (this.themeContexts == null) {
            return new LinkedList<>();
        }
        return themeContexts.values().stream().collect(Collectors.toList());
    }
}
