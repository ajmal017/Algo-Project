package com.algotrading.backtesting.indicatorcalculator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Collections;


public class SMA implements ISmaCalculator{

	// Constructors
	public SMA(Map<Date, Double> datedprice, Date recent, int magnitude) throws Exception{
		this.datedprice = datedprice; // the stock
		this.recent = recent; // the most recent day
		this.magnitude = magnitude; // m-day averages
		if (!readytocal(this.datedprice, this.magnitude)){
			throw new Exception("SMA Instantiation failed!");
		}
		this.line = this.calculate(datedprice, magnitude);
		if (!this.line.containsKey(recent)){  //  check if recent is a date in line
			 Map.Entry<Date, Double> entry = this.line.entrySet().iterator().next(); // if not, use the most recent date available
			 this.recent = entry.getKey();
			 this.value = entry.getValue();
		}else{
			this.value = this.line.get(this.recent);
		}
	}

	
    // Variables	
	private Map<Date, Double> datedprice; // the stock under inspection
	private Date recent; // the recent date of the indicator
	private int magnitude; // E.g. 50 points of a 10-day MA. Then magnitude = 10, linelen = 50
	private double value; // the SMA of the recent date 
	private Map<Date, Double> line; // the SMA line

    // setting functions	
	public void setRecent(Date recent){
		if (this.datedprice.containsKey(recent)){
			this.recent = recent;
			this.value = this.line.get(recent);
		}else{
			System.out.println("No such date in record");
		}
	}

	// getting functions

	public Date getRecent(){
        return this.recent;
	}
	
	public int getMagnitude(){
		return this.magnitude;
	}

	public double getValue(){
		return this.value;
	}

	public Map<Date, Double> getLine(){
		return this.line;
	}
	
	// main functions
	
    private boolean readytocal(Map <Date, Double> datedprice, int magnitude){ // check whether stock is sufficient for calculation
		if (datedprice == null || magnitude < 1){
			System.out.println("Initialization of variables not completed yet! Cannot proceed!");
			return false;    	
		}

		if (datedprice.size() < magnitude) { // check if the data is sufficient; 
			System.out.println("The length of line is out of range!");
			return false;
		}
		return true;
    }
	
	@Override
	public Map<Date, Double> calculate(Map<Date, Double> datedprice, int magnitude){
		if (!readytocal(datedprice, magnitude)){
			return Collections.<Date, Double>emptyMap();
		}
		List<Date> dates = new ArrayList<Date>();
		for (Map.Entry<Date, Double> entry : datedprice.entrySet()){
			dates.add(entry.getKey());
		}		
		Map<Date, Double> line = new TreeMap<Date, Double>();
		double value = 0;
		for (int i = 0; i < magnitude; i++){
			value += datedprice.get(dates.get(i));
		}
		value = value/magnitude;
		line.put(dates.get(0), value);
		int pointer = 0;
		for (int i = magnitude; i < dates.size(); i++){
			value = value - datedprice.get(dates.get(pointer))/magnitude + datedprice.get(i)/magnitude;
			pointer += 1;
			line.put(dates.get(pointer), value);
		}

		return line;
		
	}

}
