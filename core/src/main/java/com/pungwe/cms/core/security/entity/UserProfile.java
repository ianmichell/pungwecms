/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.security.entity;

import java.util.Map;
import java.util.Set;

public interface UserProfile {

    /**
     * Unique identifier for the user (over and above the username - used for reference and can't change).
     *
     * @return the unique user id
     */
    String getId();

    /**
     * Sets the id of the user. This should generally be a UUID.
     *
     * @param id the id of the user
     */
    void setId(String id);

    /**
     * Returns the password used to authenticate the user.
     *
     * @return the password
     */
    String getPassword();

    /**
     * Sets the password to be used to authenticate the user
     *
     * @param password the password
     */
    void setPassword(String password);

    /**
     * Returns the username used to authenticate the user. Cannot return <code>null</code>.
     *
     * @return the username (never <code>null</code>)
     */
    String getUsername();

    /**
     * Sets the username
     *
     * @param username the usenrame
     */
    void setUsername(String username);

    /**
     * Indicates whether the user's account has expired. An expired account cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user's account is valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    boolean isAccountNonExpired();

    /**
     * Sets teh account expiry property
     *
     * @param accountNonExpired
     */
    void setAccountNonExpired(boolean accountNonExpired);

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
     */
    boolean isAccountNonLocked();

    /**
     * Sets the account to be locked or not
     *
     * @param accountNonLocked the boolean indicating the locked status of the account, true for unlocked, false for locked
     *
     */
    void setAccountNonLocked(boolean accountNonLocked);

    /**
     * Indicates whether the user's credentials (password) has expired. Expired
     * credentials prevent authentication.
     *
     * @return <code>true</code> if the user's credentials are valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    boolean isCredentialsNonExpired();

    /**
     * Sets the credentials expiry boolean
     *
     * @param credentialsNonExpired true for not expired, false for expired
     */
    void setCredentialsNonExpired(boolean credentialsNonExpired);

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
     */
    boolean isEnabled();

    /**
     * Set the account to enabled or not
     *
     * @param enabled true for enabled, false for disabled
     */
    void setEnabled(boolean enabled);

    /**
     * List of roles that the user is a member of
     *
     * @return the list of roles for the user
     */
    Set<String> getRoles();

    /**
     * Set the roles for the user
     *
     * @param roles the roles being applied to the user
     */
    void setRoles(Set<String> roles);

    /**
     * Get the user profile data (which is configurable)
     *
     * @return the stored user profile data
     */
    Map<String, Object> getUserProfile();

    /**
     * Sets the user profile data
     *
     * @param profile the user profile data
     */
    void setUserProfile(Map<String, Object> profile);
}
