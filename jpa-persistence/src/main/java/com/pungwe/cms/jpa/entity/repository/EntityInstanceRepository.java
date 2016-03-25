package com.pungwe.cms.jpa.entity.repository;

import com.pungwe.cms.jpa.entity.EntityInstanceIdImpl;
import com.pungwe.cms.jpa.entity.EntityInstanceImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ian on 13/03/2016.
 */
@Repository
public interface EntityInstanceRepository extends JpaRepository<EntityInstanceImpl, EntityInstanceIdImpl> {
}
