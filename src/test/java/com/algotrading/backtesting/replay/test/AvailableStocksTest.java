package com.algotrading.backtesting.replay.test;

import java.io.IOException;
import java.text.ParseException;

import org.junit.Test;

import com.algotrading.backtesting.replay.AvailableStocks;
import com.algotrading.backtesting.util.Constants;

import static org.junit.Assert.assertEquals;

public class AvailableStocksTest {

	@Test
	public void testRead() throws IOException, ParseException {
		String inputFile = Constants.SRC_TEST_RESOURCE_FILEPATH + "availableStocks.txt";
		AvailableStocks availableStocks = new AvailableStocks(inputFile);
		assertEquals(8, availableStocks.get()
				.size());
	}

}
