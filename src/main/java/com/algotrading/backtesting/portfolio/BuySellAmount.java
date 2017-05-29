package com.algotrading.backtesting.portfolio;

public class BuySellAmount {

	private PortfolioComponent portfolioComponent;
	private double tradedCash;

	public BuySellAmount(PortfolioComponent portfolioComponent, double tradedCash) {
		this.portfolioComponent = portfolioComponent;
		this.tradedCash = tradedCash;
	}

	public PortfolioComponent getPortfolioComponent() {
		return portfolioComponent;
	}

	public double getTradedCash() {
		return tradedCash;
	}

}
