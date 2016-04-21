/*
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
package com.pungwe.cms.core.form.element;

import com.pungwe.cms.core.annotations.ui.ThemeInfo;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.basic.PlainTextElement;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ThemeInfo("form/checkbox")
public class CheckboxElement<T> extends AbstractFormRenderedElement<T> {

    private boolean checked;
    protected List<RenderedElement> content;

    public CheckboxElement() {
    }

    public CheckboxElement(String name, T value, RenderedElement... content) {
        setName(name);
        setValue(value);
    }

    public CheckboxElement(String name, T value, boolean checked, RenderedElement... content) {
        setName(name);
        setValue(value);
        setContent(content);
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @ModelAttribute("checked")
    public String checkedAttribute() {
        return checked ? "checked" : "";
    }

    @ModelAttribute("content")
    public List<RenderedElement> getContent() {
        if (content == null) {
            content = new ArrayList<>();
        }
        return content;
    }

    public void setContent(RenderedElement... content) {
        setContent(Arrays.asList(content));
    }

    public void setContent(List<RenderedElement> content) {
        this.content = new ArrayList<>();
        this.content.addAll(content);
    }

    public void addContent(String... content) {
        setContent(Arrays.asList(content).stream().map(s -> new PlainTextElement(s)).collect(Collectors.toList()));
    }

    public void addContent(RenderedElement... content) {
        this.getContent().addAll(Arrays.asList(content));
    }

    public void addContent(List<RenderedElement> content) {
        this.getContent().addAll(content);
    }
}
