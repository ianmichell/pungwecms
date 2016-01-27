package com.pungwe.cms.core.application;

import com.pungwe.cms.core.config.BaseApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

/**
 * Created by ian on 20/01/2016.
 */
public class PungweCMSApplication {

	public static void start(String[] args) {
		ApplicationContext ctx = SpringApplication.run(BaseApplicationConfig.class, args);
	}

	public static void main(String[] args) {
		start(args);
	}
}
