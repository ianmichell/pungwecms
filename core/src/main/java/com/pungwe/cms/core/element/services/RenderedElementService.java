package com.pungwe.cms.core.element.services;

import com.pungwe.cms.core.annotations.ThemeInfo;
import com.pungwe.cms.core.element.RenderedElement;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by ian on 03/03/2016.
 */
@Service
public class RenderedElementService {

	/**
	 * Used to convert a RenderedElement to a model and view.
	 *
	 * @param element the rendered element to be converted
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public ModelAndView convertToModelAndView(RenderedElement element) throws InvocationTargetException, IllegalAccessException {

		// Generate Model map
		ModelMap model = objectToModelMap(element);

		// Get theme infor a template name
		ThemeInfo info = element.getClass().isAnnotationPresent(ThemeInfo.class) ? element.getClass().getAnnotation(ThemeInfo.class) : null;
		String template = info == null ? element.getClass().getSimpleName() : info.value();

		// Create ModelAndView, then return it.
		return new ModelAndView(template, model);
	}

	private ModelMap objectToModelMap(Object o) throws InvocationTargetException, IllegalAccessException {
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
