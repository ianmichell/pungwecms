package com.pungwe.cms.core.application.context;

import com.pungwe.cms.core.module.services.ModuleManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Created by ian on 27/01/2016.
 */
@Component
public class ContextLoadListener {

	private static Logger logger = LoggerFactory.getLogger(ContextLoadListener.class);

	@Autowired
	private ModuleManagementService managementService;

	@EventListener
	public void applicationContextRefreshed(ContextRefreshedEvent event) {
		logger.info("Started Main Application Context", event);
		/*managementService.scan();
		managementService.startEnabledModules();*/
	}
}
