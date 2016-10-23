package com.algotrading.backtesting.indicatorcalculator;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class RSI implements IRsiCalculator{

	// Constructors
	public RSI(Map<Date, Double> datedprice, Date recent, int magnitude, int sma_magnitude) throws Exception{
		this.datedprice = datedprice; // the stock
		this.recent = recent; // the most recent day
		this.magnitude = magnitude; // equivalent to N in RSI.pdf
		this.sma_magnitude = sma_magnitude; // equivalent to a in RSI.pdf

		if (!readytocal(this.datedprice, this.magnitude, this.sma_magnitude)){
			throw new Exception("SMA Instantiation failed!");
		}
		
		// extracting the positive and negative increment
		List<Map<Date, Double>> plus_minus = differentiate(datedprice); // get the positive and negative increment in a list
		this.plus = plus_minus.get(0); // get the positive part
		this.minus = plus_minus.get(1); // get the negative part
		
		this.ema_plus = new EMA(this.plus, recent, magnitude, sma_magnitude); // create EMA by the positive part
		this.ema_minus = new EMA(this.minus, recent, magnitude, sma_magnitude); // create EMA by the negative part
		this.line = calLine(this.ema_plus.getLine(), this.ema_minus.getLine()); // get the RSI
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
	private int magnitude, sma_magnitude; // E.g. 50 points of a 10-day MA. Then magnitude = 10, linelen = 50
	private double value; // the EMA of the recent date 
	private Map<Date, Double> line; // the EMA line, with the last element the last SMA
	private Map<Date, Double> plus, minus; // the positive and negative increment of dated price
	private double alpha;
	private EMA ema_plus, ema_minus;
	
    // setting functions	
	public void setRecent(Date recent){
		if (this.line.containsKey(recent)){
			this.recent = recent;
			this.value = this.line.get(recent);
		}else{
			System.out.println("No such date in record");
		}
	}

	public void setAlpha(double alpha){ // setting the alpha used in those EMA's
		if (this.alpha != alpha){
			this.alpha = alpha;
			this.ema_plus.setAlpha(alpha);
			this.ema_minus.setAlpha(alpha);
			this.line = calLine(this.ema_plus.getLine(), this.ema_minus.getLine());
			this.value = this.line.get(this.recent);
		}		
	}	
	
	public void setAutoAlpha(){ // setting the default alpha used in those EMA's ( 2 / N)
		if (this.alpha != this.ema_plus.getAutoAlpha()){
			this.alpha = this.ema_plus.getAutoAlpha();
			this.ema_plus.setAlpha(alpha);
			this.ema_minus.setAlpha(alpha);
			this.line = calLine(this.ema_plus.getLine(), this.ema_minus.getLine());
			this.value = this.line.get(this.recent);
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

	public double getAlpha(){
		return this.alpha;
	}

	// main functions
    private boolean readytocal(Map <Date, Double> datedprice, int magnitude, int tail_magnitude){ // check whether stock is sufficient for calculation
		if (datedprice == null || magnitude < 1){
			System.out.println("Initialization of variables not completed yet! Cannot proceed!");
			return false;    	
		}

		if (datedprice.size() < magnitude + tail_magnitude - 1) { // check if the data is sufficient; 
			System.out.println("The length of line is out of range!");
			return false;
		}
		return true;
    }
    
    public List<Map<Date, Double>> differentiate(Map<Date, Double> datedprice){ // the function which extracts the positive and negative part
    	Map<Date, Double> plus = new TreeMap<Date, Double>();
    	Map<Date, Double> minus = new TreeMap<Date, Double>();
		List<Date> dates = new ArrayList<Date>();
		for (Map.Entry<Date, Double> entry : datedprice.entrySet()){
			dates.add(entry.getKey());
		}
		double difference;
		for (int i = 0; i < dates.size() - 2; i++){
			difference = datedprice.get(dates.get(i)) - datedprice.get(dates.get(i+1));
			if (difference >= 0){
				plus.put(dates.get(i), difference);
				minus.put(dates.get(i), 0.0);
			}else{
				minus.put(dates.get(i), difference);
				plus.put(dates.get(i), 0.0);				
			}
		}
		List<Map<Date, Double>> plus_minus = new ArrayList<Map<Date, Double>>();
		plus_minus.add(plus);
		plus_minus.add(minus);
    	return plus_minus;
    }
 
	
	private Map<Date, Double> calLine(Map<Date, Double> plus, Map<Date, Double> minus){ // get the line with the positive and negative increment
		Map<Date, Double> line = new TreeMap<Date, Double>();
		Date date;
		for (Map.Entry<Date, Double> entry : plus.entrySet()){
			date = entry.getKey();
			line.put(date, plus.get(date) / minus.get(date));
		}		
		return line;
	}
	
	@Override
	public Map<Date, Double> calculate(Map<Date, Double> datedprice, int magnitude, int sma_magnitude) throws Exception{ // get RSI, open to outside calls
		Map<Date, Double> line = new TreeMap<Date, Double>();
		
		if (!readytocal(datedprice, magnitude, sma_magnitude)){
			return Collections.<Date, Double>emptyMap();
		}
		Map.Entry<Date, Double> entry = this.line.entrySet().iterator().next();
		Date recent = entry.getKey();
		List<Map<Date, Double>> plus_minus = differentiate(datedprice);
		Map<Date, Double> plus = plus_minus.get(0);
		Map<Date, Double> minus = plus_minus.get(1);
		EMA ema_plus = new EMA(plus, recent, magnitude, sma_magnitude);
		EMA ema_minus = new EMA(minus, recent, magnitude, sma_magnitude);
		line = calLine(ema_plus.getLine(), ema_minus.getLine());
		return line;
	}
}
