package com.algotrading.backtesting.indicatorcalculator;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.algotrading.backtesting.stock.YahooStock;

public abstract class YahooStockEmaCalculator implements IEmaCalculator{
	protected YahooStock yahooStock;
	
	public YahooStockEmaCalculator(YahooStock yahooStock) {
		this.yahooStock = yahooStock;
	}
	
	@Override
	public abstract BigDecimal calculate(Date date, int scale);

	public BigDecimal EMA(List<Double> historicClose, int scale) {
		return null;
	}
	
}