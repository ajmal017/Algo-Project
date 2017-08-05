package com.algotrading.backtesting.replay.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

import com.algotrading.backtesting.portfolio.Portfolio;
import com.algotrading.backtesting.portfolio.PortfolioComponent;
import com.algotrading.backtesting.replay.AvailableStocks;
import com.algotrading.backtesting.replay.Replay;
import com.algotrading.backtesting.replay.TradingDate;
import com.algotrading.backtesting.stock.PortfolioHistory;
import com.algotrading.backtesting.stock.Stock;
import com.algotrading.backtesting.strategy.Strategies;
import com.algotrading.backtesting.util.Constants;

public class MainTest {

	@Test
	public void test001_buyConditionsMet() throws ParseException, IOException {
		Date startDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-14");
		Date endDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-30");
		PortfolioHistory history = new PortfolioHistory();
		Strategies strategies = new Strategies(Constants.SRC_TEST_RESOURCE_FILEPATH + "buyStrategies.txt1",
				Constants.SRC_TEST_RESOURCE_FILEPATH + "sellStrategies1.txt");
		AvailableStocks availableStocks = new AvailableStocks(Constants.SRC_TEST_RESOURCE_FILEPATH, "availableStocks1.txt");
		TradingDate tradingDate = new TradingDate(Constants.SRC_TEST_RESOURCE_FILEPATH + "tradingDate.txt");
		Replay replay = new Replay(startDate, endDate, history, strategies, availableStocks, tradingDate, 300000);
		replay.simulate();
		PortfolioHistory portfolioHistory = replay.getPortfolioHistory();
		Date triggeredDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-29");
		Date dayBeforeTriggeredDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-28");
		Portfolio portfolio20160929 = portfolioHistory.get(triggeredDate);
		Portfolio portfolio20160928 = portfolioHistory.get(dayBeforeTriggeredDate);
		Stock stockTC0001 = new Stock("SEHK_TC0001");
		
		assertTrue(portfolio20160929.containsStock(stockTC0001));
		assertEquals(false, portfolio20160928.containsStock(stockTC0001));
		assertEquals(3333, portfolio20160928.getPortfolioComponent("SEHK_TC0001").getQuantity());
		System.out.println("test001_buyConditionsMet");
		System.out.println(portfolioHistory);
	}

	@Test
	public void test002_buyCondition2Failed() throws ParseException, IOException {
		Date startDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-14");
		Date endDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-30");
		PortfolioHistory history = new PortfolioHistory();
		Strategies strategies = new Strategies(Constants.SRC_TEST_RESOURCE_FILEPATH + "buyStrategies.txt1",
				Constants.SRC_TEST_RESOURCE_FILEPATH + "sellStrategies1.txt");
		AvailableStocks availableStocks = new AvailableStocks(Constants.SRC_TEST_RESOURCE_FILEPATH, "availableStocks1.txt");
		TradingDate tradingDate = new TradingDate(Constants.SRC_TEST_RESOURCE_FILEPATH + "tradingDate.txt");
		Replay replay = new Replay(startDate, endDate, history, strategies, availableStocks, tradingDate, 300000);
		replay.simulate();
		PortfolioHistory portfolioHistory = replay.getPortfolioHistory();
		Portfolio portfolio20160930 = portfolioHistory.get(endDate);
		Stock stockTC0002 = new Stock("SEHK_TC0002");

		assertEquals(false, portfolio20160930.containsStock(stockTC0002));
		System.out.println("test002_buyCondition1Failed");
		System.out.println(portfolioHistory);
	}

	@Test
	public void test003_buyCondition1and2Failed() throws ParseException, IOException {
		Date startDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-14");
		Date endDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-30");
		PortfolioHistory history = new PortfolioHistory();
		Strategies strategies = new Strategies(Constants.SRC_TEST_RESOURCE_FILEPATH + "buyStrategies.txt1",
				Constants.SRC_TEST_RESOURCE_FILEPATH + "sellStrategies1.txt");
		AvailableStocks availableStocks = new AvailableStocks(Constants.SRC_TEST_RESOURCE_FILEPATH, "availableStocks1.txt");
		TradingDate tradingDate = new TradingDate(Constants.SRC_TEST_RESOURCE_FILEPATH + "tradingDate.txt");
		Replay replay = new Replay(startDate, endDate, history, strategies, availableStocks, tradingDate, 300000);
		replay.simulate();
		PortfolioHistory portfolioHistory = replay.getPortfolioHistory();
		Portfolio portfolio20160930 = portfolioHistory.get(endDate);
		Stock stockTC0003 = new Stock("SEHK_TC0003");

		assertEquals(false, portfolio20160930.containsStock(stockTC0003));
		System.out.println("test003_buyCondition1and2Failed");
		System.out.println(portfolioHistory);
	}

	@Test
	public void test004_buyCondition1Failed() throws ParseException, IOException {
		Date startDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-14");
		Date endDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-30");
		PortfolioHistory history = new PortfolioHistory();
		Strategies strategies = new Strategies(Constants.SRC_TEST_RESOURCE_FILEPATH + "buyStrategies.txt1",
				Constants.SRC_TEST_RESOURCE_FILEPATH + "sellStrategies1.txt");
		AvailableStocks availableStocks = new AvailableStocks(Constants.SRC_TEST_RESOURCE_FILEPATH, "availableStocks1.txt");
		TradingDate tradingDate = new TradingDate(Constants.SRC_TEST_RESOURCE_FILEPATH + "tradingDate.txt");
		Replay replay = new Replay(startDate, endDate, history, strategies, availableStocks, tradingDate, 300000);
		replay.simulate();
		PortfolioHistory portfolioHistory = replay.getPortfolioHistory();
		Portfolio portfolio20160930 = portfolioHistory.get(endDate);
		Stock stockTC0004 = new Stock("SEHK_TC0004");

		assertEquals(false, portfolio20160930.containsStock(stockTC0004));
		System.out.println("test004_buyCondition1Failed");
		System.out.println(portfolioHistory);
	}

	@Test
	public void test005_buyCondition3Failed() throws ParseException, IOException {
		Date startDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-14");
		Date endDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-30");
		PortfolioHistory history = new PortfolioHistory();
		Strategies strategies = new Strategies(Constants.SRC_TEST_RESOURCE_FILEPATH + "buyStrategies.txt1",
				Constants.SRC_TEST_RESOURCE_FILEPATH + "sellStrategies1.txt");
		AvailableStocks availableStocks = new AvailableStocks(Constants.SRC_TEST_RESOURCE_FILEPATH, "availableStocks1.txt");
		TradingDate tradingDate = new TradingDate(Constants.SRC_TEST_RESOURCE_FILEPATH + "tradingDate.txt");
		Replay replay = new Replay(startDate, endDate, history, strategies, availableStocks, tradingDate, 300000);
		replay.simulate();
		PortfolioHistory portfolioHistory = replay.getPortfolioHistory();
		Portfolio portfolio20160930 = portfolioHistory.get(endDate);
		Stock stockTC0005 = new Stock("SEHK_TC0005");

		assertEquals(false, portfolio20160930.containsStock(stockTC0005));
		System.out.println("test005_buyCondition3Failed");
		System.out.println(portfolioHistory);
	}
	
	@Test
	public void test006_takeProfit() throws ParseException, IOException {
		Date startDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-10-03");
		Date endDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-10-19");
		PortfolioHistory history = new PortfolioHistory();
		Portfolio portfolio = new Portfolio(startDate, 0);
		Stock stockTC0006 = new Stock("SEHK_TC0006");
		PortfolioComponent TC0006pc = new PortfolioComponent(stockTC0006, 3333, 3);
		portfolio.put(TC0006pc);
		
		history.put(Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-13"), portfolio);
		Strategies strategies = new Strategies(Constants.SRC_TEST_RESOURCE_FILEPATH + "buyStrategies.txt1",
				Constants.SRC_TEST_RESOURCE_FILEPATH + "sellStrategies1.txt");
		AvailableStocks availableStocks = new AvailableStocks(Constants.SRC_TEST_RESOURCE_FILEPATH, "availableStocks1.txt");
		TradingDate tradingDate = new TradingDate(Constants.SRC_TEST_RESOURCE_FILEPATH + "tradingDate.txt");
		Replay replay = new Replay(startDate, endDate, history, strategies, availableStocks, tradingDate, 200000);
		replay.simulate();
		PortfolioHistory portfolioHistory = replay.getPortfolioHistory();
		Portfolio portfolio20161013 = portfolioHistory.get(Constants.DATE_FORMAT_YYYYMMDD.parse("2016-10-13"));
		Portfolio portfolio20161019 = portfolioHistory.get(endDate);
	
		assertTrue(portfolio20161013.containsStock(stockTC0006));
		assertEquals(false, portfolio20161019.containsStock(stockTC0006));
		// assertEquals(0, portfolio20160930.getPortfolioComponent("SEHK_TC0006").getQuantity());
		assertEquals(11332.2, portfolio20161019.getCash(), 0.0001);
		System.out.println("test006_takeProfit");
		System.out.println(portfolioHistory);
	}	

	@Test
	public void test007_stopLoss() throws ParseException, IOException {
		Date startDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-10-03");
		Date endDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-10-19");
		PortfolioHistory history = new PortfolioHistory();
		Portfolio portfolio = new Portfolio(startDate, 0);
		Stock stockTC0007 = new Stock("SEHK_TC0007");
		PortfolioComponent TC0007pc = new PortfolioComponent(stockTC0007, 3333, 3);
		portfolio.put(TC0007pc);
		
		history.put(Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-13"), portfolio);
		Strategies strategies = new Strategies(Constants.SRC_TEST_RESOURCE_FILEPATH + "buyStrategies.txt1",
				Constants.SRC_TEST_RESOURCE_FILEPATH + "sellStrategies1.txt");
		AvailableStocks availableStocks = new AvailableStocks(Constants.SRC_TEST_RESOURCE_FILEPATH, "availableStocks1.txt");
		TradingDate tradingDate = new TradingDate(Constants.SRC_TEST_RESOURCE_FILEPATH + "tradingDate.txt");
		Replay replay = new Replay(startDate, endDate, history, strategies, availableStocks, tradingDate, 200000);
		replay.simulate();
		PortfolioHistory portfolioHistory = replay.getPortfolioHistory();
		Portfolio portfolio20161013 = portfolioHistory.get(Constants.DATE_FORMAT_YYYYMMDD.parse("2016-10-13"));
		Portfolio portfolio20161019 = portfolioHistory.get(endDate);
	
		assertTrue(portfolio20161013.containsStock(stockTC0007));
		assertEquals(false, portfolio20161019.containsStock(stockTC0007));
		// assertEquals(0, portfolio20160930.getPortfolioComponent("SEHK_TC0006").getQuantity());
		assertEquals(8999.1, portfolio20161019.getCash(), 0.0001);
		System.out.println("test007_stopLoss");
		System.out.println(portfolioHistory);
	}	

	@Test
	public void test008_sellConditionsNotMet() throws ParseException, IOException {
		Date startDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-10-03");
		Date endDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-10-19");
		PortfolioHistory history = new PortfolioHistory();
		Portfolio portfolio = new Portfolio(startDate, 0);
		Stock stockTC0008 = new Stock("SEHK_TC0008");
		PortfolioComponent TC0008pc = new PortfolioComponent(stockTC0008, 3333, 3);
		portfolio.put(TC0008pc);
		
		history.put(Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-13"), portfolio);
		Strategies strategies = new Strategies(Constants.SRC_TEST_RESOURCE_FILEPATH + "buyStrategies.txt1",
				Constants.SRC_TEST_RESOURCE_FILEPATH + "sellStrategies1.txt");
		AvailableStocks availableStocks = new AvailableStocks(Constants.SRC_TEST_RESOURCE_FILEPATH, "availableStocks1.txt");
		TradingDate tradingDate = new TradingDate(Constants.SRC_TEST_RESOURCE_FILEPATH + "tradingDate.txt");
		Replay replay = new Replay(startDate, endDate, history, strategies, availableStocks, tradingDate, 200000);
		replay.simulate();
		PortfolioHistory portfolioHistory = replay.getPortfolioHistory();
		Portfolio portfolio20161019 = portfolioHistory.get(endDate);
	
		assertEquals(true, portfolio20161019.containsStock(stockTC0008));
		assertEquals(3333, portfolio20161019.getPortfolioComponent("SEHK_TC0007").getQuantity());
		System.out.println("test008_sellConditionsNotMet");
		System.out.println(portfolioHistory);
	}
}
