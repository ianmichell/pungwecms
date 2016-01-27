package com.pungwe.cms.modules;

import com.pungwe.cms.core.annotations.Module;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by ian on 21/01/2016.
 */
//@Configuration
@Module(
		name = "test_module",
		label = "Test Module",
		description = "This is a unit test module for Module Management Service"
)
@ComponentScan("com.pungwe.cms.modules.test")
public class TestModule {

}
