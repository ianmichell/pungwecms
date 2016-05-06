package com.pungwe.cms.core.security.forms;

import com.pungwe.cms.core.annotations.util.Hook;
import com.pungwe.cms.core.element.ContentRenderedElement;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.basic.DivElement;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.pungwe.cms.core.element.basic.PlainTextElement;
import com.pungwe.cms.core.form.element.CheckboxElement;
import com.pungwe.cms.core.form.element.FieldsetElement;
import com.pungwe.cms.core.form.element.RadioElement;
import com.pungwe.cms.core.form.element.TextareaElement;
import com.pungwe.cms.core.security.entity.UserRole;
import com.pungwe.cms.core.security.service.UserManagementService;
import com.pungwe.cms.core.utils.services.HookService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.pungwe.cms.core.utils.Utils.translate;

/**
 * Created by 917903 on 29/04/2016.
 */
@Component
public class VisibilityFormBuilder {

    @Autowired
    private HookService hookService;

    @Autowired
    private UserManagementService userManagementService;

    public RenderedElement buildVisibilityForm(Map<String, Object> settings) {
        // Create the wrapper
        DivElement visibilityWrapper = new DivElement();
        visibilityWrapper.addClass("visibility-form-wrapper");
        visibilityWrapper.setHtmlId("visibility-form");

        // Tabs / Categories
        Map<String, FieldsetElement> map = new LinkedHashMap<>();

        // Set up path view settings
        pathView(map, settings);

        // Set up role view settings
        createRoleView(map, settings);

        try {
            hookService.executeHook("visibility_form_alter", map, settings);
        } catch (Exception ex) {
            // do nothing
        }

        for (FieldsetElement value : map.values()) {
            visibilityWrapper.addContent(value);
        }

        return visibilityWrapper;
    }

    private void pathView(Map<String, FieldsetElement> map, final Map<String, Object> settings) {
        FieldsetElement pathView = new FieldsetElement();
        pathView.setLegend(translate("Paths"));

        TextareaElement textarea = new TextareaElement();
        textarea.setName("visibility_paths");
        textarea.setLabel(translate("Paths"));
        textarea.setHtmlId("visibility_paths");
        textarea.setDefaultValue((String)settings.get("visibility_paths"));

        pathView.addContent(textarea);

        String pathAccessCondition = (String)settings.get("visibility_path_condition");

        RadioElement radioCondition = new RadioElement();
        radioCondition.setName("visibility_path_condition");
        radioCondition.addOption(translate("Show for listed paths"), "show");
        radioCondition.addOption(translate("Hide for listed paths"), "hide");
        radioCondition.setDefaultValue(StringUtils.isBlank(pathAccessCondition) ? "show" : pathAccessCondition);

        pathView.addContent(radioCondition);

        map.put("paths", pathView);
    }

    private void createRoleView(final Map<String, FieldsetElement> map, final Map<String, Object> settings) {
        FieldsetElement roleGroup = new FieldsetElement();
        roleGroup.setLegend(new PlainTextElement(translate("Roles")));

        List<UserRole> roles = userManagementService.listRoles();

        final List<String> selectedRoles = (List<String>)settings.get("visibility_roles");

        final AtomicInteger roleDelta = new AtomicInteger();
        DivElement checkboxWrapper = new DivElement();
        checkboxWrapper.addClass("checkboxes");
        checkboxWrapper.addContent(roles.stream().map(role -> {
            CheckboxElement checkbox = new CheckboxElement();
            checkbox.setName("visibility_role");
            checkbox.setDelta(roleDelta.getAndIncrement());
            checkbox.setDefaultValue(role.getRole());
            checkbox.addContent(translate(role.getLabel()));
            checkbox.setChecked(selectedRoles == null ? false : selectedRoles.contains(role.getRole()));
            return checkbox;
        }).collect(Collectors.toList()));

        roleGroup.addContent(checkboxWrapper);

        String roleAccessCondition = (String)settings.get("visibility_role_access_condition");

        RadioElement radioCondition = new RadioElement();
        radioCondition.setName("visibility_role_access_condition");
        radioCondition.addOption(translate("Grant access to selected roles"), "grant");
        radioCondition.addOption(translate("Deny access to selected roles"), "deny");
        radioCondition.setDefaultValue(StringUtils.isBlank(roleAccessCondition) ? "grant" : roleAccessCondition);

        roleGroup.addContent(radioCondition);

        map.put("roles", roleGroup);
    }
}
