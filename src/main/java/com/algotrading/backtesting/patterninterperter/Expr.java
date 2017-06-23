package com.algotrading.backtesting.patterninterperter;

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
				.equals("NoBorrowMoney[")) {
			node = new NoBorrowMoneyInterperter();
			node.parse(context);
		} else if (context.currentToken()
				.equals("SMAHigher[")) {
			node = new SmaHigherThanInterperter();
			node.parse(context);
		} else if (context.currentToken()
				.equals("RSILower[")) {
			node = new RsiLowerThanInterperter();
			node.parse(context);
		} else if (context.currentToken()
				.equals("isSufficientCash[")) {
			node = new RsiLowerThanInterperter();
			node.parse(context);
		} else if (context.currentToken()
				.equals("cashMoreThan[")) {
			node = new RsiLowerThanInterperter();
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
