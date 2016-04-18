package com.pungwe.cms.core.block.services;

import com.pungwe.cms.core.annotations.stereotypes.Block;
import com.pungwe.cms.core.annotations.ui.ThemeInfo;
import com.pungwe.cms.core.block.BlockConfig;
import com.pungwe.cms.core.block.BlockDefinition;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.model.ModelAndViewElement;
import com.pungwe.cms.core.module.services.ModuleManagementService;
import com.pungwe.cms.core.system.element.templates.AdminPageElement;
import com.pungwe.cms.core.theme.services.ThemeManagementService;
import com.pungwe.cms.core.utils.services.HookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

import static com.pungwe.cms.core.utils.Utils.matchesPathPatterns;

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
        return (List<BlockConfig>) blockConfigService.listAllBlocks(theme);
    }

    /**
     * Fetch a map of blocks for the current theme, mapped to the relevant regions.
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

        // Fetch a complete list of block config and block definitions for this theme
        final List<BlockConfig> blockConfigList = (List<BlockConfig>) blockConfigService.listAllBlocks(themeName);

        // Find all the relevant block definitions for each region; sorted by weight.
        blocks.entrySet().forEach(entry -> {
            final String region = entry.getKey();
            entry.getValue().addAll(blockConfigList.stream().filter(blockConfig -> {
                boolean hiddenFrom = (Boolean) blockConfig.getSettings().getOrDefault("hiddenFrom", false);
                if (blockConfig.getSettings().containsKey("pages") && !matchesPathPatterns(
                        ((String) blockConfig.getSettings().get("pages")).split("\\r?\\n"))) {
                    return !hiddenFrom;
                }
                boolean match = blockConfig.getRegion().equals(region);
                return match;
            }).sorted((o1, o2) -> Integer.compare(o1.getWeight(), o2.getWeight())).collect(Collectors.toList()));
        });
        return blocks;
    }

    /**
     * Fetch a map of blocks for the admin area, mapped to it's regions
     *
     * @return a map of regions with the relevant blocks
     */
    public Map<String, List<BlockConfig>> listAllAdminBlocks() {

        Map<String, String> regions = AdminPageElement.DEFAULT_REGIONS;

        // Generate admin regions
        final List<BlockConfig> blockConfigList = (List<BlockConfig>)blockConfigService.listAllBlocks("system.admin");

        final Map<String, List<BlockConfig>> blocks = new LinkedHashMap<>();
        regions.forEach((k, v) -> {
            blocks.put(k, new LinkedList<BlockConfig>());
        });

        // Find all the relevant block definitions for each region; sorted by weight.
        blocks.entrySet().forEach(entry -> {
            final String region = entry.getKey();
            entry.getValue().addAll(blockConfigList.stream().filter(blockConfig -> {
                boolean hiddenFrom = (Boolean) blockConfig.getSettings().getOrDefault("hiddenFrom", false);
                if (blockConfig.getSettings().containsKey("pages") && !matchesPathPatterns(
                        ((String) blockConfig.getSettings().get("pages")).split("\\r?\\n"))) {
                    return !hiddenFrom;
                }
                boolean match = blockConfig.getRegion().equals(region);
                return match;
            }).sorted((o1, o2) -> Integer.compare(o1.getWeight(), o2.getWeight())).collect(Collectors.toList()));
        });

        try {
            hookService.executeHook("admin_block_alter", blocks);
        } catch (InvocationTargetException e) {
            LOG.error("Error in admin block alter", e);
        } catch (IllegalAccessException e) {
            LOG.error("Error in admin block alter", e);
        }
        return blocks;
    }

    /**
     * Fetch a list of all the defined block singleton beans.
     *
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

    public Optional<BlockDefinition> getBlockDefinitionByName(String name) {
        try {
            return Optional.of((BlockDefinition) moduleManagementService.getModuleContext().getBean(name, BlockDefinition.class));
        } catch (Exception ex) {
            LOG.error("Could not find block with name: " + name);
        }
        return Optional.empty();
    }

    public Optional<Block> getBlockInfoByBlockName(String name) {
        Optional<BlockDefinition> block = getBlockDefinitionByName(name);
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
        Optional<BlockDefinition> blockDefinition = getBlockDefinitionByName(block);
        if (!blockDefinition.isPresent()) {
            return;
        }

        Map<String, Object> settings = new HashMap<>();
        settings.putAll(blockDefinition.get().getDefaultSettings());
        settings.putAll(themeSettings);

        blockConfigService.createNewInstance(theme, region, block, weight, settings);
    }

    public Block getBlockInfoForBlock(BlockDefinition block) {
        if (block == null) {
            return null;
        }
        return AnnotationUtils.findAnnotation(block.getClass(), Block.class);
    }

    public String getBlockNameForDefinition(BlockDefinition block) {
        if (block == null) {
            return null;
        }
        String[] names = applicationContext.getBeanNamesForType(block.getClass());
        return names.length > 0 ? names[0] : null;
    }

    public ModelAndViewElement buildBlockElement(BlockDefinition block, final Map<String, Object> model) {
        return buildBlockElement(null, block, model);
    }

    public ModelAndViewElement buildBlockElement(BlockConfig blockConfig, BlockDefinition block, final Map<String, Object> model) {

        // Defaults
        String blockName = getBlockNameForDefinition(block);
        String blockId = blockName;

        Map<String, Object> settings = new LinkedHashMap<>();
        if (blockConfig == null) {
            settings.putAll(block.getDefaultSettings());
        } else {
            settings.putAll(blockConfig.getSettings());
            blockName = blockConfig.getName();
            blockId = String.valueOf(blockConfig.getId());
        }
        try {
            // Look for additional settings
            hookService.executeHook("block_settings_alter", blockId, blockName, settings);
        } catch (InvocationTargetException e) {
            LOG.error("Could not execute hook block_alter", e);
        } catch (IllegalAccessException e) {
            LOG.error("Could not execute hook block_alter", e);
        }
        List<RenderedElement> elements = new LinkedList<>();
        block.build(elements, settings, model);
        if (elements.isEmpty()) {
            return null;
        }

        // Create block wrapper!
        ThemeInfo info = AnnotationUtils.findAnnotation(block.getClass(), ThemeInfo.class);

        // Create block model and view
        ModelAndView blockContent = new ModelAndView();
        blockContent.addObject("blockName", blockName);
        blockContent.addObject("blockId", blockId);
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

    public void removeBlock(String theme, String blockName) {
        blockConfigService.removeBlock(theme, blockName);
    }

    @EventListener
    public void installAdminBlocks(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getId().equals("module-application-context")) {

            Map<String, Object> adminMenuSettings = new LinkedHashMap<>();
            adminMenuSettings.put("menu", "system");
            adminMenuSettings.put("menu_class", "admin-main-menu");
            adminMenuSettings.put("parent_menu_item", "admin");

            Map<String, Object> taskBlockSettings = new LinkedHashMap<>();
            adminMenuSettings.put("menu", "system");

            Map<String, Object> breadcrumbBlockSettings = new LinkedHashMap<>();
            adminMenuSettings.put("menu", "system");

            // Create a list of the default blocks that will be used...
            addBlockToTheme("system.admin", "header", "page_title_block", -100, new HashMap<>());
            addBlockToTheme("system.admin", "breadcrumb", "breadcrumb_block", -100, breadcrumbBlockSettings);
            addBlockToTheme("system.admin", "highlighted", "status_message_block", -100, new HashMap<>());
            addBlockToTheme("system.admin", "sidebar_first", "menu_block", -100, adminMenuSettings);
            addBlockToTheme("system.admin", "content", "system_tasks_block", -101, taskBlockSettings);
            addBlockToTheme("system.admin", "content", "main_content_block", -100, new HashMap<>());
        }
    }
}
