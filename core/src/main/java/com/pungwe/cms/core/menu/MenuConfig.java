package com.pungwe.cms.core.menu;

/**
 * Created by ian on 08/03/2016.
 */
public interface MenuConfig {

	String getId();
	void setId(String id);

	String getMenu();
	void setMenu(String menu);

	String getParent();
	void setParent(String parent);

	String getName();
	void setName(String name);

	String getTitle();
	void setTitle(String title);

	String getDescription();
	void setDescription(String description);

	String getUrl();
	void setUrl(String url);

	String getTarget();
	void setTarget(String target);

	boolean isExternal();
	void setExternal(boolean external);
}
