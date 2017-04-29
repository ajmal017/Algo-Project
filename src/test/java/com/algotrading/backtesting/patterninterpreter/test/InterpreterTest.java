package com.algotrading.backtesting.patterninterpreter.test;

import java.text.ParseException;

import org.junit.Test;

import com.algotrading.backtesting.pattern.SmaHigherThanSignal;
import com.algotrading.backtesting.pattern.StockSignal;
import com.algotrading.backtesting.patterninterpreter.Expr;
import com.algotrading.backtesting.patterninterpreter.Node;
import com.algotrading.backtesting.patterninterpreter.StringContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InterpreterTest {

	@Test
	public void test001_SeInterperter() throws ParseException {
		String expected = "SE[ 012 ]";
		String input = "SE[ 012 ]";
		StockSignal pattern = interpertToPattern(input);
		assertEquals(expected, pattern.toString());
	}

	@Test
	public void test002_andInterperter() throws ParseException {
		String expected = "AND( SE[ 012 ] , SE[ 234 ] )";
		String input = "AND( SE[ 012 ] , SE[ 234 ] )";
		StockSignal pattern = interpertToPattern(input);
		assertEquals(expected, pattern.toString());
	}

	@Test
	public void test003_orInterperter() throws ParseException {
		String expected = "OR( SE[ 012 ] , SE[ 234 ] )";
		String input = "OR( SE[ 012 ] , SE[ 234 ] )";
		StockSignal pattern = interpertToPattern(input);
		assertEquals(expected, pattern.toString());
	}

	@Test
	public void test004_notInterperter() throws ParseException {
		String expected = "NOT( SE[ 012 ] )";
		String input = "NOT( SE[ 012 ] )";
		StockSignal pattern = interpertToPattern(input);
		assertEquals(expected, pattern.toString());
	}

	@Test
	public void test005_ComplexInterperter() throws ParseException {
		String expected = "AND( OR( SE[ 123 ] , NOT( SE[ 456 ] ) ) , SE[ 012 ] )";
		String input = "AND( OR( SE[ 123 ] , NOT( SE[ 456 ] ) ) , SE[ 012 ] )";
		StockSignal pattern = interpertToPattern(input);
		assertEquals(expected, pattern.toString());
	}

	@Test
	public void test006_SMAHigherInterperter() throws ParseException {
		String expected = "SMAHigher[ magnitude=10 expectedValue=30 ]";
		StockSignal pattern = interpertToPattern(expected);
		assertTrue(pattern instanceof SmaHigherThanSignal);
		SmaHigherThanSignal smaHigherThanSignal = (SmaHigherThanSignal) pattern;
		assertEquals(10, smaHigherThanSignal.getMagnitude());
		// assertEquals(30, smaHigherThanSignal.getExpectedValue(), 0.01); //
		// TODO to fix
	}

	private StockSignal interpertToPattern(String input) throws ParseException {
		Node node = new Expr();
		node.parse(new StringContext(input));
		StockSignal pattern = node.execute();
		return pattern;
	}

}
