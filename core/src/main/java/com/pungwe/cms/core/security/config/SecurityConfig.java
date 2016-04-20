/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * )with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 * Created by ian on 30/03/2016.
 *
 */
package com.pungwe.cms.core.security.config;

import com.pungwe.cms.core.annotations.security.PermissionCategories;
import com.pungwe.cms.core.annotations.security.Permissions;
import com.pungwe.cms.core.annotations.security.RoleDefinition;
import com.pungwe.cms.core.annotations.security.Roles;
import com.pungwe.cms.core.security.field.UserCredentialsField;
import com.pungwe.cms.core.security.field.formatter.UserCredentialsFormatter;
import com.pungwe.cms.core.security.field.widget.UserCredentialsWidget;
import com.pungwe.cms.core.security.service.PermissionService;
import com.pungwe.cms.core.security.service.UserManagementService;
import com.pungwe.cms.core.utils.services.HookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@Import(SecurityAutoConfiguration.class)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserManagementService userManagementService;

    @Autowired
    PermissionService permissionService;

    @Autowired
    HookService hookService;

    @Autowired
    CacheManager cacheManager;

    @EventListener
    public void configurePermissionAndRoleDefinitions(final ContextRefreshedEvent event) {
        // Only run when the context is the module application context
        if (!event.getApplicationContext().getId().equals("module-application-context")) {
            return;
        }

        // Get the application context
        ApplicationContext context = event.getApplicationContext();

        final Map<String, Object> permissionDefinitions = new LinkedHashMap<>();
        final Map<String, Object> permissionCategories = new LinkedHashMap<>();
        final Map<String, Object> defaultRoles = new LinkedHashMap<>();

                // Get parent context permissions too
        ApplicationContext parentContext = context;
        while ((parentContext = parentContext.getParent()) != null) {
            Map<String, Object> permissions = parentContext.getBeansWithAnnotation(Permissions.class);
            permissionDefinitions.putAll(permissions);
            Map<String, Object> categories = parentContext.getBeansWithAnnotation(PermissionCategories.class);
            permissionCategories.putAll(categories);
            Map<String, Object> roles = parentContext.getBeansWithAnnotation(Roles.class);
            defaultRoles.putAll(roles);
        }
        // Don't store permission categories or the permissions themselves. We just need to cache these...
        Map<String, Object> permissions = context.getBeansWithAnnotation(Permissions.class);
        permissionDefinitions.putAll(permissions);

        // Add all the permissions to a central list of permissions across the website, no need to store these...
        permissionDefinitions.forEach((k, v) -> {
            Permissions p = AnnotationUtils.findAnnotation(v.getClass(), Permissions.class);
            if (p != null) {
                permissionService.addPermission(p.value());
            }
        });

        // Don't store permission categories or the permissions themselves. We just need to cache these...
        Map<String, Object> categories = context.getBeansWithAnnotation(PermissionCategories.class);
        permissionCategories.putAll(categories);

        // Add permission categories
        permissionCategories.forEach((k, v) -> {
            PermissionCategories p = AnnotationUtils.findAnnotation(v.getClass(), PermissionCategories.class);
            if (p != null) {
                permissionService.addCategory(p.value());
            }
        });


        Map<String, Object> roleBeans = context.getBeansWithAnnotation(Roles.class);
        defaultRoles.putAll(roleBeans);

        defaultRoles.forEach((s, o) -> {
            Roles roles = AnnotationUtils.findAnnotation(o.getClass(), Roles.class);
            if (roles == null) {
                return;
            }

            for (RoleDefinition role : roles.value()) {
                if (userManagementService.roleExists(role.name()) && !role.name().equals("administrators")) {
                    continue;
                }

                // Create administrator role
                if (role.name().equals("administrators")) {
                    List<GrantedAuthority> authorities = permissionService.getPermissions().stream()
                            .map(p -> new SimpleGrantedAuthority(p.name())).collect(Collectors.toList());
                    // Overwrite the administrator role...
                    userManagementService.createRole(role.name(), role.label(), role.description(), authorities);
                    return;
                }

                // Generate granted authorities from the names of each permission collected.
                List<GrantedAuthority> authorities = permissionService.findDefaultPermissionsForRole(role.name())
                        .stream().map(p -> new SimpleGrantedAuthority(p.name())).collect(Collectors.toList());
                userManagementService.createRole(role.name(), role.label(), role.description(), authorities);
            }

        });

        // Create the default user and role
        if (userManagementService.userExistsByUsername("admin")) {
            return;
        }

        userManagementService.createUser("admin", "admin", Arrays.asList("administrators"));
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userManagementService)
                .passwordEncoder(userManagementService.getPasswordEncoder());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**")
                .hasAuthority("administration_pages")
                .antMatchers("/admin/reporting/**")
                .hasAuthority("site_reports")
                .anyRequest()
                .permitAll()
                .and()
                .formLogin().permitAll().loginPage("/login")
                .usernameParameter("username[0].value")
                .passwordParameter("password[0].value")
                .defaultSuccessUrl("/")
                .and()
                .httpBasic();
    }

    @Bean(name = "user_credentials_formatter")
    public UserCredentialsFormatter userCredentialsFormatter() {
        return new UserCredentialsFormatter();
    }

    @Bean(name = "user_credentials_widget")
    public UserCredentialsWidget userCredentialsWidget() {
        return new UserCredentialsWidget();
    }

    @Bean(name = "user_credentials_field")
    public UserCredentialsField userCredentialsField() {
        return new UserCredentialsField();
    }
}
