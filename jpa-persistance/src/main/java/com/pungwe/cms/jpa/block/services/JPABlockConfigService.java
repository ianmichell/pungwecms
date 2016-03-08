package com.pungwe.cms.jpa.block.services;

import com.pungwe.cms.core.block.services.BlockConfigService;
import com.pungwe.cms.jpa.block.BlockConfigImpl;
import com.pungwe.cms.jpa.block.repository.BlockConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
}
