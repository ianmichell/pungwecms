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
 *
 *
 * Created by Ian Michell on 27/03/2016.
 */
package com.pungwe.cms.themes.adminlte;

import com.pungwe.cms.core.annotations.stereotypes.Theme;
import com.pungwe.cms.core.annotations.stereotypes.ThemeRegion;
import com.pungwe.cms.core.annotations.util.Hook;
import com.pungwe.cms.core.block.services.BlockManagementService;
import com.pungwe.cms.core.element.HeaderRenderedElement;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.basic.*;
import com.pungwe.cms.core.form.FormRenderedElement;
import com.pungwe.cms.core.form.element.AbstractFormRenderedElement;
import com.pungwe.cms.core.form.element.ButtonElement;
import com.pungwe.cms.core.form.element.InputButtonElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Theme(
        name = "admin_lte",
        description = "Admin LTE Theme",
        label = "Admin LTE",
        regions = {
                @ThemeRegion(name = "navbar", label = "Top Navbar"),
                @ThemeRegion(name = "sidebar", label = "Main Sidebar"),
                @ThemeRegion(name = "header", label = "Page Header"),
                @ThemeRegion(name = "breadcrumb", label = "Breadcrumb"),
                @ThemeRegion(name = "highlighted", label = "Hightlighted"),
                @ThemeRegion(name = "content", label = "Page Content"),
                @ThemeRegion(name = "footer", label = "Footer")
        }
)
public class AdminLTE {

    @Autowired
    private BlockManagementService blockManagementService;

    @Hook("install")
    public void install() {
        // Create a list of the default blocks that will be used...
        blockManagementService.addBlockToTheme("admin_lte", "header", "page_title_block", -100, new HashMap<>());
        blockManagementService.addBlockToTheme("admin_lte", "breadcrumb", "breadcrumb_block", -100, breadcrumbBlockSettings());
        blockManagementService.addBlockToTheme("admin_lte", "highlighted", "status_message_block", -100, new HashMap<>());
        blockManagementService.addBlockToTheme("admin_lte", "sidebar", "menu_block", -100, primaryMenuSettings());
        blockManagementService.addBlockToTheme("admin_lte", "content", "system_tasks_block", -101, taskBlockSettings());
        blockManagementService.addBlockToTheme("admin_lte", "content", "main_content_block", -100, new HashMap<>());
    }

    private Map<String, Object> primaryMenuSettings() {
        Map<String, Object> settings = new HashMap<String, Object>();
        settings.put("menu_class", "nav navbar-nav");
        settings.put("active_item_class", "active");
        settings.put("menu", "main_navigation");
        return settings;
    }

    private Map<String, Object> taskBlockSettings() {
        Map<String, Object> map = new HashMap<>();
        map.put("menu", "main_navigation");
        return map;
    }

    private Map<String, Object> breadcrumbBlockSettings() {
        Map<String, Object> map = new HashMap<>();
        map.put("menu", "main_navigation");
        return map;
    }

    // Execute CSS AND JS Hooks
    @Hook("html_css")
    public void attachCSS(List<HeaderRenderedElement> css) {
        css.add(new LinkElement("stylesheet", "/bower_components/bootstrap/dist/css/bootstrap.min.css", "text/css"));
        css.add(new LinkElement("stylesheet", "/bower_components/font-awesome/css/font-awesome.min.css", "text/css"));
        css.add(new LinkElement("stylesheet", "/bower_components/Ionicons/css/ionicons.min.css", "text/css"));
        css.add(new LinkElement("stylesheet", "/bower_components/AdminLTE/dist/css/AdminLTE.min.css", "text/css"));
        css.add(new LinkElement("stylesheet", "/bower_components/AdminLTE/dist/css/skins/_all-skins.min.css", "text/css"));
        css.add(new LinkElement("stylesheet", "/css/admin_lte_overrides.css", "text/css"));
    }

    @Hook("html_js_top")
    public void hookJSTop(List<ScriptElement> js) {
        js.add(new ScriptElement("/bower_components/jquery/dist/jquery.min.js", "text/javascript"));
        js.add(new ScriptElement("/bower_components/html5shiv/dist/html5shiv.min.js", "text/javascript"));
        js.add(new ScriptElement("/bower_components/respond/dest/respond.min.js", "text/javascript"));
    }

    @Hook("html_js_bottom")
    public void hookJSBottom(List<ScriptElement> js) {
        js.add(new ScriptElement("/bower_components/bootstrap/dist/js/bootstrap.min.js", "text/javascript"));
        js.add(new ScriptElement("/bower_components/AdminLTE/dist/js/app.min.js", "text/javascript"));
    }

    @Hook("element_alter")
    public void hookElementAlter(RenderedElement element) {
        if (element == null) {
            return;
        }

        if (element instanceof com.pungwe.cms.core.form.element.FormElement) {
            element.addClass("form");
        }

        if (element instanceof FormRenderedElement && !(element instanceof InputButtonElement || element instanceof ButtonElement)) {
            element.addClass("form-control");
        }

        if (element instanceof InputButtonElement || element instanceof ButtonElement) {
            if (!element.getClasses().contains("close")) {
                element.addClass("btn", "btn-default");
            }
        }

        if (element instanceof DetailListElement) {
            element.addClass("dl-horizontal");
        }

        if (element instanceof TableElement) {
            ((TableElement) element).addClass("table", "table-striped");
        }
    }

    @Hook("preprocess_template")
    public void hookContentAlterAdminMenuListPages(String template, Map<String, Object> model) {
        if (!(template.equals("admin/structure") || template.equals("admin/reports") || template.equals("admin/system"))) {
            return;
        }
        DivElement element = (DivElement) model.get("content");
        element.addClass("list-group");
        element.getContent().stream().forEach(renderedElement -> {
            AnchorElement anchorElement = (AnchorElement) renderedElement;
            anchorElement.addClass("list-group-item");
            anchorElement.getContent().forEach(child -> {
                if (child instanceof HeaderElement) {
                    ((HeaderElement) child).addClass("list-group-item-heading");
                } else if (child instanceof TextFormatElement && ((TextFormatElement)child).getType() == TextFormatElement.Type.P) {
                    ((TextFormatElement) child).addClass("list-group-item-text");
                }
            });
        });
    }

    @Hook("preprocess_template")
    public void hookContentAlterAdminMenuPages(String template, Map<String, Object> model) {

        if (model.containsKey("actions") && model.get("actions") instanceof List) {
            ((List<RenderedElement>) model.get("actions")).forEach(renderedElement -> {
                if (!(renderedElement instanceof AnchorElement)) {
                    return;
                }
                AnchorElement addButton = (AnchorElement) renderedElement;
                addButton.addClass("btn btn-primary");
                TextFormatElement icon = new TextFormatElement(TextFormatElement.Type.I);
                icon.addClass("gyphicon", "glyphicon-plus");
                addButton.getContent().add(0, icon);
            });
        }
    }

    @Hook("block_alter")
    public void hookAlterSystemTasksBlock(ModelAndView blockModel) {
        if (!blockModel.getModel().getOrDefault("blockName", "").equals("system_tasks_block")) {
            return;
        }

        List<RenderedElement> elements = (List<RenderedElement>) blockModel.getModel().get("content");
        if (elements.size() > 0) {
            elements.get(0).addClass("nav", "nav-tabs");
            ((ListElement) elements.get(0)).getItems().forEach(item -> {
                item.addAttribute("role", "presentation");
            });
        }
    }

    @Hook("block_alter")
    public void hookAlterStatusMessageBlock(ModelAndView blockModel) {
        if (!blockModel.getModel().getOrDefault("blockName", "").equals("status_message_block")) {
            return;
        }
        final List<RenderedElement> elements = (List<RenderedElement>)blockModel.getModel().get("content");
        final List<RenderedElement> wrapped = new ArrayList<>(elements.size());
        elements.forEach(element -> {
            if (element instanceof ListElement && element.getClasses().contains("status-message")) {
                DivElement wrapper = new DivElement();
                wrapper.addClass("alert", "alert-dismissable");
                if (element.getClasses().contains("error-message")) {
                    wrapper.addClass("alert-danger");
                }
                wrapper.addAttribute("role", "alert");
                SpanElement buttonDismissContent = new SpanElement("&times;");
                buttonDismissContent.addAttribute("aria-hidden", "true");
                ButtonElement dismissButton = new ButtonElement(ButtonElement.ButtonType.BUTTON, buttonDismissContent);
                dismissButton.addAttribute("data-dismiss", "alert");
                dismissButton.addAttribute("aria-label", "Close");
                dismissButton.addClass("close");
                wrapper.addContent(
                        dismissButton,
                        new TextFormatElement(TextFormatElement.Type.P, new TextFormatElement(TextFormatElement.Type.STRONG, "Sorry, but there was a problem!")),
                        element
                );
                wrapped.add(wrapper);
            }
        });
        if (wrapped.size() > 0) {
            blockModel.addObject("content", wrapped);
        }
    }

    @Hook("block_alter")
    public void hookAlterPrimaryMenuBlock(ModelAndView blockModel) {
        if (!blockModel.getModel().getOrDefault("blockName", "").equals("menu_block")) {
            return;
        }

        List<RenderedElement> elements = (List<RenderedElement>) blockModel.getModel().get("content");
        if (elements.size() > 0) {
            elements.get(0).setClasses("sidebar-menu");
            ((ListElement) elements.get(0)).getItems().forEach(item -> {
                if (!item.getAttributes().containsKey("data-menu-item-name")) {
                    return;
                }
                item.addAttribute("role", "presentation");
                AnchorElement anchorElement = (AnchorElement) item.getContent().get(0);
                // Wrap the menu item into a span and reset the internal content
                SpanElement content = new SpanElement();
                TextFormatElement icon = new TextFormatElement(TextFormatElement.Type.I);
                if (item.getAttribute("data-menu-item-name").equals("structure") && item.getContent().size() > 0) {
                    icon.addClass("ion-network");
                    // Add a space
                    content.addContent(new PlainTextElement("&nbsp;"));
                } else if (item.getAttribute("data-menu-item-name").equals("dashboard")) {
                    icon.addClass("ion-speedometer");
                    // Add a space
                    content.addContent(new PlainTextElement("&nbsp;"));
                } else if (item.getAttribute("data-menu-item-name").equals("content")) {
                    icon.addClass("ion-document-text");
                    // Add a space
                    content.addContent(new PlainTextElement("&nbsp;"));
                } else if (item.getAttribute("data-menu-item-name").equals("configuration")) {
                    icon.addClass("ion-settings");
                    // Add a space
                    content.addContent(new PlainTextElement("&nbsp;"));
                } else if (item.getAttribute("data-menu-item-name").equals("reporting")) {
                    icon.addClass("ion-connection-bars");
                    // Add a space
                    content.addContent(new PlainTextElement("&nbsp;"));
                } else if (item.getAttribute("data-menu-item-name").equals("people")) {
                    icon.addClass("ion-ios-people");
                    // Add a space
                    content.addContent(new PlainTextElement("&nbsp;"));
                } else if (item.getAttribute("data-menu-item-name").equals("modules")) {
                    icon.addClass("fa fa-plug");
                } else if (item.getAttribute("data-menu-item-name").equals("appearance")) {
                    icon.addClass("ion-paintbrush");
                    // Add a space
                    content.addContent(new PlainTextElement("&nbsp;"));
                } else {
                    icon.addClass("fa fa-circle-o");
                }
                // Add the content
                content.addContent(anchorElement.getContent());
                // Set the icon
                anchorElement.setContent(icon, content);
            });
        }
    }

    @Hook("element_wrapper")
    public RenderedElement hookElementWrapper(RenderedElement element) {

        // Always do a null check
        if (element == null) {
            return element;
        }

        if (element instanceof TableElement) {
            DivElement wrapper = new DivElement();
            wrapper.addClass("table-responsive");
            wrapper.addContent(element);
            return wrapper;
        }

        if (element instanceof AbstractFormRenderedElement && !(element instanceof InputButtonElement || element instanceof ButtonElement)) {
            DivElement wrapper = new DivElement();
            wrapper.addClass("form-group");
            wrapper.addContent(element);
            if (((AbstractFormRenderedElement) element).hasError()) {
                wrapper.addClass("has-error");
            }
            return wrapper;
        }
        return element;
    }


    public BlockManagementService getBlockManagementService() {
        return blockManagementService;
    }

    public void setBlockManagementService(BlockManagementService blockManagementService) {
        this.blockManagementService = blockManagementService;
    }
}
