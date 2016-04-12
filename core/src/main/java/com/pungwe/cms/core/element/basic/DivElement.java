package com.pungwe.cms.core.element.basic;

import com.pungwe.cms.core.annotations.ui.ThemeInfo;
import com.pungwe.cms.core.element.AbstractContentElement;
import com.pungwe.cms.core.element.RenderedElement;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ian on 08/03/2016.
 */
@ThemeInfo("basic/div")
public class DivElement extends AbstractContentElement {

    public DivElement() {

    }

    public DivElement(String... content) {
        this(Arrays.asList(content).stream().map(s -> new PlainTextElement(s)).collect(Collectors.toList()));
    }

    public DivElement(RenderedElement... content) {
        this(Arrays.asList(content));
    }

    public DivElement(List<RenderedElement> content) {
        setContent(content);
    }

    @Override
    protected Collection<String> excludedAttributes() {
        return new LinkedList<>();
    }
}
