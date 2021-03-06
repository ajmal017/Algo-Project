package com.algotrading.backtesting.patterninterpreter;

import java.text.ParseException;

import com.algotrading.backtesting.pattern.SmaHigherThanSignal;
import com.algotrading.backtesting.pattern.StockSignal;

public class SmaHigherThanInterpreter implements Node {
	private static String name = "SMAHigher";
	private int magnitude;
	private String expectedValueType="number";
	private String expectedValue;
	private double multiplier=1;

	@Override
	public void parse(Context context) throws ParseException {
		while (true) {
			if (context.currentToken() == null) {
				throw new ParseException(name, 0);
			} else if (context.currentToken()
					.equals("SMAHigher[")) {
				context.skipToken("SMAHigher[");
			} else if (context.currentToken()
					.equals("]")) {
				context.skipToken("]");
				break;
			} else {
				String keyValue = context.currentToken();
				context.skipToken(context.currentToken());

				String[] keyValuePair = keyValue.split("=");
				String key = keyValuePair[0];
				String value = keyValuePair[1];

				if ("magnitude".equals(key)) {
					magnitude = Integer.parseInt(value);
				} else if ("expectedValueType".equals(key)) {
					expectedValueType = value;
				} else if ("expectedValue".equals(key)) {
					expectedValue = value;
				} else if ("multiplier".equals(key)) {
					multiplier = Double.parseDouble(value);
				} else {
					throw new ParseException(name + " no field match: " + key, 0);				
				}
			}
		}
	}

	@Override
	public StockSignal execute() {
		try {
			return new SmaHigherThanSignal(magnitude, expectedValueType, expectedValue, multiplier);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
}
