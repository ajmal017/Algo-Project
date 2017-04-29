package com.algotrading.backtesting.replay.test;

import java.io.IOException;
import java.text.ParseException;

import org.junit.Test;

import com.algotrading.backtesting.replay.AvailableStocks;

import static org.junit.Assert.assertEquals;

public class AvailableStocksTest {

	@Test
	public void testRead() throws IOException, ParseException {
		String inputFile = "src/test/resources/availableStocks.txt";
		AvailableStocks availableStocks = new AvailableStocks(inputFile);
		assertEquals(3, availableStocks.get()
				.size());
	}

}
