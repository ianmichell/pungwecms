package com.pungwe.cms.core.security.controller;

import com.pungwe.cms.core.annotations.ui.MenuItem;
import com.pungwe.cms.core.element.basic.TableElement;
import com.pungwe.cms.core.security.entity.UserProfile;
import com.pungwe.cms.core.security.entity.UserRole;
import com.pungwe.cms.core.security.service.UserManagementService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

/**
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
 * Created by ian on 28/03/2016.
 */
@MenuItem(
        menu = "system",
        parent = "admin",
        name = "people",
        title = "People",
        description = "Administer users, permissions and roles",
        weight = -100
)
@Controller
@RequestMapping("/admin/people")
public class UserAdminstrationController {

    @Autowired
    protected UserManagementService userManagementService;

    @ModelAttribute("title")
    public String title() {
        return "People";
    }

    @RequestMapping(method = RequestMethod.GET)
    public Callable<String> get(
            Model model,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "maxRows", defaultValue = "25") int maxRows) {
        return () -> {

            final Page<UserProfile> users = userManagementService.listUsers(page, maxRows);

            TableElement tableElement = new TableElement();
            tableElement.addHeaderRow(
                    new TableElement.Header("Username"),
                    new TableElement.Header("Status"),
                    new TableElement.Header("Roles"),
                    new TableElement.Header("Operartions")
            );

            users.getContent().forEach(userProfile -> {

                List<UserRole> roles = userManagementService.listRolesByName(userProfile.getRoles());

                String status = "Active";
                if (!userProfile.isEnabled()) {
                    status = "Disabled";
                } else if (!userProfile.isAccountNonLocked()) {
                    status = "Locked";
                } else if (!userProfile.isAccountNonExpired()) {
                    status = "Account Expired";
                } else if (!userProfile.isCredentialsNonExpired()) {
                    status = "Credentials Expired";
                }

                tableElement.addRow(
                        new TableElement.Column(userProfile.getUsername()),
                        new TableElement.Column(status),
                        new TableElement.Column(roles.stream().map(
                                userRole -> StringUtils.isNotBlank(userRole.getLabel()) ? userRole.getLabel()
                                        : userRole.getRole()).collect(Collectors.joining(", "))),
                        new TableElement.Column()
                );
            });

            model.addAttribute("content", tableElement);
            return "admin/people/index";
        };
    }
}
