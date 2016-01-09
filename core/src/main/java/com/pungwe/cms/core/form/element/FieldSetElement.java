package com.pungwe.cms.core.form.element;

import com.pungwe.cms.core.element.AbstractRenderedElement;
import com.pungwe.cms.core.element.RenderedElement;

import java.util.List;

/**
 * Created by ian on 09/01/2016.
 */
public class FieldSetElement extends AbstractRenderedElement {

    protected String legend;
    protected List<RenderedElement> children;

    public String getLegend() {
        return legend;
    }

    public void setLegend(String legend) {
        this.legend = legend;
    }

    public List<RenderedElement> getChildren() {
        return children;
    }

    public void setChildren(List<RenderedElement> children) {
        this.children = children;
    }
}
