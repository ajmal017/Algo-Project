package com.algotrading.backtesting.replay.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

import com.algotrading.backtesting.portfolio.Portfolio;
import com.algotrading.backtesting.portfolio.PortfolioComponent;
import com.algotrading.backtesting.replay.AvailableStocks;
import com.algotrading.backtesting.replay.LotSize;
import com.algotrading.backtesting.replay.Replay;
import com.algotrading.backtesting.replay.TradingDate;
import com.algotrading.backtesting.stock.PortfolioHistory;
import com.algotrading.backtesting.stock.Stock;
import com.algotrading.backtesting.strategy.Strategies;
import com.algotrading.backtesting.util.Constants;

public class MainTest {

	protected static String RESOURCE_PATH_NAME = Constants.SRC_TEST_RESOURCE_FILEPATH
			+ MainTest.class.getPackage().getName().replace('.', '/') + "/";
/*
	@Test
	public void test001_buyConditionsMet() throws ParseException, IOException {
		Date startDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-28");
		Date endDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-10-19");
		PortfolioHistory history = new PortfolioHistory();
		Strategies strategies = new Strategies(RESOURCE_PATH_NAME + "buyStrategies1.txt",
				RESOURCE_PATH_NAME + "sellStrategies1.txt");
		AvailableStocks availableStocks = new AvailableStocks(RESOURCE_PATH_NAME, "availableStocks1.txt");
		TradingDate tradingDate = new TradingDate(RESOURCE_PATH_NAME + "tradingDate.txt");
		LotSize lotSize = new LotSize(RESOURCE_PATH_NAME + "lotSize.csv");
		Replay replay = new Replay(startDate, endDate, history, strategies, availableStocks, tradingDate, 300000);
		replay.simulate();
		PortfolioHistory portfolioHistory = replay.getPortfolioHistory();
		Date triggeredDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-29");
		Date dayBeforeTriggeredDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-28");
		Portfolio portfolio20160929 = portfolioHistory.get(triggeredDate);
		Portfolio portfolio20160928 = portfolioHistory.get(dayBeforeTriggeredDate);
		Stock stockTC0001 = new Stock("SEHK_TC0001", lotSize.getLotSize("SEHK_TC0001"));
		stockTC0001.read(RESOURCE_PATH_NAME);
		assertEquals(false, portfolio20160928.containsStock(stockTC0001));
		assertTrue(portfolio20160929.containsStock(stockTC0001));
		assertEquals(3000, portfolio20160929.getPortfolioComponent("SEHK_TC0001").getQuantity());
		System.out.println(portfolio20160929.getPortfolioComponent("SEHK_TC0001").getQuantity());
		System.out.println("test001_buyConditionsMet");
		// System.out.println(portfolioHistory);
		System.out.println("Total Traded Volume: " + replay.getTotalTradedVolume());
		System.out.println("Total Transaction Cost: " + replay.getTotalTrasactionCost());
	}

	@Test
	public void test002_buyCondition2Failed() throws ParseException, IOException {

		Date startDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-14");
		Date endDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-30");
		PortfolioHistory history = new PortfolioHistory();
		Strategies strategies = new Strategies(RESOURCE_PATH_NAME + "buyStrategies1.txt",
				RESOURCE_PATH_NAME + "sellStrategies1.txt");
		AvailableStocks availableStocks = new AvailableStocks(RESOURCE_PATH_NAME, "availableStocks2.txt");
		TradingDate tradingDate = new TradingDate(RESOURCE_PATH_NAME + "tradingDate.txt");
		LotSize lotSize = new LotSize(RESOURCE_PATH_NAME + "lotSize.csv");
		Replay replay = new Replay(startDate, endDate, history, strategies, availableStocks, tradingDate, 300000);
		replay.simulate();
		PortfolioHistory portfolioHistory = replay.getPortfolioHistory();
		Portfolio portfolio20160930 = portfolioHistory.get(endDate);
		Stock stockTC0002 = new Stock("SEHK_TC0002", lotSize.getLotSize("SEHK_TC0002"));
		stockTC0002.read(RESOURCE_PATH_NAME);
		assertEquals(false, portfolio20160930.containsStock(stockTC0002));
		System.out.println("test002_buyCondition1Failed"); //
		// System.out.println(portfolioHistory);
		System.out.println("Total Traded Volume: " + replay.getTotalTradedVolume());
		System.out.println("Total Transaction Cost: " + replay.getTotalTrasactionCost());
	}

	@Test
	public void test003_buyCondition1and2Failed() throws ParseException, IOException {
		Date startDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-14");
		Date endDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-30");
		PortfolioHistory history = new PortfolioHistory();
		Strategies strategies = new Strategies(RESOURCE_PATH_NAME + "buyStrategies1.txt",
				RESOURCE_PATH_NAME + "sellStrategies1.txt");
		AvailableStocks availableStocks = new AvailableStocks(RESOURCE_PATH_NAME, "availableStocks3.txt");
		TradingDate tradingDate = new TradingDate(RESOURCE_PATH_NAME + "tradingDate.txt");
		LotSize lotSize = new LotSize(RESOURCE_PATH_NAME + "lotSize.csv");
		Replay replay = new Replay(startDate, endDate, history, strategies, availableStocks, tradingDate, 300000);
		replay.simulate();
		PortfolioHistory portfolioHistory = replay.getPortfolioHistory();
		Portfolio portfolio20160930 = portfolioHistory.get(endDate);
		Stock stockTC0003 = new Stock("SEHK_TC0003", lotSize.getLotSize("SEHK_TC0003"));
		stockTC0003.read(RESOURCE_PATH_NAME);
		assertEquals(false, portfolio20160930.containsStock(stockTC0003));
		System.out.println("test003_buyCondition1and2Failed");
		// System.out.println(portfolioHistory);
		System.out.println("Total Traded Volume: " + replay.getTotalTradedVolume());
		System.out.println("Total Transaction Cost: " + replay.getTotalTrasactionCost());
	}

	@Test
	public void test004_buyCondition1Failed() throws ParseException, IOException {
		Date startDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-14");
		Date endDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-30");
		PortfolioHistory history = new PortfolioHistory();
		Strategies strategies = new Strategies(RESOURCE_PATH_NAME + "buyStrategies1.txt",
				RESOURCE_PATH_NAME + "sellStrategies1.txt");
		AvailableStocks availableStocks = new AvailableStocks(RESOURCE_PATH_NAME, "availableStocks4.txt");
		TradingDate tradingDate = new TradingDate(RESOURCE_PATH_NAME + "tradingDate.txt");
		LotSize lotSize = new LotSize(RESOURCE_PATH_NAME + "lotSize.csv");
		Replay replay = new Replay(startDate, endDate, history, strategies, availableStocks, tradingDate, 300000);
		replay.simulate();
		PortfolioHistory portfolioHistory = replay.getPortfolioHistory();
		Portfolio portfolio20160930 = portfolioHistory.get(endDate);
		Stock stockTC0004 = new Stock("SEHK_TC0004", lotSize.getLotSize("SEHK_TC0004"));
		stockTC0004.read(RESOURCE_PATH_NAME);
		assertEquals(false, portfolio20160930.containsStock(stockTC0004));
		System.out.println("test004_buyCondition1Failed"); //
		// System.out.println(portfolioHistory);
		System.out.println("Total Traded Volume: " + replay.getTotalTradedVolume());
		System.out.println("Total Transaction Cost: " + replay.getTotalTrasactionCost());
	}

	@Test
	public void test005_buyCondition3Failed() throws ParseException, IOException {
		Date startDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-14");
		Date endDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-09-30");
		PortfolioHistory history = new PortfolioHistory();
		Strategies strategies = new Strategies(RESOURCE_PATH_NAME + "buyStrategies1.txt",
				RESOURCE_PATH_NAME + "sellStrategies1.txt");
		AvailableStocks availableStocks = new AvailableStocks(RESOURCE_PATH_NAME, "availableStocks5.txt");
		TradingDate tradingDate = new TradingDate(RESOURCE_PATH_NAME + "tradingDate.txt");
		LotSize lotSize = new LotSize(RESOURCE_PATH_NAME + "lotSize.csv");
		Replay replay = new Replay(startDate, endDate, history, strategies, availableStocks, tradingDate, 300000);
		replay.simulate();
		PortfolioHistory portfolioHistory = replay.getPortfolioHistory();
		Portfolio portfolio20160930 = portfolioHistory.get(endDate);
		Stock stockTC0005 = new Stock("SEHK_TC0005", lotSize.getLotSize("SEHK_TC0005"));
		stockTC0005.read(RESOURCE_PATH_NAME);
		assertEquals(false, portfolio20160930.containsStock(stockTC0005));
		System.out.println("test005_buyCondition3Failed");
		// System.out.println(portfolioHistory);
		System.out.println("Total Traded Volume: " + replay.getTotalTradedVolume());
		System.out.println("Total Transaction Cost: " + replay.getTotalTrasactionCost());
	}

	@Test
	public void test006_takeProfit() throws ParseException, IOException {
		LotSize lotSize = new LotSize(RESOURCE_PATH_NAME + "lotSize.csv");
		Date startDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-10-03");
		Date endDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-10-19");
		PortfolioHistory history = new PortfolioHistory();
		Portfolio portfolio = new Portfolio(startDate, 0);
		Stock stockTC0006 = new Stock("SEHK_TC0006", lotSize.getLotSize("SEHK_TC0006"));
		stockTC0006.read(RESOURCE_PATH_NAME);
		PortfolioComponent TC0006pc = new PortfolioComponent(stockTC0006, 3333, 3, startDate);
		portfolio.put(TC0006pc);
		history.put(startDate, portfolio);
		Strategies strategies = new Strategies(RESOURCE_PATH_NAME + "buyStrategies1.txt",
				RESOURCE_PATH_NAME + "sellStrategies1.txt");
		AvailableStocks availableStocks = new AvailableStocks(RESOURCE_PATH_NAME, "availableStocks6.txt");
		TradingDate tradingDate = new TradingDate(RESOURCE_PATH_NAME + "tradingDate.txt");
		Replay replay = new Replay(startDate, endDate, history, strategies, availableStocks, tradingDate, 0);
		replay.simulate();
		PortfolioHistory portfolioHistory = replay.getPortfolioHistory();
		Portfolio portfolio20161013 = portfolioHistory.get(Constants.DATE_FORMAT_YYYYMMDD.parse("2016-10-13"));
		Portfolio portfolio20161014 = portfolioHistory.get(Constants.DATE_FORMAT_YYYYMMDD.parse("2016-10-14"));
		Portfolio portfolio20161019 = portfolioHistory.get(endDate);
		assertTrue(portfolio20161013.containsStock(stockTC0006));
		assertEquals(false, portfolio20161014.containsStock(stockTC0006));
		assertEquals(false, portfolio20161019.containsStock(stockTC0006));
		assertEquals(11332.2, portfolio20161019.getCash(), 0.0001);
		System.out.println("test006_takeProfit");
		// System.out.println(portfolioHistory);
		System.out.println("Total Traded Volume: " + replay.getTotalTradedVolume());
		System.out.println("Total Transaction Cost: " + replay.getTotalTrasactionCost());
	}

	@Test
	public void test007_stopLoss() throws ParseException, IOException {
		LotSize lotSize = new LotSize(RESOURCE_PATH_NAME + "lotSize.csv");
		Date startDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-10-03");
		Date endDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-10-19");
		PortfolioHistory history = new PortfolioHistory();
		Portfolio portfolio = new Portfolio(startDate, 0);
		Stock stockTC0007 = new Stock("SEHK_TC0007", lotSize.getLotSize("SEHK_TC0007"));
		stockTC0007.read(RESOURCE_PATH_NAME);
		PortfolioComponent TC0007pc = new PortfolioComponent(stockTC0007, 3333, 3, startDate);
		portfolio.put(TC0007pc);
		history.put(startDate, portfolio);
		Strategies strategies = new Strategies(RESOURCE_PATH_NAME + "buyStrategies1.txt",
				RESOURCE_PATH_NAME + "sellStrategies1.txt");
		AvailableStocks availableStocks = new AvailableStocks(RESOURCE_PATH_NAME, "availableStocks7.txt");
		TradingDate tradingDate = new TradingDate(RESOURCE_PATH_NAME + "tradingDate.txt");
		Replay replay = new Replay(startDate, endDate, history, strategies, availableStocks, tradingDate, 0);
		replay.simulate();
		PortfolioHistory portfolioHistory = replay.getPortfolioHistory();
		Portfolio portfolio20161013 = portfolioHistory.get(Constants.DATE_FORMAT_YYYYMMDD.parse("2016-10-13"));
		Portfolio portfolio20161014 = portfolioHistory.get(Constants.DATE_FORMAT_YYYYMMDD.parse("2016-10-14"));
		Portfolio portfolio20161019 = portfolioHistory.get(endDate);
		assertTrue(portfolio20161013.containsStock(stockTC0007));
		assertEquals(false, portfolio20161014.containsStock(stockTC0007));
		assertEquals(false, portfolio20161019.containsStock(stockTC0007));
		assertEquals(8999.1, portfolio20161019.getCash(), 0.0001);
		System.out.println("test007_stopLoss");
		// System.out.println(portfolioHistory);
		System.out.println("Total Traded Volume: " + replay.getTotalTradedVolume());
		System.out.println("Total Transaction Cost: " + replay.getTotalTrasactionCost());
	}

	@Test
	public void test008_sellConditionsNotMet() throws ParseException, IOException {
		LotSize lotSize = new LotSize(RESOURCE_PATH_NAME + "lotSize.csv");
		Date startDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-10-03");
		Date endDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-10-19");
		PortfolioHistory history = new PortfolioHistory();
		Portfolio portfolio = new Portfolio(startDate, 0);
		Stock stockTC0008 = new Stock("SEHK_TC0008", lotSize.getLotSize("SEHK_TC0008"));
		stockTC0008.read(RESOURCE_PATH_NAME);
		PortfolioComponent TC0008pc = new PortfolioComponent(stockTC0008, 3333, 3, startDate);
		portfolio.put(TC0008pc);

		history.put(startDate, portfolio);
		Strategies strategies = new Strategies(RESOURCE_PATH_NAME + "buyStrategies1.txt",
				RESOURCE_PATH_NAME + "sellStrategies1.txt");
		AvailableStocks availableStocks = new AvailableStocks(RESOURCE_PATH_NAME, "availableStocks8.txt");
		TradingDate tradingDate = new TradingDate(RESOURCE_PATH_NAME + "tradingDate.txt");
		Replay replay = new Replay(startDate, endDate, history, strategies, availableStocks, tradingDate, 0);
		replay.simulate();
		PortfolioHistory portfolioHistory = replay.getPortfolioHistory();
		Portfolio portfolio20161019 = portfolioHistory.get(endDate);

		assertEquals(true, portfolio20161019.containsStock(stockTC0008));
		assertEquals(3333, portfolio20161019.getPortfolioComponent("SEHK_TC0008").getQuantity());
		System.out.println("test008_sellConditionsNotMet");
		// System.out.println(portfolioHistory);
		System.out.println("Total Traded Volume: " + replay.getTotalTradedVolume());
		System.out.println("Total Transaction Cost: " + replay.getTotalTrasactionCost());
	}

	@Test
	public void test009_buyConditionsMetButStockDisabled() throws ParseException, IOException {
		LotSize lotSize = new LotSize(RESOURCE_PATH_NAME + "lotSize.csv");
		Date startDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-10-03");
		Date endDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-10-19");
		PortfolioHistory history = new PortfolioHistory();
		Portfolio portfolio = new Portfolio(startDate, 0);
		Stock stockTC0009 = new Stock("SEHK_TC0009", lotSize.getLotSize("SEHK_TC0009"));
		stockTC0009.read(RESOURCE_PATH_NAME);
		PortfolioComponent TC0009pc = new PortfolioComponent(stockTC0009, 2000, 3, startDate);
		portfolio.put(TC0009pc);
		history.put(startDate, portfolio);
		Strategies strategies = new Strategies(RESOURCE_PATH_NAME + "buyStrategies9.txt",
				RESOURCE_PATH_NAME + "sellStrategies1.txt");
		AvailableStocks availableStocks = new AvailableStocks(RESOURCE_PATH_NAME, "availableStocks9.txt");
		TradingDate tradingDate = new TradingDate(RESOURCE_PATH_NAME + "tradingDate.txt");
		Replay replay = new Replay(startDate, endDate, history, strategies, availableStocks, tradingDate, 0);
		replay.simulate();
		PortfolioHistory portfolioHistory = replay.getPortfolioHistory();
		Portfolio portfolio20161013 = portfolioHistory.get(Constants.DATE_FORMAT_YYYYMMDD.parse("2016-10-13"));
		Portfolio portfolio20161014 = portfolioHistory.get(Constants.DATE_FORMAT_YYYYMMDD.parse("2016-10-14"));
		Portfolio portfolio20161017 = portfolioHistory.get(Constants.DATE_FORMAT_YYYYMMDD.parse("2016-10-17"));
		Portfolio portfolio20161018 = portfolioHistory.get(Constants.DATE_FORMAT_YYYYMMDD.parse("2016-10-18"));
		Portfolio portfolio20161019 = portfolioHistory.get(Constants.DATE_FORMAT_YYYYMMDD.parse("2016-10-19"));
		assertTrue(portfolio20161013.containsStock(stockTC0009));
		assertEquals(false, portfolio20161014.containsStock(stockTC0009));
		// On 20161018 Buy Conditions hit but stock is disabled hence no action
		assertEquals(false, portfolio20161017.containsStock(stockTC0009));
		assertEquals(false, portfolio20161018.containsStock(stockTC0009));
		assertEquals(false, portfolio20161019.containsStock(stockTC0009));
		assertEquals(5400, portfolio20161019.getCash(), 0.0001);
		System.out.println("test009_buyConditionsMetButStockDisabled");
		// System.out.println(portfolioHistory);
		System.out.println("Total Traded Volume: " + replay.getTotalTradedVolume());
		System.out.println("Total Transaction Cost: " + replay.getTotalTrasactionCost());
	}
*/
	@Test
	public void test010_stockEnabledBuyConditionsMet() throws ParseException, IOException {
		LotSize lotSize = new LotSize(RESOURCE_PATH_NAME + "lotSize.csv");
		Date startDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-10-03");
		Date endDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2016-10-21");
		PortfolioHistory history = new PortfolioHistory();
		Portfolio portfolio = new Portfolio(startDate, 0);
		Stock stockTC0010 = new Stock("SEHK_TC0010", lotSize.getLotSize("SEHK_TC0010"));
		stockTC0010.read(RESOURCE_PATH_NAME);
		PortfolioComponent TC0010pc = new PortfolioComponent(stockTC0010, 2000, 3, startDate);
		portfolio.put(TC0010pc);
		history.put(startDate, portfolio);
		Strategies strategies = new Strategies(RESOURCE_PATH_NAME + "buyStrategies10.txt",
				RESOURCE_PATH_NAME + "sellStrategies10.txt");
		AvailableStocks availableStocks = new AvailableStocks(RESOURCE_PATH_NAME, "availableStocks10.txt");
		TradingDate tradingDate = new TradingDate(RESOURCE_PATH_NAME + "tradingDate10.txt");
		Replay replay = new Replay(startDate, endDate, history, strategies, availableStocks, tradingDate, 0);
		replay.simulate();
		PortfolioHistory portfolioHistory = replay.getPortfolioHistory();
		Portfolio portfolio20161013 = portfolioHistory.get(Constants.DATE_FORMAT_YYYYMMDD.parse("2016-10-13"));
		Portfolio portfolio20161014 = portfolioHistory.get(Constants.DATE_FORMAT_YYYYMMDD.parse("2016-10-14"));
		Portfolio portfolio20161019 = portfolioHistory.get(Constants.DATE_FORMAT_YYYYMMDD.parse("2016-10-19"));
		Portfolio portfolio20161020 = portfolioHistory.get(Constants.DATE_FORMAT_YYYYMMDD.parse("2016-10-20"));
		Portfolio portfolio20161021 = portfolioHistory.get(Constants.DATE_FORMAT_YYYYMMDD.parse("2016-10-21"));

		assertTrue(portfolio20161013.containsStock(stockTC0010));
		// exit strategy triggered on 20161014
		assertEquals(false, portfolio20161014.containsStock(stockTC0010));
		assertEquals(false, portfolio20161019.containsStock(stockTC0010));

		// stock enabled again but no action yet on 20161020
		assertEquals(false, portfolio20161020.containsStock(stockTC0010));
		assertTrue(portfolio20161021.containsStock(stockTC0010)); // Buy stock

		assertEquals(4400, portfolio20161021.getCash(), 0.0001);
		System.out.println("test010_stockEnabledBuyConditionsMet");
		// System.out.println(portfolioHistory);
		System.out.println("Total Traded Volume: " + replay.getTotalTradedVolume());
		System.out.println("Total Transaction Cost: " + replay.getTotalTrasactionCost());
	}

}
