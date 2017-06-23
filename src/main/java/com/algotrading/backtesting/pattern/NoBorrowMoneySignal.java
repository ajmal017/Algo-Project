package com.algotrading.backtesting.pattern;

import java.text.ParseException;
import java.util.Date;

import com.algotrading.backtesting.portfolio.Portfolio;
import com.algotrading.backtesting.stock.Stock;

public class NoBorrowMoneySignal implements StockSignal {

	public NoBorrowMoneySignal() {
	}

	@Override
	public boolean signal(Stock stock, Date date, Portfolio portfolio, double buyCostIfMatch) throws ParseException {
		double cash = portfolio.getCash();
		double minimumCostOf1LotStock = stock.getLotSize() * stock.getHistory()
				.get(date)
				.getClose();
		return cash > minimumCostOf1LotStock;
	}

	@Override
	public String toString() {
		return "NoBorrowMoney[ ]";
	}

}
