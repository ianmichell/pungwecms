package com.pungwe.cms.modules.text;

import com.pungwe.cms.core.annotations.stereotypes.Module;
import com.pungwe.cms.core.annotations.util.Hook;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by ian on 03/01/2016.
 */
@Module(
		name = "text",
		description = "Extension to the spring field type. Provides formatted text")
@ComponentScan("com.pungwe.cms.modules.text")
public class TextModule {
    
    @Hook("install")
    public void install() {
        
    }
}
