package com.pungwe.cms.core.form.element;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pungwe.cms.core.element.AbstractRenderedElement;
import com.pungwe.cms.core.form.ElementValidator;
import com.pungwe.cms.core.form.FormRenderedElement;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by ian on 09/01/2016.
 */
public abstract class AbstractFormRenderedElement<T> extends AbstractRenderedElement implements FormRenderedElement<T> {

	protected String name;
	protected String placeholder;
	protected LabelElement label;
	protected T defaultValue;
	protected T value;
	protected int delta;
	protected boolean required;
    private List<ElementValidator> validators;
    private List<String> errors;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ModelAttribute("name")
	public String getElementName() {
		return getName() + "[" + getDelta() + "].value";
	}

	@ModelAttribute("label")
	public LabelElement getLabel() {
		return label;
	}

	public void setLabel(LabelElement label) {
		this.label = label;
		this.label.setForElement(this); // force this
	}

	public void setLabel(String label) {
		this.label = new LabelElement(label, this);
	}

	@ModelAttribute("defaultValue")
	public T getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(T defaultValue) {
		this.defaultValue = defaultValue;
	}

	@ModelAttribute("delta")
	public int getDelta() {
		return delta;
	}

	public void setDelta(int delta) {
		this.delta = delta;
	}

	@ModelAttribute("required")
	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	@ModelAttribute("placeholder")
	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	@ModelAttribute("value")
	public T getValueOrDefaultValue() {
		return getValue() != null ? getValue() : getDefaultValue();
	}

	@Override
	protected Collection<String> excludedAttributes() {
		return Arrays.asList("value", "name", "placeholder");
	}

	@ModelAttribute("error")
	@Override
	public boolean hasError() {
		return !getErrors().isEmpty();
	}

    @Override
    public List<String> getErrors() {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        return errors;
    }

    @Override
    public void addError(String... error) {
        getErrors().addAll(Arrays.asList(error));
    }

    @JsonIgnore
    @Override
    public List<ElementValidator> getValidators() {
        if (validators == null) {
            this.validators = new ArrayList<>();
        }
        return validators;
    }

    @Override
    public void setValidators(List<ElementValidator> validators) {
        this.validators = new ArrayList<>();
        validators.addAll(validators);
    }

    @Override
    public void addValidator(ElementValidator... validators) {
        this.getValidators().addAll(Arrays.asList(validators));
    }

	@Override
	public void validate() {
		getValidators().forEach(elementValidator -> {
            elementValidator.validate(this);
        });
	}
}
