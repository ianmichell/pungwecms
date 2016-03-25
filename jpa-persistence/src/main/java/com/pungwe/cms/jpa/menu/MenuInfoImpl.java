package com.pungwe.cms.jpa.menu;

import com.pungwe.cms.core.menu.MenuInfo;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * Created by ian on 17/03/2016.
 */
@Entity
@Table(name="menus", indexes = {@Index(columnList = "id, language", unique = true, name = "MENUS_IDX_ID_LANGUAGE")})
public class MenuInfoImpl implements MenuInfo {

	protected String id;
	protected String title;
	protected String description;
	protected String language;

	@Override
	@Id
	@NotBlank
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	@NotBlank
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
	@NotBlank
	public String getLanguage() {
		return language;
	}

	@Override
	public void setLanguage(String language) {
		this.language = language;
	}
}
