package com.algotrading.backtesting.pattern;

import java.text.ParseException;

public class cashMoreThanSignal extends cashSignal{

	public cashMoreThanSignal(String expectedValueType, String expectedValue, double multiplier) throws ParseException {
		super(expectedValueType, expectedValue, multiplier);
	}

	@Override
	protected boolean determine() {
		return currentCash > compareValue;
	}

	@Override
	public String toString() {
		return "cashMoreThan[ expectedValue=" + expectedValue + " multiplier = " + String.valueOf(multiplier) + "]";
	}	
	
	@Override
	protected void settestValue() throws ParseException {
		switch (expectedValueType) {
		case "number":
			compareValue = Double.parseDouble(this.expectedValue) * multiplier;
			break;
		case "cost":
			compareValue = buyCostIfMatch * multiplier;
			break;
		default:
			throw new ParseException("Invalid ExpectedvalueType -- " + expectedValue + ": no field match", 0);
		}
	}
	
}
