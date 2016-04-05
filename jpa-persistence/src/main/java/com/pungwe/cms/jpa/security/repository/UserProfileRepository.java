/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.jpa.security.repository;

import com.pungwe.cms.jpa.security.entity.UserProfileImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileImpl, String> {

    Optional<UserProfileImpl> findOneByUsername(String username);
}
