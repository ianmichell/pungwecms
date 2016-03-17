package com.pungwe.cms.core.element.basic;

import com.pungwe.cms.core.annotations.ui.ThemeInfo;

import java.util.List;

/**
 * Created by ian on 15/03/2016.
 */
@ThemeInfo("basic/ordered_list")
public class OrderedListElement extends ListElement {

	public OrderedListElement() {
	}

	public OrderedListElement(List<ListItem> items) {
		super(items);
	}

	public OrderedListElement(String... item) {
		super(item);
	}
}
