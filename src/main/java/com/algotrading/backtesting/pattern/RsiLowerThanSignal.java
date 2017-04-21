package com.algotrading.backtesting.pattern;

import java.text.ParseException;

public class RsiLowerThanSignal extends RsiSignal {

	public RsiLowerThanSignal(int magnitude, int sma_magnitude, String expectedValueType, String expectedValue,
			double multiplier) throws ParseException {
		super(magnitude, sma_magnitude, expectedValueType, expectedValue, multiplier);
	}

	@Override
	protected boolean determine(double value) {
		return value < (testValue * multiplier);
	}

}
