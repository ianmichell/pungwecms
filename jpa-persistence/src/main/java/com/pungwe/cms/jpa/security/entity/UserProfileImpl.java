/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.jpa.security.entity;

import com.pungwe.cms.core.security.entity.UserProfile;
import com.pungwe.cms.jpa.converter.HashMapBinaryJSONConverter;
import com.pungwe.cms.jpa.converter.LinkedHashBinaryJSONConverter;
import com.pungwe.cms.jpa.converter.TreeSetBinaryJSONConverter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "users", indexes = {@Index(columnList = "username", unique = true, name = "USERNAME_UNIQUE_IDX")})
public class UserProfileImpl implements UserProfile {

    private String id;
    private String password;
    private String username;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private Set<String> roles;
    private Map<String, Object> userProfile;

    @Override
    @Id
    @NotBlank
    @Column(name = "uid")
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    @Column(name = "user_password", nullable = false)
    @NotBlank
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    @NotBlank
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    @Column(name = "account_non_expired")
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    @Column(name = "account_non_locked")
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    @Column(name = "credential_non_expired")
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    @Override
    @Column(name = "account_enabled")
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    @Convert(converter = LinkedHashBinaryJSONConverter.class)
    @Column(name = "roles")
    public Set<String> getRoles() {
        return roles;
    }

    @Override
    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    @Override
    @Convert(converter = HashMapBinaryJSONConverter.class)
    @Column(name = "profile_data")
    public Map<String, Object> getUserProfile() {
        return userProfile;
    }

    @Override
    public void setUserProfile(Map<String, Object> userProfile) {
        this.userProfile = userProfile;
    }
}
