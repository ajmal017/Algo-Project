package com.algotrading.backtesting.indicatorcalculator;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.algotrading.backtesting.stock.YahooStock;

public abstract class YahooStockSmaCalculator implements IEmaCalculator{
	protected YahooStock yahooStock;
	
	public YahooStockSmaCalculator(YahooStock yahooStock) {
		this.yahooStock = yahooStock;
	}
	
	@Override
	public abstract BigDecimal calculate(Date date, int scale);
	
	public abstract BigDecimal EMA(List<Double> historicClose, int scale);
}