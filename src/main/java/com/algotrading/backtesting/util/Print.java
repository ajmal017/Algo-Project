package com.algotrading.backtesting.util;

import java.text.ParseException;
import java.util.Date;

import com.algotrading.backtesting.portfolio.BuySellAmount;
import com.algotrading.backtesting.stock.PortfolioHistory;

public class Print {

	private PrintMethod method;

	public Print(String printMethod, Date startDate, Date endDate, PortfolioHistory portfolioHistory)
			throws ParseException {
		if (printMethod.equals("KPI")) {
			method = new Print_KPI(startDate, endDate, portfolioHistory);
		} else if (printMethod.equals("Console")) {
			method = new Print_Console(startDate, endDate, portfolioHistory);
		} else {
			throw new ParseException("Method '" + printMethod + "' is not defined.", 0);
		}

	}

	public void record(BuySellAmount buySellAmount) {
		method.record(buySellAmount);

	}

	public void print() {
		method.print();

	}

}
