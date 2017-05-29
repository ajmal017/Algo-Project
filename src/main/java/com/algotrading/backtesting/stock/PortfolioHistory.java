package com.algotrading.backtesting.stock;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import com.algotrading.backtesting.portfolio.Portfolio;
import com.algotrading.backtesting.util.Constants;

public class PortfolioHistory {

	private Map<Date, Portfolio> history;

	public PortfolioHistory() {
		this.history = new TreeMap<>();
	}

	public void put(Date date, Portfolio portfolio) {
		history.put(date, portfolio);
	}

	public double portfolioReturn(double startDate, double endDate) {
		// TODO: no exception handling yet...
		double startDateMarketValue = history.get(startDate)
				.marketValue();
		double endDateMarketValue = history.get(endDate)
				.marketValue();
		return (endDateMarketValue - startDateMarketValue) / startDateMarketValue;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<Date, Portfolio> entry : history.entrySet()) {
			sb.append(Constants.DATE_FORMAT_YYYYMMDD.format(entry.getKey()))
					.append(":")
					.append(entry.getValue())
					.append("\n");
		}
		return sb.toString();
	}
}
