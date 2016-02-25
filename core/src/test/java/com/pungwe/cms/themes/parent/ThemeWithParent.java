package com.pungwe.cms.themes.parent;

import com.pungwe.cms.core.annotations.Hook;
import com.pungwe.cms.core.annotations.Theme;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ian on 13/02/2016.
 */
@Theme(name="theme_with_parent", label="Test Theme with Parent", description = "This is a test theme with a parent", parent = "test_theme")
public class ThemeWithParent {

	@Hook("theme")
	public Map<String, String> hookTheme() {
		Map<String, String> theme = new HashMap<>();
		theme.put("my_view", "theme_with_parent/my_view");
		return theme;
	}
}
