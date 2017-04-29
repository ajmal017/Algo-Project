package com.algotrading.backtesting.patterninterpreter.test;

import java.text.ParseException;

import org.junit.Test;

import com.algotrading.backtesting.pattern.SmaHigherThanSignal;
import com.algotrading.backtesting.patterninterpreter.SmaHigherThanInterpreter;
import com.algotrading.backtesting.patterninterpreter.StringContext;

import static org.junit.Assert.assertEquals;


public class SmaHigherThanInterpreterTest {

	@Test
	public void test001_basic() throws ParseException {
		StringContext context = new StringContext("SMAHigher[ magnitude=10 expectedValueType=number expectedValue=30 multiplier=1.5]");
		SmaHigherThanInterpreter interpreter = new SmaHigherThanInterpreter();
		interpreter.parse(context);
		assertEquals(new SmaHigherThanSignal(10, "number", "30", 1.5 ), interpreter.execute());
	}

	@Test
	public void test002_default_expectedValueType() throws ParseException {
		StringContext context = new StringContext("Pos[ magnitude=10 expectedValue=30 multiplier=1.2]");
		SmaHigherThanInterpreter interpreter = new SmaHigherThanInterpreter();
		interpreter.parse(context);
		assertEquals(new SmaHigherThanSignal(10, "number", "30", 1.2 ), interpreter.execute());
	}

	@Test
	public void test003_default_multiplier() throws ParseException {
		StringContext context = new StringContext("Pos[ magnitude=15 expectedValueType=number expectedValue=30 ]");
		SmaHigherThanInterpreter interpreter = new SmaHigherThanInterpreter();
		interpreter.parse(context);
		assertEquals(new SmaHigherThanSignal(15, "number", "30", 1 ), interpreter.execute());
	}

	@Test
	public void test004_variable_closingHistory() throws ParseException {
		StringContext context = new StringContext("Pos[ magnitude=15 expectedValueType=variable expectedValue=closingHistory ]");
		SmaHigherThanInterpreter interpreter = new SmaHigherThanInterpreter();
		interpreter.parse(context);
		assertEquals(new SmaHigherThanSignal(15, "variable", "closing", 1 ), interpreter.execute());
	}
}
