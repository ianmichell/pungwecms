package com.pungwe.cms.core.block.services;

import com.pungwe.cms.core.annotations.stereotypes.Block;
import com.pungwe.cms.core.annotations.ui.ThemeInfo;
import com.pungwe.cms.core.block.BlockConfig;
import com.pungwe.cms.core.block.BlockDefinition;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.model.ModelAndViewElement;
import com.pungwe.cms.core.module.services.ModuleManagementService;
import com.pungwe.cms.core.theme.ThemeConfig;
import com.pungwe.cms.core.theme.services.ThemeManagementService;
import com.pungwe.cms.core.utils.services.HookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ian on 05/03/2016.
 */
@Service
public class BlockManagementService {

	private static final Logger LOG = LoggerFactory.getLogger(BlockManagementService.class);

	@Autowired
	protected BlockConfigService<? extends BlockConfig> blockConfigService;

	@Autowired
	protected ThemeManagementService themeManagementService;

	@Autowired
	protected ModuleManagementService moduleManagementService;

	@Autowired
	protected ApplicationContext applicationContext;

    @Autowired
    protected HookService hookService;

	public List<BlockConfig> getBlockConfigForDefaultTheme() {
		String theme = themeManagementService.getDefaultThemeName();
		if (StringUtils.isEmpty(theme)) {
			return null;
		}
		return (List<BlockConfig>)blockConfigService.listAllBlocks(theme);
	}

	/**
	 * Fetch a list of blocks for the current theme.
	 *
	 * @return a map containing live block definitions and their assigned regions.
	 */
	public Map<String, List<BlockConfig>> listAllBlocksByRegionForCurrentTheme() {
		/* What's the current theme name */
		String themeName = themeManagementService.getCurrentThemeNameForRequest();

		/* Fetch a list of regions */
		Map<String, String> regions = themeManagementService.getRegionsForDefaultThemeByRequest();

		final Map<String, List<BlockConfig>> blocks = new LinkedHashMap<>();
		regions.forEach((k, v) -> {
			blocks.put(k, new LinkedList<BlockConfig>());
		});

		/* Get a list of the blocks by region */
//		if (StringUtils.isEmpty(themeName)) {
//			return blocks;
//		}

		// Fetch a complete list of block config and block definitions for this theme
		final List<BlockConfig> blockConfigList = (List<BlockConfig>)blockConfigService.listAllBlocks(themeName);
		// Find all the relevant block definitions for each region; sorted by weight.
		blocks.entrySet().forEach(entry -> {
			final String region = entry.getKey();
			entry.getValue().addAll(blockConfigList.stream().filter(blockConfig -> {
				boolean match = blockConfig.getRegion().equals(region);
				return match;
			}).sorted((o1, o2) -> Integer.compare(o1.getWeight(), o2.getWeight())).collect(Collectors.toList()));
		});
		return blocks;
	}

	/**
	 * Fetch a list of all the defined block singleton beans.
	 * @return a list of block names...
	 */
	public Set<String> listAllBlockNames() {
		Set<String> blocks = new HashSet<>();
		// Return the combined list of block names
		List<BlockDefinition> blockDefinitions = listAllBlocks();
		blocks.addAll(blockDefinitions.parallelStream().filter(b -> b != null && b.getClass().isAnnotationPresent(Block.class))
				.map(b -> b.getClass().getAnnotation(Block.class).value()).collect(Collectors.toSet()));
		return blocks;
	}

	public List<BlockDefinition> listAllBlocks() {
		final List<BlockDefinition> blocks = new LinkedList<>();
		// Main Context
		Map<String, Object> mainContextBlocks = moduleManagementService.getModuleContext().getBeansWithAnnotation(Block.class);
		mainContextBlocks.entrySet().parallelStream().filter(b -> b != null && b instanceof BlockDefinition).forEach(b -> {
			blocks.add((BlockDefinition) b);
		});
		return blocks;
	}


	public Optional<BlockDefinition> getBlockByName(String name) {
		try {
			return Optional.of((BlockDefinition)moduleManagementService.getModuleContext().getBean(name, BlockDefinition.class));
		} catch (Exception ex) {
			LOG.error("Could not find block with name: " + name);
		}
		return Optional.empty();
	}

	public Optional<Block> getBlockInfoByBlockName(String name) {
		Optional<BlockDefinition> block = getBlockByName(name);
		if (!block.isPresent()) {
			return Optional.empty();
		}
		Block blockAnnotation = AnnotationUtils.findAnnotation(block.get().getClass(), Block.class);
		if (blockAnnotation != null) {
			return Optional.of(blockAnnotation);
		} else {
			return Optional.empty();
		}
	}

	public void addBlockToTheme(String theme, String region, String block, int weight, Map<String, Object> themeSettings) {
		Optional<BlockDefinition> blockDefinition = getBlockByName(block);
		if (!blockDefinition.isPresent()) {
			return;
		}

		Map<String, Object> settings = new HashMap<>();
		settings.putAll(blockDefinition.get().getDefaultSettings());
		settings.putAll(themeSettings);

		blockConfigService.createNewInstance(theme, region, block, weight, settings);
	}

	public ModelAndViewElement buildBlockElement(BlockConfig blockConfig, BlockDefinition block, final Map<String, Object> model) {
		List<RenderedElement> elements = new LinkedList<>();
		block.build(elements, blockConfig.getSettings(), model);
		if (elements.isEmpty()) {
			return null;
		}
		ThemeInfo info = AnnotationUtils.findAnnotation(block.getClass(), ThemeInfo.class);

		// Create block model and view
		ModelAndView blockContent = new ModelAndView();
		blockContent.addObject("blockName", blockConfig.getName());
		blockContent.addObject("blockId", blockConfig.getId());
		blockContent.addObject("content", elements);
		if (info != null) {
			blockContent.setViewName(info.value());
		} else {
			blockContent.setViewName("blocks/default");
		}
        try {
            hookService.executeHook("block_alter", blockContent);
        } catch (InvocationTargetException e) {
            LOG.error("Could not execute hook block_alter", e);
        } catch (IllegalAccessException e) {
            LOG.error("Could not execute hook block_alter", e);
        }
        ModelAndViewElement modelAndViewElement = new ModelAndViewElement();
		modelAndViewElement.setContent(blockContent);
		return modelAndViewElement;
	}
}
