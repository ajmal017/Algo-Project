package com.algotrading.backtesting.patterninterpreter.test;

import java.text.ParseException;

import org.junit.Test;

import com.algotrading.backtesting.pattern.PositionEqualSignal;
import com.algotrading.backtesting.patterninterpreter.PositionEqualInterpreter;
import com.algotrading.backtesting.patterninterpreter.StringContext;

import static org.junit.Assert.assertEquals;


public class PositionEqualInterpreterTest {

	@Test
	public void test001_basic() throws ParseException {
		StringContext context = new StringContext("Pos[ expectedValueType=number expectedValue=30 multiplier=1.5]");
		PositionEqualInterpreter interpreter = new PositionEqualInterpreter();
		interpreter.parse(context);
		assertEquals(new PositionEqualSignal("number", "30", 1.5 ), interpreter.execute());
	}

	@Test
	public void test002_default_expectedValueType() throws ParseException {
		StringContext context = new StringContext("Pos[ expectedValue=30 multiplier=1.2]");
		PositionEqualInterpreter interpreter = new PositionEqualInterpreter();
		interpreter.parse(context);
		assertEquals(new PositionEqualSignal("number", "30", 1.2 ), interpreter.execute());
	}

	@Test
	public void test003_default_multiplier() throws ParseException {
		StringContext context = new StringContext("Pos[ expectedValueType=number expectedValue=30 ]");
		PositionEqualInterpreter interpreter = new PositionEqualInterpreter();
		interpreter.parse(context);
		assertEquals(new PositionEqualSignal("number", "30", 1 ), interpreter.execute());
	}
	
}
