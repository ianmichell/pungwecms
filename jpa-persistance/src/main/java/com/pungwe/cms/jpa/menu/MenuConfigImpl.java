package com.pungwe.cms.jpa.menu;

import com.pungwe.cms.core.menu.MenuConfig;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

/**
 * Created by ian on 09/03/2016.
 */
@Entity
@Table(name="menu_config")
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

	@Override
	@Id
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	@NotBlank
	@Column(name = "menu", nullable = false)
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
	@NotBlank
	@Column(name = "menu_item_name", nullable = false)
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	@NotBlank
	@Column(name = "title", nullable = false)
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	@Lob
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	@NotBlank
	@Column(name="url", length = 1024)
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
}
