package com.algotrading.backtesting.patterninterperter;

import java.text.ParseException;
import com.algotrading.backtesting.pattern.StockSignal;
import com.algotrading.backtesting.pattern.isSufficientCashSignal;
import com.algotrading.backtesting.pattern.cashMoreThanSignal;
public class cashInterpreter implements Node {
	private String name = "";
	private String expectedValueType="number";
	private String expectedValue;
	private double multiplier=1;

	@Override
	public void parse(Context context) throws ParseException {
		while (true) {
			if (context.currentToken() == null) {
				throw new ParseException(name, 0);
			} else if (context.currentToken()
					.equals("isSufficientCash[") && name == "") {
				context.skipToken("isSufficientCash[");
				name = "isSufficientCash";
			} else if (context.currentToken()
					.equals("cashMoreThan[") && name == "") {
				context.skipToken("cashMoreThan[");
				name = "cashMoreThan";
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

				if ("expectedValueType".equals(key) && name == "cashMoreThan") {
					expectedValueType = value;
				} else if ("expectedValue".equals(key) && name == "cashMoreThan") {
					expectedValue = value;
				} else if ("multiplier".equals(key) && name == "cashMoreThan") {
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
			switch (name) {
			case "cashMoreThan":
				return new cashMoreThanSignal(expectedValueType, expectedValue, multiplier);
			case "isSufficientCash":
				return new isSufficientCashSignal();
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
