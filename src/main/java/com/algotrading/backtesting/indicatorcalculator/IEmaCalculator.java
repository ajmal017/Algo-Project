package com.algotrading.backtesting.indicatorcalculator;

import java.util.Date;
import java.util.Map;

public interface IEmaCalculator {
	public Map<Date, Double> calculate(Map<Date, Double> datedprice, int magnitude, int sma_magnitude, double alpha, Map<Date, Double> boundary);
}
