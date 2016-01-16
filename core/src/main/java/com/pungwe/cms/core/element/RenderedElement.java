package com.pungwe.cms.core.element;

import java.util.Map;

/**
 * Created by ian on 09/01/2016.
 */
public interface RenderedElement {

    String getHtmlId();
    void setHtmlId(String htmlId);

    String getName();
    void setName(String name);

    int getWeight();
    void setWeight(int weight);

    Map<String, Object> getAttributes();
    void setAttributes(Map<String, Object> settings);

    String getTheme();

}
