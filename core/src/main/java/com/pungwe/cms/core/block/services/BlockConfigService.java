package com.pungwe.cms.core.block.services;

import com.pungwe.cms.core.block.BlockConfig;

import java.util.Collection;
import java.util.List;

/**
 * Created by ian on 05/03/2016.
 */
public interface BlockConfigService<T extends BlockConfig> {

	List<T> listAllBlocks(String theme);
	List<T> listAllBlocks(String theme, String... name);
}
