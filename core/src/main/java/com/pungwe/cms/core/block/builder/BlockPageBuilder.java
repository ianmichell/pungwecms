package com.pungwe.cms.core.block.builder;

import com.pungwe.cms.core.annotations.Block;
import com.pungwe.cms.core.block.BlockConfig;
import com.pungwe.cms.core.block.BlockDefinition;
import com.pungwe.cms.core.block.services.BlockManagementService;
import com.pungwe.cms.core.block.system.MainContentBlock;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.model.ModelAndViewElement;
import com.pungwe.cms.core.module.services.ModuleManagementService;
import com.pungwe.cms.core.system.builder.PageBuilder;
import com.pungwe.cms.core.system.element.templates.PageElement;
import com.pungwe.cms.core.theme.services.ThemeManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * Created by ian on 05/03/2016.
 */
@Component
public class BlockPageBuilder implements PageBuilder {

	@Autowired
	protected ThemeManagementService themeManagementService;

	@Autowired
	protected BlockManagementService blockManagementService;

	@Autowired
	protected ModuleManagementService moduleManagementService;

	@Override
	public void build(final HttpServletRequest request, final PageElement page, final Map<String, Object> model) {
		// Fetch the relevant block config for the current theme...
		Map<String, List<BlockConfig>> blocksByRegion = blockManagementService.listAllBlocksByRegionForCurrentTheme();

		blocksByRegion.entrySet().forEach(entry -> {
			List<RenderedElement> elements = new LinkedList<RenderedElement>();
			entry.getValue().stream().forEach(blockConfig -> {
				Optional<BlockDefinition> block = blockManagementService.getBlockByName(blockConfig.getName());
				if (!block.isPresent()) {
					return;
				}
				elements.addAll(buildBlock(blockConfig, block.get(), model));
			});
			page.addRegion(entry.getKey(), elements);
		});

		AtomicBoolean hasContentBlock = new AtomicBoolean(false);
		// Check for content block, if none, then check for content region and add the relevant content...
		blocksByRegion.values().parallelStream().forEach(configList -> {
			Optional<BlockConfig> found = configList.parallelStream().filter(blockConfig -> blockConfig.getName() == MainContentBlock.class.getAnnotation(Block.class).value()).findFirst();
			if (found.isPresent()) {
				hasContentBlock.set(true);
			}
		});

		if (!hasContentBlock.get() && blocksByRegion.containsKey("content") && model.containsKey("content")) {
			Object content = model.get("content");
			if (content != null && content instanceof RenderedElement) {
				page.addRegion("content", (RenderedElement)content);
			} else if (content instanceof ModelAndView) {
				page.addRegion("content", new ModelAndViewElement((ModelAndView)content));
			}
		}
	}

	private List<RenderedElement> buildBlock(BlockConfig blockConfig, BlockDefinition block, final Map<String, Object> model) {
		List<RenderedElement> elements = new LinkedList<>();
		block.build(elements, blockConfig.getSettings(), model);
		return elements;
	}
}
