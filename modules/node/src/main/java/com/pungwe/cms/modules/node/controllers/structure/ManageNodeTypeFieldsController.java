package com.pungwe.cms.modules.node.controllers.structure;

import com.pungwe.cms.core.annotations.ui.MenuItem;
import com.pungwe.cms.core.entity.controller.AbstractEntityTypeFieldController;
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
        name = "fields",
        parent = "admin.structure.content-types",
        title = "Manage Fields",
        pattern = true,
        task = true
)
@Controller
@RequestMapping("/admin/structure/content-types/{nodeType}/fields")
public class ManageNodeTypeFieldsController extends AbstractEntityTypeFieldController {

    @Override
    public String getFormId() {
        return "manage-node-fields";
    }


    @RequestMapping(method = RequestMethod.GET)
    public Callable<String> fields(Model model) {
        return () -> {
            return "node_type/add";
        };
    }

    @Override
    protected void buildInternal(FormElement element) {

    }
}
