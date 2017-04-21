package com.algotrading.backtesting.indicatorcalculator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class EMA implements IEmaCalculator {

	/** Map with dates as keys and prices as values */
	private Map<Date, Double> datedPrice;

	/** the recent date of the indicator */
	private Date recent;

	/** magnitude-day averages; */
	private int magnitude;

	/** sma_magnitude using as the magnitude of the SMA for boundary */
	private int smaMagnitude;

	/** the EMA of the recent date */
	private double value;

	/** the EMA line, with the last element the last SMA */
	private Map<Date, Double> line;

	/** the alpha used in the calculation */
	private double alpha;

	/**
	 * SMA object created for boundaries; correspond to M_N in our definition of
	 * EMA
	 */
	private SMA sma;

	/** leaving a flexibility that users can set another boundary */
	private Map<Date, Double> boundary;

	public EMA(Map<Date, Double> datedprice, Date recent, int magnitude, int sma_magnitude) throws Exception {

		// Map with dates as keys and prices as values
		this.datedPrice = datedprice;

		// the most recent day
		this.recent = recent;

		// magnitude-day averages
		this.magnitude = magnitude;

		// using as the magnitude of the SMA for boundary
		this.smaMagnitude = sma_magnitude;

		if (!readyToCalculate(this.datedPrice, this.magnitude, this.smaMagnitude)) {
			throw new Exception("SMA Instantiation failed!");
		}
		this.sma = new SMA(datedprice, recent, sma_magnitude);
		this.boundary = this.sma.getLine();
		setAutoAlpha();
		// this.line = calculate(this.datedprice, this.magnitude,
		// this.sma_magnitude, this.alpha, this.boundary);

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
		if (this.datedPrice.containsKey(recent)) {
			this.recent = recent;
			this.value = this.line.get(recent);
		} else {
			System.out.println("No such date in record");
		}
	}

	/**
	 * do the calculation only if setting a different alpha
	 */
	public void setAlpha(double alpha) {
		if (this.alpha != alpha) {
			this.alpha = alpha;
			this.line = calculate(this.datedPrice, this.magnitude, this.smaMagnitude, this.alpha, this.boundary);
			this.value = this.line.get(recent);
		}
	}

	/**
	 * do the calculation only if setting a different alpha
	 */
	public void setAutoAlpha() {
		if (this.alpha != getAutoAlpha()) {
			this.alpha = getAutoAlpha();
			this.line = calculate(this.datedPrice, this.magnitude, this.smaMagnitude, this.alpha, this.boundary);
			this.value = this.line.get(recent);
		}
	}

	public void setBoundary(Map<Date, Double> boundary) {
		this.boundary = boundary;
		this.line = calculate(this.datedPrice, this.magnitude, this.smaMagnitude, this.alpha, this.boundary);
		this.value = this.line.get(recent);
	}

	public void setAutoBoundary() {
		this.boundary = this.sma.getLine();
		this.line = calculate(this.datedPrice, this.magnitude, this.smaMagnitude, this.alpha, this.boundary);
		this.value = this.line.get(recent);
	}

	public Date getRecent() {
		return this.recent;
	}

	public int getMagnitude() {
		return this.magnitude;
	}

	public int getSMA_Magnitude() {
		return this.smaMagnitude;
	}

	public double getValue() {
		return this.value;
	}

	public double getAlpha() {
		return this.alpha;
	}

	public Map<Date, Double> getLine() {
		return this.line;
	}

	public SMA getSMA() {
		return this.sma;
	}

	/** the default formula of alpha */
	public double getAutoAlpha() {
		return 2.0 / (this.magnitude + 1);
	}

	/** check whether stock is sufficient for calculation */
	private boolean readyToCalculate(Map<Date, Double> datedprice, int magnitude, int tail_magnitude) {
		if (datedprice == null || magnitude < 1) {
			System.out.println("Initialization of variables not completed yet! Cannot proceed!");
			return false;
		}

		// check of the data is sufficient
		if (datedprice.size() < magnitude + tail_magnitude - 1) {
			System.out.println("The length of line is out of range!");
			return false;
		}
		return true;
	}

	@Override
	public Map<Date, Double> calculate(Map<Date, Double> datedprice, int magnitude, int tail_magnitude, double alpha,
			Map<Date, Double> boundary) {
		if (!readyToCalculate(datedprice, magnitude, tail_magnitude)) {
			return Collections.<Date, Double> emptyMap();
		}
		List<Date> dates = new ArrayList<Date>();
		int pointer = 0;
		System.out.println("Initialization of EMA");
		for (Map.Entry<Date, Double> entry : datedprice.entrySet()) {
			dates.add(entry.getKey());
			// System.out.println(entry.getKey().toString() + "/" +
			// entry.getValue());
			System.out.println(entry.getKey()
					.toString() + "/" + boundary.get(entry.getKey()));
			if (pointer >= magnitude - 1 && pointer < datedprice.size() - tail_magnitude) {
				if (boundary.get(entry.getKey()) == null) {
					SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-mm-dd");
					System.out.println("Insufficient boundary: " + dateformat.format(entry.getKey()) + " is missing! ");
					return Collections.<Date, Double> emptyMap();
				}
				pointer++;
			}
		}
		Map<Date, Double> line = new TreeMap<>();
		double value = 0;
		System.out.println("alpha = " + alpha);
		// for (int i = 0; i < magnitude - 1; i++) {
		for (int i = dates.size() - 1; i >= dates.size() - magnitude + 1; i--) {
			value += alpha * Math.pow(1 - alpha, dates.size() - 1 - i) * datedprice.get(dates.get(i));
		}
		// double coefficient = alpha * Math.pow(1 - alpha, magnitude - 1);
		double coefficient = Math.pow(1 - alpha, magnitude - 1);
		value += coefficient * boundary.get(dates.get(dates.size() - magnitude));
		line.put(dates.get(dates.size() - 1), value);
		pointer = dates.size() - 1;

		// for (int i = magnitude; i < dates.size() - tail_magnitude; i++) {
		for (int i = dates.size() - magnitude - 1; i >= tail_magnitude - 1; i--) {
			value = (value - alpha * datedprice.get(dates.get(pointer)) - coefficient * boundary.get(dates.get(i + 1)))
					/ (1 - alpha) + (alpha * coefficient / (1 - alpha)) * datedprice.get(dates.get(i + 1))
					+ coefficient * boundary.get(dates.get(i));
			pointer -= 1;
			line.put(dates.get(pointer), value);
		}
		return line;
	}

}
