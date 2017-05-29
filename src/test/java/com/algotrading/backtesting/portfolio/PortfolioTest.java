package com.algotrading.backtesting.portfolio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.algotrading.backtesting.stock.Stock;
import com.algotrading.backtesting.stock.StockHistory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PortfolioTest {

	private Date date;
	private Stock stock1382;
	private Stock stock0281;

	public PortfolioTest() throws ParseException {
		date = new SimpleDateFormat("yyyyMMdd").parse("20170318");

		StockHistory stockHistory1382 = new StockHistory(date, 5, 5, 5, 5, 5, 1000);
		Map<Date, StockHistory> stockMap1382 = new HashMap<>();
		stockMap1382.put(date, stockHistory1382);
		stock1382 = new Stock("1382", stockMap1382);

		StockHistory stockHistory0281 = new StockHistory(date, 10, 10, 10, 10, 10, 1000);
		Map<Date, StockHistory> stockMap0281 = new HashMap<>();
		stockMap0281.put(date, stockHistory0281);
		stock0281 = new Stock("0281", stockMap0281);
	}

	@Test
	public void test1_addPortfolio() {

		Portfolio portfolio = new Portfolio(date, 10000);

		PortfolioComponent pc1382_1 = new PortfolioComponent(stock1382, 1000, 10);
		PortfolioComponent pc1382_2 = new PortfolioComponent(stock1382, 1000, 20);

		portfolio.add(pc1382_1);
		portfolio.add(pc1382_2);

		assertEquals(10000, portfolio.marketValue(), 0.01);
		assertEquals(30000, portfolio.cost(), 0.01);

		assertTrue(portfolio.containsStock(stock1382));
	}

	@Test
	public void test2_addPortfolio() {

		Portfolio portfolio = new Portfolio(date, 10000);

		PortfolioComponent pc1382_1 = new PortfolioComponent(stock1382, 1000, 10);
		PortfolioComponent pc1382_2 = new PortfolioComponent(stock1382, 1000, 20);

		PortfolioComponent pc0281_1 = new PortfolioComponent(stock0281, 500, 5);
		PortfolioComponent pc0281_2 = new PortfolioComponent(stock0281, 500, 10);

		portfolio.add(pc1382_1);
		portfolio.add(pc1382_2);
		portfolio.add(pc0281_1);
		portfolio.add(pc0281_2);

		assertEquals(20000, portfolio.marketValue(), 0.01);
		assertEquals(37500, portfolio.cost(), 0.01);

		assertTrue(portfolio.containsStock(stock1382));
		assertTrue(portfolio.containsStock(stock0281));

	}
}
