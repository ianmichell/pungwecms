package com.pungwe.cms.core.utils.services;

import com.pungwe.cms.core.annotations.Hook;
import com.pungwe.cms.core.module.services.ModuleManagementService;
import com.pungwe.cms.core.theme.services.ThemeManagementService;
import com.pungwe.cms.core.utils.HookCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.type.StandardMethodMetadata;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * Created by ian on 01/02/2016.
 */
@Service
public class HookService {

	@Autowired
	ModuleManagementService moduleManagementService;

	@Autowired
	ThemeManagementService themeManagementService;

	public void executeHook(String name, Object... parameters) throws InvocationTargetException, IllegalAccessException {
		executeHook(null, name, parameters);
	}

	private Map<Method, Object> getHookMethods(ApplicationContext applicationContext, Class<?> clazz, String hook) {
		Map<Method, Object> methods = new HashMap<>();
		String[] beanNames = applicationContext.getBeanDefinitionNames();
		for (String beanName : beanNames) {
			Object o = applicationContext.getBean(beanName);
			Class<?> c = o.getClass();
			if (clazz != null && !clazz.isAssignableFrom(c)) {
				continue;
			}
			for (Method m : c.getMethods()) {
				m.setAccessible(true);
				if (m.isAnnotationPresent(Hook.class) && m.getAnnotation(Hook.class).value().equalsIgnoreCase(hook)) {
					methods.put(m, o);
				}
			}
		}
		return methods;
	}

	// FIXME: Add error handling
	public void executeHook(Class<?> c, String name, Object... parameters) throws InvocationTargetException, IllegalAccessException {
		executeHook(c, name, null, parameters);
	}

	public void executeHook(String name, HookCallback callback, Object... parameters) throws InvocationTargetException, IllegalAccessException {
		executeHook(null, name, callback, parameters);
	}

	public void executeHook(Class<?> c, String name, HookCallback callback, Object... parameters) throws InvocationTargetException, IllegalAccessException {
		Map<Method, Object> methods = new LinkedHashMap<>();

		if (moduleManagementService.getModuleContext() != null) {
			methods.putAll(getHookMethods(moduleManagementService.getModuleContext(), c, name));
		}

//      // FIXME: This needs to be sorted out to get the default theme and it's parents...
		ApplicationContext themeContext = themeManagementService.getDefaultThemeContext();
		if (themeContext != null) {
			ApplicationContext parentContext = themeContext;
			while ((parentContext = parentContext.getParent()) != null) {
				methods.putAll(getHookMethods(parentContext, c, name));
			}
			methods.putAll(getHookMethods(themeContext, c, name));
		}
		// Execute hooks
		for (Map.Entry<Method, Object> e : methods.entrySet()) {
			// we can't execute hooks when there are more expected parameters with less passed in...
			if (e.getKey().getParameterCount() > parameters.length) {
				continue;
			}
			Object result = executeHookMethod(e.getKey(), e.getValue(), parameters);
			if (callback != null) {
				callback.call(e.getValue().getClass(), result);
			}
		}
	}

	// TODO: Add annotations for parameters...
	private Object executeHookMethod(Method m, Object o, Object... parameters) throws InvocationTargetException, IllegalAccessException, IllegalArgumentException {
		// Create an array list with an initial size of parameters.length
		List<Object> usableParameters = new ArrayList<>(parameters.length);
		for (Object p : parameters) {
			for (Class<?> c : m.getParameterTypes()) {
				if (p != null && p.getClass() == c) {
					usableParameters.add(p);
				}
			}
		}
		if (usableParameters.size() < m.getParameterCount()) {
			throw new IllegalArgumentException("Wrong parameters types for hook: " + m.toGenericString());
		}
		return m.invoke(o, usableParameters.subList(0, m.getParameterCount()).toArray());
	}
}
