package com.pungwe.cms.core.system.element.templates;

import com.pungwe.cms.core.annotations.ui.ThemeInfo;
import com.pungwe.cms.core.block.BlockConfig;
import com.pungwe.cms.core.element.RenderedElement;

import java.util.*;

/**
 * Created by ian on 28/02/2016.
 */
@ThemeInfo("system/admin_page")
public class AdminPageElement extends PageElement {


    public AdminPageElement() {
        super();
        for (String region : DEFAULT_REGIONS.keySet()) {
            addRegion(region, new LinkedList<RenderedElement>());
        }
    }
}
