package com.pungwe.cms.core.block.services;

import com.pungwe.cms.core.block.impl.BlockConfigImpl;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ian on 07/03/2016.
 */
@Service
public class BlockConfigServiceImpl implements BlockConfigService<BlockConfigImpl> {

	public static List<BlockConfigImpl> blocks = new LinkedList<>();

	@Override
	public List<BlockConfigImpl> listAllBlocks(final String theme) {
		return blocks.stream().filter(blockConfig -> blockConfig.getTheme() == theme).collect(Collectors.toList());
	}

	@Override
	public List<BlockConfigImpl> listAllBlocks(String theme, final String... name) {
		return listAllBlocks(theme).stream().filter(blockConfig -> name.length == 0 || Arrays.asList(name).contains(blockConfig.getName())).collect(Collectors.toList());
	}

	@Override
	public void createNewInstance(String theme, String region, String block, int weight, Map<String, Object> defaultSettings) {
		BlockConfigImpl blockConfig = new BlockConfigImpl();
		blockConfig.setId(UUID.randomUUID().toString());
		blockConfig.setName(block);
		blockConfig.setRegion(region);
		blockConfig.setTheme(theme);
		blockConfig.setWeight(weight);
		// Save the block
		blocks.add(blockConfig);
	}
}
