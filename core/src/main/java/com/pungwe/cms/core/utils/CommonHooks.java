package com.pungwe.cms.core.utils;

/**
 * Created by ian on 09/01/2016.
 */
public interface CommonHooks {

	String INSTALL = "install";
	String UNINSTALL = "uninstall";
	String UPDATE = "update_";
	String ALTER = "alter_";
	String PREPROCESS = "preprocess_";
	String THEME = "theme";

	// Preprocess
	String PREPROCESS_VARIABLES = "preprocess_variables";
	String PREPROCESS_HTML = "preprocess_html";

	// Alter Form
	String ALTER_FORM = "alter_form";
	String ALTER_FORM_BY_NAME_PREFIX = "alter_form_";

	// Alter Entity
	String ALTER_ENTITY = "alter_entity";
	String ALTER_ENTITY_BY_NAME_PREIFX = "alter_entity_";

}
