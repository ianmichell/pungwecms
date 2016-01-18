package com.pungwe.cms.core.entity;

import java.io.Serializable;

/**
 * Created by ian on 05/12/2015.
 */
public interface EntityTypeInfo extends Serializable {
	String getType();

	void setType(String type);

	String getBundle();

	void setBundle(String bundle);
}
