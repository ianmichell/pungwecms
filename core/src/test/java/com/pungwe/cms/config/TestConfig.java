package com.pungwe.cms.config;

import com.pungwe.cms.core.block.services.BlockConfigServiceImpl;
import com.pungwe.cms.core.menu.impl.MenuConfigServiceImpl;
import com.pungwe.cms.core.menu.impl.MenuInfoServiceImpl;
import com.pungwe.cms.core.module.config.ModuleContextConfig;
import com.pungwe.cms.core.module.services.impl.ModuleConfigServiceImpl;
import com.pungwe.cms.core.theme.services.impl.ThemeConfigServiceImpl;
import com.pungwe.cms.core.utils.services.HookService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * )with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * Created by ian on 30/03/2016.
 */
@Configuration
@Import(ModuleContextConfig.class)
public class TestConfig {

    @Bean
    public ModuleConfigServiceImpl moduleConfigService() {
        return new ModuleConfigServiceImpl();
    }

    @Bean
    public ThemeConfigServiceImpl themeConfigService() {
        return new ThemeConfigServiceImpl();
    }

    @Bean
    public MenuConfigServiceImpl menuConfigService() {
        return new MenuConfigServiceImpl();
    }

    @Bean
    public MenuInfoServiceImpl menuInfoService() {
        return new MenuInfoServiceImpl();
    }

    @Bean
    public BlockConfigServiceImpl blockConfigService() {
        return new BlockConfigServiceImpl();
    }
}
