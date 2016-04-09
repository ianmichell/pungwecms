package com.pungwe.cms.core.menu.controller;

import com.pungwe.cms.core.annotations.ui.MenuItem;
import com.pungwe.cms.core.entity.EntityDefinition;
import com.pungwe.cms.core.form.element.FormElement;
import com.pungwe.cms.core.menu.MenuInfo;
import com.pungwe.cms.core.menu.services.MenuManagementService;
import com.pungwe.cms.core.system.exceptions.ResourceNotFoundException;
import com.pungwe.cms.core.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;
import java.util.concurrent.Callable;

/**
 * Created by ian on 19/03/2016.
 */
@Controller
@RequestMapping("/admin/structure/menu/edit/{menuInfoId}")
@MenuItem(
        menu = "system",
        parent = "admin.structure.menus",
        name = "edit",
        title = "Edit Menu",
        description = "Edit Menu",
        pattern = true,
		task = true
)
public class MenuEditController extends AbstractMenuInfoController {

	@Autowired
	protected MenuManagementService menuManagementService;

	@Override
	public String getFormId() {
		return "edit_menu_form";
	}

	@Override
	protected void buildInternal(FormElement<MenuInfo> element) {
		final String id = Utils.getRequestPathVariable("menuInfoId");
		// Fetch the entity and set the relevant values
		Optional<MenuInfo> info = menuManagementService.getMenu(id, LocaleContextHolder.getLocale().getLanguage());
		if (!info.isPresent()) {
			// Throw a 404
			throw new ResourceNotFoundException("Could not find menu with id: " + id);
		}
		element.setTargetObject(info.get());
		element.setValue("title", 0, info.get().getTitle());
		element.setValue("description", 0, info.get().getDescription());
		element.setValue("language", 0, info.get().getLanguage());

		element.addSubmitHandler((form, variables) -> {
			if (form.getErrors() != null && form.getErrors().hasErrors()) {
				return;
			}
			menuManagementService.updateMenu(id, (String)form.getValue("title", 0), (String)form.getValue("description", 0), (String)form.getValue("language", 0));
		});
	}

	@ModelAttribute("menuInfoId")
    public String menuInfoId(@PathVariable("menuInfoId") String menuInfoId) {
        return menuInfoId;
    }

	@RequestMapping(method=RequestMethod.GET)
	public Callable<String> edit(Model model) {
		return () -> {
			model.addAttribute("title", "Edit Menu");
			return "menu/edit";
		};
	}

	@RequestMapping(method = RequestMethod.POST)
	public Callable<String> submit(@PathVariable("menuInfoId") String id, ModelMap model, @Valid @ModelAttribute("form") FormElement<EntityDefinition> form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		return () -> {
			if (bindingResult.hasErrors()) {
				return "menu/edit";
			}
			form.getSubmitHandlers().forEach(f -> {
				f.submit(form, model);
			});
			redirectAttributes.addFlashAttribute("status.message.success", "Success! You've updated a new menu: " + form.getValue("title", 0));
			redirectAttributes.addAttribute("menuInfoId", id);
			return "redirect:/admin/structure/menu/edit/{menuInfoId}";
		};
	}
}
