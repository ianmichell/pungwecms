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
import org.springframework.web.bind.annotation.ModelAttribute;

@ThemeInfo("form/checkbox")
public class CheckboxElement extends AbstractFormRenderedElement<String> {

    private boolean checked;

    public CheckboxElement() {
    }

    public CheckboxElement(String name, String value) {
        setName(name);
        setValue(value);
    }

    public CheckboxElement(String name, String value, boolean checked) {
        setName(name);
        setValue(value);
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
}
