package com.algotrading.backtesting.patterninterpreter;

import java.text.ParseException;

import com.algotrading.backtesting.pattern.StockSignal;

public class Interpreter {
	public static StockSignal toPattern(String input) throws ParseException {
		Node node = new Expr();
		node.parse(new StringContext(input));
		StockSignal pattern = node.execute();
		return pattern;
	}
}
