package com.algotrading.backtesting.indicatorcalculator;

import java.math.BigDecimal;
import java.util.Date;

import com.algotrading.backtesting.stock.YahooStock;

public abstract class YahooStockRsiCalculator implements IRsiCalculator{

	protected YahooStockEmaCalculator emaCalculator;
	protected YahooStock yahooStock;

	
	public YahooStockRsiCalculator(YahooStock yahooStock, YahooStockEmaCalculator emaCalculator) {
		this.yahooStock = yahooStock;
		this.emaCalculator = emaCalculator;
	}
	
	@Override
	public abstract BigDecimal calculate(Date date, int scale);

}