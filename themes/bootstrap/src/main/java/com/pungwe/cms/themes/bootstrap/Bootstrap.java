package com.pungwe.cms.themes.bootstrap;

import com.pungwe.cms.core.annotations.stereotypes.Theme;
import com.pungwe.cms.core.annotations.util.Hook;
import com.pungwe.cms.core.block.services.BlockManagementService;
import com.pungwe.cms.core.element.AbstractContentElement;
import com.pungwe.cms.core.element.HeaderRenderedElement;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.basic.*;
import com.pungwe.cms.core.form.FormRenderedElement;
import com.pungwe.cms.core.form.element.AbstractFormRenderedElement;
import com.pungwe.cms.core.form.element.ButtonElement;
import com.pungwe.cms.core.form.element.FormElement;
import com.pungwe.cms.core.form.element.InputButtonRenderedElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ian on 08/03/2016.
 */
@Theme(name = "bootstrap", label = "Bootstrap Theme", description = "The official bootstrap theme!", regions = {
		"navigation", "header", "highlighted", "breadcrumb", "sidebar_first", "content", "sidebar_second", "footer"
})
public class Bootstrap {

	@Autowired
	protected BlockManagementService blockManagementService;

	@Hook("install")
	public void install() {
		// Create a list of the default blocks that will be used...
		blockManagementService.addBlockToTheme("bootstrap", "header", "page_title_block", -100, new HashMap<>());
		blockManagementService.addBlockToTheme("bootstrap", "breadcrumb", "breadcrumb_block", -100, new HashMap<>());
		blockManagementService.addBlockToTheme("bootstrap", "highlighted", "status_message_block", -100, new HashMap<>());
		blockManagementService.addBlockToTheme("bootstrap", "navigation", "primary_menu_block", -100, primaryMenuSettings());
		blockManagementService.addBlockToTheme("bootstrap", "content", "main_content_block", -100, new HashMap<>());
		blockManagementService.addBlockToTheme("bootstrap", "sidebar_second", "secondary_menu_block", -100, new HashMap<>());
	}

	private Map<String, Object> primaryMenuSettings() {
		Map<String, Object> settings = new HashMap<String, Object>();
		settings.put("menu_class", "nav navbar-nav");
		settings.put("active_item_class", "active");
		return settings;
	}

	// Execute CSS AND JS Hooks
	@Hook("html_css")
	public void attachCSS(List<HeaderRenderedElement> css) {
		css.add(new LinkElement("stylesheet", "/bower_components/bootstrap/dist/css/bootstrap.min.css", "text/css"));
	}

	@Hook("html_js_top")
	public void hookJSTop(List<ScriptElement> js) {
		js.add(new ScriptElement("/bower_components/jquery/dist/jquery.min.js", "text/javascript"));
	}

	@Hook("html_js_bottom")
	public void hookJSBottom(List<ScriptElement> js) {
		js.add(new ScriptElement("/bower_components/bootstrap/dist/js/bootstrap.min.js", "text/javascript"));
	}

	@Hook("element_alter")
	public void hookElementAlter(RenderedElement element) {
		if (element == null) {
			return;
		}

		if (element instanceof FormElement) {
			element.addClass("form");
		}

		if (element instanceof FormRenderedElement && !(element instanceof InputButtonRenderedElement || element instanceof ButtonElement)) {
			element.addClass("form-control");
		}

		if (element instanceof InputButtonRenderedElement || element instanceof ButtonElement) {
			if (!element.getClasses().contains("close")) {
				element.addClass("btn", "btn-default");
			}
		}

		if (element instanceof TableElement) {
			((TableElement) element).addClass("table", "table-striped");
		}
	}

	@Hook("preprocess_template")
	public void hookContentAlterAdminMenuListPages(String template, Map<String, Object> model) {
		if (!(template.equals("admin/structure") || template.equals("admin/reports") || template.equals("admin/system"))) {
			return;
		}
		DivElement element = (DivElement) model.get("content");
		element.addClass("list-group");
		element.getContent().stream().forEach(renderedElement -> {
			AnchorElement anchorElement = (AnchorElement) renderedElement;
			anchorElement.addClass("list-group-item");
			anchorElement.getContent().forEach(child -> {
				if (child instanceof HeaderElement) {
					((HeaderElement) child).addClass("list-group-item-heading");
				} else if (child instanceof ParagraphElement) {
					((ParagraphElement) child).addClass("list-group-item-text");
				}
			});
		});
	}

	@Hook("preprocess_template")
	public void hookContentAlterAdminMenuPages(String template, Map<String, Object> model) {
		if (!template.equals("menu/index")) {
			return;
		}

		AnchorElement addButton = (AnchorElement)model.get("action");
		addButton.addClass("btn btn-primary");
		TextFormatElement icon = new TextFormatElement(TextFormatElement.Type.I);
		icon.addClass("gyphicon", "glyphicon-plus");
		addButton.getContent().add(0, icon);
	}

	@Hook("element_wrapper")
	public RenderedElement hookElementWrapper(RenderedElement element) {

		// Always do a null check
		if (element == null) {
			return element;
		}

		if (element instanceof TableElement) {
			DivElement wrapper = new DivElement();
			wrapper.addClass("table-responsive");
			wrapper.addContent(element);
			return wrapper;
		}

		if (element instanceof ListElement && element.getClasses().contains("status-message")) {
			DivElement wrapper = new DivElement();
			wrapper.addClass("alert", "alert-dismissable");
			if (element.getClasses().contains("error-message")) {
				wrapper.addClass("alert-danger");
			}
			wrapper.addAttribute("role", "alert");
			SpanElement buttonDismissContent = new SpanElement("&times;");
			buttonDismissContent.addAttribute("aria-hidden", "true");
			ButtonElement dismissButton = new ButtonElement(ButtonElement.ButtonType.BUTTON, buttonDismissContent);
			dismissButton.addAttribute("data-dismiss", "alert");
			dismissButton.addAttribute("aria-label", "Close");
			dismissButton.addClass("close");
			wrapper.addContent(
					dismissButton,
					new ParagraphElement(new TextFormatElement(TextFormatElement.Type.STRONG, "Sorry, but there was a problem!")),
					element
			);
			return wrapper;
		}

		if (element instanceof AbstractFormRenderedElement && !(element instanceof InputButtonRenderedElement || element instanceof ButtonElement)) {
			DivElement wrapper = new DivElement();
			wrapper.addClass("form-group");
			wrapper.addContent(element);
			if (((AbstractFormRenderedElement) element).hasError()) {
				wrapper.addClass("has-error");
			}
			return wrapper;
		}
		return element;
	}
}