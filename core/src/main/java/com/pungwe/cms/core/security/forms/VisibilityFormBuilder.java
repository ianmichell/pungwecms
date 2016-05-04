package com.pungwe.cms.core.security.forms;

import com.pungwe.cms.core.element.ContentRenderedElement;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.basic.DivElement;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by 917903 on 29/04/2016.
 */
@Component
public class VisibilityFormBuilder {

    public RenderedElement buildVisibilityForm(Map<String, Object> settings) {
        // Create the wrapper
        DivElement visibilityWrapper = new DivElement();
        visibilityWrapper.addClass("visibility-form-wrapper");

        // Tabs / Categories

        // Content for each bit


        return visibilityWrapper;
    }
}
