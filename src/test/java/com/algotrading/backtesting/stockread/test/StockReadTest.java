package com.algotrading.backtesting.stockread.test;

import java.text.ParseException;

import org.junit.Test;

import com.algotrading.backtesting.stock.Stock;
// import com.algotrading.backtesting.stock.StockHistory;
import com.algotrading.backtesting.util.Constants;

import static org.junit.Assert.assertEquals;
// import static org.junit.Assert.assertNull;

public class StockReadTest {
	@Test
	public void test001_OpenPrice() throws ParseException {
		double expected = 98.5;
		String ticker = "SEHK_0001";
		String strDate = "2016-09-30";
		String strInformation = "open";
		assertEquals(expected, getStockHistory(ticker, strDate, strInformation), 0.01);
	}

	@Test
	public void test002_ClosePrice() throws ParseException {
		double expected = 100;
		String ticker = "SEHK_0001";
		String strDate = "2016-09-30";
		String strInformation = "close";
		assertEquals(expected, getStockHistory(ticker, strDate, strInformation), 0.01);
	}

	@Test
	public void test003_HighPrice() throws ParseException {
		double expected = 100;
		String ticker = "SEHK_0001";
		String strDate = "2016-09-30";
		String strInformation = "high";
		assertEquals(expected, getStockHistory(ticker, strDate, strInformation), 0.01);
	}

	@Test
	public void test004_LowPrice() throws ParseException {
		double expected = 98.05;
		String ticker = "SEHK_0001";
		String strDate = "2016-09-30";
		String strInformation = "low";
		assertEquals(expected, getStockHistory(ticker, strDate, strInformation), 0.01);
	}

	@Test
	public void test005_AdjClosePrice() throws ParseException {
		double expected = 98.6;
		String ticker = "SEHK_0001";
		String strDate = "2016-09-30";
		String strInformation = "adjclose";
		assertEquals(expected, getStockHistory(ticker, strDate, strInformation), 0.01);
	}

	@Test
	public void test006_Volume() throws ParseException {
		double expected = 10000;
		String ticker = "SEHK_0001";
		String strDate = "2016-09-30";
		String strInformation = "volume";
		assertEquals(expected, getStockHistory(ticker, strDate, strInformation), 0.01);
	}

	private double getStockHistory(String ticker, String strDate, String strInformation) throws ParseException {
		Stock SEHK_0001_HK = new Stock(ticker);
		SEHK_0001_HK.read(Constants.SRC_TEST_RESOURCE_FILEPATH, false);
		// StockHistory test_sh = SEHK_0001_HK.getHistory().get(Constants.DATE_FORMAT_YYYYMMDD.parse(strDate));
		// assertNull(test_sh);
		if (strInformation.equals("open")) {
			return SEHK_0001_HK.getHistory()
					.get(Constants.DATE_FORMAT_YYYYMMDD.parse(strDate))
					.getOpen();
		}
		if (strInformation.equals("close")) {
			return SEHK_0001_HK.getHistory()
					.get(Constants.DATE_FORMAT_YYYYMMDD.parse(strDate))
					.getClose();
		}
		if (strInformation.equals("high")) {
			return SEHK_0001_HK.getHistory()
					.get(Constants.DATE_FORMAT_YYYYMMDD.parse(strDate))
					.getHigh();
		}
		if (strInformation.equals("low")) {
			return SEHK_0001_HK.getHistory()
					.get(Constants.DATE_FORMAT_YYYYMMDD.parse(strDate))
					.getLow();
		}
		if (strInformation.equals("adjclose")) {
			return SEHK_0001_HK.getHistory()
					.get(Constants.DATE_FORMAT_YYYYMMDD.parse(strDate))
					.getAdjClose();
		} else if (strInformation.equals("volume")) {
			return SEHK_0001_HK.getHistory()
					.get(Constants.DATE_FORMAT_YYYYMMDD.parse(strDate))
					.getVolume();
		} else {
			return 0.0;
		}
	}

}
