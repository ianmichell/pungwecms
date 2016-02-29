package com.pungwe.cms.core.system.install;

import com.pungwe.cms.core.config.BaseApplicationConfig;
import com.pungwe.cms.test.AbstractWebTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.ui.ModelMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

/**
 * Created by ian on 27/02/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(BaseApplicationConfig.class)
@WebAppConfiguration("src/main/resources")
public class InstallControllerTest extends AbstractWebTest {

	@Test
	public void testViaWebTest() throws Exception {
		MvcResult result = mockMvc.perform(get("/install")).andExpect(request().asyncStarted()).andReturn();
		result.getAsyncResult();

		mockMvc.perform(asyncDispatch(result)).andExpect(status().isOk()).andExpect(content().contentType("text/html;charset=UTF-8")).andExpect(view().name("install/index"));
	}
}
