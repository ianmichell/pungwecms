/*
 * Copyright (c) 2016. Ian Michell
 */

package com.pungwe.cms.core.block.config;

import com.pungwe.cms.core.block.builder.AdminPageBuilder;
import com.pungwe.cms.core.block.builder.BlockPageBuilder;
import com.pungwe.cms.core.block.controller.BlockAddController;
import com.pungwe.cms.core.block.controller.BlockLayoutController;
import com.pungwe.cms.core.block.controller.BlockPlacementController;
import com.pungwe.cms.core.block.controller.BlockSettingsController;
import com.pungwe.cms.core.block.services.BlockManagementService;
import com.pungwe.cms.core.block.system.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BlockSystemConfig {

    //================================================
    // Services
    //================================================

    @Bean
    public BlockManagementService blockManagementService() {
        return new BlockManagementService();
    }

    //================================================
    // Builders
    //================================================

    @Bean
    public BlockPageBuilder blockPageBuilder() {
        return new BlockPageBuilder();
    }

    @Bean
    public AdminPageBuilder adminPageBuilder() {
        return new AdminPageBuilder();
    }

    //================================================
    // Controllers
    //================================================

    @Bean
    public BlockLayoutController blockLayoutController() {
        return new BlockLayoutController();
    }

    @Bean
    public BlockSettingsController blockSettingsController() {
        return new BlockSettingsController();
    }

    @Bean
    BlockAddController blockAddController() {
        return new BlockAddController();
    }

    @Bean
    public BlockPlacementController blockPlacementController() {
        return new BlockPlacementController();
    }

    //================================================
    // Blocks
    //================================================

    @Bean(name = "breadcrumb_block")
    public BreadcrumbBlock breadcrumbBlock() {
        return new BreadcrumbBlock();
    }

    @Bean(name = "main_content_block")
    public MainContentBlock mainContentBlock() {
        return new MainContentBlock();
    }

    @Bean(name = "page_title_block")
    public PageTitleBlock pageTitleBlock() {
        return new PageTitleBlock();
    }

    @Bean(name = "status_message_block")
    public StatusMessageBlock statusMessageBlock() {
        return new StatusMessageBlock();
    }

    @Bean(name = "system_tasks_block")
    public SystemTasksBlock systemTasksBlock() {
        return new SystemTasksBlock();
    }

    @Bean(name = "menu_block")
    public MenuBlock menuBlock() {
        return new MenuBlock();
    }
}
