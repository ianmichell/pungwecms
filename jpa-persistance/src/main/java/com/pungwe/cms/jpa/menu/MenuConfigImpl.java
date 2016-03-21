package com.pungwe.cms.jpa.menu;

import com.pungwe.cms.core.menu.MenuConfig;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

import javax.persistence.*;

/**
 * Created by ian on 09/03/2016.
 */
@Entity
@Table(name="menu_config", indexes = {
		@Index(columnList = "menu", name = "MENU_CONFIG_IDX_MENU_PARENT"),
		@Index(columnList = "parent", name = "MENU_CONFIG_IDX_MENU_PARENT"),
		@Index(columnList = "menu", name = "MENU_CONFIG_IDX_MENU_ITEM"),
		@Index(columnList = "menu_item_path", name = "MENU_CONFIG_IDX_MENU_ITEM"),
		@Index(columnList = "menu", name = "MENU_CONFIG_IDX_MENU_URL"),
		@Index(columnList = "url", name = "MENU_CONFIG_IDX_MENY_URL")
}, uniqueConstraints = {
		@UniqueConstraint(columnNames = {"menu", "parent", "menu_item_name"})
})
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
	@Column(name="url", length = 255)
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

	@Column(name = "menu_item_path", nullable = false)
	@NotBlank
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
