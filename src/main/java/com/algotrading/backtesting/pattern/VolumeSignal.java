package com.algotrading.backtesting.pattern;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.algotrading.backtesting.portfolio.Portfolio;
import com.algotrading.backtesting.stock.Stock;
import com.algotrading.backtesting.stock.StockHistory;

public abstract class VolumeSignal implements StockSignal {

	protected String expectedValueType; // "number", "variable" (not yet
										// support)
	protected String expectedValue; // number, "volume", "closing",
									// "holdingprice"
	protected int expectedLag; // positive integer
	protected double multiplier;
	protected double calExpectedValue;

	public VolumeSignal(String expectedValueType, String expectedValue, int expectedLag, double multiplier) {
		this.expectedValueType = expectedValueType;
		this.expectedValue = expectedValue;
		this.expectedLag = expectedLag;
		this.multiplier = multiplier;
	}

	public String getExpectedValueType() {
		return expectedValueType;
	}

	public String getExpectedValue() {
		return expectedValue;
	}

	public int getExpectedLag() {
		return expectedLag;
	}

	public double getMultiplier() {
		return multiplier;
	}

	@Override
	public boolean signal(Stock stock, Date date, Portfolio portfolio, double buyCostIfMatch) {
		Map<Date, StockHistory> history = stock.getHistory();
		if (expectedValueType.equals("variable")) {
			if (expectedValue.equals("volume")) {

				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.DATE, 0 - expectedLag);
				Date earlierDate = cal.getTime();

				calExpectedValue = history.get(earlierDate).getVolume() * multiplier;
			}
		} else {
			calExpectedValue = Double.valueOf(expectedValue) * multiplier;
		}
		try {
			double value = history.get(date).getVolume();
			return determine(value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	protected abstract boolean determine(double value);

}
