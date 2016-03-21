package com.pungwe.cms.core.form.element;

import com.pungwe.cms.core.annotations.ui.ThemeInfo;
import com.pungwe.cms.core.element.AbstractContentElement;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.form.FormRenderedElement;
import com.pungwe.cms.core.form.handler.FormSubmitHandler;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorUtils;
import org.springframework.util.ClassUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by ian on 24/02/2016.
 */
@Validated
@ThemeInfo("form/form")
public class FormElement extends AbstractContentElement {

	private Errors errors;
	private List<FormSubmitHandler> submissionHandlers;

	public void setEnctype(String enctype) {
		addAttribute("enctype", enctype);
	}

	public String getEnctype() {
		return getAttribute("enctype");
	}

	public void setMethod(String method) {
		addAttribute("method", method);
	}

	public String getMethod() {
		return getAttribute("method");
	}

	public void setTarget(String target) {
		addAttribute("target", target);
	}

	public String getTarget() {
		return getAttribute("target");
	}

	public void setAcceptCharset(String acceptCharset) {
		addAttribute("accept-charset", acceptCharset);
	}

	public String getAcceptCharset() {
		return getAttribute("accept-charset");
	}

	public void setAction(String action) {
		addAttribute("action", action);
	}

	public String getAction() {
		return getAttribute("action");
	}

	public void setName(String name) {
		addAttribute("name", name);
	}

	public String getName() {
		return getAttribute("name");
	}

	@Override
	protected Collection<String> excludedAttributes() {
		return new LinkedList<>();
	}

	public List<FormRenderedElement<?>> getFields() {
		final List<FormRenderedElement<?>> elements = new LinkedList<>();
		getContent().stream().forEach(renderedElement -> {
			if (renderedElement instanceof FormRenderedElement) {
				elements.add((FormRenderedElement<?>) renderedElement);
			} else if (renderedElement instanceof AbstractContentElement) {
				findProperties(elements, (AbstractContentElement) renderedElement);
			}
		});
		return elements;
	}

	private void findProperties(final List<FormRenderedElement<?>> elements, AbstractContentElement element) {
		element.getContent().stream().forEach(renderedElement -> {
			if (renderedElement instanceof FormRenderedElement) {
				elements.add((FormRenderedElement<?>) renderedElement);
			} else if (renderedElement instanceof AbstractContentElement) {
				findProperties(elements, (AbstractContentElement) renderedElement);
			}
		});
	}

	public FormRenderedElement<?> getField(String name, int delta) {
		int index = getFormFieldIndex(name, delta);
		if (index < 0) {
			return null;
		}
		return getFields().get(index);
	}

	public int getFormFieldIndex(String name, int delta) {
		AtomicInteger counter = new AtomicInteger(0);
		Optional<FormRenderedElement<?>> field = getFields().stream().filter(e -> {
			boolean found = e.getName() != null && e.getName().equals(name) && e.getDelta() == delta;
			if (found) {
				return true;
			}
			counter.incrementAndGet();
			return false;
		}).findFirst();
		if (!field.isPresent()) {
			return -1;
		}
		return counter.get();
	}

	public boolean isRequiredField(String name, int delta) {
		return getField(name, delta).isRequired();
	}

	/**
	 * Provides values in a map.
	 * @return
	 */
	public Map<String, Map<Integer, Object>> getValues() {
		final Map<String, Map<Integer, Object>> values = new LinkedHashMap<>();
		getFields().stream().forEach(formRenderedElement -> {
			if (!values.containsKey(formRenderedElement.getName())) {
				values.put(formRenderedElement.getName(), new LinkedHashMap<>());
			}
			values.get(formRenderedElement.getName()).put(formRenderedElement.getDelta(), formRenderedElement.getValue());
		});
		return values;
	}

	public Object getValue(String field, int delta) {
		return getValues().getOrDefault(field, new HashMap<>()).get(delta);
	}

	public void setValue(String field, int delta, Object value) {
		FormRenderedElement<?> fieldToSet = getField(field, delta);
		if (fieldToSet != null) {
			((FormRenderedElement<Object>)fieldToSet).setValue(value);
		}
	}

	public void setErrors(Errors errors) {
		this.errors = errors;
	}

	public Errors getErrors() {
		return errors;
	}

	public List<FormSubmitHandler> getSubmissionHandlers() {
		if (submissionHandlers == null) {
			submissionHandlers = new ArrayList<>();
		}
		return submissionHandlers;
	}

	public void setSubmissionHandlers(List<FormSubmitHandler> submissionHandlers) {
		this.submissionHandlers = submissionHandlers;
	}

	public void addSubmitHandler(FormSubmitHandler handler) {
		getSubmissionHandlers().add(handler);
	}

	public void submit() throws Exception {
		for (FormSubmitHandler handler : getSubmissionHandlers()) {
			handler.submit(this);
		}
	}
}
