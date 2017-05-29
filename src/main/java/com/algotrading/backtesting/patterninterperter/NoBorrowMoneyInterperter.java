package com.algotrading.backtesting.patterninterperter;

import java.text.ParseException;

import com.algotrading.backtesting.pattern.NoBorrowMoneySignal;
import com.algotrading.backtesting.pattern.StockSignal;

public class NoBorrowMoneyInterperter implements Node {

	@Override
	public void parse(Context context) throws ParseException {
		while (true) {
			if (context.currentToken() == null) {
				throw new ParseException("Empty token on NoBorrowMoney", 0);
			} else if (context.currentToken()
					.equals("NoBorrowMoney[")) {
				context.skipToken("NoBorrowMoney[");
			} else if (context.currentToken()
					.equals("]")) {
				context.skipToken("]");
				break;
			}
		}
	}

	@Override
	public StockSignal execute() {
		return new NoBorrowMoneySignal();
	}
}
