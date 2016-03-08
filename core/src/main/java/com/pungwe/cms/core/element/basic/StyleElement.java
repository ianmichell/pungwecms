package com.pungwe.cms.core.element.basic;

import com.pungwe.cms.core.annotations.ThemeInfo;
import com.pungwe.cms.core.element.AbstractRenderedElement;
import com.pungwe.cms.core.element.HeaderRenderedElement;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by ian on 03/03/2016.
 */
@ThemeInfo("basic/style")
public class StyleElement extends AbstractRenderedElement implements HeaderRenderedElement {

	protected String media;
	protected String type;
	protected String content;

	@ModelAttribute("media")
	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	@ModelAttribute("type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@ModelAttribute("content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	protected Collection<String> excludedAttributes() {
		return Arrays.asList("media", "type");
	}
}
