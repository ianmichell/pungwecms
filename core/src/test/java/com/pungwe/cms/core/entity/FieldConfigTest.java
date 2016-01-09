package com.pungwe.cms.core.entity;

import org.junit.Test;

import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

/**
 * Created by ian on 03/12/2015.
 */
public class FieldConfigTest {

    @Test
    public void testAddUniqueNameAndEqualWeightToTreeSet() {
        FieldConfig one = new FieldConfig();
        one.setName("one");
        FieldConfig two = new FieldConfig();
        two.setName("two");

        SortedSet<FieldConfig> set = new TreeSet<FieldConfig>();
        set.add(one);
        set.add(two);

        assertEquals(2, set.size());
        assertEquals(one, set.first());
    }

    @Test
    public void testAddUniqueNameAndWeightToTreeSet() {
        FieldConfig one = new FieldConfig();
        one.setName("one");
        one.setWeight(1);
        FieldConfig two = new FieldConfig();
        two.setName("two");
        two.setWeight(2);

        SortedSet<FieldConfig> set = new TreeSet<FieldConfig>();
        set.add(one);
        set.add(two);

        assertEquals(2, set.size());
        assertEquals(one, set.first());
    }

    @Test
    public void testAddUniqueWeightAndEqualName() {
        FieldConfig one = new FieldConfig();
        one.setName("one");
        one.setWeight(1);
        FieldConfig two = new FieldConfig();
        two.setName("one");
        two.setWeight(2);

        SortedSet<FieldConfig> set = new TreeSet<FieldConfig>();
        set.add(one);
        set.add(two);

        assertEquals("Size is not one", 1, set.size());

        // Two replaces one
        assertEquals(1, set.first().getWeight());
    }
}
