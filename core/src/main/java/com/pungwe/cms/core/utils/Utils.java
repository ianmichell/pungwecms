package com.pungwe.cms.core.utils;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.UriTemplate;

import javax.servlet.http.HttpServletRequest;
import java.text.Collator;
import java.util.*;
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

    public static Comparator<String> pathPatternComparator() {
        String path = (String)RequestContextHolder.getRequestAttributes().getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
        if (StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException("Path must not be empty");
        }
        AntPathMatcher matcher = new AntPathMatcher();
        return matcher.getPatternComparator(path);
    }

    public static boolean matchesPathPattern(String pattern) {
        String path = (String)RequestContextHolder.getRequestAttributes().getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
        // If They are both blank, then there is a default pattern
        if (StringUtils.isEmpty(path) && StringUtils.isEmpty(pattern)) {
            return true;
        }
        AntPathMatcher apm = new AntPathMatcher();
        return apm.match(pattern, path);
    }

    public static boolean matchesPathPatterns(String... patterns) {
        if (patterns == null || patterns.length == 0) {
            return true; // return true by default, as it's visible anywhere
        }
        for (String pattern : patterns) {
            if (matchesPathPattern(pattern)) {
                return true;
            }
        }
        return false;
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
		if (path == null || bestMatchPattern == null) {
			return false;
		}
        return !path.equals(bestMatchPattern);
    }

    public static String getRequestPathVariablePattern() {
        if (!hasRequestPathVariable()) {
            return null;
        }
        String bestMatchPattern = (String)RequestContextHolder.getRequestAttributes().getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
        return bestMatchPattern;
    }

	public static String getRequestContextPath() {
		return ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath();
	}

    public static String translate(String value, Object... args) {
        return String.format(value, args);
    }

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static <T> void setFlashAttribute(String key, T value) {
        RequestContextUtils.getOutputFlashMap(getRequest()).put(key, value);
    }

    public static Map<String, ?> getFlashMap() {
        return RequestContextUtils.getInputFlashMap(getRequest());
    }

    public static Object getFlashAttribute(String key) {
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(getRequest());
        return flashMap != null ? flashMap.get(key) : null;
    }


    public static Object getOutputFlashAttribute(String key) {
        Map<String, ?> flashMap = RequestContextUtils.getOutputFlashMap(getRequest());
        return flashMap != null ? flashMap.get(key) : null;
    }

    public static boolean containsFlashAttribute(String key) {
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(getRequest());
        return flashMap == null ? false : flashMap.containsKey(key);
    }

    public static boolean containsOutputFlashAttribute(String key) {
        FlashMap map = RequestContextUtils.getOutputFlashMap(getRequest());
        return map == null ? false : map.containsKey(key);
    }

    public static String getRequestPath() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String currentPath = request.getRequestURI().substring(request.getContextPath().length());
        return currentPath;
    }
}
