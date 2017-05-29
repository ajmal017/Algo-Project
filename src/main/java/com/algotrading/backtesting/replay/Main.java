package com.algotrading.backtesting.replay;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import com.algotrading.backtesting.stock.PortfolioHistory;
import com.algotrading.backtesting.strategy.Strategies;
import com.algotrading.backtesting.util.Constants;

public class Main {

	public static void main(String[] args) throws IOException, ParseException {
		Date startDate = Constants.DATE_FORMAT_YYYYMMDD.parse("20160401");
		Date endDate = Constants.DATE_FORMAT_YYYYMMDD.parse("20160411");
		PortfolioHistory history = new PortfolioHistory();
		Strategies strategies = new Strategies(Constants.SRC_FILEPATH + "buyStrategies.txt",
				Constants.SRC_FILEPATH + "sellStrategies.txt");
		AvailableStocks availableStocks = new AvailableStocks(Constants.SRC_FILEPATH + "availableStocks.txt");
		TradingDate tradingDate = new TradingDate(Constants.SRC_FILEPATH + "tradingDate.txt");

		Replay replay = new Replay(startDate, endDate, history, strategies, availableStocks, tradingDate, 300000);

		replay.simulate();
		PortfolioHistory portfolioHistory = replay.getPortfolioHistory();
		System.out.println(portfolioHistory);
	}

}
