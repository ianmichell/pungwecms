package com.pungwe.cms.core.element.basic;

import com.pungwe.cms.core.element.AbstractRenderedElement;
import com.pungwe.cms.core.element.RenderedElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ian on 12/01/2016.
 */
public class TableElement extends AbstractRenderedElement {

	protected String summary;
	protected List<Row> header;
	protected List<Row> rows;

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public List<Row> getHeader() {
		return header;
	}

	public void setHeader(List<Row> header) {
		this.header = header;
	}

	public List<Row> getRows() {
		return rows;
	}

	public void setRows(List<Row> rows) {
		this.rows = rows;
	}

	@Override
	public String getTheme() {
		return "table";
	}

	public void addRow(Row row) {
		if (this.rows == null) {
			this.rows = new ArrayList<>();
		}
		this.rows.add(row);
	}

	public void addRow(Column... columns) {
		Row row = new Row();
		row.addColumn(columns);
		addRow(row);
	}

	public void addHeader(Row... header) {
		if (this.header == null) {
			this.header = new ArrayList<>();
		}
		for (Row h : header) {
			this.header.add(h);
		}
	}

	public void addHeaderRow(Column... columns) {
		Row row = new Row();
		row.addColumn(columns);
		addHeader(row);
	}

	public static class Row {

		Map<String, Object> attributes = new HashMap<String, Object>();
		protected List<Column> columns = new ArrayList<>();

		public void addColumn(Column... columns) {
			for (Column column : columns) {
				this.columns.add(column);
			}
		}

		public Map<String, Object> getAttributes() {
			return attributes;
		}

		public void setAttributes(Map<String, Object> attributes) {
			this.attributes = attributes;
		}

		public List<Column> getColumns() {
			return columns;
		}

		public void setColumns(List<Column> columns) {
			this.columns = columns;
		}
	}

	public static class Column {

		public Column() {
		}

		public Column(RenderedElement content) {
			this.content = content;
		}

		protected RenderedElement content;
		Map<String, Object> attributes = new HashMap<String, Object>();

		public RenderedElement getContent() {
			return content;
		}

		public void setContent(RenderedElement content) {
			this.content = content;
		}

		public Map<String, Object> getAttributes() {
			return attributes;
		}

		public void setAttributes(Map<String, Object> attributes) {
			this.attributes = attributes;
		}
	}
}
