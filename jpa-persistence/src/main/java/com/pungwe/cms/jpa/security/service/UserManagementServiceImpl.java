/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.jpa.security.service;

import com.pungwe.cms.core.security.entity.UserProfile;
import com.pungwe.cms.core.security.entity.UserRole;
import com.pungwe.cms.core.security.service.UserManagementService;
import com.pungwe.cms.jpa.security.entity.UserProfileImpl;
import com.pungwe.cms.jpa.security.entity.UserRoleImpl;
import com.pungwe.cms.jpa.security.repository.UserProfileRepository;
import com.pungwe.cms.jpa.security.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserManagementServiceImpl implements UserManagementService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserProfileImpl> userProfile = userProfileRepository.findOneByUsername(username);
        if (!userProfile.isPresent()) {
            throw new UsernameNotFoundException("Could not find user: " + username);
        }

        List<UserRoleImpl> roles = userRoleRepository.findAllByRoleIn(userProfile.get().getRoles());

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
    public List<UserRole> listRolesByName(Collection<String> roles) {
        return userRoleRepository.findAllByRoleIn(roles).stream().map(userRole -> (UserRole)userRole).collect(Collectors.toList());
    }

    @Override
    public void createUser(String username, String password, List<String> administrators) {
        UserProfileImpl userProfile = new UserProfileImpl();
        userProfile.setId(UUID.randomUUID().toString());
        userProfile.setUsername(username);
        userProfile.setPassword(new BCryptPasswordEncoder().encode(password));
        userProfile.setRoles(administrators.stream().collect(Collectors.toSet()));
        userProfile.setAccountNonExpired(true);
        userProfile.setAccountNonLocked(true);
        userProfile.setCredentialsNonExpired(true);
        userProfile.setEnabled(true);
        userProfileRepository.save(userProfile);
    }

    @Override
    public void createRole(String roleName, List<GrantedAuthority> authorities) {
        UserRoleImpl role = new UserRoleImpl();
        role.setRole(roleName);
        role.setAuthorities(authorities.stream().map(a -> new SimpleGrantedAuthority(a.getAuthority())).collect(Collectors.toSet()));
        userRoleRepository.save(role);
    }

    @Override
    public boolean userExistsByUsername(String username) {
        return userProfileRepository.findOneByUsername(username).isPresent();
    }

    @Override
    public boolean roleExists(String id) {
        return userRoleRepository.exists(id);
    }

    @Override
    public Page<UserProfile> listUsers(int page, int maxRows) {
        PageRequest pageable = new PageRequest(page, maxRows);
        Page<UserProfileImpl> users = userProfileRepository.findAll(pageable);
        return new PageImpl<UserProfile>(users.getContent().stream()
                .map(userProfile -> (UserProfile)userProfile).collect(Collectors.toList()), pageable, users.getTotalElements());
    }
}
