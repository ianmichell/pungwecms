package com.pungwe.cms.core.element.basic;

import com.pungwe.cms.core.annotations.ui.ThemeInfo;
import com.pungwe.cms.core.element.AbstractContentElement;
import com.pungwe.cms.core.element.AbstractRenderedElement;
import com.pungwe.cms.core.element.RenderedElement;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ian on 15/03/2016.
 */
public abstract class ListElement extends AbstractRenderedElement {

	public ListElement() {

	}

	public ListElement(List<ListItem> items) {
		this.items = items;
	}

	public ListElement(String... item) {
		addItem(item);
	}

	protected List<ListItem> items;

	@ModelAttribute("items")
	public List<ListItem> getItems() {
		if (items == null) {
			items = new LinkedList<>();
		}
		return items;
	}

	public void setItems(List<ListItem> items) {
		this.items = items;
	}

	public void addItem(ListItem... item) {
		getItems().addAll(Arrays.asList(item));
	}

	public void addItem(String... items) {
		for (String item : items) {
			addItem(new ListItem(item));
		}
	}

	@Override
	protected Collection<String> excludedAttributes() {
		return new LinkedList<>();
	}

	@ThemeInfo("basic/list_item")
	public static class ListItem extends AbstractContentElement {

		public ListItem(RenderedElement... content) {
			setContent(content);
		}

		public ListItem(String... content) {
			setContent(content);
		}
		@Override
		protected Collection<String> excludedAttributes() {
			return new LinkedList<>();
		}
	}
}
