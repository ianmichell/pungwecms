package com.pungwe.cms.core.theme.services;

import com.pungwe.cms.core.theme.ThemeConfig;

import java.net.URL;
import java.util.Collection;
import java.util.Set;

/**
 * Created by ian on 29/01/2016.
 */
public interface ThemeConfigService<E extends ThemeConfig> {

	/**
	 * Registers a theme into the theme registry
	 *
	 * @param entryPoint the main theme class
	 * @param themeLocation the jar file that the theme is located
	 */
	void registerTheme(Class<?> entryPoint, URL themeLocation);

	/**
	 * Removes themes from the registry. This is generally used to remove themes that are no longer on the
	 * class path
	 * @param themes the names of the themes to be removed
	 *
	 */
	void removeThemes(String... themes);

	/**
	 * Removes themes from the registry. This is generally used to remove themes that are no longer on the
	 * class path
	 * @param themes the names of the themes to be removed
	 *
	 */
	void removeThemes(Collection<String> themes);

	/**
	 * Enable / disable a theme
	 * @param themeName the name of the theme to be enabled / disabled
	 * @param enabled true if the theme is to be enabled, false otherwise
	 */
	void setThemeEnabled(String themeName, boolean enabled);

	/**
	 * Check if a theme with the given name is enabled
	 * @param theme the name of the theme to be checked
	 * @return true if it's enabled, false otherwise
	 */
	boolean isEnabled(String theme);

	/**
	 * Return a list of all the installed themes
	 * @return a list of themes found in the registry
	 */
	Set<E> listAllThemes();

	/**
	 * Return a list of all enabled themes
	 * @return a list of enabled themes
	 */
	Set<E> listEnabledThemes();

	/**
	 * Get the current default theme
	 * @return The ThemeConfig for the current default theme
	 */
	E getDefaultTheme();

	/**
	 * Get the current default admin theme (useful if different themes are being used)
	 * @return The ThemeConfig for the current default admin theme
	 */
	E getDefaultAdminTheme();

	E getTheme(String name);

}
