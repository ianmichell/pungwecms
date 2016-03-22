package com.pungwe.cms.modules.node.controllers.structure;

import com.pungwe.cms.core.form.Form;
import com.pungwe.cms.core.form.element.FormElement;
import com.pungwe.cms.core.form.element.StringRenderedElement;
import com.pungwe.cms.core.form.element.TextareaRenderedElement;
import com.pungwe.cms.core.utils.services.HookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by 917903 on 22/03/2016.
 */
public abstract class AbstractNodeTypeFormController implements Form {

    @Autowired
    protected HookService hookService;

    @Override
    @ModelAttribute("form")
    public void build(FormElement element) throws InvocationTargetException, IllegalAccessException {

        // Title field
        StringRenderedElement title = new StringRenderedElement();
        title.setSize(60);
        title.setName("title");
        title.setPlaceholder("Content Type Title");
        title.setRequired(true);
        element.addContent(title);

        // Description field
        StringRenderedElement description = new StringRenderedElement();
        description.setName("description");
        description.setPlaceholder("Administrative description");
        description.setSize(255);
        description.setLabel("Description");
        element.addContent(description);

        StringRenderedElement bundleName = new StringRenderedElement();
        bundleName.setSize(60);
        bundleName.setPlaceholder("Bundle Name");
        bundleName.setRequired(true);
        bundleName.setName("bundle");
        element.addContent(bundleName);

        buildInternal(element);

        hookService.executeHook("form_alter", getFormId(), element);
    }

    @Override
    public void validate(FormElement form, Errors errors) {
        // do nothing
    }

    protected abstract void buildInternal(FormElement element);
}
