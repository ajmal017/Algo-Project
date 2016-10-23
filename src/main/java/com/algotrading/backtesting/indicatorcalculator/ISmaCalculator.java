package com.algotrading.backtesting.indicatorcalculator;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ISmaCalculator {
	public Map<Date, Double> calculate(Map<Date, Double> datedprice, int magnitude);
}
