package com.pungwe.cms.core.form.element;

import com.pungwe.cms.core.annotations.ui.ThemeInfo;
import com.pungwe.cms.core.element.AbstractContentElement;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.form.ElementValidator;
import com.pungwe.cms.core.form.FormRenderedElement;
import com.pungwe.cms.core.form.handler.FormSubmitHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by ian on 24/02/2016.
 */
@Validated
@ThemeInfo("form/form")
public class FormElement<T> extends AbstractContentElement {

    // Target Object
    private boolean rebuildRequired = false;
    private T targetObject;
    private Errors errors;
    private List<FormSubmitHandler> submitHandlers;
    private List<ElementValidator> validators;

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
        final Set<FormRenderedElement<?>> fields = new LinkedHashSet<>();
        findFields(fields, this);
        // Return a unique list of fields
        return fields.stream().collect(Collectors.toList());
    }

    private void findFields(final Set<FormRenderedElement<?>> fields, Object o) {
        if (o == null || !RenderedElement.class.isAssignableFrom(o.getClass())) {
            return; // no point going further if o is null.
        } else if (FormRenderedElement.class.isAssignableFrom(o.getClass())) {
            fields.add((FormRenderedElement<?>)o);
            return;
        }
        PropertyDescriptor[] descriptors = BeanUtils.getPropertyDescriptors(o.getClass());
        // Run through each descriptor
        for (PropertyDescriptor descriptor : descriptors) {

            Class<?> clazz = descriptor.getPropertyType();
            Method method = descriptor.getReadMethod();
            // We only want the annotated methods
            if (method == null || AnnotationUtils.findAnnotation(method, ModelAttribute.class) == null) {
                continue;
            }
            try {
                if (Collection.class.isAssignableFrom(clazz)) {
                    Collection results = (Collection) method.invoke(o);
                    results.forEach(r -> findFields(fields, r));
                } else if (Map.class.isAssignableFrom(clazz)) {
                    findFieldsInMap(fields, (Map)method.invoke(o));
                } else if (RenderedElement.class.isAssignableFrom(clazz)) {
                    findFields(fields, method.invoke(o));
                }
            } catch (IllegalAccessException e) {
                continue;
            } catch (InvocationTargetException e) {
                continue;
            }
        }
    }

    private void findFieldsInMap(final Set<FormRenderedElement<?>> fields, Map o) {
        o.forEach((k, v) -> {
            findFields(fields, v);
            findFields(fields, k);
        });
    }

    /*public List<FormRenderedElement<?>> getFields() {
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
    } */

    public FormRenderedElement<?> getField(String name, int delta) {
        int index = getFormFieldIndex(name, delta);
        if (index < 0) {
            return null;
        }
        return getFields().get(index);
    }

    public List<FormRenderedElement<?>> getField(String name) {
        return getFields().stream().filter(f -> !StringUtils.isEmpty(f.getName()) && f.getName().equals(name))
                .sorted((f1, f2) -> Integer.compare(f1.getDelta(), f2.getDelta())).collect(Collectors.toList());
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
     * Provides values in a map by field name.
     *
     * @return a map of values by field name, with a list of that field's values.
     */
    public Map<String, List<Object>> getValues() {
        final Map<String, List<Object>> values = new LinkedHashMap<>();
        getFields().stream().sorted((f1, f2) -> {
            return Integer.compare(f1.getDelta(), f2.getDelta());
        }).forEach(formRenderedElement -> {
            if (!values.containsKey(formRenderedElement.getName())) {
                values.put(formRenderedElement.getName(), new ArrayList<>());
            }
            values.get(formRenderedElement.getName()).add(formRenderedElement.getValue());
        });
        return values;
    }

    /**
     * Provides a list of values by delta for a field
     *
     * @param field the name of the field
     * @return a list of values from 0 to n
     */
    public List<Object> getValues(String field) {
        List<FormRenderedElement<?>> fields = getField(field);
        return fields.stream().map(f -> f.getValue()).collect(Collectors.toList());
    }

    public void setValues(String field, List<Object> values) {
        int i = 0;
        for (Object value : values) {
            setValue(field, i++, value);
        }
    }

    public Object getValue(String field, int delta) {
        List<Object> values = getValues(field);
        if (delta >= values.size()) {
            return null;
        }
        return values.get(delta);
    }

    public void setValue(String field, int delta, Object value) {
        FormRenderedElement<?> fieldToSet = getField(field, delta);
        if (fieldToSet != null) {
            ((FormRenderedElement<Object>) fieldToSet).setValue(value);
        }
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }

    public List<FormSubmitHandler> getSubmitHandlers() {
        if (submitHandlers == null) {
            submitHandlers = new ArrayList<>();
        }
        return submitHandlers;
    }

    public void setSubmitHandlers(List<FormSubmitHandler> submitHandlers) {
        this.submitHandlers = new ArrayList<>();
        this.submitHandlers.addAll(submitHandlers);
    }

    public void addSubmitHandler(FormSubmitHandler<T> handler) {
        getSubmitHandlers().add(handler);
    }

    public void submit(final Map<String, Object> variables) {
        getSubmitHandlers().forEach(f -> {
            f.submit(this, variables);
        });
    }

    public List<ElementValidator> getValidators() {
        if (validators == null) {
            validators = new ArrayList<>();
        }
        return validators;
    }

    public void setValidators(List<ElementValidator> validators) {
        this.validators = new ArrayList<>();
        this.validators.addAll(validators);
    }

    public void addValidator(ElementValidator... validators) {
        this.getValidators().addAll(Arrays.asList(validators));
    }

    public T getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(T targetObject) {
        this.targetObject = targetObject;
    }

    public boolean isRebuildRequired() {
        return rebuildRequired;
    }

    public void setRebuildRequired(boolean rebuildRequired) {
        this.rebuildRequired = rebuildRequired;
    }

    public void hideField(String name, int delta) {
        FormRenderedElement element = getField(name, delta);
        element.setVisible(false);
    }
}
