package com.pungwe.cms.core.theme.services;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Created by ian on 29/01/2016.
 */
@Service
public class ThemeManagementService {

	private AnnotationConfigApplicationContext themeContext;

	public ApplicationContext getThemeContext() {
		return themeContext;
	}
}
