package com.algotrading.backtesting.replay;

import java.text.ParseException;
import java.util.Date;

import com.algotrading.backtesting.portfolio.BuySellAmount;
import com.algotrading.backtesting.portfolio.Portfolio;
import com.algotrading.backtesting.portfolio.PortfolioComponent;
import com.algotrading.backtesting.stock.PortfolioHistory;
import com.algotrading.backtesting.stock.Stock;
import com.algotrading.backtesting.strategy.Strategies;
import com.algotrading.backtesting.util.Print;

public class Replay {
	private Date startDate;
	private Date endDate;
	private PortfolioHistory portfolioHistory;
	private Strategies strategies;
	private AvailableStocks availableStocks;
	private TradingDate tradingDate;
	// private LotSize lotSize;
	// private Portfolio initialPortfolio;
	private Portfolio portfolio;
	private double totalTradedVolume = 0;
	private double totalTrasactionCost = 0;
	private Boolean doPrint = false;
	private Print print;

	public double getTotalTradedVolume() {
		return totalTradedVolume;
	}

	public double getTotalTrasactionCost() {
		return totalTrasactionCost;
	}

	private void setData(Date startDate, Date endDate, PortfolioHistory portfolioHistory, Strategies strategies,
			AvailableStocks availableStocks, TradingDate tradingDate, double initialCash) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.portfolioHistory = portfolioHistory;
		this.strategies = strategies;
		this.availableStocks = availableStocks;
		this.tradingDate = tradingDate;
		// this.lotSize = lotSize;

		this.portfolio = portfolioHistory.get(startDate);
		if (this.portfolio != null) {
			this.portfolio.addCash(initialCash);

		} else {
			this.portfolio = new Portfolio(startDate, initialCash);
		}
		this.portfolioHistory.setInitValue(portfolio.marketValue());
		// this.initialPortfolio = portfolio.clone();
	}

	public Replay(Date startDate, Date endDate, PortfolioHistory portfolioHistory, Strategies strategies,
			AvailableStocks availableStocks, TradingDate tradingDate, double initialCash, String printMethod)
			throws ParseException {
		setData(startDate, endDate, portfolioHistory, strategies, availableStocks, tradingDate, initialCash);
		print = new Print(printMethod, startDate, endDate, portfolioHistory);
		doPrint = true;
	}

	public Replay(Date startDate, Date endDate, PortfolioHistory portfolioHistory, Strategies strategies,
			AvailableStocks availableStocks, TradingDate tradingDate, double initialCash) {
		setData(startDate, endDate, portfolioHistory, strategies, availableStocks, tradingDate, initialCash);
	}

	public void simulate() throws ParseException {
		Date currentDate = startDate;
		// System.out.println("startdate: " + startDate);
		tradingDate.setCurrentDate(currentDate);
		// System.out.println("End setCurrentDate");
		while (tradingDate.isNotLastDate() && tradingDate.currentDate().compareTo(endDate) <= 0) {
			currentDate = tradingDate.currentDate();
			// System.out.println("tradingDate.currentDate(): " +
			// tradingDate.currentDate());
			portfolio.setDate(currentDate);
			for (Stock stock : availableStocks.get()) {
				// System.out.println("simulate: " + currentDate);
				BuySellAmount buySellAmount = strategies.buySellAmount(stock, currentDate, portfolio);
				PortfolioComponent component = buySellAmount.getPortfolioComponent();
				if (component.getQuantity() != 0) {
					totalTradedVolume = totalTradedVolume
							+ Math.abs(component.getQuantity() * component.getUnitPrice());
					totalTrasactionCost = totalTrasactionCost + buySellAmount.getTransaction();
					double tradedCash = buySellAmount.getTradedCash();

					portfolio.add(component);
					portfolio.addCash(tradedCash);
					portfolio.addTransaction(buySellAmount);
					if (doPrint) {
						print.record(buySellAmount);
					}
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
