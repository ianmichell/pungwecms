package com.pungwe.cms.core.system.services;

import com.pungwe.cms.core.block.builder.AdminPageBuilder;
import com.pungwe.cms.core.block.builder.BlockPageBuilder;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.system.builder.PageBuilder;
import com.pungwe.cms.core.system.element.templates.AdminPageElement;
import com.pungwe.cms.core.system.element.templates.PageElement;
import com.pungwe.cms.core.theme.services.ThemeManagementService;
import com.pungwe.cms.core.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by ian on 05/03/2016.
 */
@Service
public class PageBuilderService {

    @Autowired
    protected ThemeManagementService themeManagementService;

    @Autowired
    protected ApplicationContext applicationContext;

    // Uses the http request and the content object to load and build a page based on the URL and current theme! Just LIKE MAGIC!
    public PageElement buildPage(final HttpServletRequest request, final Map<String, Object> model) {
        Map<String, String> regions = themeManagementService.getRegionsForDefaultThemeByRequest();
        // Build the page element...
        PageElement pageElement = new PageElement();

        // Build the page with a page builder! Defaults to BlockPageBuilder
        // FIXME: Build with a page manager.
        // Default page builder
        PageBuilder builder = applicationContext.getBean(BlockPageBuilder.class);
        if (Utils.matchesPathPattern("/admin/**")) {
            builder = applicationContext.getBean(AdminPageBuilder.class);
            pageElement = new AdminPageElement();
        } else {
            for (String region : regions.keySet()) {
                pageElement.addRegion(region, new LinkedList<RenderedElement>());
            }
        }
        builder.build(request, pageElement, model);
        return pageElement;
    }
}
