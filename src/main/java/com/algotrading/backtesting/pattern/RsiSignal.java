package com.algotrading.backtesting.pattern;

// next step: -implement in a way that RSI of the same stock does not need to be created every time. 
//            -may want to modify the way to get an element particular field (eg. Volume) using date.
// find the index of an element of a list: List.indexOf(elm)
// Not = !
// How should we write in buyStrategies.txt

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import com.algotrading.backtesting.common.AlgoTradingConstants;
import com.algotrading.backtesting.indicatorcalculator.RSI;
import com.algotrading.backtesting.portfolio.Portfolio;
import com.algotrading.backtesting.stock.Stock;
import com.algotrading.backtesting.stock.StockHistory;

public abstract class RsiSignal implements StockSignal {

	protected int magnitude = 10;
	protected int sma_magnitude = 10;
	protected String expectedValueType = AlgoTradingConstants.NUMBER;
	protected String expectedValue = "10";
	protected double multiplier = 1;
	protected double testValue;
	protected RSI rsi;
	protected Map<Date, Double> closingHistory;

	public RsiSignal(int magnitude, int sma_magnitude, String expectedValueType, String expectedValue,
			double multiplier) throws ParseException {
		this.magnitude = magnitude;
		this.sma_magnitude = sma_magnitude;
		this.expectedValueType = expectedValue;
		this.expectedValue = expectedValue;
		this.multiplier = multiplier;
		// settestValue();
	}

	public int getMagnitude() {
		return magnitude;
	}

	public int getSMA_Magnitude() {
		return sma_magnitude;
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
	public boolean signal(Stock stock, Date date, Portfolio portfolio) throws ParseException {
		if (closingHistory == null) {
			Map<Date, StockHistory> history = stock.getHistory();
			// Map<Date, Double> closingHistory = new TreeMap<>();
			for (Map.Entry<Date, StockHistory> entry : history.entrySet()) {
				closingHistory.put(entry.getKey(), entry.getValue()
						.getClose());
			}
			try {
				rsi = new RSI(closingHistory, date, magnitude, sma_magnitude);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}

		rsi.setRecent(date);
		settestValue(date);
		try {
			RSI rsi = new RSI(closingHistory, date, magnitude, sma_magnitude);
			double value = rsi.getValue();
			return determine(value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	protected abstract boolean determine(double value);

	private void settestValue(Date date) throws ParseException {
		switch (expectedValueType) {
		case AlgoTradingConstants.NUMBER:
			testValue = Double.parseDouble(this.expectedValue);
		case AlgoTradingConstants.VARIABLE:
			switch (expectedValue) {
			case AlgoTradingConstants.CLOSING:
				// should depend on expectedValue
				testValue = closingHistory.get(date);
			default:
				throw new ParseException("Invalid ExpectedvalueType -- " + expectedValue + ": no field match", 0);
			}
		default:
			throw new ParseException("Invalid ExpectedvalueType -- " + expectedValue + ": no field match", 0);
		}
	}
}
