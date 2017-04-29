package com.algotrading.backtesting.patterninterpreter;

import java.text.ParseException;

import com.algotrading.backtesting.pattern.NotSignal;
import com.algotrading.backtesting.pattern.StockSignal;

public class Not implements Node {
	private Node node;
	private String name;

	@Override
	public void parse(Context context) throws ParseException {
		name = context.currentToken();
		context.skipToken(name);
		if (!(name.equals("NOT("))) {
			throw new ParseException(name, 0);
		}
		while (true) {
			if (context.currentToken() == null) {
				throw new ParseException(name, 0);
			} else if (context.currentToken().equals(")")) {
				context.skipToken(")");
				break;
			} else {
				Node expr = new Expr();
				expr.parse(context);
				node = expr;
			}
		}
	}

	@Override
	public StockSignal execute() {
		return new NotSignal(node.execute());
	}
}
