package com.algotrading.backtesting.pattern;

import java.util.Date;

import com.algotrading.backtesting.portfolio.Portfolio;
import com.algotrading.backtesting.stock.Stock;

public class SeSignal implements StockSignal {

	private String name;

	public SeSignal(String name) {
		this.name = name;
	}

	@Override
	public boolean signal(Stock stock, Date date, Portfolio portfolio, double buyCostIfMatch) {
		return false;
	}

	@Override
	public String toString() {
		return "SE[ " + name + " ]";
	}

}
