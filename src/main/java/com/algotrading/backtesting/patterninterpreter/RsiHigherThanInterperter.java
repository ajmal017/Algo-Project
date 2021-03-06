package com.algotrading.backtesting.patterninterpreter;

import java.text.ParseException;

import com.algotrading.backtesting.pattern.RsiHigherThanSignal;
import com.algotrading.backtesting.pattern.StockSignal;

public class RsiHigherThanInterperter implements Node {
	private static String name = "RsiLower";
	private int magnitude = 10;
	private int sma_magnitude = 10;
	private String expectedValueType = "number";
	private String expectedValue;
	private double multiplier = 1;

	@Override
	public void parse(Context context) throws ParseException {
		while (true) {
			if (context.currentToken() == null) {
				throw new ParseException(name, 0);
			} else if (context.currentToken().equals("RSIHigher[")) {
				context.skipToken("RSIHigher[");
			} else if (context.currentToken().equals("]")) {
				context.skipToken("]");
				break;
			} else {
				String keyValue = context.currentToken();
				context.skipToken(context.currentToken());
				String[] keyValuePair = keyValue.split("=");
				String key = keyValuePair[0];
				String value = keyValuePair[1];
				// System.out.println("key = " + key);
				// System.out.println("value = " + value);
				if ("magnitude".equals(key)) {
					magnitude = Integer.parseInt(value);
				} else if ("sma_magnitude".equals(key)) {
					sma_magnitude = Integer.parseInt(value);
				} else if ("expectedValueType".equals(key)) {
					expectedValueType = value;
				} else if ("expectedValue".equals(key)) {
					expectedValue = value;
				} else if ("multiplier".equals(key)) {
					multiplier = Double.parseDouble(value);
				} else {
					throw new ParseException(name + " no field match", 0);
				}
			}
		}
	}

	@Override
	public StockSignal execute() {
		try {
			return new RsiHigherThanSignal(magnitude, sma_magnitude, expectedValueType, expectedValue, multiplier);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
