package com.pungwe.cms.core.form.element;

import com.pungwe.cms.core.element.AbstractRenderedElement;

import java.util.Set;

/**
 * Created by ian on 09/01/2016.
 */
public class SelectListElement<T> extends AbstractFormElement<T> {

    protected Set<T> items;

    public Set<T> getItems() {
        return items;
    }

    public void setItems(Set<T> items) {
        this.items = items;
    }
}
