package com.pungwe.cms.jpa.theme.repository;

import com.pungwe.cms.jpa.theme.ThemeConfigImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Created by ian on 13/02/2016.
 */
@Repository
public interface ThemeConfigRepository extends JpaRepository<ThemeConfigImpl, String> {

	List<ThemeConfigImpl> findAllByEnabled(boolean enabled);
	List<ThemeConfigImpl> findAllByNameIn(Collection<String> name);
	Long deleteByNameIn(Collection<String> modules);

}
