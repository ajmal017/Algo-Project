package com.algotrading.backtesting.pattern;

import java.text.ParseException;
import java.util.Date;

import com.algotrading.backtesting.portfolio.Portfolio;
import com.algotrading.backtesting.stock.Stock;

public class NotSignal implements StockSignal {

	private StockSignal pattern;

	public NotSignal(StockSignal pattern) {
		this.pattern = pattern;
	}

	@Override
	public boolean signal(Stock stock, Date date, Portfolio portfolio, double buyCostIfMatch) throws ParseException {
		return !pattern.signal(stock, date, portfolio, buyCostIfMatch);
	}

	@Override
	public String toString() {
		return "NOT( " + pattern.toString() + " )";
	}

}
