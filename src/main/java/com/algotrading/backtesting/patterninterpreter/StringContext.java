package com.algotrading.backtesting.patterninterpreter;

import java.util.ArrayList;
import java.util.List;

public class StringContext extends Context {
	public StringContext(String input) {
		List<String> tokenList = new ArrayList<String>();
		for (String token : input.trim().split("\\s+")) {
			tokenList.add(token);
		}
		tokens = tokenList.iterator();
		nextToken();
	}
}
