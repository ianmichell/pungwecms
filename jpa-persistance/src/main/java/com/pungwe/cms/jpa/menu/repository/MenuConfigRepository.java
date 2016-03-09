package com.pungwe.cms.jpa.menu.repository;

import com.pungwe.cms.jpa.menu.MenuConfigImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ian on 09/03/2016.
 */
@Repository
public interface MenuConfigRepository extends JpaRepository<MenuConfigImpl, String> {
}
