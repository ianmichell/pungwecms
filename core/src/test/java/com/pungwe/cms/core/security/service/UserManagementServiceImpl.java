/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.security.service;

import com.pungwe.cms.core.security.entity.UserProfile;
import com.pungwe.cms.core.security.entity.UserProfileImpl;
import com.pungwe.cms.core.security.entity.UserRole;
import com.pungwe.cms.core.security.entity.UserRoleImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserManagementServiceImpl implements UserManagementService {

    private static List<UserProfile> users = new LinkedList<>();
    private static List<UserRole> roles = new LinkedList<>();

    @Override
    public void createUser(String username, String password, List<String> roles) {
        UserProfileImpl user = new UserProfileImpl();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(username);
        user.setRoles(roles.stream().collect(Collectors.toSet()));
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        users.add(user);
    }

    @Override
    public void createRole(String roleName, String label, String description, List<GrantedAuthority> authorities) {
        UserRoleImpl role = new UserRoleImpl();
        role.setRole(roleName);
        role.setLabel(label);
        role.setDescription(description);
        role.setAuthorities(authorities.stream().map(a -> new SimpleGrantedAuthority(a.getAuthority())).collect(Collectors.toSet()));
        roles.add(role);
    }

    @Override
    public boolean userExistsByUsername(final String username) {
        return users.stream().filter(u -> u.getUsername().equals(username)).findFirst().isPresent();
    }

    @Override
    public boolean roleExists(final String role) {
        return roles.stream().filter(r -> r.getRole().equals(role)).findFirst().isPresent();
    }

    @Override
    public Page<UserProfile> listUsers(int page, int maxRows) {
        PageRequest pageRequest =  new PageRequest(page, maxRows);
        PageImpl<UserProfile> results = new PageImpl<UserProfile>(users.subList(pageRequest.getOffset(),
                pageRequest.getPageSize() > users.size() ? users.size() : pageRequest.getPageSize()), pageRequest, users.size());
        return results;
    }

    @Override
    public List<UserRole> listRolesByName(Collection<String> roles) {
        return UserManagementServiceImpl.roles.stream().filter(r -> roles.contains(r.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserProfile> userProfile = users.stream().filter(u -> u.getUsername().equals(username)).findFirst();
        if (!userProfile.isPresent()) {
            throw new UsernameNotFoundException("Could not find user: " + username);
        }

        List<UserRole> roles = listRolesByName(userProfile.get().getRoles());

        return new User(
                userProfile.get().getUsername(),
                userProfile.get().getPassword(),
                userProfile.get().isEnabled(),
                userProfile.get().isAccountNonExpired(),
                userProfile.get().isCredentialsNonExpired(),
                userProfile.get().isAccountNonLocked(),
                roles.stream().flatMap(userRole -> userRole.getAuthorities().stream()).collect(Collectors.toList())
        );
    }

    @Override
    public List<UserRole> listRoles() {
        return roles;
    }
}
