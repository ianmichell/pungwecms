package com.pungwe.cms.core.form.processors;

import com.google.common.collect.Maps;
import com.pungwe.cms.core.form.FormRenderedElement;
import com.pungwe.cms.core.form.element.FormElement;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ian on 19/03/2016.
 */
// FIXME: Add Form ID / Token to this...
public class FormModelAttributeMethodProcessor extends ServletModelAttributeMethodProcessor {

	public FormModelAttributeMethodProcessor(boolean annotationNotRequired) {
		super(annotationNotRequired);
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return super.supportsParameter(parameter);
	}

	@Override
	protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest request) {
		ServletRequest servletRequest = request.getNativeRequest(ServletRequest.class);
		ServletRequestDataBinder servletBinder = (ServletRequestDataBinder) binder;
		bind(servletRequest, servletBinder);
	}

	public void bind(ServletRequest request, ServletRequestDataBinder binder) {
		Map<String, ?> values = parseValues(request, binder);
		MutablePropertyValues mpvs = new MutablePropertyValues(values);
		MultipartRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartRequest.class);
		if (multipartRequest != null) {
			bindMultipart(multipartRequest.getMultiFileMap(), mpvs);
		}
		// two lines copied from ExtendedServletRequestDataBinder
		String attr = HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE;
		mpvs.addPropertyValues((Map<String, String>) request.getAttribute(attr));
		binder.bind(mpvs);
	}

	private Map<String, ?> parseValues(ServletRequest request, ServletRequestDataBinder binder) {
		Map<String, Object> params = Maps.newTreeMap();
		Assert.notNull(request, "Request must not be null!");
		Map<String, String> parameterMappings = getParameterMappings(request, binder);
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			String[] values = request.getParameterValues(paramName);

			String fieldName = parameterMappings.get(paramName);
			// no annotation exists, use the default - the param name=field name
			if (fieldName == null) {
				fieldName = paramName;
			}

			if (values == null || values.length == 0) {
				// Do nothing, no values found at all.
			} else if (values.length > 1) {
				params.put(fieldName, values);
			} else {
				params.put(fieldName, values[0]);
			}
		}

		return params;
	}

	private Map<String, String> getParameterMappings(ServletRequest request, ServletRequestDataBinder binder) {
		Assert.notNull(binder.getTarget());
		Class<?> targetClass = binder.getTarget().getClass();
		final Map<String, String> map = Maps.newTreeMap();
		// If the target is a form element, then we need to convert to field[idx] :)
		if (FormElement.class.isAssignableFrom(targetClass)) {
			FormElement formElement = (FormElement)binder.getTarget();
			final List<FormRenderedElement<?>> fields = formElement.getFields();
			Enumeration<String> paramNames = request.getParameterNames();
			while (paramNames != null && paramNames.hasMoreElements()) {
				String param = paramNames.nextElement();
				AtomicInteger index = new AtomicInteger(0);
				// Find the field field and index for the given parameter name
				Optional<FormRenderedElement<?>> field = fields.stream().filter(e -> {
					boolean found = getElementName(e).equals(param);
					if (found) {
						return true;
					}
					index.getAndIncrement();
					return false;
				}).findFirst();
				// If the field was found, then put it into the map.
				if (field.isPresent()) {
					map.putIfAbsent(param, "fields[" + index.get() + "].value");
				}
			}

		}
		// Return a tree map for now...
		return map;
	}

	private String getElementName(FormRenderedElement<?> element) {
		Method[] methods = element.getClass().getMethods();
		for (Method method : methods) {
			ModelAttribute attribute = AnnotationUtils.findAnnotation(method, ModelAttribute.class);
			// If there is no annotation, then don't bother and move on...
			if (attribute == null) {
				continue;
			}
			String elementName = attribute.value();
			if ("name".equals(attribute.value())) {
				try {
					Object value = method.invoke(element);
					return value == null ? "" : value.toString();
				} catch (IllegalAccessException e) {
				} catch (InvocationTargetException e) {
				}
			}
		}
		return "";
	}

	/**
	 * Copied from WebDataBinder.
	 *
	 * @param multipartFiles
	 * @param mpvs
	 */
	protected void bindMultipart(Map<String, List<MultipartFile>> multipartFiles, MutablePropertyValues mpvs) {
		for (Map.Entry<String, List<MultipartFile>> entry : multipartFiles.entrySet()) {
			String key = entry.getKey();
			List<MultipartFile> values = entry.getValue();
			if (values.size() == 1) {
				MultipartFile value = values.get(0);
				if (!value.isEmpty()) {
					mpvs.add(key, value);
				}
			} else {
				mpvs.add(key, values);
			}
		}
	}
}