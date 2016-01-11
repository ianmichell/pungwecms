package com.pungwe.cms.core.module;

import java.util.Map;

/**
 * Created by ian on 11/01/2016.
 */
public interface ModuleConfig {

    public String getName();

    public void setName(String name);

    public String getVersion();

    public void setVersion(String version);

    public Class<?> getEntryPoint();

    public void setEntryPoint(Class<?> entryPoint);

    public boolean isEnabled();

    public void setEnabled(boolean enabled);

    Map<String, Object> getSettings();

    void setSettings(Map<String, Object> settings);
}
