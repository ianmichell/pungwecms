/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.security.controller;

import com.pungwe.cms.core.element.basic.DivElement;
import com.pungwe.cms.core.form.controller.AbstractFormController;
import com.pungwe.cms.core.form.element.FormElement;
import com.pungwe.cms.core.form.element.InputButtonElement;
import com.pungwe.cms.core.form.element.PasswordElement;
import com.pungwe.cms.core.form.element.StringElement;
import com.pungwe.cms.core.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Callable;

import static com.pungwe.cms.core.utils.Utils.translate;

@Controller
@RequestMapping("/login")
public class LoginController extends AbstractFormController {

    @Override
    public String getFormId() {
        return "login-form";
    }

    @Override
    public void build(FormElement element) {

        DivElement container = new DivElement();
        container.addClass("login-form");

        StringElement username = new StringElement();
        username.setName("username");
        username.setLabel(translate("Username"));
        PasswordElement password = new PasswordElement();
        password.setName("password");
        password.setLabel(translate("Password"));

        container.addContent(username, password);

        container.addContent(new InputButtonElement(InputButtonElement.InputButtonType.SUBMIT, translate("Login")));

        element.addContent(container);
    }

    @Override
    public void validate(FormElement form, Errors errors) {

    }

    @ModelAttribute("title")
    public String title() {
        return translate("Login");
    }

    @RequestMapping(method = RequestMethod.GET)
    public Callable<String> get(HttpServletRequest request, Model model) {
        return () -> "security/login";
    }

}
