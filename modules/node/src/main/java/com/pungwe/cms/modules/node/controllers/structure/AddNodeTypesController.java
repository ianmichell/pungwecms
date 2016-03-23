package com.pungwe.cms.modules.node.controllers.structure;

import com.pungwe.cms.core.annotations.ui.MenuItem;
import com.pungwe.cms.core.entity.controller.AbstractEntityTypeEditController;
import com.pungwe.cms.core.form.element.FormElement;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.concurrent.Callable;

/**
 * Created by 917903 on 22/03/2016.
 */
@MenuItem(
        menu = "system",
        name = "add",
        parent = "admin.structure.content-types",
        title = "Add a new Content Type"
)
@Controller
@RequestMapping("/admin/structure/content-types/add")
public class AddNodeTypesController extends AbstractEntityTypeEditController {

    @RequestMapping(method = RequestMethod.GET)
    public Callable<String> add(Model model) {
        return () -> {
            return "node_type/add";
        };
    }

    @Override
    protected void buildInternal(FormElement element) {
        element.addSubmitHandler(form -> {
            
        });
    }

    @Override
    public String getFormId() {
        return "add_node_form";
    }

//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    public Callable<String> add(Model model, @ModelAttribute("form") FormElement form, BindingResult result) {
//        return () -> {
//            return "node_type/add";
//        };
//    }
}
