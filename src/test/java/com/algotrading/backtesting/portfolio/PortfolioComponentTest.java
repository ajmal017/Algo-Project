package com.algotrading.backtesting.portfolio;

import org.junit.Test;

import com.algotrading.backtesting.stock.Stock;

import static org.junit.Assert.assertEquals;

public class PortfolioComponentTest {

	private Stock stock1382 = new Stock("1382");

	@Test
	public void test1_addPortfolioComponent() {
		PortfolioComponent pc1 = new PortfolioComponent(stock1382, 1000, 10);
		PortfolioComponent pc2 = new PortfolioComponent(stock1382, 1000, 20);
		pc1.add(pc2);
		assertEquals(2000, pc1.getQuantity());
		assertEquals(15, pc1.getUnitPrice(), 0.01);
	}

	@Test
	public void test2_addPortfolioComponent() {
		PortfolioComponent pc1 = new PortfolioComponent(stock1382, 1000, 10);
		pc1.add(1000, 20);
		assertEquals(2000, pc1.getQuantity());
		assertEquals(15, pc1.getUnitPrice(), 0.01);
	}

}
