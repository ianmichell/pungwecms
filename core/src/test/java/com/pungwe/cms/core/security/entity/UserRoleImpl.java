/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.security.entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.LinkedHashSet;
import java.util.Set;

public class UserRoleImpl implements UserRole {

    private String role;
    private String label;
    private String description;
    private Set<SimpleGrantedAuthority> authorities;

    @Override
    public String getRole() {
        return role;
    }

    @Override
    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Set<SimpleGrantedAuthority> getAuthorities() {
        if (authorities == null) {
            authorities = new LinkedHashSet<>();
        }
        return authorities;
    }

    @Override
    public void setAuthorities(Set<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
