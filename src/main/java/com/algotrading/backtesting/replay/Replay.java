package com.algotrading.backtesting.replay;

import java.util.Date;

import com.algotrading.backtesting.portfolio.Portfolio;
import com.algotrading.backtesting.portfolio.PortfolioComponent;
import com.algotrading.backtesting.stock.PortfolioHistory;
import com.algotrading.backtesting.stock.Stock;
import com.algotrading.backtesting.strategy.Strategies;

public class Replay {
	private Date startDate;
	private Date endDate;
	private PortfolioHistory portfolioHistory;
	private Strategies strategies;
	private AvailableStocks availableStocks;
	private TradingDate tradingDate;

	public Replay(Date startDate, Date endDate, PortfolioHistory portfolioHistory, Strategies strategies,
			AvailableStocks availableStocks, TradingDate tradingDate) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.portfolioHistory = portfolioHistory;
		this.strategies = strategies;
		this.availableStocks = availableStocks;
		this.tradingDate = tradingDate;
	}

	public void simulate() {
		Date currentDate = startDate;
		while (!tradingDate.isLastDate() && tradingDate.currentDate()
				.compareTo(endDate) < 0) {
			currentDate = tradingDate.currentDate(); // find tradingDate in
														// currentDate
			Portfolio portfolio = new Portfolio(currentDate);
			for (Stock stock : availableStocks.get()) { // bring yesterday
														// portfolio to today
				PortfolioComponent component = strategies.buySellAmount(stock, currentDate);
				if (component.getQuantity() != 0) {
					portfolio.add(component);
				}
			}
			portfolioHistory.put(currentDate, portfolio);
			tradingDate.rollDay();
		}
	}

	public PortfolioHistory getPortfolioHistory() {
		return portfolioHistory;
	}
}
