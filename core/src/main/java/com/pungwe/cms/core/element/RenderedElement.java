package com.pungwe.cms.core.element;

import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by ian on 09/01/2016.
 */
public interface RenderedElement {

	String getHtmlId();

	void setHtmlId(String htmlId);

	int getWeight();

	void setWeight(int weight);

	Map<String, String> getAttributes();

	void setAttributes(Map<String, String> attributes);

	String getAttribute(String name);

}
