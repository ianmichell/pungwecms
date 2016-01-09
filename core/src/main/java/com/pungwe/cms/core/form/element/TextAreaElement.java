package com.pungwe.cms.core.form.element;

import com.pungwe.cms.core.element.AbstractRenderedElement;
import com.pungwe.cms.core.element.RenderedElement;

/**
 * Created by ian on 09/01/2016.
 */
public class TextAreaElement extends AbstractFormElement<String> {

    protected int rows;

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

}
