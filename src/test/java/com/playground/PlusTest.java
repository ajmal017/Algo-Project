package com.playground;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlusTest {

	@Test
	public void testPlus() {
		Plus plus = new Plus(2, 4);
		double expected = 6;
		double actual = plus.calculate();
		assertEquals(expected, actual, 0.01);
	}

	@Test
	public void testPlusNegative() {
		Plus plus = new Plus(-1, 0);
		double expected = -1;
		double actual = plus.calculate();
		assertEquals(expected, actual, 0.01);
	}

}
