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
	// private LotSize lotSize;
	private Portfolio initialPortfolio;
	private Portfolio portfolio;
	private double totalTradedVolume = 0;
	private double totalTrasactionCost = 0;
	
	public double getTotalTradedVolume(){
		return totalTradedVolume;
	}

	public double getTotalTrasactionCost(){
		return totalTrasactionCost;
	}
	
	public Replay(Date startDate, Date endDate, PortfolioHistory portfolioHistory, Strategies strategies,
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
		this.initialPortfolio = portfolio.clone();
	}

	public void simulate() throws ParseException {
		Date currentDate = startDate;
//		System.out.println("startdate: " + startDate);
		tradingDate.setCurrentDate(currentDate);
//		System.out.println("End setCurrentDate");
		// Portfolio portfolio = new Portfolio(currentDate, initialCash);
		while (tradingDate.isNotLastDate() && tradingDate.currentDate().compareTo(endDate) <= 0) {
			currentDate = tradingDate.currentDate();
//			System.out.println("tradingDate.currentDate(): " + tradingDate.currentDate());
			portfolio.setDate(currentDate);
			for (Stock stock : availableStocks.get()) {
//				System.out.println("simulate: " + currentDate);
				BuySellAmount buySellAmount = strategies.buySellAmount(stock, currentDate, portfolio);
				PortfolioComponent component = buySellAmount.getPortfolioComponent();
				totalTradedVolume = totalTradedVolume + Math.abs(component.getQuantity() * component.getUnitPrice());
				totalTrasactionCost = totalTrasactionCost + buySellAmount.getTransaction();
				double tradedCash = buySellAmount.getTradedCash();
				
				if (component.getQuantity() != 0) {
					portfolio.add(component);
					portfolio.addCash(tradedCash);
					portfolio.addTransaction(buySellAmount);
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
