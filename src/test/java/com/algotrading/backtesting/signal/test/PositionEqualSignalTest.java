package com.algotrading.backtesting.signal.test;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;


import org.junit.Test;

import com.algotrading.backtesting.stock.Stock;
import com.algotrading.backtesting.portfolio.Portfolio;
import com.algotrading.backtesting.portfolio.PortfolioComponent;
import com.algotrading.backtesting.pattern.PositionEqualSignal;

public class PositionEqualSignalTest {

	@Test
	public void test001_PositionEqualSignal() throws Exception {
		// Test case: Enter a portfolio with quan 1000, and the signal expects 1000. should return true
		
		Stock CK = new Stock("SEHK_0001.HK");
//		CK.read();
		PortfolioComponent position = new PortfolioComponent(CK, 1000, 80);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse("20160930");
		Portfolio portfolio = new Portfolio(date);
		portfolio.add(position);
		PositionEqualSignal testSignal = new PositionEqualSignal("number", "1000", 1 );
		assertEquals(testSignal.signal(CK, null, portfolio), true);
		
	}
	
	@Test
	public void test002_PositionEqualSignal() throws Exception {
		// Test case: Enter a portfolio with quan 1000, and the signal expects 2000. should return false
		
		Stock CK = new Stock("SEHK_0001.HK");
		CK.read();
		PortfolioComponent position = new PortfolioComponent(CK, 1000, 80);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse("20160930");
		Portfolio portfolio = new Portfolio(date);
		portfolio.add(position);
		PositionEqualSignal testSignal = new PositionEqualSignal("number", "2000", 1 );
		assertEquals(testSignal.signal(CK, null, portfolio), false);
		
	}
	
	@Test
	public void test003_PositionEqualSignal() throws Exception {
		// Test case: Enter a portfolio with quan 1000, and the signal expects 1000 * 2. should return true
		
		Stock CK = new Stock("SEHK_0001.HK");
		CK.read();
		PortfolioComponent position = new PortfolioComponent(CK, 1000, 80);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse("20160930");
		Portfolio portfolio = new Portfolio(date);
		portfolio.add(position);
		PositionEqualSignal testSignal = new PositionEqualSignal("number", "2000", 2 );
		assertEquals(testSignal.signal(CK, null, portfolio), true);
		
	}	

	@Test
	public void test004_PositionEqualSignal() throws Exception {
		// Test case: Enter a portfolio with quan 1000, and the signal expects 1000 * 0.5. should return false
		
		Stock CK = new Stock("SEHK_0001.HK");
		CK.read();
		PortfolioComponent position = new PortfolioComponent(CK, 1000, 80);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse("20160930");
		Portfolio portfolio = new Portfolio(date);
		portfolio.add(position);
		PositionEqualSignal testSignal = new PositionEqualSignal("number", "1000", 0.5 );
		assertEquals(testSignal.signal(CK, null, portfolio), false);
		
	}
	
	
}
