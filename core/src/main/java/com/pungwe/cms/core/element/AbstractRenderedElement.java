package com.pungwe.cms.core.element;

import java.util.Map;

/**
 * Created by ian on 09/01/2016.
 */
public abstract class AbstractRenderedElement implements RenderedElement {

    protected String htmlId;
    protected String name;
    protected int weight;
    // FIXME: Should this be here?
    protected Map<String, Object> settings;
    protected String theme;

    @Override
    public String getHtmlId() {
        return htmlId;
    }

    @Override
    public void setHtmlId(String htmlId) {
        this.htmlId = htmlId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public Map<String, Object> getSettings() {
        return settings;
    }

    @Override
    public void setSettings(Map<String, Object> settings) {
        this.settings = settings;
    }
}
