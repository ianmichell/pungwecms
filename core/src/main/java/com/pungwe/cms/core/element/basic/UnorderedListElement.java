package com.pungwe.cms.core.element.basic;

import com.pungwe.cms.core.annotations.ui.ThemeInfo;

import java.util.List;

/**
 * Created by ian on 15/03/2016.
 */
@ThemeInfo("basic/unordered_list")
public class UnorderedListElement extends ListElement {

	public UnorderedListElement() {
	}

	public UnorderedListElement(List<ListItem> items) {
		super(items);
	}

	public UnorderedListElement(String... item) {
		super(item);
	}
}
