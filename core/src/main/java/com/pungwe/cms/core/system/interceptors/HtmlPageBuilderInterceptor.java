package com.pungwe.cms.core.system.interceptors;

import com.pungwe.cms.core.system.services.HtmlWrapperService;
import com.pungwe.cms.core.utils.services.HookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ian on 01/03/2016.
 */
@Component
public class HtmlPageBuilderInterceptor extends HandlerInterceptorAdapter {

	private static Logger LOG = LoggerFactory.getLogger(HtmlPageBuilderInterceptor.class);

	@Autowired
	protected HtmlWrapperService htmlWrapperService;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// Ensure we have a view...
		if (modelAndView == null || !modelAndView.hasView()) {
			return;
		}

		if (modelAndView.getViewName().startsWith("forward:") || modelAndView.getViewName().startsWith("redirect:")) {
			return;
		}

		// rewrites the model and view to wrap it with HtmlElement and PageElement.
		// This will work with JSON response as well, if you have client side executed themes
		htmlWrapperService.wrapModelAndView(request, modelAndView);
	}

}
