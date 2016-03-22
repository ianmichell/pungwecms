package com.pungwe.cms.modules.node.controllers.structure;

import com.pungwe.cms.core.annotations.ui.MenuItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.concurrent.Callable;

/**
 * Created by 917903 on 22/03/2016.
 */
@MenuItem(
        menu = "system",
        name = "edit",
        parent = "admin.structure.content-types",
        title = "Edit",
        pattern = true,
        task = true
)
@Controller
@RequestMapping("/admin/structure/content-types/{nodeType}/edit")
public class EditNodeTypesController {

    @ModelAttribute("nodeType")
    public String nodeType(@PathVariable("nodeType") String nodeType) {
        return nodeType;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Callable<String> edit(@PathVariable("nodeType") String nodeType, Model model) {
        return () -> {
            return "node_type/add";
        };
    }
}
