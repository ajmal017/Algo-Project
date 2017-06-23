package com.algotrading.backtesting.pattern.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.algotrading.backtesting.pattern.isSufficientCashSignal;
import com.algotrading.backtesting.portfolio.Portfolio;
import com.algotrading.backtesting.stock.Stock;

public class isSufficientCashSignalTest {
	
	private Date date;
	Portfolio portfolio;
	Stock stock0281;
	
	
	@Test
	public void test001_haveSufficientCash() throws ParseException {
		date = new SimpleDateFormat("yyyyMMdd").parse("20170318");
		portfolio = new Portfolio(date, 25000);
		stock0281 = new Stock("0281");
		isSufficientCashSignal iscs = new isSufficientCashSignal();
		assertTrue(iscs.signal(stock0281, date, portfolio, 20000));			
	}

	@Test
	public void test002_notSufficientCash() throws ParseException {
		date = new SimpleDateFormat("yyyyMMdd").parse("20170318");
		portfolio = new Portfolio(date, 19000);
		stock0281 = new Stock("0281");
		isSufficientCashSignal iscs = new isSufficientCashSignal();
		assertEquals(false, iscs.signal(stock0281, date, portfolio, 20000));			
	}
	
}
