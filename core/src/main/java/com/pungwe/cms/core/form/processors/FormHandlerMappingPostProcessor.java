package com.pungwe.cms.core.form.processors;

import com.google.common.collect.Lists;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ian on 19/03/2016.
 */
public class FormHandlerMappingPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof RequestMappingHandlerAdapter) {
			RequestMappingHandlerAdapter adapter = (RequestMappingHandlerAdapter) bean;
			List<HandlerMethodArgumentResolver> resolvers = new LinkedList<>();
			resolvers.addAll(adapter.getArgumentResolvers());
			resolvers.replaceAll(handlerMethodArgumentResolver -> {
				if (handlerMethodArgumentResolver instanceof ServletModelAttributeMethodProcessor) {
					return new FormModelAttributeMethodProcessor(false);
				}
				return handlerMethodArgumentResolver;
			});
			adapter.setArgumentResolvers(resolvers);
		}
		return bean;
	}
}
