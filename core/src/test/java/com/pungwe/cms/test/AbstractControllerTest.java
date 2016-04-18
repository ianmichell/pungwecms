package com.pungwe.cms.test;

import com.pungwe.cms.core.block.services.BlockManagementService;
import com.pungwe.cms.core.module.config.ModuleContextConfig;
import com.pungwe.cms.core.module.services.ModuleManagementService;
import com.pungwe.cms.core.theme.services.ThemeConfigService;
import com.pungwe.cms.core.theme.services.ThemeManagementService;
import com.pungwe.cms.themes.test.TestTheme;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * Created by 917903 on 18/04/2016.
 */
public abstract class AbstractControllerTest extends AbstractWebTest {

    @Autowired
    protected ModuleManagementService moduleManagementService;

    @Autowired
    protected BlockManagementService blockManagementService;

    @Autowired
    protected ThemeManagementService themeManagementService;

    @Autowired
    protected ThemeConfigService themeConfigService;

    @Override
    @Before
    public void setup() throws Exception {
        super.setup();
        AnnotationConfigWebApplicationContext moduleContext = new AnnotationConfigWebApplicationContext();
        moduleContext.setServletContext(webApplicationContext.getServletContext());
        moduleContext.setId("module-application-context");
        moduleContext.setParent(webApplicationContext);
        moduleManagementService.setModuleContext(moduleContext);
        moduleManagementService.startEnabledModules();
        moduleContext.refresh();

        themeConfigService.registerTheme(TestTheme.class,
                TestTheme.class.getProtectionDomain().getCodeSource().getLocation());

        themeConfigService.removeThemes("missing");

        themeManagementService.enable("test_theme");
        themeManagementService.setDefaultTheme("test_theme");
        themeManagementService.setDefaultAdminTheme("test_theme");
        themeManagementService.startEnabledThemes();
    }
}
