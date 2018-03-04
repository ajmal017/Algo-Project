package com.algotrading.backtesting.stock;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import com.algotrading.backtesting.portfolio.Portfolio;
import com.algotrading.backtesting.util.Constants;

public class PortfolioHistory {

	private Map<Date, Portfolio> history;

	private double initialCash;

	public PortfolioHistory() {
		this(0);
	}

	public PortfolioHistory(double initialCash) {
		this.history = new TreeMap<>();
		this.initialCash = initialCash;
	}

	public void put(Date date, Portfolio portfolio) {
		history.put(date, portfolio);
	}

	public double portfolioReturn(Date endDate) {
		// TODO: no exception handling yet...
		return getNetProfit(endDate)/ initialCash;
	}	
	
	public double portfolioReturn(Date startDate, Date endDate) {
		// TODO: no exception handling yet...
		double startDateMarketValue = history.get(startDate)
				.marketValue();
		double endDateMarketValue = history.get(endDate)
				.marketValue();
		return (endDateMarketValue - startDateMarketValue) / startDateMarketValue;
	}

	public double getNetProfit(Date endDate) {
		// TODO: no exception handling yet...
		return history.get(endDate).marketValue() - initialCash;
	}

	public double getNetProfit(Date startDate, Date endDate) {
		// TODO: no exception handling yet...
		return history.get(endDate).marketValue() - 
					history.get(startDate).marketValue();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<Date, Portfolio> entry : history.entrySet()) {
			sb.append(Constants.DATE_FORMAT_YYYYMMDD.format(entry.getKey()))
					.append(":")
					.append(entry.getValue())
					.append(", profit: ")
					.append(entry.getValue()
							.marketValue() - initialCash)
					.append("\n");
		}
		return sb.toString();
	}
	
	public Portfolio get(Date date){
		return history.get(date);
	}

}
