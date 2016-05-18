package com.pungwe.cms.jpa.entity.services;

import com.pungwe.cms.core.annotations.stereotypes.EntityType;
import com.pungwe.cms.core.entity.EntityDefinition;
import com.pungwe.cms.core.entity.EntityTypeDefinition;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.entity.services.EntityDefinitionService;
import com.pungwe.cms.jpa.entity.EntityDefinitionImpl;
import com.pungwe.cms.jpa.entity.EntityTypeInfoImpl;
import com.pungwe.cms.jpa.entity.repository.EntityDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * Created by ian on 13/03/2016.
 */
@Service
public class JPAEntityDefinitionService implements EntityDefinitionService<EntityDefinitionImpl> {

	@Autowired
	protected EntityDefinitionRepository entityDefinitionRepository;

	@Autowired
	protected EntityManager entityManager;

	@Override
	public EntityDefinitionImpl newInstance(EntityTypeDefinition type, String bundle) {
		EntityType typeInfo = AnnotationUtils.findAnnotation(type.getClass(), EntityType.class);
		EntityDefinitionImpl entityDefinition = new EntityDefinitionImpl(new EntityTypeInfoImpl(typeInfo.type(), bundle));
		entityDefinition.addField(type.getBaseFields().toArray(new FieldConfig[0]));
		return entityDefinition;
	}

	@Override
	public EntityDefinitionImpl newInstance(EntityTypeDefinition type) {
		return newInstance(type, null);
	}

	@Override
	@Cacheable(value="entityDefinitions", key = "#root.methodName + '_' + #a0 + '_' + #a1")
	public EntityDefinitionImpl get(String type, String bundle) {
		return entityDefinitionRepository.findOne(new EntityTypeInfoImpl(type, bundle));
	}

	@Override
	@Transactional
	public Page<EntityDefinitionImpl> list(String type, Pageable page) {
		return entityDefinitionRepository.findByType(type, page);
	}

	@Override
	@Transactional
	@CacheEvict(value="entityDefinitions", allEntries = true)
	public void create(EntityDefinitionImpl instance) {
		entityDefinitionRepository.save(instance);
	}

	@Override
	@Transactional
	public void remove(String type, String bundle) {
		entityDefinitionRepository.delete(new EntityTypeInfoImpl(type, bundle));
	}

    @Override
    public void update(EntityDefinitionImpl entityDefinition) {
        entityDefinitionRepository.save(entityDefinition);
    }
}
