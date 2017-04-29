package com.algotrading.backtesting.patterninterpreter;

import java.text.ParseException;

import com.algotrading.backtesting.pattern.OrSignal;
import com.algotrading.backtesting.pattern.StockSignal;

public class Or implements Node {
	private Node left;
	private Node right;
	private String name;
	private boolean parsedLeft = false;

	@Override
	public void parse(Context context) throws ParseException {
		name = context.currentToken();
		context.skipToken(name);
		if (!(name.equals("OR("))) {
			throw new ParseException(name, 0);
		}
		while (true) {
			if (context.currentToken() == null) {
				throw new ParseException(name, 0);
			} else if (context.currentToken().equals(")")) {
				context.skipToken(")");
				break;
			} else if (context.currentToken().equals(",")) {
				context.skipToken(",");
				parsedLeft = true;
			} else {
				Node expr = new Expr();
				expr.parse(context);
				if (!parsedLeft) {
					left = expr;
				} else {
					right = expr;
				}
			}
		}
	}

	@Override
	public StockSignal execute() {
		return new OrSignal(left.execute(), right.execute());
	}
}
