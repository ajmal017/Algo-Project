package com.algotrading.backtesting.pattern;

import java.util.Date;

import com.algotrading.backtesting.stock.Stock;

public interface IStockPattern {

	public boolean signal(Stock stock, Date date);
}
