package com.pungwe.cms.jpa.block;

import com.pungwe.cms.core.block.BlockConfig;
import com.pungwe.cms.jpa.converter.HashMapBinaryJSONConverter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ian on 05/03/2016.
 */
@Entity
@Table(name="blocks")
public class BlockConfigImpl implements BlockConfig<String, BlockConfigImpl> {

	protected String id;
	protected String name;
    protected String adminTitle;
    protected String description;
	protected String theme;
	protected String region;
	protected String context;
	protected int weight;
	protected Map<String, Object> settings;

	@Id
	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	@NotBlank
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

    @Column(name = "admin_title")
    @Override
    public String getAdminTitle() {
        return adminTitle;
    }

    @Override
    public void setAdminTitle(String adminTitle) {
        this.adminTitle = adminTitle;
    }

    @Column(name = "description")
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
	public String getTheme() {
		return theme;
	}

	@Override
	public void setTheme(String theme) {
		this.theme = theme;
	}

	@Override
	public String getRegion() {
		return region;
	}

	@Override
	public void setRegion(String region) {
		this.region = region;
	}

	@Override
	public String getContext() {
		return context;
	}

	@Override
	public void setContext(String context) {
		this.context = context;
	}

	@Override
	@Column(name = "weight")
	public int getWeight() {
		return weight;
	}

	@Override
	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	@Column(name = "settings")
	@Convert(converter = HashMapBinaryJSONConverter.class)
	public Map<String, Object> getSettings() {
		if (settings == null) {
			settings = new HashMap<>();
		}
		return settings;
	}

	@Override
	public void setSettings(Map<String, Object> settings) {
		this.settings = settings;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BlockConfigImpl that = (BlockConfigImpl) o;

		return id.equals(that.id);

	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

}
