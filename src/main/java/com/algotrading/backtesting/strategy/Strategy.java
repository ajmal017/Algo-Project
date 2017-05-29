package com.algotrading.backtesting.strategy;

import java.text.ParseException;
import java.util.Date;

import com.algotrading.backtesting.pattern.StockSignal;
import com.algotrading.backtesting.portfolio.Portfolio;
import com.algotrading.backtesting.portfolio.PortfolioComponent;
import com.algotrading.backtesting.stock.Stock;

public class Strategy {
	private StockSignal pattern;
	private double buyCostIfMatch;

	public Strategy(StockSignal pattern, double buyCostIfMatch) {
		super();
		this.pattern = pattern;
		this.buyCostIfMatch = buyCostIfMatch;
	}

	public boolean shouldPutOrder(Stock stock, Date date, Portfolio portfolio) throws ParseException {
		return pattern.signal(stock, date, portfolio);
	}

	public PortfolioComponent buyAmount(Stock stock, Date date) {
		double unitPrice = stock.getHistory()
				.get(date)
				.getClose();
		int buyVolumeBeforeLotSize = (int) (buyCostIfMatch / unitPrice);
		int buyBolumeAfterLotSize = (buyVolumeBeforeLotSize / stock.getLotSize()) * stock.getLotSize();
		return new PortfolioComponent(stock, buyBolumeAfterLotSize, unitPrice);
	}

	public PortfolioComponent sellAmount(Stock stock, Date date, Portfolio portfolio) {
		double unitPrice = stock.getHistory()
				.get(date)
				.getClose();
		int sellVolumeBeforeLotSize = (int) (buyCostIfMatch / unitPrice);
		int sellVolumeAfterLotSize = (sellVolumeBeforeLotSize / stock.getLotSize()) * stock.getLotSize();
		int sellVolumeAfterPossibleSoldAll = buyCostIfMatch == 0 ? portfolio.getPortfolioComponent(stock.getTicker())
				.getQuantity() : sellVolumeAfterLotSize;
		return new PortfolioComponent(stock, 0 - sellVolumeAfterPossibleSoldAll, unitPrice);
	}
}
