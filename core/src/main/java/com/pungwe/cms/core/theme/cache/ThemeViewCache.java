package com.pungwe.cms.core.theme.cache;

import com.lyncode.jtwig.cache.JtwigTemplateCacheSystem;
import com.lyncode.jtwig.content.api.Renderable;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.concurrent.Callable;

/**
 * Created by ian on 16/03/2016.
 */
public class ThemeViewCache implements JtwigTemplateCacheSystem {

	protected CacheManager cacheManager;

	public ThemeViewCache(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@Override
	public Renderable get(String key, Callable<Renderable> instanceProvider) {
		Cache cache = cacheManager.getCache("jtwig_templates");
		Cache.ValueWrapper wrapper = cache.get(key);
		if (wrapper == null || wrapper.get() == null) {
			try {
				Renderable r = instanceProvider.call();
				cache.put(key, r);
				return r;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return (Renderable)wrapper.get();
	}
}
