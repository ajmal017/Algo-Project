package com.algotrading.backtesting.util;

import java.util.Date;

import com.algotrading.backtesting.portfolio.BuySellAmount;
import com.algotrading.backtesting.stock.PortfolioHistory;

public class Print_Console extends PrintMethod {

	public Print_Console(Date startDate, Date endDate, PortfolioHistory portfolioHistory) {
		super(startDate, endDate, portfolioHistory);
	}

	@Override
	public void record(BuySellAmount buySellAmount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void print() {
		// TODO Auto-generated method stub

	}

}
