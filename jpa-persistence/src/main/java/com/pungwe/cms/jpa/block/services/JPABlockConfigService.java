package com.pungwe.cms.jpa.block.services;

import com.pungwe.cms.core.block.services.BlockConfigService;
import com.pungwe.cms.jpa.block.BlockConfigImpl;
import com.pungwe.cms.jpa.block.repository.BlockConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by ian on 05/03/2016.
 */
@Service
public class JPABlockConfigService implements BlockConfigService<BlockConfigImpl> {

	@Autowired
	BlockConfigRepository blockConfigRepository;

	@Override
	@Transactional
	public List<BlockConfigImpl> listAllBlocks(String theme) {
		return blockConfigRepository.findAllByTheme(theme);
	}

	@Override
	public List<BlockConfigImpl> listAllBlocks(String theme, String... name) {
		return blockConfigRepository.findAllByThemeAndNameIn(theme, Arrays.asList(name));
	}

	@Override
	public void createNewInstance(String theme, String region, String block, int weight, Map<String, Object> defaultSettings) {
		BlockConfigImpl blockConfig = new BlockConfigImpl();
		blockConfig.setId(theme + "." + block);
		blockConfig.setName(block);
		blockConfig.setRegion(region);
		blockConfig.setTheme(theme);
		blockConfig.setWeight(weight);
		blockConfig.setSettings(defaultSettings);
		// Save the block!
		blockConfigRepository.save(blockConfig);
	}

	@Override
	public void removeBlock(String theme, String blockName) {
		BlockConfigImpl config = blockConfigRepository.findOneByThemeAndName(theme, blockName);
		config.setRegion(null);
		// Save the block, don't delete it
		blockConfigRepository.save(config);
	}
}
