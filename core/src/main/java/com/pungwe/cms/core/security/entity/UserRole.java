/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.security.entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

public interface UserRole {

    String getRole();
    void setRole(String role);

    String getLabel();
    void setLabel(String label);

    Set<SimpleGrantedAuthority> getAuthorities();
    void setAuthorities(Set<SimpleGrantedAuthority> authorities);

}
