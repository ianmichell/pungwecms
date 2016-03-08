package com.pungwe.cms.jpa.block.repository;

import com.pungwe.cms.jpa.block.BlockConfigImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Created by ian on 05/03/2016.
 */
@Repository
public interface BlockConfigRepository extends JpaRepository<BlockConfigImpl, String> {

	List<BlockConfigImpl> findAllByTheme(String theme);
	List<BlockConfigImpl> findAllByThemeAndNameIn(String theme, Collection<String> name);
}