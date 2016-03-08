package com.pungwe.cms.core.block.services;

import com.pungwe.cms.core.block.impl.BlockConfigImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
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

}
