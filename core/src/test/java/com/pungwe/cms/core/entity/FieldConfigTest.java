package com.pungwe.cms.core.entity;

import com.pungwe.cms.core.entity.impl.FieldConfigImpl;
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
        FieldConfigImpl one = new FieldConfigImpl();
        one.setName("one");
        FieldConfigImpl two = new FieldConfigImpl();
        two.setName("two");

        SortedSet<FieldConfigImpl> set = new TreeSet<FieldConfigImpl>();
        set.add(one);
        set.add(two);

        assertEquals(2, set.size());
        assertEquals(one, set.first());
    }

    @Test
    public void testAddUniqueWeightAndEqualName() {
        FieldConfigImpl one = new FieldConfigImpl();
        one.setName("one");
        one.setWeight(1);
        FieldConfigImpl two = new FieldConfigImpl();
        two.setName("one");
        two.setWeight(2);

        SortedSet<FieldConfigImpl> set = new TreeSet<FieldConfigImpl>();
        set.add(one);
        set.add(two);

        assertEquals("Size is not one", 1, set.size());

        // Two replaces one
        assertEquals(1, set.first().getWeight());
    }
}
