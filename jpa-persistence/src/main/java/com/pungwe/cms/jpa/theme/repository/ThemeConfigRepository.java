package com.pungwe.cms.jpa.theme.repository;

import com.pungwe.cms.core.theme.ThemeConfig;
import com.pungwe.cms.jpa.theme.ThemeConfigImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Created by ian on 13/02/2016.
 */
@Repository
public interface ThemeConfigRepository extends JpaRepository<ThemeConfigImpl, String> {

	List<ThemeConfigImpl> findAllByEnabled(boolean enabled);
	List<ThemeConfigImpl> findAllByNameIn(Collection<String> name);
	Long deleteByNameIn(Collection<String> modules);

	@Cacheable("themes.findDefaultTheme")
	@Query("FROM ThemeConfigImpl WHERE defaultTheme = true")
	Optional<ThemeConfigImpl> findDefaultTheme();

	@Cacheable("themes.findDefaultAdminTheme")
	@Query("FROM ThemeConfigImpl WHERE defaultAdminTheme = true")
	Optional<ThemeConfigImpl> findDefaultAdminTheme();

}
