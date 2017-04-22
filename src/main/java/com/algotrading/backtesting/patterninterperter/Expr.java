package com.algotrading.backtesting.patterninterperter;

import java.text.ParseException;

import com.algotrading.backtesting.common.AlgoTradingConstants;
import com.algotrading.backtesting.pattern.StockSignal;

public class Expr implements Node {
	private Node node;

	@Override
	public void parse(Context context) throws ParseException {
		if (context.currentToken()
				.equals(AlgoTradingConstants.AND)) {
			node = new And();
			node.parse(context);
		} else if (context.currentToken()
				.equals(AlgoTradingConstants.OR)) {
			node = new Or();
			node.parse(context);
		} else if (context.currentToken()
				.equals(AlgoTradingConstants.NOT)) {
			node = new Not();
			node.parse(context);
		} else if (context.currentToken()
				.equals(AlgoTradingConstants.SE)) {
			node = new Se();
			node.parse(context);
		} else if (context.currentToken()
				.equals(AlgoTradingConstants.SMA_HIGHER)) {
			node = new SmaHigherThanInterperter();
			node.parse(context);
		} else if (context.currentToken()
				.equals(AlgoTradingConstants.RSI_LOWER)) {
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
