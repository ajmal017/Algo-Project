package com.algotrading.backtesting.pattern.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

import com.algotrading.backtesting.pattern.RsiLowerThanSignal;
import com.algotrading.backtesting.pattern.RsiSignal;
import com.algotrading.backtesting.pattern.SmaHigherThanSignal;
import com.algotrading.backtesting.pattern.SmaSignal;
import com.algotrading.backtesting.portfolio.Portfolio;
import com.algotrading.backtesting.replay.test.MainTest;
import com.algotrading.backtesting.stock.Stock;
import com.algotrading.backtesting.util.Constants;

public class SmaHigherThanSignalTest {
	protected static String RESOURCE_PATH_NAME = Constants.SRC_TEST_RESOURCE_FILEPATH
			+ MainTest.class.getPackage().getName().replace('.', '/') + "/";
	
	private Date date;
	private Portfolio portfolio;
	private String expectedValueType;
	private String expectedValue;
	private int magnitude = 10;
	private double multiplier = 1;

	@Test
	public void test001_HigherThanClosing() throws ParseException {
		date = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-29");
		portfolio = new Portfolio(date, 25000);
		expectedValueType = "number";
		expectedValue = "4.8";
		Stock stockSEHK_TC0001 = new Stock("SEHK_TC0001");
		// rely on Stock.read();
		stockSEHK_TC0001.read(RESOURCE_PATH_NAME);	
		SmaSignal testSignal = new SmaHigherThanSignal(magnitude, expectedValueType, expectedValue, multiplier);
		assertTrue(testSignal.signal(stockSEHK_TC0001, date, portfolio, 10000));
		expectedValue = "5";
		testSignal.setExpectedValue(expectedValue);
		assertEquals(false, testSignal.signal(stockSEHK_TC0001, date, portfolio, 10000));
	}

	@Test
	public void test002_RsiLowerThanWith2Stocks() throws ParseException {
		date = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-29");
		portfolio = new Portfolio(date, 25000);
		expectedValueType = "number";
		expectedValue = "8.7";
		Stock stockSEHK_TC0001 = new Stock("SEHK_TC0001");
		// rely on Stock.read();
		stockSEHK_TC0001.read(RESOURCE_PATH_NAME);
		Stock stockSEHK_TC0002 = new Stock("SEHK_TC0002");
		stockSEHK_TC0002.read(RESOURCE_PATH_NAME);
		SmaSignal testSignal = new SmaHigherThanSignal(magnitude, expectedValueType, expectedValue, multiplier);
		// get stockSEHK_TC0001 first
		assertEquals(false, testSignal.signal(stockSEHK_TC0001, date, portfolio, 10000));
		// then get stockSEHK_TC0002
		assertTrue(testSignal.signal(stockSEHK_TC0002, date, portfolio, 10000));
		assertEquals(2, testSignal.getInitiatedSMA().size());
		expectedValue = "8.9";
		testSignal.setExpectedValue(expectedValue);
		assertEquals(false, testSignal.signal(stockSEHK_TC0002, date, portfolio, 10000));
		// look at stockSEHK_TC0001 again
		expectedValue = "4.8";
		testSignal.setExpectedValue(expectedValue);
		assertTrue(testSignal.signal(stockSEHK_TC0001, date, portfolio, 10000));
		expectedValue = "5";
		testSignal.setExpectedValue(expectedValue);
		assertEquals(false, testSignal.signal(stockSEHK_TC0001, date, portfolio, 10000));
		// check again the map initiatedRSI
		assertEquals(2, testSignal.getInitiatedSMA().size());
	}
}
