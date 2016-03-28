package com.pungwe.cms.core.config;

//import org.springframework.boot.actuate.autoconfigure.*;

import com.pungwe.cms.core.annotations.stereotypes.Block;
import com.pungwe.cms.core.annotations.stereotypes.FieldFormatter;
import com.pungwe.cms.core.annotations.stereotypes.FieldWidget;
import com.pungwe.cms.core.annotations.stereotypes.Theme;
import com.pungwe.cms.core.annotations.stereotypes.FieldType;
import com.pungwe.cms.core.module.config.ModuleContextConfig;
import org.springframework.boot.autoconfigure.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ian on 20/01/2016.
 */
@Configuration()
@Import({
		PropertyPlaceholderAutoConfiguration.class,
		CacheAutoConfiguration.class
})
// We only want to scan the core package
@ComponentScan(
		basePackages = {"com.pungwe.cms.core"},
		includeFilters = {
				@ComponentScan.Filter(value = Service.class)
		},
		excludeFilters = {
				@ComponentScan.Filter(value = ModuleContextConfig.class, type = FilterType.ASSIGNABLE_TYPE),
				@ComponentScan.Filter(value = Block.class),
                @ComponentScan.Filter(value = Theme.class),
				@ComponentScan.Filter(value = Controller.class),
				@ComponentScan.Filter(value = RequestMapping.class),
				@ComponentScan.Filter(value = FieldType.class),
				@ComponentScan.Filter(value = FieldWidget.class),
				@ComponentScan.Filter(value = FieldFormatter.class)
		}
)
@EnableConfigurationProperties
@EnableCaching
public class BaseApplicationConfig {

	@Bean()
	@ConfigurationProperties(prefix = "modules.enabled")
	public List<String> defaultEnabledModules() {
		return new LinkedList<String>();
	}
}
