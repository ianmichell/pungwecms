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
 * Created by ian on 28/03/2016.
 */
package com.pungwe.cms.core.module.controller;

import com.pungwe.cms.core.annotations.stereotypes.Module;
import com.pungwe.cms.core.annotations.ui.MenuItem;
import com.pungwe.cms.core.element.basic.DetailListElement;
import com.pungwe.cms.core.element.basic.DivElement;
import com.pungwe.cms.core.element.basic.TableElement;
import com.pungwe.cms.core.form.controller.AbstractFormController;
import com.pungwe.cms.core.form.element.CheckboxElement;
import com.pungwe.cms.core.form.element.FormElement;
import com.pungwe.cms.core.form.element.InputButtonElement;
import com.pungwe.cms.core.form.element.LabelElement;
import com.pungwe.cms.core.module.ModuleConfig;
import com.pungwe.cms.core.module.services.ModuleConfigService;
import com.pungwe.cms.core.module.services.ModuleManagementService;
import com.pungwe.cms.core.utils.services.StatusMessageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.pungwe.cms.core.utils.Utils.translate;

@MenuItem(menu = "system", parent = "admin", name = "modules", title = "Modules",
        description = "Manage your modules", weight = -150)
@Controller
@RequestMapping("/admin/modules")
public class ModuleManagementController extends AbstractFormController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ModuleManagementController.class);

    @Autowired
    protected ModuleManagementService moduleManagementService;

    @Autowired
    protected ModuleConfigService moduleConfigService;

    @Autowired
    protected StatusMessageService statusMessageService;

    /**
     * Page title model attribute
     *
     * @return the page title
     */
    @ModelAttribute("title")
    public String title() {
        return translate("Modules");
    }

    /**
     * Returns a page with a table of modules.
     *
     * @param model the model for the controller action
     * @return a callable object with a reference to the view being used.
     */
    @RequestMapping(method = RequestMethod.GET)
    public Callable<String> get(final Model model) {
        return () -> {
            return "admin/modules/index";
        };
    }

    @RequestMapping(method = RequestMethod.POST)
    public Callable<String> post(final Model model, @ModelAttribute("form") FormElement form,
                                 BindingResult result, RedirectAttributes redirectAttributes) {
        return () -> {
            if (result.hasErrors()) {
                return "admin/modules/index";
            }
            form.submit(model.asMap());
            // FIXME: This message could be better
            statusMessageService.addSuccessStatusMessage(translate("Success! You've enabled new modules"));
            return "redirect:/admin/modules";
        };
    }

    @Override
    public String getFormId() {
        return "module-management-form";
    }

    @Override
    public void build(final FormElement element) {
        final TableElement tableElement = new TableElement();
        tableElement.addHeaderRow(
                new TableElement.Header(translate("Enabled")),
                new TableElement.Header(translate("Name")),
                new TableElement.Header(translate("Description"))
        );

        // Illustrates if a module is enabled or not.
        final Set<ModuleConfig> moduleConfigList = moduleConfigService.listAllModules();

        final Map<String, Module> annotations = new HashMap<String, Module>();
        moduleConfigList.forEach(moduleConfig -> {
            try {
                Class<?> c = Class.forName(moduleConfig.getEntryPoint());
                Module annotation = AnnotationUtils.findAnnotation(c, Module.class);
                if (annotation == null) {
                    LOGGER.error("Invalid module: " + moduleConfig.getName() + " class: " + moduleConfig.getEntryPoint());
                    return; // skip this row
                }
                annotations.put(annotation.name(), annotation);
            } catch (ClassNotFoundException ex) {
                LOGGER.error("Class: " + moduleConfig.getEntryPoint() + " not found for module: " +
                        moduleConfig.getName(), ex);
            }
        });
        AtomicInteger delta = new AtomicInteger();
        moduleConfigList.stream().filter(moduleConfig -> annotations.containsKey(moduleConfig.getName())).forEach(moduleConfig -> {
            // We should not have a problem with loading classes, however if a classnotfoundexception is thrown
            // ignore it and log as an error
            final Module annotation = annotations.get(moduleConfig.getName());

            // Checkbox
            final CheckboxElement checkboxElement = new CheckboxElement();
            checkboxElement.setName("name");
            checkboxElement.setDefaultValue(moduleConfig.getName());
            checkboxElement.setHtmlId("module_enabled_" + moduleConfig.getName());
            checkboxElement.setDelta(delta.getAndIncrement());
            checkboxElement.setChecked(moduleConfig.isEnabled());

            // Label
            final LabelElement label = new LabelElement(StringUtils.isBlank(annotation.label()) ? annotation.name() : annotation.label());
            label.setForElement(checkboxElement);

            // Description
            final DivElement description = new DivElement(annotation.description());
            DetailListElement details = new DetailListElement();
            details.addItem(
                    new DetailListElement.DTItem(translate("Module Name:")),
                    new DetailListElement.DDItem(translate(annotation.name())),
                    new DetailListElement.DTItem(translate("Dependencies:")),
                    new DetailListElement.DDItem(annotations.values().stream()
                            .filter(module -> Arrays.asList(annotation.dependencies())
                                    .stream().map(dep -> dep.value()).collect(Collectors.toList())
                                    .contains(module.name()))
                            .map(a -> StringUtils.isBlank(a.label()) ? translate(a.name()) : translate(a.label()))
                            .collect(Collectors.joining(", ")))
            );

            tableElement.addRow(
                    new TableElement.Column(checkboxElement),
                    new TableElement.Column(label),
                    new TableElement.Column(description, details)
            );
        });

        element.addContent(tableElement, new InputButtonElement(InputButtonElement.InputButtonType.SUBMIT, translate("Save and Install")));

        element.addSubmitHandler((form, variables) -> {
            List<String> modulesToEnable = form.getValues("name");
            moduleManagementService.setEnabledModules(modulesToEnable);
            moduleManagementService.reloadModules();
        });
    }


    @Override
    public void validate(FormElement form, Errors errors) {
        // do nothing here
    }
}
