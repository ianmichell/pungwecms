package com.pungwe.cms.core.menu.controller;

import com.pungwe.cms.core.annotations.ui.MenuItem;
import com.pungwe.cms.core.element.basic.PlainTextElement;
import com.pungwe.cms.core.menu.MenuInfo;
import com.pungwe.cms.core.menu.services.MenuManagementService;
import com.pungwe.cms.core.system.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;
import java.util.concurrent.Callable;

import static com.pungwe.cms.core.utils.Utils.translate;

/**
 * Created by 917903 on 22/03/2016.
 */
@Controller
@RequestMapping("/admin/structure/menu/links/{menuInfoId}/{language}")
@MenuItem(
        menu = "system",
        parent = "admin.structure.menus",
        name = "manage_links",
        title = "Manage Links",
        description = "Manage Menu Links",
        pattern = true,
        task = true
)
public class MenuLinksController {

    @Autowired
    MenuManagementService menuManagementService;

    @ModelAttribute("menuInfoId")
    public String menuInfoId(@PathVariable("menuInfoId") String menuInfoId) {
        return menuInfoId;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Callable<String> index(@ModelAttribute("menu") MenuInfo menu, Model model) {
        return () -> {
            model.addAttribute("title", translate("Manage Links"));
            model.addAttribute("content", new PlainTextElement(""));
            return "menu/links";
        };
    }
}
