package com.pungwe.cms.themes.bootstrap;

import com.pungwe.cms.core.annotations.stereotypes.Theme;
import com.pungwe.cms.core.annotations.util.Hook;
import com.pungwe.cms.core.block.services.BlockManagementService;
import com.pungwe.cms.core.element.HeaderRenderedElement;
import com.pungwe.cms.core.element.basic.LinkElement;
import com.pungwe.cms.core.element.basic.ScriptElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

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
		blockManagementService.addBlockToTheme("bootstrap", "header", "page_title_block", -100);
		blockManagementService.addBlockToTheme("bootstrap", "breadcrumb", "breadcrumb_block", -100);
		blockManagementService.addBlockToTheme("bootstrap", "sidebar_first", "primary_menu_block", -100);
		blockManagementService.addBlockToTheme("bootstrap", "content", "main_content_block", -100);
		blockManagementService.addBlockToTheme("bootstrap", "sidebar_second", "secondary_menu_block", -100);
	}

	// Execute CSS AND JS Hooks
	@Hook("css")
	public void attachCSS(List<HeaderRenderedElement> css) {
		css.add(new LinkElement("stylesheet", "/bower_components/bootstrap/dist/css/bootstrap.min.css", "text/css"));
	}

	@Hook("js_top")
	public void hookJSTop(List<ScriptElement> js) {
		js.add(new ScriptElement("/bower_components/jquery/dist/jquery.min.js", "text/javascript"));
	}

	@Hook("js_bottom")
	public void hookJSBottom(List<ScriptElement> js) {
		js.add(new ScriptElement("/bower_components/bootstrap/dist/bootstrap.min.js", "text/javascript"));
	}
}
