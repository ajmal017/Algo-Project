package com.algotrading.backtesting.testsuite.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.algotrading.backtesting.indicatorcalculator.test.IndicatorCalculatorTestSuite;
import com.algotrading.backtesting.pattern.test.PatternTestSuite;
import com.algotrading.backtesting.patterninterpreter.test.PatternInterperterTestSuite;
import com.algotrading.backtesting.portfolio.test.PortfolioTestSuite;
import com.algotrading.backtesting.printmethod.test.PrintMethodTestSuite;
import com.algotrading.backtesting.replay.test.ReplayTestSuite;
import com.algotrading.backtesting.signal.test.SignalTestSuite;
import com.algotrading.backtesting.stockread.test.StockReadTestSuite;

@RunWith(Suite.class)
// @formatter:off
@SuiteClasses({ IndicatorCalculatorTestSuite.class, PatternTestSuite.class, PatternInterperterTestSuite.class,
		PortfolioTestSuite.class, ReplayTestSuite.class, SignalTestSuite.class, StockReadTestSuite.class,
		PrintMethodTestSuite.class })

// @formatter:on
public class AllTests {

}