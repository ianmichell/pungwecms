package com.pungwe.cms.core.system.services;

import com.pungwe.cms.core.element.HeaderRenderedElement;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.basic.LinkElement;
import com.pungwe.cms.core.element.basic.ScriptElement;
import com.pungwe.cms.core.element.basic.StyleElement;
import com.pungwe.cms.core.element.model.ModelAndViewElement;
import com.pungwe.cms.core.element.services.RenderedElementService;
import com.pungwe.cms.core.system.element.templates.HtmlElement;
import com.pungwe.cms.core.system.element.templates.PageElement;
import com.pungwe.cms.core.utils.services.HookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by ian on 04/03/2016.
 */
@Service
public class HtmlWrapperService {

	@Autowired
	protected RenderedElementService renderedElementService;

	@Autowired
	protected HookService hookService;

	@Autowired
	protected PageBuilderService pageBuilderService;

	public void wrapModelAndView(HttpServletRequest request, ModelAndView modelAndView) throws InvocationTargetException, IllegalAccessException {

		ModelAndView wrappedModelAndView = new ModelAndView();
		wrappedModelAndView.addAllObjects(modelAndView.getModel());
		if (modelAndView.getView() != null) {
			wrappedModelAndView.setView(modelAndView.getView());
		} else {
			wrappedModelAndView.setViewName(modelAndView.getViewName());
		}

		// Clear the original model
		modelAndView.clear();

		// Call hooks for each of the variables in the html template
		//==========================================================
		// Title - void, no callback
		final List<String> title = processHook(String.class, "html_title");
		// Head
		final List<HeaderRenderedElement> head = processHook(HeaderRenderedElement.class, "html_head");
		// CSS
		final List<HeaderRenderedElement> css = processHook(HeaderRenderedElement.class, "html_css");
		// JS Top
		final List<ScriptElement> jsTop = processHook(ScriptElement.class, "html_js_top");
		// Page Top
		final List<RenderedElement> pageTop = processHook(RenderedElement.class, "html_page_top");
		// Page Bottom
		final List<RenderedElement> pageBottom = processHook(RenderedElement.class, "html_page_bottom");
		// JS Bottom
		final List<ScriptElement> jsBottom = processHook(ScriptElement.class, "html_js_bottom");

		// Create the html element, then execute the preprocess html hook. By this point we should have it fairly well populated...
		final HtmlElement htmlElement = new HtmlElement();
		htmlElement.setTitle(title);
		htmlElement.addToHead(head);
		htmlElement.addToCss(css);
		htmlElement.addToJsTop(jsTop);
		htmlElement.addToJsBottom(jsBottom);
		htmlElement.addToPageTop(pageTop);
		htmlElement.addToPageBottom(pageBottom);

		// Call the preprocess hook. Pass in the htmlElement as an argument.
		// Delcare a callback to ensure that those methods without parameters can return a Collection of RenderedElements
		hookService.executeHook("preprocess_html", (clazz, result) -> {
			// Potential of non-void methods, so we need to take into account the model.
			// Hooks don't require parameters, so if someone creates a hook without the HtmlElement parameter,
			// then we should look for the relevant variables here.
			if (result != null && result instanceof Map) {
				Map<String, Object> map = (Map<String, Object>) result;
				// we need to spin throug the appropriate variables and then add them to the html element
				processHtmlPreprocessHookResult(map, htmlElement);
			}
		}, htmlElement);

		if (wrappedModelAndView.getModel().containsKey("title") && wrappedModelAndView.getModel().get("title") != null) {
			htmlElement.addTitle(wrappedModelAndView.getModel().get("title").toString()); // force to string...
		}

		// This should be placed in the page...
		ModelAndViewElement content = new ModelAndViewElement();
		content.setContent(wrappedModelAndView);

		// Build the page...
		Map<String, Object> model = new HashMap<String, Object>();
		if (wrappedModelAndView.getModel().containsKey("title")) {
			model.put("title", wrappedModelAndView.getModel().get("title"));
		}
		model.put("content", content);
		PageElement page = pageBuilderService.buildPage(request, model);

		// Add the page to the html element
		htmlElement.addToPageContent(page);

		ModelAndView htmlElementModelAndView = renderedElementService.convertToModelAndView(htmlElement);
		// We know it will be a view name here...
		modelAndView.setViewName(htmlElementModelAndView.getViewName());
		modelAndView.addAllObjects(htmlElementModelAndView.getModelMap());
	}

	protected void processHtmlPreprocessHookResult(Map<String, Object> result, HtmlElement element) {
		for (Map.Entry<String, Object> entry : result.entrySet()) {
			if (entry.getValue() == null) {
				continue; // ignore null values
			}
			if (entry.getKey() == "title") {
				element.addTitle(entry.getValue().toString());
				// Head
			} else if (entry.getKey() == "head" && entry.getValue() instanceof HeaderRenderedElement) {
				element.addToHead((HeaderRenderedElement) entry.getValue());
			} else if (entry.getKey() == "head" && entry.getValue() instanceof Collection) {
				element.addToHead(((Collection<HeaderRenderedElement>) entry.getValue()).toArray(new HeaderRenderedElement[0]));
				// CSS
			} else if (entry.getKey() == "css" && (entry.getValue() instanceof LinkElement || entry.getValue() instanceof StyleElement)) {
				element.addToHead((HeaderRenderedElement) entry.getValue());
			} else if (entry.getKey() == "css" && entry.getValue() instanceof Collection) {
				element.addToHead(((Collection<HeaderRenderedElement>) entry.getValue()).toArray(new HeaderRenderedElement[0]));
				// JS TOP
			} else if (entry.getKey() == "js_top" && entry.getValue() instanceof ScriptElement) {
				element.addToJsTop((ScriptElement) entry.getValue());
			} else if (entry.getKey() == "js_top" && entry.getValue() instanceof Collection) {
				element.addToJsTop(((Collection<ScriptElement>) entry.getValue()).toArray(new ScriptElement[0]));
			// PAGE TOP
			} else if (entry.getKey() == "page_top" && entry.getValue() instanceof RenderedElement) {
				element.addToPageTop((RenderedElement) entry.getValue());
			} else if (entry.getKey() == "page_top" && entry.getValue() instanceof Collection) {
				element.addToPageTop(((Collection<RenderedElement>) entry.getValue()).toArray(new RenderedElement[0]));
			// PAGE BOTTOM
			} else if (entry.getKey() == "page_bottom" && entry.getValue() instanceof RenderedElement) {
				element.addToPageBottom((RenderedElement) entry.getValue());
			} else if (entry.getKey() == "page_bottom" && entry.getValue() instanceof Collection) {
				element.addToPageBottom(((Collection<RenderedElement>) entry.getValue()).toArray(new RenderedElement[0]));
			// JS BOTTOM
			} else if (entry.getKey() == "js_bottom" && entry.getValue() instanceof HeaderRenderedElement) {
				element.addToJsBottom((ScriptElement) entry.getValue());
			} else if (entry.getKey() == "js_bottom" && entry.getValue() instanceof Collection) {
				element.addToPageBottom(((Collection<ScriptElement>) entry.getValue()).toArray(new ScriptElement[0]));
			} else if (entry.getKey() == "attributes" && entry.getValue() instanceof Map) {
				element.getAttributes().putAll((Map<String, String>)entry.getValue());
			} else if (entry.getKey() == "body_attributes" && entry.getValue() instanceof Map) {
				element.getBodyAttributes().putAll((Map<String, String>)entry.getValue());
			}
		}
	}

	protected <T> List<T> processHook(Class<T> elementType, String hook) throws InvocationTargetException, IllegalAccessException {
		List<T> results = new LinkedList<>();
		hookService.executeHook(hook, (clazz, result) -> {
			if (result != null && elementType.isAssignableFrom(result.getClass())) {
				results.add((T) result);
			}
		}, results);
		return results;
	}
}
