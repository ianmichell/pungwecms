package com.pungwe.cms.core.menu.impl;

import com.pungwe.cms.core.menu.MenuInfo;

/**
 * Created by ian on 17/03/2016.
 */
public class MenuInfoImpl implements MenuInfo {

	String id;
	String title;
	String description;
	String language;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getLanguage() {
		return language;
	}

	@Override
	public void setLanguage(String language) {
		this.language = language;
	}
}
