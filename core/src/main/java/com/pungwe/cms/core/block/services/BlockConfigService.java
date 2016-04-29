package com.pungwe.cms.core.block.services;

import com.pungwe.cms.core.block.BlockConfig;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by ian on 05/03/2016.
 */
public interface BlockConfigService<T extends BlockConfig> {

	List<T> listAllBlocks(String theme);
	List<T> listAllBlocks(String theme, String... name);

    T newInstance();

	void createNewInstance(String id, String theme, String region, String block, int weight, Map<String, Object> defaultSettings);

	void removeBlock(String theme, String blockName);

	void updateBlocks(List<BlockConfig> blocks);

    T getBlockConfigById(String blockId);
}
