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

import com.pungwe.cms.core.security.entity.UserProfile;
import com.pungwe.cms.core.security.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@Configuration
@Import(SecurityAutoConfiguration.class)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserManagementService userManagementService;

    @EventListener
    public void contextRefreshedEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // Only run when the context is the module application context
        if (!contextRefreshedEvent.getApplicationContext().getId().equals("module-application-context")) {
            return;
        }

        // Create the default user and role
        if (userManagementService.userExistsByUsername("admin")) {
            return;
        }

        if (!userManagementService.roleExists("administrators")) {
            userManagementService.createRole("administrators", Arrays.asList());
        }

        userManagementService.createUser("admin", "admin", Arrays.asList("administrators"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userManagementService).passwordEncoder(passwordEncoder());
    }
}
