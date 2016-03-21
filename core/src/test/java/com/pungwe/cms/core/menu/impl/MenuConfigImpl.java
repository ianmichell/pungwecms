package com.pungwe.cms.core.menu.impl;

import com.pungwe.cms.core.menu.MenuConfig;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

/**
 * Created by ian on 09/03/2016.
 */
public class MenuConfigImpl implements MenuConfig {

	protected String id;
	protected String menu;
	protected String parent;
	protected String name;
	protected String title;
	protected String description;
	protected String url;
	protected String target;
	protected boolean external;
	protected String path;
	protected int weight = 0;
    protected boolean pattern = false;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getMenu() {
		return menu;
	}

	@Override
	public void setMenu(String menu) {
		this.menu = menu;
	}

	@Override
	public String getParent() {
		return parent;
	}

	@Override
	public void setParent(String parent) {
		this.parent = parent;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
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
	public String getUrl() {
		return url;
	}

	@Override
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String getTarget() {
		return target;
	}

	@Override
	public void setTarget(String target) {
		this.target = target;
	}

	@Override
	public boolean isExternal() {
		return external;
	}

	@Override
	public void setExternal(boolean external) {
		this.external = external;
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

    @Override
    public boolean isPattern() {
        return pattern;
    }

    @Override
    public void setPattern(boolean pattern) {
        this.pattern = pattern;
    }
}
