package com.algotrading.backtesting.replay;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.algotrading.backtesting.common.AlgoTradingConstants;
import com.algotrading.backtesting.stock.PortfolioHistory;
import com.algotrading.backtesting.strategy.Strategies;

public class Main {

	public static void main(String[] args) throws IOException, ParseException {
		Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2016-04-01");
		Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2016-04-11");
		PortfolioHistory history = new PortfolioHistory();
		Strategies strategies = new Strategies(AlgoTradingConstants.SRC_FILEPATH + "buyStrategies.txt",
				AlgoTradingConstants.SRC_FILEPATH + "sellStrategies.txt");
		AvailableStocks availableStocks = new AvailableStocks(
				AlgoTradingConstants.SRC_FILEPATH + "availableStocks.txt");
		TradingDate tradingDate = new TradingDate(AlgoTradingConstants.SRC_FILEPATH + "tradingDate.txt");

		Replay replay = new Replay(startDate, endDate, history, strategies, availableStocks, tradingDate);

		replay.simulate();
		PortfolioHistory portfolioHistory = replay.getPortfolioHistory();
		System.out.println(portfolioHistory);
	}

}
