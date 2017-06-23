package com.algotrading.backtesting.pattern;

//next step: -implement in a way that SMA of the same stock does not need to be created every time;
//           -may want to modify the way to get an element particular field (eg. Volume) using date. 

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import com.algotrading.backtesting.indicatorcalculator.SMA;
import com.algotrading.backtesting.portfolio.Portfolio;
import com.algotrading.backtesting.stock.Stock;
import com.algotrading.backtesting.stock.StockHistory;

public abstract class SmaSignal implements StockSignal {

	protected int magnitude = 10;
	protected String expectedValueType = "number";
	protected String expectedValue = "10";
	protected double multiplier = 1;
	protected double testValue;
	protected Map<Date, Double> closingHistory;
	protected SMA sma;

	public SmaSignal(int magnitude, String expectedValueType, String expectedValue, double multiplier) {
		this.magnitude = magnitude;
		this.expectedValueType = expectedValueType;
		this.expectedValue = expectedValue;
		// settestValue();
	}

	public int getMagnitude() {
		return magnitude;
	}

	public String getExpectedValueType() {
		return expectedValueType;
	}

	public String getExpectedValue() {
		return expectedValue;
	}

	public double getMultiplier() {
		return multiplier;
	}

	@Override
	public boolean signal(Stock stock, Date date, Portfolio portfolio, double buyCostIfMatch) throws ParseException {
		if (closingHistory == null) {
			Map<Date, StockHistory> history = stock.getHistory();
			// Map<Date, Double> closingHistory = new TreeMap<>();
			for (Map.Entry<Date, StockHistory> entry : history.entrySet()) {
				closingHistory.put(entry.getKey(), entry.getValue()
						.getClose());
			}
			try {
				sma = new SMA(closingHistory, date, magnitude);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		sma.setRecent(date);
		settestValue(date);
		try {
			double value = sma.getValue();
			return determine(value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	protected abstract boolean determine(double value);

	private void settestValue(Date date) throws ParseException {
		switch (expectedValueType) {
		case "number":
			testValue = Double.parseDouble(this.expectedValue);
		case "variable":
			switch (expectedValue) {
			case "closing":
				testValue = closingHistory.get(date); // should depend on
														// expectedValue
			default:
				throw new ParseException("Invalid ExpectedvalueType -- " + expectedValue + ": no field match", 0);
			}
		default:
			throw new ParseException("Invalid ExpectedvalueType -- " + expectedValue + ": no field match", 0);
		}
	}
}
