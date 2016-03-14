package com.pungwe.cms.jpa.entity.services;

import com.pungwe.cms.core.entity.EntityType;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.entity.services.EntityDefinitionService;
import com.pungwe.cms.jpa.entity.EntityDefinitionImpl;
import com.pungwe.cms.jpa.entity.EntityTypeInfoImpl;
import com.pungwe.cms.jpa.entity.repository.EntityDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ian on 13/03/2016.
 */
@Service
public class JPAEntityDefinitionService implements EntityDefinitionService<EntityDefinitionImpl> {

	@Autowired
	protected EntityDefinitionRepository entityDefinitionRepository;

	@Override
	public EntityDefinitionImpl newInstance(EntityType type, String bundle) {
		EntityDefinitionImpl entityDefinition = new EntityDefinitionImpl(new EntityTypeInfoImpl(type.getType(), bundle));
		entityDefinition.addField(type.getBaseFields().toArray(new FieldConfig[0]));
		return entityDefinition;
	}

	@Override
	public EntityDefinitionImpl get(String type, String bundle) {
		return entityDefinitionRepository.findOne(new EntityTypeInfoImpl(type, bundle));
	}

	@Override
	public Page<EntityDefinitionImpl> list(String type, Pageable page) {
		return entityDefinitionRepository.findAllByType(type, page);
	}

	@Override
	public void create(EntityDefinitionImpl instance) {

	}

	@Override
	public void remove(String type, String bundle) {

	}
}
