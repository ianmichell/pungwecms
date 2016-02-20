package com.pungwe.cms.themes.test;

import com.pungwe.cms.core.annotations.Hook;
import com.pungwe.cms.core.annotations.Theme;

/**
 * Created by ian on 13/02/2016.
 */
@Theme(name="test_theme", label="Test Theme", description = "This is a test theme with no parent")
public class TestTheme {

	@Hook("install")
	public void install() {

	}
}
