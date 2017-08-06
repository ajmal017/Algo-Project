package com.algotrading.backtesting.indicatorcalculator.test;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import com.algotrading.backtesting.indicatorcalculator.RSI;

public class RSITest {

	@Test
	public void test001_RSI() throws Exception {
		// Test case setting begins
		Calendar loopdate = new GregorianCalendar(2016, 1, 15);
		int map_length = 20;
		Date recent = loopdate.getTime();
		double expected_value = 0; // expected value of recent date
		int magnitude = 5;
		int sma_magnitude = 5;

		Double[] series = new Double[] { 97.35, 95.8, 96.4, 95.95, 95.0, 95.5, 96.9, 96.2, 95.9, 95.8, 97.95, 96.1, 96.7, 95.0, 94.0, 92.55, 93.6, 92.55, 93.0, 92.6}; // the series

		Map<Date, Double> datedprice = new TreeMap<Date, Double>();
		for (int i = 0; i < map_length; i++) {
			datedprice.put(loopdate.getTime(), series[i]);
			loopdate.add(Calendar.DATE, -1);
		}
		RSI rsi = new RSI(datedprice, recent, magnitude, sma_magnitude);
		// Test case setting ends

		// print the resulting line from rsi
		System.out.println("Result");
		Map<Date, Double> result_line = rsi.getLine();
		for (Map.Entry<Date, Double> entry : result_line.entrySet()) {
			System.out.println(entry.getKey().toString() + "/" + entry.getValue());
		}

		// print the expecting line and compare
		System.out.println("Expected");
		Map<Date, Double> expected_line = new TreeMap<Date, Double>();
		loopdate = new GregorianCalendar(2016, 1, 15);
		Double[] rsi_points = new Double[] { 0.0, 3.950617284, 7.901234568, 11.85185185, 15.80246914, 19.75308642,
				29.62962963, 44.44444444, 66.66666667, 100.0, 100.0, 100.0 }; // this
																				// is
																				// the
																				// expecting
																				// line
		for (int i = 0; i < map_length - magnitude - sma_magnitude + 1; i++) {
			expected_line.put(loopdate.getTime(), rsi_points[i]);
			System.out.println(loopdate.getTime().toString() + "/" + expected_line.get(loopdate.getTime()));
			assertEquals(result_line.get(loopdate.getTime()), rsi_points[i], 0.0001); // comparing
																						// each
																						// element
			loopdate.add(Calendar.DATE, -1);

		}

		assertEquals(rsi.getValue(), expected_value, 0.0001);
	}

}
