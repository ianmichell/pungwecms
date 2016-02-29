package com.pungwe.cms.modules.test;

import com.pungwe.cms.core.annotations.Hook;
import com.pungwe.cms.core.annotations.Module;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ian on 21/01/2016.
 */
//@Configuration
@Module(
		name = "test_module",
		label = "Test Module",
		description = "This is a unit test module for Module Management Service"
)
@ComponentScan("com.pungwe.cms.modules.test")
public class TestModule {

	@Hook("theme")
	public Map<String, String> hookTheme() {
		Map<String, String> theme = new HashMap<>();
		theme.put("my_view", "test_module/my_view");
		return theme;
	}

	@Hook("test_hook")
	public String testHook() {
		return "hooked";
	}

	@Hook("void_hook")
	public void voidHook() {
	}

	@Hook("no_callback_hook")
	public void noCallbackHook() {
	}

	@Hook("parameter_hook")
	public String parameterHook(String parameter) {
		return parameter;
	}
}
