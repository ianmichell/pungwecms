package com.pungwe.cms.core.theme.functions;

import com.lyncode.jtwig.functions.annotations.JtwigFunction;
import com.lyncode.jtwig.functions.annotations.Parameter;
import com.lyncode.jtwig.functions.exceptions.FunctionException;
import com.lyncode.jtwig.util.render.RenderHttpServletResponse;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.services.RenderedElementService;
import com.pungwe.cms.core.utils.services.HookService;
import org.apache.commons.lang3.mutable.MutableObject;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ian on 14/02/2016.
 */
public class TemplateFunctions {

    private ApplicationContext applicationContext;
    private ViewResolver viewResolver;
    private LocaleResolver localeResolver;

    public TemplateFunctions(ApplicationContext context, ViewResolver resolver, LocaleResolver localeResolver) {
        this.applicationContext = context;
        this.viewResolver = resolver;
        this.localeResolver = localeResolver;
    }

    @JtwigFunction(name = "render")
    public <T extends RenderedElement> String render(HttpServletRequest request, @Parameter String template) throws FunctionException {
        if (StringUtils.isEmpty(template)) {
            return "";
        }
        return render(request, template, new HashMap<>());
    }

    @JtwigFunction(name = "render")
    public <T extends RenderedElement> String render(HttpServletRequest request, @Parameter String template, @Parameter Map<String, ?> parameters) throws FunctionException {
        if (template == null) {
            return "";
        }
        if (StringUtils.isEmpty(template)) {
            return "";
        }
        ModelAndView modelAndView = new ModelAndView(template, parameters);
        return render(request, modelAndView);
    }

    @JtwigFunction(name = "render")
    public <T extends RenderedElement> String render(HttpServletRequest request, @Parameter Collection<T> input) throws FunctionException {
        if (input == null) {
            return "";
        }
        StringBuilder html = new StringBuilder();
        for (T element : input) {
            html.append(render(request, element)).append("\n");
        }
        return html.toString();
    }

    @JtwigFunction(name = "render")
    public <T extends RenderedElement> String render(HttpServletRequest request, @Parameter T input) throws FunctionException {
        try {
            if (input == null || !input.isVisible()) {
                return "";
            }
            RenderedElementService renderedElementService = applicationContext.getBean(RenderedElementService.class);
            HookService hookService = applicationContext.getBean(HookService.class);
            final MutableObject<RenderedElement> elementToRender = new MutableObject<>(input);
            if (!input.isWrapped()) {
                hookService.executeHook("element_wrapper", (c, o) -> {
                    if (o != null && o instanceof RenderedElement && !input.isWrapped()) {
                        input.setWrapped(true);
                        elementToRender.setValue((RenderedElement) o);
                    }
                }, input);
            }
            hookService.executeHook("element_alter", elementToRender.getValue());
            ModelAndView modelAndView = renderedElementService.convertToModelAndView(elementToRender.getValue());
            return render(request, modelAndView);
        } catch (Exception ex) {
            throw new FunctionException(ex);
        }
    }

    @JtwigFunction(name = "render")
    public <T extends ModelAndView> String render(HttpServletRequest request, @Parameter T model) throws FunctionException {
        if (model == null || model.getModel() == null || (!(Boolean) model.getModel().getOrDefault("element_visible", true))) {
            return "";
        }
        RenderHttpServletResponse responseWrapper = new RenderHttpServletResponse();
        try {
            if (model.getView() == null && StringUtils.isEmpty(model.getViewName())) {
                throw new FunctionException("No view has been found");
            }
            View view = viewResolver.resolveViewName(model.getViewName(), localeResolver.resolveLocale(request));
            view.render(model.getModel(), request, responseWrapper);
            return responseWrapper.toString();
        } catch (Exception ex) {
            throw new FunctionException(ex);
        }
    }

    @JtwigFunction(name = "printAttribute")
    public String printAttribute(@Parameter String name, @Parameter String value) {
        return !StringUtils.isEmpty(value) ? " " + name + "=\"" + value + "\"" : "";
    }

    @JtwigFunction(name = "printAttribute")
    public <T extends Number> String printAttribute(@Parameter String name, @Parameter T value) {
        return value != null ? " " + name + "=\"" + value + "\"" : "";
    }

    @JtwigFunction(name = "printAttribute")
    public String printAttribute(@Parameter String name, @Parameter Boolean value) {
        return value != null ? " " + name + "=\"" + value + "\"" : "";
    }

    @JtwigFunction(name = "translate")
    public String translate(@Parameter String value) {
        return value;
    }

    @JtwigFunction(name = "translate")
    public String translate(@Parameter String value, @Parameter Map<String, Object> parameters) {
        return value; // Return value on it's own for now... We need to expose this in the i810n
    }
}
