package com.algotrading.backtesting.pattern.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.algotrading.backtesting.pattern.cashMoreThanSignal;
import com.algotrading.backtesting.portfolio.Portfolio;
import com.algotrading.backtesting.stock.Stock;

public class cashMoreThanSignalTest {

	private Date date;
	private Portfolio portfolio;
	private Stock stock0281;
	private String expectedValueType;
	private String expectedValue;
	private double multiplier = 1;

	@Test
	public void test001_haveSufficientCash() throws ParseException {
		date = new SimpleDateFormat("yyyyMMdd").parse("20170318");
		portfolio = new Portfolio(date, 25000);
		stock0281 = new Stock("0281");
		expectedValueType = "number";
		expectedValue = "23000";
		cashMoreThanSignal cmt = new cashMoreThanSignal(expectedValueType, expectedValue, multiplier);
		assertTrue(cmt.signal(stock0281, date, portfolio, 20000));			
	}

	@Test
	public void test002_notSufficientCash() throws ParseException {
		date = new SimpleDateFormat("yyyyMMdd").parse("20170318");
		portfolio = new Portfolio(date, 25000);
		stock0281 = new Stock("0281");
		expectedValueType = "number";
		expectedValue = "30000";
		cashMoreThanSignal cmt = new cashMoreThanSignal(expectedValueType, expectedValue, multiplier);
		assertEquals(false, cmt.signal(stock0281, date, portfolio, 20000));			
	}
	
	@Test
	public void test003_mimic_isSufficientCash() throws ParseException {
		date = new SimpleDateFormat("yyyyMMdd").parse("20170318");
		portfolio = new Portfolio(date, 25000);
		stock0281 = new Stock("0281");
		expectedValueType = "cost";
		expectedValue = "0";
		cashMoreThanSignal cmt = new cashMoreThanSignal(expectedValueType, expectedValue, multiplier);
		assertTrue(cmt.signal(stock0281, date, portfolio, 20000));				
	}
	
	@Test
	public void test004_mimic_NotisSufficientCash() throws ParseException {
		date = new SimpleDateFormat("yyyyMMdd").parse("20170318");
		portfolio = new Portfolio(date, 19000);
		stock0281 = new Stock("0281");
		expectedValueType = "cost";
		expectedValue = "0";
		cashMoreThanSignal cmt = new cashMoreThanSignal(expectedValueType, expectedValue, multiplier);
		assertEquals(false, cmt.signal(stock0281, date, portfolio, 20000));					
	}

	@Test
	public void test005_SufficientCashByMultiplier() throws ParseException {
		date = new SimpleDateFormat("yyyyMMdd").parse("20170318");
		portfolio = new Portfolio(date, 25000);
		stock0281 = new Stock("0281");
		expectedValueType = "cost";
		expectedValue = "0";
		multiplier = 1.2;
		cashMoreThanSignal cmt = new cashMoreThanSignal(expectedValueType, expectedValue, multiplier);
		assertTrue(cmt.signal(stock0281, date, portfolio, 20000));				
	}
	@Test
	public void test006_NotSufficientCashByMultiplier() throws ParseException {
		date = new SimpleDateFormat("yyyyMMdd").parse("20170318");
		portfolio = new Portfolio(date, 28000);
		stock0281 = new Stock("0281");
		expectedValueType = "cost";
		expectedValue = "0";
		multiplier = 1.5;
		cashMoreThanSignal cmt = new cashMoreThanSignal(expectedValueType, expectedValue, multiplier);
		assertEquals(false, cmt.signal(stock0281, date, portfolio, 20000));					
	}
}
