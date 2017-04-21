package com.algotrading.backtesting.pattern;

import java.text.ParseException;

public class SmaHigherThanSignal extends SmaSignal {

	public SmaHigherThanSignal(int magnitude, String expectedValueType, String expectedValue, double multiplier)
			throws ParseException {
		super(magnitude, expectedValueType, expectedValue, multiplier);
	}

	@Override
	protected boolean determine(double value) {
		return value > (testValue * multiplier);
	}

}
