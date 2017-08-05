package com.algotrading.backtesting.pattern;

public class ClosingHigherThanSignal extends ClosingSignal {

	public ClosingHigherThanSignal(String expectedValueType, String expectedValue, int expectedLag, double multiplier) {
		super(expectedValueType, expectedValue, expectedLag, multiplier);
	}

	@Override
	protected boolean determine(double value) {
		if (value > calExpectedValue) {
			return true;
		} else {
			return false;
		}
	}

}
