package com.pungwe.cms.webapp;

import com.pungwe.cms.core.application.PungweCMSApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ian on 20/01/2016.
 */
public class Application {

	public static void main(String[] args) {
		PungweCMSApplication.start(args);
	}

}
