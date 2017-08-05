package com.algotrading.backtesting.signal.test;

import java.util.Date;

import org.junit.Test;

import com.algotrading.backtesting.pattern.SmaHigherThanSignal;
import com.algotrading.backtesting.stock.Stock;
import com.algotrading.backtesting.util.Constants;

import static org.junit.Assert.assertEquals;

public class SmaHigherThanSignalTest {

	protected static double BUY_COST_IF_MATCH = 1000;

	protected static double PORTFOLIO_INITIAL_CASH = 0;

	@Test
	public void test001_higherthan99() throws Exception {
		// Test case: Use SEHK_0001.csv, Average = (100 * 4 + 2 * 99) / 6 > 99,
		// should return true

		Stock CK = new Stock("SEHK_0001");
		CK.read(Constants.SRC_TEST_RESOURCE_FILEPATH, false);
		Date date = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-30");
		SmaHigherThanSignal testSignal = new SmaHigherThanSignal(5, "number", "99", 1);
		assertEquals(testSignal.signal(CK, date, null, BUY_COST_IF_MATCH), true);

	}

	@Test
	public void test002_lessthan100() throws Exception {
		// Test case: Use SEHK_0001.csv, Average = (100 * 4 + 2 * 99) / 6 < 100,
		// should return false

		Stock CK = new Stock("SEHK_0001");
		CK.read(Constants.SRC_TEST_RESOURCE_FILEPATH, false);
		Date date = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-30");
		SmaHigherThanSignal testSignal = new SmaHigherThanSignal(5, "number", "100", 1);
		assertEquals(testSignal.signal(CK, date, null, BUY_COST_IF_MATCH), false);
	}

	@Test
	public void test003_multiplier() throws Exception {
		// Test case: Use SEHK_0001.csv, Average = (100 * 4 + 2 * 99) / 6 * 6 >
		// 597, should return true

		Stock CK = new Stock("SEHK_0001");
		CK.read(Constants.SRC_TEST_RESOURCE_FILEPATH, false);
		Date date = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-30");
		SmaHigherThanSignal testSignal = new SmaHigherThanSignal(5, "number", "597", 6);
		assertEquals(testSignal.signal(CK, date, null, BUY_COST_IF_MATCH), true);
	}

	@Test
	public void test004_multiplier() throws Exception {
		// Test case: Use SEHK_0001.csv, Average = (100 * 4 + 2 * 99) / 6 * 6 <
		// 599, should return true

		Stock CK = new Stock("SEHK_0001");
		CK.read(Constants.SRC_TEST_RESOURCE_FILEPATH, false);
		Date date = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-30");
		SmaHigherThanSignal testSignal = new SmaHigherThanSignal(5, "number", "599", 6);
		assertEquals(testSignal.signal(CK, date, null, BUY_COST_IF_MATCH), false);
	}

	@Test
	public void test005_lessthanclosing() throws Exception {
		// Test case: Use SEHK_0001.csv. SMA < 100 should return false
		Stock CK = new Stock("SEHK_0001");
		CK.read(Constants.SRC_TEST_RESOURCE_FILEPATH, false);
		Date date = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-30"); // closing
																		// = 100
		SmaHigherThanSignal testSignal = new SmaHigherThanSignal(5, "variable", "closing", 1);
		assertEquals(testSignal.signal(CK, date, null, BUY_COST_IF_MATCH), false);
	}

	@Test
	public void test006_higherthanclosingwithmultipler() throws Exception {
		// Test case: Use SEHK_0001.csv, SMA * 101/99.8 = 101 > 100 should
		// return true
		Stock CK = new Stock("SEHK_0001");
		CK.read(Constants.SRC_TEST_RESOURCE_FILEPATH, false);
		Date date = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-30"); // closing
																		// = 100
		SmaHigherThanSignal testSignal = new SmaHigherThanSignal(5, "variable", "closing", 101 / 99.8);
		assertEquals(testSignal.signal(CK, date, null, BUY_COST_IF_MATCH), true);
	}

	@Test
	public void test007_higherthanclosing() throws Exception {
		// Test case: Use SEHK_0001.csv, SMA = 100.2 > 100 should return true
		Stock CK = new Stock("SEHK_0002");
		CK.read(Constants.SRC_TEST_RESOURCE_FILEPATH, false);
		Date date = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-30"); // closing
																		// = 100
		SmaHigherThanSignal testSignal = new SmaHigherThanSignal(5, "variable", "closing", 1);
		assertEquals(testSignal.signal(CK, date, null, BUY_COST_IF_MATCH), true);
	}
}
