package com.algotrading.backtesting.replay;

import java.text.ParseException;
import java.util.Date;

import com.algotrading.backtesting.portfolio.BuySellAmount;
import com.algotrading.backtesting.portfolio.Portfolio;
import com.algotrading.backtesting.portfolio.PortfolioComponent;
import com.algotrading.backtesting.stock.PortfolioHistory;
import com.algotrading.backtesting.stock.Stock;
import com.algotrading.backtesting.strategy.Strategies;
import com.algotrading.backtesting.util.PrintMethod;

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

	private PrintMethod print;
	private Boolean doPrint = false;

	public double getTotalTradedVolume() {
		return totalTradedVolume;
	}

	public double getTotalTransactionCost() {
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
			this.portfolioHistory.put(startDate, portfolio);
		}
		this.portfolioHistory.setInitValue(this.portfolio.marketValue());
	}

	public Replay(Date startDate, Date endDate, PortfolioHistory portfolioHistory, Strategies strategies,
			AvailableStocks availableStocks, TradingDate tradingDate, double initialCash, PrintMethod printMethod)
			throws ParseException {
		print = printMethod;
		print.setDatesAndHistory(startDate, endDate, portfolioHistory);
		doPrint = true;
		setData(startDate, endDate, portfolioHistory, strategies, availableStocks, tradingDate, initialCash);
	}

	public Replay(Date startDate, Date endDate, PortfolioHistory portfolioHistory, Strategies strategies,
			AvailableStocks availableStocks, TradingDate tradingDate, double initialCash) {
		setData(startDate, endDate, portfolioHistory, strategies, availableStocks, tradingDate, initialCash);
	}

	public void simulate() throws ParseException {
		System.out.println("Simulation started... ");
		Date currentDate = startDate;
		tradingDate.setCurrentDate(currentDate);
		while (tradingDate.isNotLastDate() && tradingDate.currentDate().compareTo(endDate) <= 0) {
			currentDate = tradingDate.currentDate();
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
				}
			}
			if (doPrint) {
				print.record(currentDate, portfolio);
			}
			portfolio = portfolio.clone();
			tradingDate.rollDay();
		}
	}

	public PortfolioHistory getPortfolioHistory() {
		return portfolioHistory;
	}

	public void print() throws ParseException {
		if (doPrint)
			print.print();
		else
			throw new ParseException("Print Error: Print method has not been initiated.", 0);
	}

	public PrintMethod getPrintMethod() {
		return print;
	}

}
