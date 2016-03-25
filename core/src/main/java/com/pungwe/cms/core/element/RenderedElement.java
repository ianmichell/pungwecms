package com.pungwe.cms.core.element;

import com.pungwe.cms.core.element.basic.PlainTextElement;

import java.util.Arrays;
import java.util.List;
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

	String getAttribute(String name, String defaultValue);

	boolean isWrapped();

	void setWrapped(boolean parent);

	boolean isVisible();
	void setVisible(boolean visible);

	List<String> getClasses();

	default void setClasses(String... classes) {
		setClasses(Arrays.asList(classes));
	}

	default void addClass(String... c) {
		addClass(Arrays.asList(c));
	}

	default void addClass(List<String> c) {
		List<String> classes = getClasses();
		if (classes != null) {
			classes.addAll(c);
		}
	}

	void setClasses(List<String> classes);
}
