package com.pungwe.cms.core.theme.functions;

import com.lyncode.jtwig.functions.annotations.JtwigFunction;
import com.lyncode.jtwig.functions.annotations.Parameter;
import com.lyncode.jtwig.functions.exceptions.FunctionException;
import com.lyncode.jtwig.util.render.RenderHttpServletResponse;
import com.pungwe.cms.core.element.RenderedElement;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by ian on 14/02/2016.
 */
public class TemplateFunctions {

	private ViewResolver viewResolver;
	private LocaleResolver localeResolver;

	public TemplateFunctions(ViewResolver resolver, LocaleResolver localeResolver) {
		this.viewResolver = resolver;
	}

	@JtwigFunction(name = "render")
	public <T extends RenderedElement> String render (HttpServletRequest request, @Parameter T input) throws FunctionException {
		try {
			Map<String, Object> model = objectToModelMap(input);
			RenderHttpServletResponse responseWrapper = new RenderHttpServletResponse();
			View view = viewResolver.resolveViewName(input.getTheme(), localeResolver != null ? localeResolver.resolveLocale(request) : null);
			view.render(model, request, responseWrapper);
			return responseWrapper.toString();
		} catch (Exception ex) {
			throw new FunctionException(ex);
		}
	}

	@JtwigFunction(name = "printAttributes")
	public <T extends Map<String, ?>> String printAttributes(HttpServletRequest request, @Parameter T input) throws FunctionException {
		if (input != null) {
			return " " + input.entrySet().stream().map(e -> e.getKey() + "=\"" + e.getValue() + "\"").collect(Collectors.joining(" "));
		}
		return "";
	}

	protected ModelMap objectToModelMap(Object o) throws InvocationTargetException, IllegalAccessException {
		ModelMap map = new ModelMap();
		Method[] methods = o.getClass().getMethods();
		for (Method m : methods) {
			if (!m.isAnnotationPresent(ModelAttribute.class)) {
				continue;
			}
			ModelAttribute attr = m.getAnnotation(ModelAttribute.class);
			Object v = m.invoke(o);
			map.addAttribute(attr.value(), v);
		}
		return map;
	}
}
