package com.pungwe.cms.jpa.entity;

import com.pungwe.cms.core.entity.EntityTypeInfo;

import javax.persistence.Embeddable;

/**
 * Created by ian on 05/12/2015.
 */
@Embeddable
public class EntityTypeInfoImpl implements EntityTypeInfo {

    private String type;
    private String bundle;

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
