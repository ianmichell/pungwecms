/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.jpa.security.entity;

import com.pungwe.cms.core.security.entity.UserRole;
import com.pungwe.cms.jpa.converter.GrantedAuthoritySetJSONConverter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "user_roles")
public class UserRoleImpl implements UserRole {

    private String role;
    private String label;
    private Set<SimpleGrantedAuthority> authorities;

    @Override
    @Id
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

    @Override
    @Column(name = "authorities")
    @Convert(converter = GrantedAuthoritySetJSONConverter.class)
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
