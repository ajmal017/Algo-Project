package com.algotrading.backtesting.indicatorcalculator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Collections;
import java.text.SimpleDateFormat;

public class EMA implements IEmaCalculator{
	// Constructors
	public EMA(Map<Date, Double> datedprice, Date recent, int magnitude, int sma_magnitude) throws Exception{
		this.datedprice = datedprice; // Map with dates as keys and prices as values  
		this.recent = recent; // the most recent day
		this.magnitude = magnitude; // magnitude-day averages
		this.sma_magnitude = sma_magnitude; // using as the magnitude of the SMA for boundary
		if (!readytocal(this.datedprice, this.magnitude, this.sma_magnitude)){
			throw new Exception("SMA Instantiation failed!");
		}
		this.sma = new SMA(datedprice, recent, sma_magnitude);
		this.boundary = this.sma.getLine();
		setAutoAlpha();
		this.line = calculate(this.datedprice, this.magnitude, this.sma_magnitude, this.alpha, this.boundary);
		if (!this.line.containsKey(recent)){  //  check if recent is a date in line
			 Map.Entry<Date, Double> entry = this.line.entrySet().iterator().next(); // if not, use the most recent date available
			 this.recent = entry.getKey();
			 this.value = entry.getValue();
		}else{
			this.value = this.line.get(this.recent);
		}
	}

	
    // Variables	
	private Map<Date, Double> datedprice; //  Map with dates as keys and prices as values
	private Date recent; // the recent date of the indicator
	private int magnitude, sma_magnitude; //magnitude-day averages; sma_magnitude using as the magnitude of the SMA for boundary
	private double value; // the EMA of the recent date 
	private Map<Date, Double> line; // the EMA line, with the last element the last SMA
	private double alpha; // the alpha used in the calculation
	private SMA sma; // SMA object created for boundaries; correspond to M_N in our definition of EMA
	private Map<Date, Double> boundary; // leaving a flexibility that users can set another boundary

    // setting functions	
	public void setRecent(Date recent){
		if (this.datedprice.containsKey(recent)){
			this.recent = recent;
			this.value = this.line.get(recent);
		}else{
			System.out.println("No such date in record");
		}
	}

	public void setAlpha(double alpha){ // do the calculation only if setting a different alpha
		if (this.alpha != alpha){
			this.alpha = alpha;
			this.line = calculate(this.datedprice, this.magnitude, this.sma_magnitude, this.alpha, this.boundary);
			this.value = this.line.get(recent);
		}		
	}

	public void setAutoAlpha(){ // do the calculation only if setting a different alpha
		if (this.alpha == getAutoAlpha()){
			this.alpha = getAutoAlpha();
			this.line = calculate(this.datedprice, this.magnitude, this.sma_magnitude, this.alpha, this.boundary);
			this.value = this.line.get(recent);
		}		
	}
	
	public void setBoundary(Map<Date, Double> boundary){
		this.boundary = boundary;
		this.line = calculate(this.datedprice, this.magnitude, this.sma_magnitude, this.alpha, this.boundary);
		this.value = this.line.get(recent);
	}
	
	public void setAutoBoundary(){
		this.boundary = this.sma.getLine();
		this.line = calculate(this.datedprice, this.magnitude, this.sma_magnitude, this.alpha, this.boundary);
		this.value = this.line.get(recent);
	}	
	// getting functions

	public Date getRecent(){
        return this.recent;
	}
	
	public int getMagnitude(){
		return this.magnitude;
	}

	public int getSMA_Magnitude(){
		return this.sma_magnitude;
	}	
	
	public double getValue(){
		return this.value;
	}

	
	public double getAlpha(){
		return this.alpha;
	}	
	
	public Map<Date, Double> getLine(){
		return this.line;
	}

	public SMA getSMA(){
		return this.sma;
	}
	
	public double getAutoAlpha(){ // the default formula of alpha 
		return 2.0/this.magnitude;
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
    
    @Override
    public Map<Date, Double> calculate(Map<Date, Double> datedprice, int magnitude, int tail_magnitude, double alpha, Map<Date, Double> boundary){
		if (!readytocal(datedprice, magnitude, tail_magnitude)){
			return Collections.<Date, Double>emptyMap();
		}
		List<Date> dates = new ArrayList<Date>();
		int pointer = 0;
		for (Map.Entry<Date, Double> entry : datedprice.entrySet()){
			dates.add(entry.getKey());
			if (pointer >= magnitude - 1 && pointer < datedprice.size() - tail_magnitude){
				if (boundary.get(entry.getKey())== null){
					SimpleDateFormat dateformat  = new SimpleDateFormat("yyyy-mm-dd");
					System.out.println("Insufficient boundary: " + dateformat.format(entry.getKey()) + " is missing! " );
					return Collections.<Date, Double>emptyMap();
				}
			pointer ++;
			}
		}
		Map<Date, Double> line = new TreeMap<Date, Double>();
		double value = 0;
		for (int i = 0; i < magnitude - 1; i++){
			value += alpha * Math.pow(1 - alpha, i) * datedprice.get(dates.get(i));
		}
		double coefficient = alpha * Math.pow(1 - alpha,  magnitude - 1);
		value += alpha * coefficient * boundary.get(dates.get(magnitude - 1));
		line.put(dates.get(0), value);
		pointer = 0;
		
		for (int i = magnitude; i < dates.size() - tail_magnitude; i++){
			value = (value - alpha * datedprice.get(dates.get(pointer)) - coefficient * boundary.get(dates.get(i - 1)))/ ( 1 - alpha) + coefficient/(1 - alpha) * datedprice.get(dates.get(i - 1)) + coefficient * boundary.get(dates.get(i)) ;
			pointer += 1;
			line.put(dates.get(pointer), value);
		}
		return line;		
		}    	
    
    
    }
    
    	