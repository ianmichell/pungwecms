package com.pungwe.cms.core.element.basic;

import com.pungwe.cms.core.annotations.ui.ThemeInfo;
import com.pungwe.cms.core.element.AbstractContentElement;
import com.pungwe.cms.core.element.AbstractRenderedElement;
import com.pungwe.cms.core.element.RenderedElement;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collection;
import java.util.Arrays;
import java.util.stream.Collectors;


/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * )with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * Created by ian on 30/03/2016.
 */
@ThemeInfo("basic/detail_list/list")
public class DetailListElement extends AbstractRenderedElement {


    private List<DetailListItem> items;

    @ModelAttribute("items")
    public List<DetailListItem> getItems() {
        if (items == null) {
            items = new LinkedList<>();
        }
        return items;
    }

    public void setItems(final List<DetailListItem> items) {
        this.items = new ArrayList<>(items.size());
        this.items.addAll(items);
    }

    public void addItem(final DetailListItem... item) {
        addItems(Arrays.asList(item));
    }

    public void addItems(final List<DetailListItem> items) {
        getItems().addAll(items);
    }

    @Override
    protected final Collection<String> excludedAttributes() {
        return new LinkedList<>();
    }

    public abstract static class DetailListItem extends AbstractContentElement {

        public DetailListItem() {
        }

        public DetailListItem(String... content) {
            this(Arrays.asList(content).stream().map(s -> new PlainTextElement(s)).collect(Collectors.toList()));
        }

        public DetailListItem(RenderedElement... content) {
            this(Arrays.asList(content));
        }

        public DetailListItem(List<RenderedElement> elements) {
            setContent(elements);
        }

        @Override
        protected final Collection<String> excludedAttributes() {
            return new LinkedList<>();
        }
    }

    @ThemeInfo("basic/detail_list/dd_item")
    public static class DDItem extends DetailListItem {

        public DDItem() {
        }

        public DDItem(String... content) {
            super(content);
        }

        public DDItem(RenderedElement... content) {
            super(content);
        }

        public DDItem(List<RenderedElement> elements) {
            super(elements);
        }
    }

    @ThemeInfo("basic/detail_list/dt_item")
    public static class DTItem extends DetailListItem {

        public DTItem() {
        }

        public DTItem(String... content) {
            super(content);
        }

        public DTItem(RenderedElement... content) {
            super(content);
        }

        public DTItem(List<RenderedElement> elements) {
            super(elements);
        }
    }
}
