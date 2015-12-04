package com.pungwe.cms.core.entity;

import com.pungwe.cms.core.entity.impl.FieldGroupConfigImpl;
import org.junit.Test;

import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ian on 03/12/2015.
 */
public class FieldGroupConfigTest {

    @Test
    public void testAddUniqueNameAndEqualWeightToTreeSet() {
        FieldGroupConfigImpl one = new FieldGroupConfigImpl();
        one.setName("one");
        FieldGroupConfigImpl two = new FieldGroupConfigImpl();
        two.setName("two");

        SortedSet<FieldGroupConfig> set = new TreeSet<FieldGroupConfig>();
        set.add(one);
        set.add(two);

        assertEquals(2, set.size());
        assertEquals(one, set.first());
    }

    @Test
    public void testAddUniqueWeightAndEqualName() {
        FieldGroupConfigImpl one = new FieldGroupConfigImpl();
        one.setName("one");
        one.setWeight(1);
        FieldGroupConfigImpl two = new FieldGroupConfigImpl();
        two.setName("one");
        two.setWeight(2);

        SortedSet<FieldGroupConfig> set = new TreeSet<FieldGroupConfig>();
        set.add(one);
        set.add(two);

        assertEquals("Size is not one", 1, set.size());

        // Two replaces one
        assertEquals(1, set.first().getWeight());
    }

    @Test
    public void testAddChild() {
        FieldGroupConfigImpl one = new FieldGroupConfigImpl();
        one.setName("one");
        one.setWeight(1);
        one.addChild("two");
        assertTrue(one.hasChild("two"));
    }
}
