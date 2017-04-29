package com.algotrading.backtesting.stockread.test;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

import com.algotrading.backtesting.stock.Stock;

public class StockReadTest {
	@Test
	public void test001_OpenPrice() throws ParseException {
		double expected = 97.20;
		String ticker = "SEHK_0001.HK";
		String strDate = "20160822";
		String strInformation = "open";
		assertEquals(expected, getStockHistory(ticker, strDate, strInformation), 0.01);
	}

	@Test
	public void test002_ClosePrice() throws ParseException {
		double expected = 98.05;
		String ticker = "SEHK_0001.HK";
		String strDate = "20160822";
		String strInformation = "close";
		assertEquals(expected, getStockHistory(ticker, strDate, strInformation), 0.01);
	}

	@Test
	public void test003_HighPrice() throws ParseException {
		double expected = 98.20;
		String ticker = "SEHK_0001.HK";
		String strDate = "20160822";
		String strInformation = "high";
		assertEquals(expected, getStockHistory(ticker, strDate, strInformation), 0.01);
	}

	@Test
	public void test004_LowPrice() throws ParseException {
		double expected = 97.00;
		String ticker = "SEHK_0001.HK";
		String strDate = "20160822";
		String strInformation = "low";
		assertEquals(expected, getStockHistory(ticker, strDate, strInformation), 0.01);
	}

	@Test
	public void test005_AdjClosePrice() throws ParseException {
		double expected = 97.315;
		String ticker = "SEHK_0001.HK";
		String strDate = "20160822";
		String strInformation = "adjclose";
		assertEquals(expected, getStockHistory(ticker, strDate, strInformation), 0.01);
	}

	@Test
	public void test006_Volume() throws ParseException {
		double expected = 4021200;
		String ticker = "SEHK_0001.HK";
		String strDate = "20160822";
		String strInformation = "volume";
		assertEquals(expected, getStockHistory(ticker, strDate, strInformation), 0.01);
	}

	private double getStockHistory(String ticker, String strDate, String strInformation) throws ParseException {
		Stock SEHK_0001_HK = new Stock(ticker);
		SEHK_0001_HK.read();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if (strInformation.equals("open"))
			return SEHK_0001_HK.getHistory().get(sdf.parse(strDate)).getOpen();
		if (strInformation.equals("close"))
			return SEHK_0001_HK.getHistory().get(sdf.parse(strDate)).getClose();
		if (strInformation.equals("high"))
			return SEHK_0001_HK.getHistory().get(sdf.parse(strDate)).getHigh();
		if (strInformation.equals("low"))
			return SEHK_0001_HK.getHistory().get(sdf.parse(strDate)).getLow();
		if (strInformation.equals("adjclose"))
			return SEHK_0001_HK.getHistory().get(sdf.parse(strDate)).getAdjClose();
		else if (strInformation.equals("volume"))
			return SEHK_0001_HK.getHistory().get(sdf.parse(strDate)).getVolume();
		else
			return 0.0;
	}

}
