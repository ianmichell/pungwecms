package com.pungwe.cms.core.theme;

import com.lyncode.jtwig.cache.JtwigTemplateCacheSystem;
import com.lyncode.jtwig.cache.impl.PersistentTemplateCacheSystem;
import com.lyncode.jtwig.configuration.JtwigConfiguration;
import com.lyncode.jtwig.functions.SpringFunctions;
import com.lyncode.jtwig.functions.parameters.convert.DemultiplexerConverter;
import com.lyncode.jtwig.functions.parameters.convert.impl.ObjectToStringConverter;
import com.lyncode.jtwig.functions.parameters.input.InputParameters;
import com.lyncode.jtwig.functions.parameters.resolve.HttpRequestParameterResolver;
import com.lyncode.jtwig.functions.parameters.resolve.api.InputParameterResolverFactory;
import com.lyncode.jtwig.functions.parameters.resolve.api.ParameterResolver;
import com.lyncode.jtwig.functions.parameters.resolve.impl.CompoundParameterResolver;
import com.lyncode.jtwig.functions.parameters.resolve.impl.InputDelegateMethodParametersResolver;
import com.lyncode.jtwig.functions.parameters.resolve.impl.ParameterAnnotationParameterResolver;
import com.lyncode.jtwig.functions.resolver.api.FunctionResolver;
import com.lyncode.jtwig.functions.resolver.impl.CompoundFunctionResolver;
import com.lyncode.jtwig.functions.resolver.impl.DelegateFunctionResolver;
import com.lyncode.jtwig.resource.loader.JtwigResourceResolver;
import com.pungwe.cms.core.theme.cache.ThemeViewCache;
import com.pungwe.cms.core.theme.resolver.ThemeResourceResolver;
import com.pungwe.cms.core.theme.services.ThemeManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import javax.annotation.PostConstruct;

/**
 * Created by ian on 19/01/2016.
 */
public class PungweJtwigViewResolver extends AbstractTemplateViewResolver {

	private String encoding;
	private boolean cached = true;

	private JtwigResourceResolver loader;
	private JtwigConfiguration configuration = new JtwigConfiguration();
	private SpringFunctions springFunctions = null;
	private CompoundParameterResolver parameterResolver = new CompoundParameterResolver();
	private CompoundFunctionResolver functionResolver = new CompoundFunctionResolver()
			.withResolver(resolver(new DemultiplexerConverter()))
			.withResolver(resolver(new DemultiplexerConverter().withConverter(String.class, new ObjectToStringConverter())));
	private JtwigTemplateCacheSystem cache = new PersistentTemplateCacheSystem();

	// Autowired beans
	@Autowired
	ThemeManagementService themeManagementService;

	@Autowired(required = false)
	CacheManager cacheManager;

	public PungweJtwigViewResolver(String prefix, String suffix) {
		this();
		setPrefix(prefix);
		setSuffix(suffix);
	}

	public PungweJtwigViewResolver() {
		setViewClass(requiredViewClass());
		setContentType("text/html; charset=UTF-8");

		parameterResolver.withResolver(new HttpRequestParameterResolver());
	}

	@PostConstruct
	public void setupCache() {
		if (cached && cacheManager != null) {
			cache = new ThemeViewCache(cacheManager);
		}
	}

	@Override
	protected Class<?> requiredViewClass() {
		return PungweJtwigView.class;
	}

	@Override
	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		AbstractUrlBasedView abstractUrlBasedView = super.buildView(viewName);
		abstractUrlBasedView.setUrl(viewName);
		return abstractUrlBasedView;
	}

	@Deprecated // Remove in 4.0.0
	public PungweJtwigViewResolver setCached(boolean cached) {
		this.cached = cached;
		return this;
	}

	/**
	 * Do not use. Use JtwigConfiguration.render().renderThreadingConfig().maxThreads()
	 *
	 * @param value
	 * @return
	 */
	@Deprecated
	public PungweJtwigViewResolver setConcurrentMaxThreads(int value) {
		configuration.render().renderThreadingConfig().maxThreads(value);
		return this;
	}

	/**
	 * Do not use. Use JtwigConfiguration.render().renderThreadingConfig().minThreads()
	 *
	 * @param value
	 * @return
	 */
	@Deprecated
	public PungweJtwigViewResolver setConcurrentMinThreads(int value) {
		configuration.render().renderThreadingConfig().minThreads(value);
		return this;
	}

	public FunctionResolver functionResolver() {
		return functionResolver;
	}

	public PungweJtwigViewResolver setEncoding(String encoding) {
		this.encoding = encoding;
		return this;
	}

	public JtwigConfiguration configuration() {
		return configuration;
	}

	public PungweJtwigViewResolver setConfiguration(JtwigConfiguration configuration) {
		this.configuration = configuration;
		return this;
	}

	public PungweJtwigViewResolver include(ParameterResolver resolver) {
		parameterResolver.withResolver(resolver);
		return this;
	}

	public PungweJtwigViewResolver includeFunctions(Object functionBean) {
		configuration.render().functionRepository().include(functionBean);
		return this;
	}

	public PungweJtwigViewResolver setResourceLoader(JtwigResourceResolver resourceLoader) {
		this.loader = resourceLoader;
		return this;
	}

	public PungweJtwigViewResolver setCacheSystem(JtwigTemplateCacheSystem cache) {
		this.cache = cache;
		return this;
	}

	// Methods only accessible to JtwigView, no need to give them to the end user
	public JtwigResourceResolver resourceLoader() {
		if (loader == null) {
			setResourceLoader(new ThemeResourceResolver(themeManagementService, getServletContext(), getPrefix(), getSuffix()));
		}
		return loader;
	}

	public String getEncoding() {
		return encoding;
	}

	public boolean isCached() {
		return cached;
	}

	public JtwigTemplateCacheSystem cache() {
		return cache;
	}

	private InputParameterResolverFactory parameterResolverFactory(final DemultiplexerConverter converter) {
		return new InputParameterResolverFactory() {
			@Override
			public ParameterResolver create(InputParameters parameters) {
				return new CompoundParameterResolver()
						.withResolver(new ParameterAnnotationParameterResolver(parameters, converter))
						.withResolver(parameterResolver);
			}
		};
	}

	private DelegateFunctionResolver resolver(DemultiplexerConverter converter) {
		return new DelegateFunctionResolver(configuration.render().functionRepository(),
				new InputDelegateMethodParametersResolver(
						parameterResolverFactory(converter)));
	}
}
