/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.security.service;

import com.pungwe.cms.core.annotations.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Base Roles
@Roles({
        @RoleDefinition(
                name = "anonymous",
                label = "Anonymous User"
        ),
        @RoleDefinition(
                name = "authenticated_user",
                label = "Authenticated User"
        ),
        @RoleDefinition(
                name = "administrators",
                label = "Administrators"
        )
})
// Base Permission Categories
@PermissionCategories({
        @PermissionCategory(
                name = "system",
                label = "System"
        ),
        @PermissionCategory(
                name = "users",
                label = "Users, roles and permissions"
        ),
        @PermissionCategory(
                name = "blocks",
                label = "Blocks"
        )
})
// Base Permissions
@Permissions({
        @PermissionDefinition(
                name = "administer_blocks",
                label = "Administer Blocks",
                description = "Give users access to administer blocks",
                category = "blocks"
        ),
        @PermissionDefinition(
                name = "administration_pages",
                label = "Administration pages and help",
                description = "Access to administration area",
                category = "system",
                defaultRoles = {"authenticated_user"}
        ),
        @PermissionDefinition(
                name = "administer_menus",
                label = "Administer Menus and Menu Items",
                category = "system"
        ),
        @PermissionDefinition(
                name = "administer_site",
                label = "Administer Site Configuration",
                category = "system"
        ),
        @PermissionDefinition(
                name = "administer_themes",
                label = "Administer Themes",
                category = "system"
        ),
        @PermissionDefinition(
                name = "administer_modules",
                label = "Administer Modules",
                category = "system"
        ),
        @PermissionDefinition(
                name = "maintenance_mode",
                label = "Put site into maintenance mode",
                category = "system"
        ),
        @PermissionDefinition(
                name = "site_reports",
                label = "Access site reports",
                category = "system"
        ),
})
@Service
public class PermissionService {

    private List<PermissionDefinition> permissions;
    private List<PermissionCategory> categories;

    public List<PermissionDefinition> getPermissions() {
        if (permissions == null) {
            permissions = new LinkedList<>();
        }
        return permissions;
    }

    public void setPermissions(List<PermissionDefinition> permissions) {
        this.permissions = permissions;
    }

    public void addPermission(PermissionDefinition... permissionDefinitions) {
        getPermissions().addAll(Arrays.asList(permissionDefinitions));
    }

    public Optional<PermissionDefinition> findPermission(String name) {
        return getPermissions().stream().filter(p -> p.name().equals(name)).findFirst();
    }

    public List<PermissionDefinition> findPermissionsByCategory(String category) {
        return getPermissions().stream().filter(p -> p.category().equals(category)).collect(Collectors.toList());
    }

    public List<PermissionCategory> getCategories() {
        if (categories == null) {
            categories = new LinkedList<>();
        }
        return categories;
    }

    public void setCategories(List<PermissionCategory> categories) {
        this.categories = categories;
    }

    public void addCategory(PermissionCategory... category) {
        getCategories().addAll(Arrays.asList(category));
    }

    public List<PermissionDefinition> findDefaultPermissionsForRole(String role) {
        return getPermissions().stream().filter(p -> Arrays.asList(p.defaultRoles()).contains(role))
                .collect(Collectors.toList());
    }
}
