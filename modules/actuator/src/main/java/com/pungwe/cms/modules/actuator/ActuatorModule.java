package com.pungwe.cms.modules.actuator;

import com.pungwe.cms.core.annotations.Module;
import org.springframework.boot.actuate.autoconfigure.*;
import org.springframework.context.annotation.Import;

/**
 * Created by ian on 20/01/2016.
 */
@Module(
		name = "actuator",
		description = "A module for production deployment based on spring boot actuator"
)
@Import({
		// Actuator
		ManagementServerPropertiesAutoConfiguration.class,
		EndpointMBeanExportAutoConfiguration.class,
		HealthIndicatorAutoConfiguration.class,
		AuditAutoConfiguration.class,
		CacheStatisticsAutoConfiguration.class,
		EndpointAutoConfiguration.class,
		EndpointWebMvcAutoConfiguration.class,
		TraceWebFilterAutoConfiguration.class,
		TraceRepositoryAutoConfiguration.class,
})
public class ActuatorModule {
}
