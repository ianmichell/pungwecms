package com.pungwe.cms.modules.actuator.controller;

import com.pungwe.cms.core.annotations.ui.MenuItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.concurrent.Callable;

/**
 * Created by 917903 on 23/03/2016.
 */
@Controller
@RequestMapping("/admin/reporting/system/environment")
@MenuItem(
        name = "environment",
        parent = "admin.reporting.system",
        menu = "system",
        title = "Environment",
        description = "Displays a table of system of environment information"
)
public class SystemEnvironmentController {

    @RequestMapping(method= RequestMethod.GET)
    public Callable<String> index(Model model) {
        return () -> {
            model.addAttribute("title", "Environment");
          return "actuator/environment";
        };
    }
}
