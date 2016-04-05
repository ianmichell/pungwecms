package com.pungwe.cms.core.system.install;

import com.pungwe.cms.config.TestConfig;
import com.pungwe.cms.core.annotations.stereotypes.Block;
import com.pungwe.cms.core.block.impl.BlockConfigImpl;
import com.pungwe.cms.core.block.services.BlockConfigServiceImpl;
import com.pungwe.cms.core.block.system.MainContentBlock;
import com.pungwe.cms.core.block.system.PageTitleBlock;
import com.pungwe.cms.core.config.BaseApplicationConfig;
import com.pungwe.cms.core.module.services.ModuleManagementService;
import com.pungwe.cms.test.AbstractWebTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by ian on 27/02/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({TestConfig.class, BaseApplicationConfig.class})
@WebAppConfiguration("src/main/resources")
public class InstallControllerTest extends AbstractWebTest {

	@Autowired
	ModuleManagementService moduleManagementService;

	@Before
	public void setup() throws Exception {
		super.setup();
		AnnotationConfigWebApplicationContext moduleContext = new AnnotationConfigWebApplicationContext();
		moduleContext.setServletContext(webApplicationContext.getServletContext());
		moduleContext.setId("module-application-context");
		moduleContext.setParent(webApplicationContext);
		moduleManagementService.setModuleContext(moduleContext);
		moduleManagementService.startEnabledModules();
		moduleContext.refresh();
		// Add a block
		BlockConfigImpl titleBlock = new BlockConfigImpl();
		titleBlock.setId(UUID.randomUUID().toString());
		titleBlock.setName(PageTitleBlock.class.getAnnotation(Block.class).value());
		titleBlock.setRegion("header");
		BlockConfigServiceImpl.blocks.add(titleBlock);
	}

	@Test
	public void testViaWebTest() throws Exception {
		MvcResult result = mockMvc.perform(get("/install")).andExpect(request().asyncStarted()).andReturn();
		result.getAsyncResult();

		MvcResult finalResult = mockMvc.perform(asyncDispatch(result)).andExpect(status().isOk()).andExpect(content().contentType("text/html;charset=UTF-8")).andExpect(view().name("system/html")).andReturn();
	}

	@Test
	public void testWithContentBlock() throws Exception {

		BlockConfigImpl contentBlock = new BlockConfigImpl();
		contentBlock.setId(UUID.randomUUID().toString());
		contentBlock.setName(MainContentBlock.class.getAnnotation(Block.class).value());
		contentBlock.setRegion("header");
		BlockConfigServiceImpl.blocks.add(contentBlock);

		MvcResult result = mockMvc.perform(get("/install")).andExpect(request().asyncStarted()).andReturn();
		result.getAsyncResult();

		MvcResult finalResult = mockMvc.perform(asyncDispatch(result)).andExpect(status().isOk()).andExpect(content().contentType("text/html;charset=UTF-8")).andExpect(view().name("system/html")).andReturn();
		System.out.println(finalResult.getResponse().getContentAsString());
	}
}
