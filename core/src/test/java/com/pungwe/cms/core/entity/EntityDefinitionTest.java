package com.pungwe.cms.core.entity;

import com.pungwe.cms.core.entity.impl.EntityDefinitionImpl;
import org.junit.Test;

import java.util.SortedSet;

import static org.junit.Assert.*;

/**
 * Created by ian on 03/12/2015.
 */
public class EntityDefinitionTest {

	@Test
	public void testAddFieldGroup() {
		FieldGroupConfig g = new FieldGroupConfig();
		g.setName("test");

		EntityDefinition e = new EntityDefinitionImpl();
		e.addFieldGroup(g);

		assertEquals(1, e.getFieldGroups().size());

	}

	@Test
	public void testAddField() {
		FieldConfig fieldConfig = new FieldConfig();
		fieldConfig.setName("test");

		EntityDefinition e = new EntityDefinitionImpl();
		e.addField(fieldConfig);

		assertEquals(1, e.getFields().size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddTwoFieldsSameName() {
		FieldConfig fieldConfig1 = new FieldConfig();
		fieldConfig1.setName("test");

		FieldConfig fieldConfig2 = new FieldConfig();
		fieldConfig2.setName("test");

		EntityDefinition e = new EntityDefinitionImpl();
		e.addField(fieldConfig1);
		e.addField(fieldConfig2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddTwoFieldGroupsSameName() {
		FieldGroupConfig fieldConfig1 = new FieldGroupConfig();
		fieldConfig1.setName("test");

		FieldGroupConfig fieldConfig2 = new FieldGroupConfig();
		fieldConfig2.setName("test");

		EntityDefinition e = new EntityDefinitionImpl();
		e.addFieldGroup(fieldConfig1);
		e.addFieldGroup(fieldConfig2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddFieldAndFieldGroupSameName() {
		FieldGroupConfig fieldConfig1 = new FieldGroupConfig();
		fieldConfig1.setName("test");

		FieldConfig fieldConfig2 = new FieldConfig();
		fieldConfig2.setName("test");

		EntityDefinition e = new EntityDefinitionImpl();
		e.addFieldGroup(fieldConfig1);
		e.addField(fieldConfig2);
	}

	@Test
	public void testNonExistantFieldGroupByName() {
		EntityDefinition e = new EntityDefinitionImpl();
		assertFalse(e.hasFieldGroup("group"));
	}

	@Test
	public void testNonExistantFieldByName() {
		EntityDefinition e = new EntityDefinitionImpl();
		assertFalse(e.hasField("group"));
	}

	@Test
	public void testCreateFieldInsideGroup() {
		FieldGroupConfig fieldConfig1 = new FieldGroupConfig();
		fieldConfig1.setName("group");

		FieldConfig fieldConfig2 = new FieldConfig();
		fieldConfig2.setName("field");
		fieldConfig1.addChild(fieldConfig2.getName());

		EntityDefinition e = new EntityDefinitionImpl();
		e.addFieldGroup(fieldConfig1);
		e.addField(fieldConfig1.getName(), fieldConfig2);

		SortedSet<? extends FieldConfig> result = e.getFieldsByGroup("group");
		assertTrue(result.contains(fieldConfig2));
	}

	@Test
	public void testGetFieldByNonExistantGroup() {
		FieldConfig fieldConfig2 = new FieldConfig();
		fieldConfig2.setName("field");
		EntityDefinition e = new EntityDefinitionImpl();
		e.addField(fieldConfig2);
		assertTrue(e.getFieldsByGroup("group").isEmpty());
	}

	@Test
	public void testGetNoFieldsByGroup() {
		FieldGroupConfig fieldConfig1 = new FieldGroupConfig();
		fieldConfig1.setName("group");
		EntityDefinition e = new EntityDefinitionImpl();
		e.addFieldGroup(fieldConfig1);
		assertTrue(e.getFieldsByGroup("group").isEmpty());
	}

	@Test
	public void testGetFieldsByIncorrectGroupName() {
		FieldGroupConfig fieldConfig1 = new FieldGroupConfig();
		fieldConfig1.setName("group");

		FieldConfig fieldConfig2 = new FieldConfig();
		fieldConfig2.setName("field");
		fieldConfig1.addChild(fieldConfig2.getName());

		EntityDefinition e = new EntityDefinitionImpl();
		e.addFieldGroup(fieldConfig1);
		e.addField(fieldConfig1.getName(), fieldConfig2);

		SortedSet<? extends FieldConfig> result = e.getFieldsByGroup("wrong");
		assertTrue(result.isEmpty());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddFieldByIncorrectGroupName() {
		FieldGroupConfig fieldConfig1 = new FieldGroupConfig();
		fieldConfig1.setName("group");

		FieldConfig fieldConfig2 = new FieldConfig();
		fieldConfig2.setName("field");
		fieldConfig1.addChild(fieldConfig2.getName());

		EntityDefinition e = new EntityDefinitionImpl();
		e.addFieldGroup(fieldConfig1);
		e.addField("wrong", fieldConfig2);
	}

	@Test
	public void testAddFieldGroupToGroup() {
		FieldGroupConfig one = new FieldGroupConfig();
		one.setName("group");
		one.setWeight(5);

		FieldGroupConfig two = new FieldGroupConfig();
		two.setName("child");
		two.setWeight(6);

		EntityDefinition e = new EntityDefinitionImpl();
		e.addFieldGroup(one);
		e.addFieldGroup("group", two);

		assertTrue(e.hasFieldGroup("child"));
		assertTrue(e.hasFieldGroup("group"));
		assertEquals(1, e.getFieldGroupsByParent("group").size());
		assertEquals(0, e.getFieldGroupsByParent("invalid").size());
		assertTrue(one.hasChild("child"));
		assertFalse(one.hasChild("none"));
		assertFalse(two.hasChild("another child"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddFieldGroupToInvalidGroup() {
		FieldGroupConfig one = new FieldGroupConfig();
		one.setName("group");

		FieldGroupConfig two = new FieldGroupConfig();
		two.setName("child");

		EntityDefinition e = new EntityDefinitionImpl();
		e.addFieldGroup(one);
		e.addFieldGroup("group1", two);
	}
}
