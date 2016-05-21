package com.algotrading.backtesting.indicatorcalculator;

import java.math.BigDecimal;
import java.util.Date;

public interface ISmaCalculator {
	public BigDecimal calculate(Date date, int scale);
}