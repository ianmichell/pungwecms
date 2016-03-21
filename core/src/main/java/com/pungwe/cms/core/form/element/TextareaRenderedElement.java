package com.pungwe.cms.core.form.element;

import com.pungwe.cms.core.annotations.ui.ThemeInfo;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by ian on 09/01/2016.
 */
@ThemeInfo("form/textarea")
public class TextareaRenderedElement extends StringRenderedElement {

	private int rows;
	private int columns;

	@ModelAttribute("rows")
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	@ModelAttribute("columns")
	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	@Override
	protected Collection<String> excludedAttributes() {
		Collection<String> excluded = new LinkedList<>(super.excludedAttributes());
		excluded.add("cols");
		excluded.add("rows");
		return excluded;
	}
}
