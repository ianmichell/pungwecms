package com.pungwe.cms.modules.actuator.controller;

import com.pungwe.cms.core.element.basic.PlainTextElement;
import com.pungwe.cms.core.element.basic.TableElement;
import com.pungwe.cms.core.module.services.ModuleManagementService;
import com.pungwe.cms.core.theme.services.ThemeManagementService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.*;
import org.springframework.context.support.LiveBeansView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by ian on 13/03/2016.
 */
@Controller
@RequestMapping("/admin/reporting/system/health")
public class SystemHealthController {

	@Autowired
	List<HealthIndicator> healthIndicators;

	@RequestMapping(method= RequestMethod.GET)
	public Callable<String> health(final Model model) {
		return () -> {
			final TableElement tableElement = new TableElement();
			tableElement.addHeaderRow(new TableElement.Header(new PlainTextElement("Key")), new TableElement.Header(new PlainTextElement("Value")));
			healthIndicators.forEach(healthIndicator -> {
				healthIndicator.health().getDetails().forEach((key, value) -> {
					tableElement.addRow(
							new TableElement.Header(new PlainTextElement(getIndicatorName(healthIndicator))),
							new TableElement.Column(new PlainTextElement(key)),
							new TableElement.Column(new PlainTextElement(value.toString()))
					);
				});
				tableElement.addRow(
						new TableElement.Header(new PlainTextElement(getIndicatorName(healthIndicator))),
						new TableElement.Column(new PlainTextElement("Status")),
						new TableElement.Column(new PlainTextElement(healthIndicator.health().getStatus().getCode()))
				);
			});
			model.addAttribute("title", "System Health");
			model.addAttribute("content", tableElement);

			return "actuator/health";
		};
	}

	private String getIndicatorName(HealthIndicator indicator) {
		if (indicator instanceof ApplicationHealthIndicator) {
			return "Application";
		}
		if (indicator instanceof CassandraHealthIndicator) {
			return "Cassandra";
		}
		if (indicator instanceof MongoHealthIndicator) {
			return "MongoDB";
		}
		if (indicator instanceof DataSourceHealthIndicator) {
			return "Data Soruce";
		}
		if (indicator instanceof ElasticsearchHealthIndicator) {
			return "Elastic Search";
		}
		if (indicator instanceof DiskSpaceHealthIndicator) {
			return "Disk Space";
		}
		if (indicator instanceof JmsHealthIndicator) {
			return "JMS";
		}
		if (indicator instanceof MailHealthIndicator) {
			return "Mail";
		}
		if (indicator instanceof RabbitHealthIndicator) {
			return "Rabbit MQ";
		}
		if (indicator instanceof RedisHealthIndicator) {
			return "Redis";
		}
		return null;
	}
}
