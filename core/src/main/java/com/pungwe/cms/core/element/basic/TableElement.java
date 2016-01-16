package com.pungwe.cms.core.element.basic;

import com.pungwe.cms.core.element.AbstractRenderedElement;
import com.pungwe.cms.core.element.RenderedElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ian on 12/01/2016.
 */
public class TableElement extends AbstractRenderedElement {

    public static class Row {

        protected List<RenderedElement> columns = new ArrayList<>();

        public void addColumn(RenderedElement column) {
            this.columns.add(column);
        }
    }

    protected String summary;
    protected List<RenderedElement> header;
    protected List<Row> rows;

    public void addRow(Row row) {
        if (this.rows == null) {
            this.rows = new ArrayList<>();
        }
        this.rows.add(row);
    }

    public void addHeader(RenderedElement... header) {
        if (this.header == null) {
            this.header = new ArrayList<>();
        }
        for (RenderedElement h : header) {
            this.header.add(h);
        }
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<RenderedElement> getHeader() {
        return header;
    }

    public void setHeader(List<RenderedElement> header) {
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
}
