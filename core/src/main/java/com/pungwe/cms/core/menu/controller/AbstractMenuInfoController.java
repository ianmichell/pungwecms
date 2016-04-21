package com.pungwe.cms.core.menu.controller;

import com.pungwe.cms.core.form.controller.AbstractFormController;
import com.pungwe.cms.core.form.element.FormElement;
import com.pungwe.cms.core.form.element.InputButtonElement;
import com.pungwe.cms.core.form.element.SingleSelectListElement;
import com.pungwe.cms.core.form.element.TextElement;
import com.pungwe.cms.core.menu.MenuInfo;
import com.pungwe.cms.core.utils.Utils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Locale;

import static com.pungwe.cms.core.utils.Utils.translate;

/**
 * Created by ian on 19/03/2016.
 */
public abstract class AbstractMenuInfoController extends AbstractFormController<MenuInfo> {

    @Override
    public void build(FormElement<MenuInfo> element) {
        TextElement title = new TextElement();
        title.setHtmlId(getFormId() + "_title");
        title.setLabel(translate("Title"));
        title.setName("title");
        title.setPlaceholder(translate("Menu Title"));
        title.setSize(20);
        title.setRequired(true);

        TextElement description = new TextElement();
        description.setHtmlId(getFormId() + "_description");
        description.setName("description");
        description.setPlaceholder(translate("Administrative Description"));
        description.setSize(100);
        description.setLabel(translate("Description"));

        // Language is populated from LocaleResolver
        final SingleSelectListElement language = new SingleSelectListElement();
        language.setHtmlId(getFormId() + "_language");
        language.setLabel(translate("Language"));
        language.setName("language");
        language.setRequired(true);
        final Locale currentLocale = LocaleContextHolder.getLocale();
        List<Locale> locales = Utils.getSortedLocales(currentLocale);
        locales.stream().forEach(locale -> {
            language.addOption(locale.getDisplayName(currentLocale), locale.getLanguage());
        });
        language.setDefaultValue(currentLocale.getLanguage());

        element.addContent(title, description, language);

        buildInternal(element);

        // Create Form
        element.setMethod(RequestMethod.POST.name());
        element.addContent(new InputButtonElement(InputButtonElement.InputButtonType.SUBMIT, "Save"));
    }

    protected abstract void buildInternal(FormElement<MenuInfo> element);

    @Override
    public void validate(FormElement form, Errors errors) {
        // Validate title and language regardless if the required flag is set...
        if (StringUtils.isEmpty(form.getValue("title", 0))) {
            errors.rejectValue("fields[" + form.getFormFieldIndex("title", 0) + "].value", "empty_title", translate("Please provide a menu title"));
            form.getField("title", 0).setError(true);
        }
        if (StringUtils.isEmpty(form.getValue("language", 0))) {
            errors.rejectValue("fields[" + form.getFormFieldIndex("language", 0) + "].value", "empty_language", translate("Please select a language"));
            form.getField("language", 0).setError(true);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = BindException.class)
    public void bindException() {
    }
}
