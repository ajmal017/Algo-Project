package com.algotrading.backtesting.pattern;

import java.text.ParseException;
import java.util.Date;

import com.algotrading.backtesting.portfolio.Portfolio;
import com.algotrading.backtesting.stock.Stock;

public class OrSignal implements StockSignal {

	private StockSignal left;
	private StockSignal right;

	public OrSignal(StockSignal left, StockSignal right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public boolean signal(Stock stock, Date date, Portfolio portfolio, double buyCostIfMatch) throws ParseException {
		return left.signal(stock, date, portfolio, buyCostIfMatch) || right.signal(stock, date, portfolio, buyCostIfMatch);
	}

	@Override
	public String toString() {
		return "OR( " + left.toString() + " , " + right.toString() + " )";
	}

}
