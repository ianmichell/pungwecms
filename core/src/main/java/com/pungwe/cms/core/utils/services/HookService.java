package com.pungwe.cms.core.utils.services;

import com.pungwe.cms.core.annotations.util.Hook;
import com.pungwe.cms.core.module.services.ModuleManagementService;
import com.pungwe.cms.core.theme.services.ThemeManagementService;
import com.pungwe.cms.core.utils.HookCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by ian on 01/02/2016.
 */
@Service
public class HookService {

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	ModuleManagementService moduleManagementService;

	@Autowired
	ThemeManagementService themeManagementService;

	public void executeHook(String name, Object... parameters) throws InvocationTargetException, IllegalAccessException {
		executeHook(null, name, parameters);
	}

	// FIXME: Add error handling
	public void executeHook(Class<?> c, String name, Object... parameters) throws InvocationTargetException, IllegalAccessException {
		executeHook(c, name, null, parameters);
	}

	public void executeHook(String name, HookCallback callback, Object... parameters) throws InvocationTargetException, IllegalAccessException {
		executeHook(null, name, callback, parameters);
	}

	public void executeHook(Class<?> c, String name, HookCallback callback, Object... parameters) throws InvocationTargetException, IllegalAccessException {

		if (moduleManagementService.getModuleContext() != null) {
			executeHook(moduleManagementService.getModuleContext(), c, name, callback, parameters);
		}

		ApplicationContext themeContext = themeManagementService.getDefaultThemeContext();
		if (themeContext != null) {
			ApplicationContext parentContext = themeContext;
			while ((parentContext = parentContext.getParent()) != null) {
				executeHook(parentContext, c, name, callback, parameters);
			}
			executeHook(themeContext, c, name, callback, parameters);
		}
//      // FIXME: This needs to be sorted out to get the default theme and it's parents...

	}

	public void executeHook(ApplicationContext context, Class<?> c, String name, Object... parameters) throws InvocationTargetException, IllegalAccessException {
		executeHook(context, c, name, null, parameters);
	}

	public void executeHook(ApplicationContext context, Class<?> c, String name, HookCallback callback, Object... parameters) throws InvocationTargetException, IllegalAccessException {

		Map<Method, Object> methods = new LinkedHashMap<>();

		if (context != null) {
			methods.putAll(getHookMethods(context, c, name));
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

	// TODO: Add annotations for parameters...
	private Object executeHookMethod(Method m, Object o, Object... parameters) throws InvocationTargetException, IllegalAccessException, IllegalArgumentException {
		// Create an array list with an initial size of parameters.length
		List<Object> usableParameters = new ArrayList<>(parameters.length);
		for (Object p : parameters) {
			for (Class<?> c : m.getParameterTypes()) {
				if (p != null && c.isAssignableFrom(p.getClass())) {
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
