package com.algotrading.backtesting.patterninterperter.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import com.algotrading.backtesting.patterninterperter.StringContext;
import com.algotrading.backtesting.patterninterperter.cashInterpreter;
import com.algotrading.backtesting.portfolio.Portfolio;
import com.algotrading.backtesting.stock.Stock;

public class cashInterpreterTest {

	private Date date;
	private Portfolio portfolio;
	private Stock stock0281;
	private cashInterpreter interpreter;
	private StringContext stringContext;
	
	@Test
	public void test001_CashMoreThan20000() throws ParseException {
		date = new SimpleDateFormat("yyyyMMdd").parse("20170318");
		portfolio = new Portfolio(date, 25000);
		stock0281 = new Stock("0281");
		interpreter = new cashInterpreter();
		stringContext = new StringContext("cashMoreThan[ expectedValueType=number expectedValue=20000 ]");
		interpreter.parse(stringContext);
		assertTrue(interpreter.execute().signal(stock0281, date, portfolio, 20000));
	}	

	@Test
	public void test002_NotCashMoreThan20000() throws ParseException {
		date = new SimpleDateFormat("yyyyMMdd").parse("20170318");
		portfolio = new Portfolio(date, 19000);
		stock0281 = new Stock("0281");
		interpreter = new cashInterpreter();
		stringContext = new StringContext("cashMoreThan[ expectedValueType=number expectedValue=20000 ]");
		interpreter.parse(stringContext);
		assertEquals(false, interpreter.execute().signal(stock0281, date, portfolio, 20000));
	}	

	
	@Test
	public void test003_CashMoreThan20000x1point2() throws ParseException {
		date = new SimpleDateFormat("yyyyMMdd").parse("20170318");
		portfolio = new Portfolio(date, 25000);
		stock0281 = new Stock("0281");
		interpreter = new cashInterpreter();
		stringContext = new StringContext("cashMoreThan[ expectedValueType=number expectedValue=20000 multiplier=1.2 ]");
		interpreter.parse(stringContext);
		assertTrue(interpreter.execute().signal(stock0281, date, portfolio, 20000));
	}

	@Test
	public void test004_NotCashMoreThan20000x1point5() throws ParseException {
		date = new SimpleDateFormat("yyyyMMdd").parse("20170318");
		portfolio = new Portfolio(date, 28000);
		stock0281 = new Stock("0281");
		interpreter = new cashInterpreter();
		stringContext = new StringContext("cashMoreThan[ expectedValueType=number expectedValue=20000 multiplier=1.5 ]");
		interpreter.parse(stringContext);
		assertEquals(false, interpreter.execute().signal(stock0281, date, portfolio, 20000));
	}	

	@Test
	public void test005_isSufficientCash() throws ParseException {
		date = new SimpleDateFormat("yyyyMMdd").parse("20170318");
		portfolio = new Portfolio(date, 25000);
		stock0281 = new Stock("0281");
		interpreter = new cashInterpreter();
		stringContext = new StringContext("isSufficientCash[ ]");
		interpreter.parse(stringContext);
		assertTrue(interpreter.execute().signal(stock0281, date, portfolio, 20000));
	}

	@Test
	public void test006_NotisSufficientCash() throws ParseException {
		date = new SimpleDateFormat("yyyyMMdd").parse("20170318");
		portfolio = new Portfolio(date, 25000);
		stock0281 = new Stock("0281");
		interpreter = new cashInterpreter();
		stringContext = new StringContext("isSufficientCash[ ]");
		interpreter.parse(stringContext);
		assertEquals(false, interpreter.execute().signal(stock0281, date, portfolio, 30000));
	}

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void test007_isSufficientCashErrorIfParam() throws ParseException {
		expectedEx.expect(ParseException.class);
		expectedEx.expectMessage("Signal 'isSufficientCash' : 'expectedValueType' no field match");
		date = new SimpleDateFormat("yyyyMMdd").parse("20170318");
		portfolio = new Portfolio(date, 25000);
		stock0281 = new Stock("0281");
		interpreter = new cashInterpreter();
		stringContext = new StringContext("isSufficientCash[ expectedValueType=number ]");
		interpreter.parse(stringContext);
	}

	@Test
	public void test008_cashMoreThanErrorInvalidParam() throws ParseException {
		expectedEx.expect(ParseException.class);
		expectedEx.expectMessage("Signal 'cashMoreThan' : 'expectedValues' no field match");
		date = new SimpleDateFormat("yyyyMMdd").parse("20170318");
		portfolio = new Portfolio(date, 25000);
		stock0281 = new Stock("0281");
		interpreter = new cashInterpreter();
		stringContext = new StringContext("cashMoreThan[ expectedValues=10000 ]");
		interpreter.parse(stringContext);
	}
}
