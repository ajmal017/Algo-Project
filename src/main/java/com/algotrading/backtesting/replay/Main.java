package com.algotrading.backtesting.replay;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import com.algotrading.backtesting.stock.PortfolioHistory;
import com.algotrading.backtesting.strategy.Strategies;
import com.algotrading.backtesting.util.Constants;

public class Main {

	public static void main(String[] args) throws IOException, ParseException {

		System.out.println("Constants.SRC_MAIN_RESOURCE_FILEPATH: " + Constants.SRC_MAIN_RESOURCE_FILEPATH);
		Date startDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2001-01-02");
		Date endDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2009-12-27");
		// Date endDate = Constants.DATE_FORMAT_YYYYMMDD.parse("2000-01-10");
		int initialCash = 300000;
		PortfolioHistory history = new PortfolioHistory();
		Strategies strategies = new Strategies(Constants.SRC_MAIN_RESOURCE_FILEPATH + "buyStrategies1.txt",
				Constants.SRC_MAIN_RESOURCE_FILEPATH + "sellStrategies1.txt",
				Constants.SRC_MAIN_RESOURCE_FILEPATH + "exitStrategies.txt",
				Constants.SRC_MAIN_RESOURCE_FILEPATH + "reentryStrategies.txt");
		AvailableStocks availableStocks = new AvailableStocks(Constants.SRC_MAIN_RESOURCE_FILEPATH,
				"availableStocks1.txt");
		TradingDate tradingDate = new TradingDate(Constants.SRC_MAIN_RESOURCE_FILEPATH + "tradingDate.txt");
		Replay replay = new Replay(startDate, endDate, history, strategies, availableStocks, tradingDate, initialCash);
		replay.simulate();
		PortfolioHistory portfolioHistory = replay.getPortfolioHistory();
		Date lastTradingDate = tradingDate.rollBackCurrentDate(endDate, endDate);
		double years = ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24) / 365);
		double profitRate = portfolioHistory.portfolioReturn(lastTradingDate);
		// int days = Duration.between(startDate, endDate).toDays();
		// Date firstTradingDate = tradingDate.rollToCurrentDate(startDate,
		// startDate);
		System.out.println(portfolioHistory);
		System.out.println("Net Profit: " + portfolioHistory.getNetProfit(lastTradingDate) + "\n");
		System.out.println("Start: " + startDate.toString() + "; End: " + endDate.toString());
		System.out.println("Duration: " + years + " years");
		System.out.println("Profit Rate: " + profitRate);
		System.out.println("Annual Increment: " + (Math.pow(1 + profitRate, 1 / years) - 1));
		System.out.println("Total Traded Volume: " + replay.getTotalTradedVolume());
		System.out.println("Total Transaction Cost: " + replay.getTotalTrasactionCost());
	}

}
