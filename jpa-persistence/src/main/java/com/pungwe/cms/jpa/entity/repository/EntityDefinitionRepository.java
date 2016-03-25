package com.pungwe.cms.jpa.entity.repository;

import com.pungwe.cms.jpa.entity.EntityDefinitionImpl;
import com.pungwe.cms.jpa.entity.EntityTypeInfoImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ian on 13/03/2016.
 */
@Repository
public interface EntityDefinitionRepository extends JpaRepository<EntityDefinitionImpl, EntityTypeInfoImpl> {

	@Query("SELECT d FROM EntityDefinitionImpl d WHERE d.id.type = :type")
	Page<EntityDefinitionImpl> findByType(@Param("type") String type, Pageable page);

}
