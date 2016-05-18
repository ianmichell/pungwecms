package com.pungwe.cms.modules.jquery;

import com.pungwe.cms.core.annotations.stereotypes.Module;
import com.pungwe.cms.core.annotations.system.ModuleDependency;
import com.pungwe.cms.core.annotations.util.Hook;
import com.pungwe.cms.core.element.basic.ScriptElement;

import java.util.List;

/**
 * Created by ian on 08/05/2016.
 */
@Module(
        name = "jquery",
        description = "A JQuery javascript library module",
        label = "JQuery"
)
public class JQueryModule {

    @Hook("html_js_top")
    public void hookJSTop(List<ScriptElement> js) {
        js.add(new ScriptElement("/bower_components/jquery/dist/jquery.min.js", "text/javascript"));
    }
}
