package com.algotrading.backtesting.replay;

import java.text.ParseException;
import java.util.Date;

import com.algotrading.backtesting.portfolio.BuySellAmount;
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
	private double initialCash;

	public Replay(Date startDate, Date endDate, PortfolioHistory portfolioHistory, Strategies strategies,
			AvailableStocks availableStocks, TradingDate tradingDate, double initialCash) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.portfolioHistory = portfolioHistory;
		this.strategies = strategies;
		this.availableStocks = availableStocks;
		this.tradingDate = tradingDate;
		this.initialCash = initialCash;
	}

	public void simulate() throws ParseException {
		Date currentDate = startDate;
		tradingDate.setCurrentDate(currentDate);
		Portfolio portfolio = new Portfolio(currentDate, initialCash);
		while (tradingDate.isNotLastDate() && tradingDate.currentDate()
				.compareTo(endDate) <= 0) {
			currentDate = tradingDate.currentDate();
			portfolio.setDate(currentDate);
			for (Stock stock : availableStocks.get()) {
				BuySellAmount buySellAmount = strategies.buySellAmount(stock, currentDate, portfolio);
				PortfolioComponent component = buySellAmount.getPortfolioComponent();
				double tradedCash = buySellAmount.getTradedCash();
				if (component.getQuantity() != 0) {
					portfolio.add(component);
					portfolio.addCash(tradedCash);
				}
			}
			portfolioHistory.put(currentDate, portfolio);
			portfolio = portfolio.clone();
			tradingDate.rollDay();
		}
	}

	public PortfolioHistory getPortfolioHistory() {
		return portfolioHistory;
	}
}
