package com.pungwe.cms.core.entity.impl;

import com.pungwe.cms.core.entity.EntityTypeInfo;

/**
 * Created by ian on 05/12/2015.
 */
public class EntityTypeInfoImpl implements EntityTypeInfo {
	private String type;
	private String bundle;

	public EntityTypeInfoImpl(String type, String bundle) {
		this.type = type;
		this.bundle = bundle;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getBundle() {
		return bundle;
	}

	@Override
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}
}
