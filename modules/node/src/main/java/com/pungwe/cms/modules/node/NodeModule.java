package com.pungwe.cms.modules.node;

import com.pungwe.cms.core.annotations.Hook;
import com.pungwe.cms.core.annotations.Module;
import com.pungwe.cms.core.entity.EntityDefinition;
import com.pungwe.cms.core.services.EntityDefinitionService;
import com.pungwe.cms.core.utils.CommonHooks;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ian on 13/12/2015.
 */
@Module(value = "node", includePackages = {"com.pungwe.cms.modules.node"})
public class NodeModule {

    @Hook(CommonHooks.INSTALL)
    public void installBasicPage(EntityDefinitionService entityDefinitionService) {

        NodeEntityType entityType = new NodeEntityType();
        EntityDefinition basicPage = entityDefinitionService.newInstance(entityType, "basic_page");

        // Add Fields Here
        entityDefinitionService.save(basicPage);
    }

    @Hook(CommonHooks.INSTALL)
    public void installArticle(EntityDefinitionService entityDefinitionService) {
        NodeEntityType entityType = new NodeEntityType();
        EntityDefinition article = entityDefinitionService.newInstance(entityType, "article");

        // Add Fields Here
        entityDefinitionService.save(article);
    }

    @Hook(CommonHooks.UNINSTALL)
    public void uninstall(EntityDefinitionService entityDefinitionService) {
        entityDefinitionService.remove("node", "basic_page");
        entityDefinitionService.remove("node", "article");
    }
}
