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
 * Created by Ian Michell on 30/03/2016.
 */
package com.pungwe.cms.core.security.service;

import com.pungwe.cms.core.annotations.security.*;
import com.pungwe.cms.core.security.entity.UserProfile;
import com.pungwe.cms.core.security.entity.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface UserManagementService extends UserDetailsService {

    void createUser(String username, String password, List<String> roles);
    void createRole(String roleName, String label, String description, List<GrantedAuthority> authorities);

    boolean userExistsByUsername(String username);
    boolean roleExists(String role);

    Page<UserProfile> listUsers(int page, int maxRows);

    List<UserRole> listRolesByName(Collection<String> roles);

    default PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
