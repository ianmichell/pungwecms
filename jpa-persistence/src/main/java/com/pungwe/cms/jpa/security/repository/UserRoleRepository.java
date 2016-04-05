/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.jpa.security.repository;

import com.pungwe.cms.jpa.security.entity.UserRoleImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleImpl, String> {

    List<UserRoleImpl> findAllByRoleIn(Collection<String> roles);

}
