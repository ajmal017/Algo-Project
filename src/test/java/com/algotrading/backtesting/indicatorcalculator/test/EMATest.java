package com.algotrading.backtesting.indicatorcalculator.test;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import com.algotrading.backtesting.indicatorcalculator.EMA;

public class EMATest {

	@Test
	public void test001_EMA() throws Exception {
		// Test case setting begins
		Calendar loopdate = new GregorianCalendar(2016, 1, 15);
		int map_length = 20;
		Date recent = loopdate.getTime();
		double expected_value = 5; // expected value of recent date
		int magnitude = 5;
		int sma_magnitude = 5;

		Double[] series = new Double[] { 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0,
				17.0, 18.0, 19.0, 20.0, 21.0, 22.0 }; // the series
		Map<Date, Double> datedprice = new TreeMap<Date, Double>();
		for (int i = 0; i < map_length; i++) {
			datedprice.put(loopdate.getTime(), series[i]);
			loopdate.add(Calendar.DATE, -1);
		}
		EMA ema = new EMA(datedprice, recent, magnitude, sma_magnitude);
		// Test case setting ends

		// print the resulting line from ema
		System.out.println("Result");
		Map<Date, Double> result_line = ema.getLine();
		for (Map.Entry<Date, Double> entry : result_line.entrySet()) {
			System.out.println(entry.getKey().toString() + "/" + entry.getValue());
		}

		// print the expecting line and compare
		System.out.println("Expected");
		Map<Date, Double> expected_line = new TreeMap<Date, Double>();
		loopdate = new GregorianCalendar(2016, 1, 15);

		// the expecting line
		Double[] ema_points = new Double[] { 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0 };
		for (int i = 0; i < map_length - magnitude - sma_magnitude + 2; i++) {
			expected_line.put(loopdate.getTime(), ema_points[i]);
			System.out.println(loopdate.getTime().toString() + "/" + expected_line.get(loopdate.getTime()));
			// comparing each element
			assertEquals(result_line.get(loopdate.getTime()), ema_points[i], 0.0001);
			loopdate.add(Calendar.DATE, -1);

		}

		assertEquals(ema.getValue(), expected_value, 0.0001);
	}

	@Test
	public void test002_EMA() throws Exception {
		// Test case setting begins
		Calendar loopdate = new GregorianCalendar(2016, 1, 15);
		int map_length = 20;
		Date recent = loopdate.getTime();
		double expected_value = 4.5; // expected value of recent date
		int magnitude = 4;
		int sma_magnitude = 4;

		Double[] series = new Double[] { 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0,
				17.0, 18.0, 19.0, 20.0, 21.0, 22.0 }; // the series
		Map<Date, Double> datedprice = new TreeMap<Date, Double>();
		for (int i = 0; i < map_length; i++) {
			datedprice.put(loopdate.getTime(), series[i]);
			loopdate.add(Calendar.DATE, -1);
		}

		EMA ema = new EMA(datedprice, recent, magnitude, sma_magnitude);
		// Test case setting ends

		// print the resulting line from ema
		System.out.println("Result");
		Map<Date, Double> result_line = ema.getLine();
		for (Map.Entry<Date, Double> entry : result_line.entrySet()) {
			System.out.println(entry.getKey().toString() + "/" + entry.getValue());
		}

		// print the expecting line and compare
		System.out.println("Expected");
		Map<Date, Double> expected_line = new TreeMap<Date, Double>();
		loopdate = new GregorianCalendar(2016, 1, 15);

		// the expecting line
		Double[] ema_points = new Double[] { 4.5, 5.5, 6.5, 7.5, 8.5, 9.5, 10.5, 11.5, 12.5, 13.5, 14.5, 15.5, 16.5,
				17.5 };
		for (int i = 0; i < map_length - magnitude - sma_magnitude + 2; i++) {
			expected_line.put(loopdate.getTime(), ema_points[i]);
			System.out.println(loopdate.getTime().toString() + "/" + expected_line.get(loopdate.getTime()));
			// comparing each element
			assertEquals(result_line.get(loopdate.getTime()), ema_points[i], 0.0001);
			loopdate.add(Calendar.DATE, -1);

		}

		assertEquals(ema.getValue(), expected_value, 0.0001);
	}

}
