package com.algotrading.backtesting.signal.test;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;


import org.junit.Test;

import com.algotrading.backtesting.stock.Stock;
import com.algotrading.backtesting.pattern.SmaHigherThanSignal;
import com.algotrading.backtesting.portfolio.Portfolio;
import com.algotrading.backtesting.portfolio.PortfolioComponent;

public class SmaHigherThanSignalTest {

	@Test
	public void test001_higherthan99() throws Exception {
		// Test case: Use SEHK_0001.csv, Average = (100 * 4 + 2 * 99) / 6 > 99, should return true
		
		Stock CK = new Stock("SEHK_0001.HK");
		CK.read();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse("20160930");
		SmaHigherThanSignal testSignal = new SmaHigherThanSignal(5, "number", "99", 1 );
		assertEquals(testSignal.signal(CK, date, null), true);
		
	}
	
	@Test
	public void test002_lessthan100() throws Exception {
		// Test case: Use SEHK_0001.csv, Average = (100 * 4 + 2 * 99) / 6 < 100, should return false
		
		Stock CK = new Stock("SEHK_0001.HK");
		CK.read();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse("20160930");
		SmaHigherThanSignal testSignal = new SmaHigherThanSignal(5, "number", "100", 1 );
		assertEquals(testSignal.signal(CK, date, null), false);
	}
	
	public void test003_multiplier() throws Exception {
		// Test case: Use SEHK_0001.csv, Average = (100 * 4 + 2 * 99) / 6 * 6 > 597, should return true
		
		Stock CK = new Stock("SEHK_0001.HK");
		CK.read();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse("20160930");
		SmaHigherThanSignal testSignal = new SmaHigherThanSignal(5, "number", "597", 6 );
		assertEquals(testSignal.signal(CK, date, null), true);
	}
	
	public void test004_multiplier() throws Exception {
		// Test case: Use SEHK_0001.csv, Average = (100 * 4 + 2 * 99) / 6 * 6 < 599, should return true
		
		Stock CK = new Stock("SEHK_0001.HK");
		CK.read();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse("20160930");
		SmaHigherThanSignal testSignal = new SmaHigherThanSignal(5, "number", "599", 6 );
		assertEquals(testSignal.signal(CK, date, null), false);
	}

	public void test005_higherthanclosing() throws Exception {
		// Test case: Use SEHK_0001.csv, Average = (100 * 4 + 2 * 99) / 6 * 6 < 599, should return true
		
		Stock CK = new Stock("SEHK_0001.HK");
		CK.read();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse("20160926"); // closing = 99
		SmaHigherThanSignal testSignal = new SmaHigherThanSignal(5, "variable", "closing", 1 );
		assertEquals(testSignal.signal(CK, date, null), true);
	}
	
	public void test006_lessthanclosing() throws Exception {
		// Test case: Use SEHK_0001.csv, Average = (100 * 4 + 2 * 99) / 6 * 6 < 599, should return true
		
		Stock CK = new Stock("SEHK_0001.HK");
		CK.read();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse("20160930"); // closing = 100
		SmaHigherThanSignal testSignal = new SmaHigherThanSignal(5, "variable", "closing", 1 );
		assertEquals(testSignal.signal(CK, date, null), false);
	}
	
}
