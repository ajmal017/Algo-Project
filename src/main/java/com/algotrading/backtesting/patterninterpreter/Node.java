package com.algotrading.backtesting.patterninterpreter;

import java.text.ParseException;

import com.algotrading.backtesting.pattern.StockSignal;

public interface Node {
	void parse(Context context) throws ParseException;

	StockSignal execute ();
}

// <expr> ::= <and> | <or> | <not> | <se>
// <and> ::= AND( <expr> , <expr> )
// <or> ::= OR( <expr> , <expr> )
// <not> ::= NOT( <expr> )
// <se> :: SE[ a ]
