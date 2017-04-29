package com.algotrading.backtesting.patterninterpreter;

import java.text.ParseException;

import com.algotrading.backtesting.pattern.StockSignal;

public class Expr implements Node {
	private Node node;

	@Override
	public void parse(Context context) throws ParseException {
		if (context.currentToken()
				.equals("AND(")) {
			node = new And();
			node.parse(context);
		} else if (context.currentToken()
				.equals("OR(")) {
			node = new Or();
			node.parse(context);
		} else if (context.currentToken()
				.equals("NOT(")) {
			node = new Not();
			node.parse(context);
		} else if (context.currentToken()
				.equals("SE[")) {
			node = new Se();
			node.parse(context);
		} else if (context.currentToken()
				.equals("SMAHigher[")) {
			node = new SmaHigherThanInterpreter();
			node.parse(context);
		} else if (context.currentToken()
				.equals("RSILower[")) {
			node = new RsiLowerThanInterpreter();
			node.parse(context);
		} else if (context.currentToken()
				.equals("PosEqual[")) {
			node = new PositionEqualInterpreter();
			node.parse(context);
		} else {
			throw new ParseException("", 0);
		}
	}

	@Override
	public StockSignal execute() {
		return node.execute();
	}
}
