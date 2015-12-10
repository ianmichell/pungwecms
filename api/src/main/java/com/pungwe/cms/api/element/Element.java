package com.pungwe.cms.api.element;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by ian on 09/12/2015.
 */
public class Element extends AbstractMap<String, Object> {

    Set<Entry<String, Object>> set = new HashSet<>();

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return set;
    }
}
