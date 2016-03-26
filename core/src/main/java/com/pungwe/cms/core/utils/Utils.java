package com.pungwe.cms.core.utils;

import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.UriTemplate;

import javax.servlet.ServletRequest;
import java.text.Collator;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by ian on 18/03/2016.
 */
public class Utils {

	public static List<Locale> getSortedLocales(final Locale currentLocale) {
		final Collator collator = Collator.getInstance(currentLocale);
		List<Locale> locales = Arrays.asList(Collator.getAvailableLocales());
		return locales.stream().sorted((o1, o2) -> collator.compare(o1.getDisplayName(currentLocale), o2.getDisplayName(currentLocale))).collect(Collectors.toList());
	}

	public static String getRequestPathVariable(String variableName) {
		String path = (String)RequestContextHolder.getRequestAttributes().getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
		String bestMatchPattern = (String)RequestContextHolder.getRequestAttributes().getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
		AntPathMatcher apm = new AntPathMatcher();
		Map<String, String> variables = apm.extractUriTemplateVariables(bestMatchPattern, path);
		return variables.getOrDefault(variableName, null);
	}

	/** Expands the url template to a url using the most appropriate variables */
	public static String processUrlVariables(String url, Map<String, Object> variables) {
		UriTemplate template = new UriTemplate(url);
		return template.expand(variables).toString();
	}

    public static boolean hasRequestPathVariable() {
        String path = (String)RequestContextHolder.getRequestAttributes().getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
        String bestMatchPattern = (String)RequestContextHolder.getRequestAttributes().getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
        return !path.equals(bestMatchPattern);
    }

    public static String getRequestPathVariablePattern() {
        if (!hasRequestPathVariable()) {
            return null;
        }
        String bestMatchPattern = (String)RequestContextHolder.getRequestAttributes().getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
        return bestMatchPattern;
    }
}
