package com.algotrading.backtesting.replay.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
//@formatter:off
@SuiteClasses({ 
	AvailableStocksTest.class, 
	ReplayTest.class,
	TradingDateTest.class,
	ReplayTestSelf.class
})
//@formatter:on
public class ReplayTestSuite {

}