package com.pungwe.cms.core.form.element;

import com.pungwe.cms.core.element.AbstractRenderedElement;
import com.pungwe.cms.core.element.RenderedElement;

/**
 * Created by ian on 09/01/2016.
 */
public class StringElement extends AbstractFormElement<String> {

    int size = 60;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
