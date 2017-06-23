package com.algotrading.backtesting.pattern;

import java.text.ParseException;
import java.util.Date;

import com.algotrading.backtesting.portfolio.Portfolio;
import com.algotrading.backtesting.stock.Stock;

public abstract class cashSignal implements StockSignal{
	protected double currentCash;
	protected double buyCostIfMatch;
	protected String expectedValueType = "number";
	protected String expectedValue = "10";
	protected double multiplier = 1;
	protected double compareValue = 1;
	
	public cashSignal(){}
	public cashSignal(String expectedValueType, String expectedValue, double multiplier) throws ParseException{
		this.expectedValueType = expectedValueType;
		this.expectedValue = expectedValue;
		this.multiplier = multiplier;
	}
	
	
	@Override
	public boolean signal(Stock stock, Date date, Portfolio portfolio, double buyCostIfMatch) throws ParseException {
		currentCash = portfolio.getCash();
		this.buyCostIfMatch = buyCostIfMatch;
		this.settestValue();
		return determine();
	}
	
	protected abstract boolean determine();

	protected abstract void settestValue() throws ParseException;

}
