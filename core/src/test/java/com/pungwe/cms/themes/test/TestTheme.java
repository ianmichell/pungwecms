package com.pungwe.cms.themes.test;

import com.pungwe.cms.core.annotations.util.Hook;
import com.pungwe.cms.core.annotations.stereotypes.Theme;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ian on 13/02/2016.
 */
@Theme(name="test_theme", label="Test Theme", description = "This is a test theme with no parent")
public class TestTheme {

	@Hook("install")
	public void install() {

	}

	@Hook("theme")
	public Map<String, String> themeHook() {
		HashMap<String, String> theme = new HashMap<>();
		theme.put("my_view", "my_theme/my_view");
		return theme;
	}
}
