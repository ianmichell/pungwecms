package com.pungwe.cms.core.entity;

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
		FieldGroupConfig one = new FieldGroupConfig();
		one.setName("one");
		FieldGroupConfig two = new FieldGroupConfig();
		two.setName("two");

		SortedSet<FieldGroupConfig> set = new TreeSet<FieldGroupConfig>();
		set.add(one);
		set.add(two);

		assertEquals(2, set.size());
		assertEquals(one, set.first());
	}

	@Test
	public void testAddUniqueWeightAndEqualName() {
		FieldGroupConfig one = new FieldGroupConfig();
		one.setName("one");
		one.setWeight(1);
		FieldGroupConfig two = new FieldGroupConfig();
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
		FieldGroupConfig one = new FieldGroupConfig();
		one.setName("one");
		one.setWeight(1);
		one.addChild("two");
		assertTrue(one.hasChild("two"));
	}
}
