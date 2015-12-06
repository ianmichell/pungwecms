package com.pungwe.cms.core.entity;

import com.pungwe.cms.core.entity.impl.EntityDefinitionImpl;
import com.pungwe.cms.core.entity.impl.EntityTypeInfoImpl;
import com.pungwe.cms.core.entity.impl.FieldConfigImpl;
import com.pungwe.cms.core.entity.impl.FieldGroupConfigImpl;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by ian on 03/12/2015.
 */
public class EntityDefinitionTest {

    @Test
    public void testAddFieldGroup() {
        FieldGroupConfigImpl g = new FieldGroupConfigImpl();
        g.setName("test");

        EntityDefinition e = new EntityDefinitionImpl();
        e.addFieldGroup(g);

        assertEquals(1, e.getFieldGroups().size());

    }

    @Test
    public void testAddField() {
        FieldConfigImpl fieldConfig = new FieldConfigImpl();
        fieldConfig.setName("test");

        EntityDefinition e = new EntityDefinitionImpl();
        e.addField(fieldConfig);

        assertEquals(1, e.getFields().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddTwoFieldsSameName() {
        FieldConfigImpl fieldConfig1 = new FieldConfigImpl();
        fieldConfig1.setName("test");

        FieldConfigImpl fieldConfig2 = new FieldConfigImpl();
        fieldConfig2.setName("test");

        EntityDefinition e = new EntityDefinitionImpl();
        e.addField(fieldConfig1);
        e.addField(fieldConfig2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddTwoFieldGroupsSameName() {
        FieldGroupConfigImpl fieldConfig1 = new FieldGroupConfigImpl();
        fieldConfig1.setName("test");

        FieldGroupConfigImpl fieldConfig2 = new FieldGroupConfigImpl();
        fieldConfig2.setName("test");

        EntityDefinition e = new EntityDefinitionImpl();
        e.addFieldGroup(fieldConfig1);
        e.addFieldGroup(fieldConfig2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddFieldAndFieldGroupSameName() {
        FieldGroupConfigImpl fieldConfig1 = new FieldGroupConfigImpl();
        fieldConfig1.setName("test");

        FieldConfigImpl fieldConfig2 = new FieldConfigImpl();
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
        FieldGroupConfigImpl fieldConfig1 = new FieldGroupConfigImpl();
        fieldConfig1.setName("group");

        FieldConfigImpl fieldConfig2 = new FieldConfigImpl();
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
        FieldConfigImpl fieldConfig2 = new FieldConfigImpl();
        fieldConfig2.setName("field");
        EntityDefinition e = new EntityDefinitionImpl();
        e.addField(fieldConfig2);
        assertTrue(e.getFieldsByGroup("group").isEmpty());
    }

    @Test
    public void testGetNoFieldsByGroup() {
        FieldGroupConfigImpl fieldConfig1 = new FieldGroupConfigImpl();
        fieldConfig1.setName("group");
        EntityDefinition e = new EntityDefinitionImpl();
        e.addFieldGroup(fieldConfig1);
        assertTrue(e.getFieldsByGroup("group").isEmpty());
    }

    @Test
    public void testGetFieldsByIncorrectGroupName() {
        FieldGroupConfigImpl fieldConfig1 = new FieldGroupConfigImpl();
        fieldConfig1.setName("group");

        FieldConfigImpl fieldConfig2 = new FieldConfigImpl();
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
        FieldGroupConfigImpl fieldConfig1 = new FieldGroupConfigImpl();
        fieldConfig1.setName("group");

        FieldConfigImpl fieldConfig2 = new FieldConfigImpl();
        fieldConfig2.setName("field");
        fieldConfig1.addChild(fieldConfig2.getName());

        EntityDefinition e = new EntityDefinitionImpl();
        e.addFieldGroup(fieldConfig1);
        e.addField("wrong", fieldConfig2);
    }

    @Test
    public void testAddFieldGroupToGroup() {
        FieldGroupConfigImpl one = new FieldGroupConfigImpl();
        one.setName("group");
        one.setWeight(5);

        FieldGroupConfigImpl two = new FieldGroupConfigImpl();
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
        FieldGroupConfigImpl one = new FieldGroupConfigImpl();
        one.setName("group");

        FieldGroupConfigImpl two = new FieldGroupConfigImpl();
        two.setName("child");

        EntityDefinition e = new EntityDefinitionImpl();
        e.addFieldGroup(one);
        e.addFieldGroup("group1", two);
    }
}
