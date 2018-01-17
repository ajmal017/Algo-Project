package com.algotrading.backtesting.pattern;

import java.text.ParseException;

public class SmaHigherThanSignal extends SmaSignal {

	public SmaHigherThanSignal(int magnitude, String expectedValueType, String expectedValue, double multiplier)
			throws ParseException {
		super(magnitude, expectedValueType, expectedValue, multiplier);
	}

	@Override
	protected boolean determine(double value) {
		if (testValue < 0) {
			return false;
		} else if (value * multiplier > testValue) {
			// System.out.println(value + "/" + testValue);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "SMAHigher[ magnitude=" + magnitude + " expectedValue=" + expectedValue + " ]";
	}

}
