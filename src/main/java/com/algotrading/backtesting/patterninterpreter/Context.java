package com.algotrading.backtesting.patterninterpreter;

import java.util.Iterator;

public abstract class Context {
	protected Iterator<String> tokens;
	protected String currentToken;

	String nextToken() {
		currentToken = null;
		if (tokens.hasNext()) {
			currentToken = tokens.next();
		}
		return currentToken;
	}

	String currentToken() {
		return currentToken;
	}

	void skipToken(String token) {
		if (!token.equals(currentToken)) {
			System.err.println("Warning: " + token + " is expected, but "
					+ currentToken + " is found.");
		}
		nextToken();
	}

	int currentNumber() {
		return Integer.parseInt(currentToken);
	}
}

