package com.pungwe.cms.core.theme.functions;

import com.lyncode.jtwig.functions.annotations.JtwigFunction;
import com.lyncode.jtwig.functions.annotations.Parameter;
import com.lyncode.jtwig.functions.exceptions.FunctionException;
import com.lyncode.jtwig.util.render.RenderHttpServletRequest;
import com.lyncode.jtwig.util.render.RenderHttpServletResponse;
import com.pungwe.cms.core.element.RenderedElement;
import org.springframework.stereotype.Component;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.springframework.http.HttpMethod.GET;

/**
 * Created by ian on 14/02/2016.
 */
@Component
public class TemplateFunctions {

//	@Autowired(required = false)
//	private AssetResolver assetResolver;
//
//	@Autowired(required = false)
//	private MessageSource messageSource;
//
//	@Autowired(required = false)
//	private LocaleResolver localeResolver;
//
//	@Autowired(required = false)
//	private Environment environment;

	@JtwigFunction(name = "render")
	public <T extends RenderedElement> String render (HttpServletRequest request, @Parameter T input) throws FunctionException {

		RenderHttpServletResponse responseWrapper = new RenderHttpServletResponse();
		RenderHttpServletRequest builder = new RenderHttpServletRequest(request).to(input.getTheme()).withMethod(GET);
		builder.setAttribute("element", input);

		try {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(request.getServletPath());
			requestDispatcher.include(builder, responseWrapper);

			return responseWrapper.toString();
		} catch (ServletException | IOException e) {
			throw new FunctionException(e);
		}
	}
}
