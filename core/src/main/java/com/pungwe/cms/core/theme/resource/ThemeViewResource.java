package com.pungwe.cms.core.theme.resource;

import com.lyncode.jtwig.exception.ResourceException;
import com.lyncode.jtwig.resource.JtwigResource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import java.io.*;

import static com.lyncode.jtwig.util.FilePath.parentOf;

/**
 * Created by ian on 10/03/2016.
 */
public class ThemeViewResource implements JtwigResource {

	private UrlResource resource;

	public ThemeViewResource(String resource) {
		try {
			if (resource.indexOf(ResourceUtils.JAR_URL_SEPARATOR) > -1) {
				resource = "jar:" + resource;
			}
			this.resource = new UrlResource(resource);
		} catch (Exception ex) {
			// do nothing
		}
	}

	@Override
	public boolean exists() {
		return this.resource != null && this.resource.exists();
	}

	@Override
	public InputStream retrieve() throws ResourceException {
		try {
			return this.resource == null ? null : this.resource.getInputStream();
		} catch (IOException e) {
			throw new ResourceException(e);
		}
	}

	@Override
	public JtwigResource resolve(String relativePath) throws ResourceException {
		return new ThemeViewResource(parentOf(resource.getFilename()).append(relativePath).normalize());
	}

	@Override
	public String toString() {
		return resource.getFilename();
	}
}
