package com.pungwe.cms.core.element.basic;

import com.pungwe.cms.core.annotations.ui.ThemeInfo;
import com.pungwe.cms.core.element.AbstractContentElement;
import com.pungwe.cms.core.element.AbstractRenderedElement;
import com.pungwe.cms.core.element.RenderedElement;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.*;

/**
 * Created by ian on 12/01/2016.
 */
@ThemeInfo("basic/table/table")
public class TableElement extends AbstractRenderedElement {

	protected RenderedElement caption;
	protected List<Row<Header>> header;
	protected List<Row> rows;
	protected List<Row> footer;

	@ModelAttribute("caption")
	public RenderedElement getCaption() {
		return caption;
	}

	public void setCaption(RenderedElement caption) {
		this.caption = caption;
	}

	@ModelAttribute("header")
	public List<Row<Header>> getHeader() {
		if (this.header == null) {
			this.header = new ArrayList<>();
		}
		return header;
	}

	public void setHeader(List<Row<Header>> header) {
		this.header = header;
	}

	@ModelAttribute("footer")
	public List<Row> getFooter() {
		if (this.footer == null) {
			this.footer = new ArrayList<>();
		}
		return footer;
	}

	public void setFooter(List<Row> footer) {
		this.footer = footer;
	}

	@ModelAttribute("rows")
	public List<Row> getRows() {
		if (this.rows == null) {
			this.rows = new ArrayList<>();
		}
		return rows;
	}

	public void setRows(List<Row> rows) {
		this.rows = rows;
	}

	public void addRow(Row row) {
		this.getRows().add(row);
	}

	public void addRow(Column... columns) {
		Row row = new Row();
		row.addColumn(columns);
		addRow(row);
	}

	public void addHeader(Row<Header>... header) {
		for (Row h : header) {
			this.getHeader().add(h);
		}
	}

	public void addHeaderRow(Header... columns) {
		Row row = new Row();
		row.addColumn(columns);
		addHeader(row);
	}

	public void addFooter(Row... header) {
		for (Row h : header) {
			this.getFooter().add(h);
		}
	}

	public void addFooterRow(Column... columns) {
		Row row = new Row();
		row.addColumn(columns);
		addFooter(row);
	}

	@Override
	protected Collection<String> excludedAttributes() {
		return new LinkedList<>();
	}

	@ThemeInfo("basic/table/row")
	public static class Row<T extends Column> extends AbstractRenderedElement {

		protected List<T> columns = new ArrayList<>();

		public void addColumn(T... columns) {
			for (T column : columns) {
				this.columns.add(column);
			}
		}

		@ModelAttribute("columns")
		public List<T> getColumns() {
			return columns;
		}

		public void setColumns(List<T> columns) {
			this.columns = columns;
		}

		@Override
		protected Collection<String> excludedAttributes() {
			return new LinkedList<>();
		}
	}

	@ThemeInfo("basic/table/column")
	public static class Column extends AbstractContentElement {

		public Column() {
		}

		public Column(RenderedElement... content) {
			setContent(content);
		}

		@Override
		protected Collection<String> excludedAttributes() {
			return new LinkedList<>();
		}
	}

	@ThemeInfo("basic/table/header")
	public static class Header extends Column {
		public Header() {
		}

		public Header(RenderedElement content) {
			super(content);
		}
	}
}
