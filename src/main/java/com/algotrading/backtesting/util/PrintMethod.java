package com.algotrading.backtesting.util;

import java.util.Date;

import com.algotrading.backtesting.portfolio.BuySellAmount;
import com.algotrading.backtesting.stock.PortfolioHistory;

public abstract class PrintMethod {

	private Date startDate;
	private Date endDate;
	protected PortfolioHistory portfolioHistory;

	public PrintMethod(Date startDate, Date endDate, PortfolioHistory portfolioHistory) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.portfolioHistory = portfolioHistory;
	}

	public abstract void record(BuySellAmount buySellAmount);

	public abstract void print();

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public PortfolioHistory getPortfolioHistory() {
		return portfolioHistory;
	}

}
