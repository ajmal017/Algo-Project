package com.algotrading.backtesting.indicatorcalculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SMA implements ISmaCalculator {

	/** the stock under inspection */
	private Map<Date, Double> datedprice;

	/** the recent date of the indicator */
	private Date recent;

	/** E.g. 50 points of a 10-day MA. Then magnitude = 10, linelen = 50 */
	private int magnitude;

	/** the SMA of the recent date */
	private double value;

	/** the SMA line */
	private Map<Date, Double> line;

	public SMA(Map<Date, Double> datedprice, Date recent, int magnitude) throws Exception {
		// the stock
		this.datedprice = datedprice;

		// the most recent day
		this.recent = recent;

		// m-day averages
		this.magnitude = magnitude;

		if (!readyToCalulate(this.datedprice, this.magnitude)) {
			throw new Exception("SMA Instantiation failed!");
		}
		this.line = this.calculate(datedprice, magnitude);

		// check if recent is a date in line
		if (!this.line.containsKey(recent)) {
			// if not, use the most recent date available
			Map.Entry<Date, Double> entry = this.line.entrySet()
					.iterator()
					.next();
			this.recent = entry.getKey();
			this.value = entry.getValue();
		} else {
			this.value = this.line.get(this.recent);
		}
	}

	public void setRecent(Date recent) {
		if (this.datedprice.containsKey(recent)) {
			this.recent = recent;
			this.value = this.line.get(recent);
		} else {
			System.out.println("No such date in record");
		}
	}

	public Date getRecent() {
		return this.recent;
	}

	public int getMagnitude() {
		return this.magnitude;
	}

	public double getValue() {
		return this.value;
	}

	public Map<Date, Double> getLine() {
		return this.line;
	}

	/** check whether stock is sufficient for calculation */
	private boolean readyToCalulate(Map<Date, Double> datedPrice, int magnitude) {
		if (datedPrice == null || magnitude < 1) {
			System.out.println("Initialization of variables not completed yet! Cannot proceed!");
			return false;
		}

		// check if the date is sufficient
		if (datedPrice.size() < magnitude) {
			System.out.println("The length of line is out of range!");
			return false;
		}
		return true;
	}

	@Override
	public Map<Date, Double> calculate(Map<Date, Double> datedPrice, int magnitude) {
		if (!readyToCalulate(datedPrice, magnitude)) {
			return Collections.<Date, Double> emptyMap();
		}
		List<Date> dates = new ArrayList<>();
		// System.out.println("Initialization");
		for (Map.Entry<Date, Double> entry : datedPrice.entrySet()) {
			dates.add(entry.getKey());
			// System.out.println(entry.getKey().toString() + "/" +
			// entry.getValue());
		}
		Map<Date, Double> line = new TreeMap<Date, Double>();
		double value = 0;
		// for (int i = 0; i < magnitude; i++) {
		for (int i = dates.size() - 1; i >= dates.size() - magnitude; i--) {
			value += datedPrice.get(dates.get(i));
			// System.out.println(dates.get(i).toString());
		}
		value = value / magnitude;
		line.put(dates.get(dates.size() - 1), value);
		int pointer = dates.size() - 1;
		// System.out.println("Calculating");
		// System.out.println(dates.get(pointer) + "/" + value);
		// for (int i = magnitude; i < dates.size(); i++) {
		for (int i = dates.size() - magnitude - 1; i >= 0; i--) {
			value = value - datedPrice.get(dates.get(pointer)) / magnitude + datedPrice.get(dates.get(i)) / magnitude;
			pointer -= 1;
			line.put(dates.get(pointer), value);
			// System.out.println(dates.get(pointer) + "/" + value);
		}
		return line;
	}

}
