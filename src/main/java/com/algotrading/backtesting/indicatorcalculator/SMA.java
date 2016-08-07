package com.algotrading.backtesting.indicatorcalculator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.algotrading.backtesting.stock.Stock;

public class SMA implements ISmaCalculator {

	// Constructors
	public SMA(Stock s, Date d, int m, int numRecentSmaStored){
		this.stock = s; // the stock
		this.recent = d; // the most recent day
		this.magnitude = m; // m-day averages
		this.linelen = numRecentSmaStored; // the length of the moving averages
		this.update();
	}

	
    // Variables	
	private Stock stock; // the stock under inspection
	private Date recent; // the recent date of the indicator
	private int magnitude, linelen; // E.g. 50 points of a 10-day MA. Then magnitude = 10, linelen = 50
	private BigDecimal recentSmaValue; // the SMA of the recent date 
	private List<BigDecimal> line; // the SMA line
	private boolean updatestatus = false;

    // setting functions	
	public void setStock(Stock stock){
		this.stock = stock;
		updatestatus = false;
	}

	public void setRecent(Date date){
		if (recent != date){
			this.recent = date;
			updatestatus = false;
		}
	}
	
	public void setMagnitude(int magnitude){
		if (this.magnitude != magnitude){
			this.magnitude = magnitude;
			updatestatus = false;
		}
	}

	public void setLinelen(int linelen){
		if (this.linelen != linelen){
			this.linelen = linelen;
			updatestatus = false;
		}
	}	

	// getting functions
	public Stock getStock(){
		return this.stock;
	}

	public Date getRecent(){
        return this.recent;

	}
	
	public int getMagnitude(){
		return this.magnitude;
	}

	public int getLinelen(){
		return this.linelen;
	}	

	// main functions
	
    private boolean readyToCal(Date date, int magnitude, int linelen){ // check whether stock is sufficient for calculation
		if (this.stock == null || date == null || magnitude < 1 || linelen < 1 ){
			System.out.println("Initialization of variables not completed yet! Cannot proceed!");
			return false;    	
		}
		if (!this.stock.checkDataExistence(date)){ // if the data on that date does not exist, shift the closest earlier day
			date = this.stock.shiftDate(date, -1);
		}
		if (date == null){
		    System.out.println("Data insufficient!"); // if that day does not exists this means data is not sufficient
			return false;
		}
		if (this.stock.shiftDate(date, -magnitude - linelen + 2) == null){ // check if the data is sufficient; for the reason of +2, think of a 10-day MA with l=1
			System.out.println("The length of line is out of range!");
			return false;
		}
		return true;
    }
	
	public void forward(){ // forward one day, add the latest value of SMA to line, like it is moving forward
		if (! updatestatus){ // has to initiate line first by using update before using forward
			System.out.println("Need to update first before moving forward.");
			return;
		}
		Date date = stock.shiftDate(recent, 1);
			if (date == null) {
				System.out.println("Stock does not contain data of a forward date!");
				return;
			}
		recent = date;
		BigDecimal divisor = new BigDecimal(magnitude);
		BigDecimal head = stock.adjClose(recent);
		BigDecimal tail = stock.adjClose(stock.shiftDate(recent, -magnitude));
		head = head.divide(divisor);
		tail = tail.divide(divisor);
		recentSmaValue = recentSmaValue.add(head.subtract(tail));
		line.add(0, recentSmaValue); // add the value to the head of line
		linelen = linelen + 1;
	}
	
	public void update(){ // update line and value
		if (!readyToCal(recent, magnitude, linelen)){
			return;
		}
		List<BigDecimal> points = new ArrayList<BigDecimal>();
		points = stock.getAdj_Closed_List(recent, stock.shiftDate(recent, -magnitude - linelen + 2));
		line = calculate(points, magnitude);
		if (!(line == null)){
			recentSmaValue = line.get(0);
			updatestatus = true;
		}
	}

/*
	public List<BigDecimal> calculate(Date date, int magnitude, int linelen) { // a function called by update() and can be accessed from outside
		if (! stock.checkDataexistence(date)){
			date = stock.shiftDate(date, -1);
		}
		
		if (!readytocal(date, magnitude, linelen)){
			return new ArrayList<BigDecimal>();
		}
		
		List<BigDecimal> SMALine = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		BigDecimal divisor = new BigDecimal(magnitude);
		Date loopdate = date;
		for(int i = 0; i < magnitude; i++){
			sum.add(stock.adjClose(loopdate));
			loopdate = stock.shiftDate(loopdate, -1);
		}
		sum.divide(divisor);
		SMALine.add(sum); // getting the head of the SMA
		BigDecimal head, tail;
		//   moving the line backward, it is moving from \sum_{i=0}^{m-1} x_i/m to \sum_{i=1}^{m} x_i/m.
		//   and the difference is just -x_0/m + x_m/m
		//   the following is implementing this idea
		
		for(int i = 0; i < linelen - 1; i++){
			head = stock.adjClose(date);
			tail = stock.adjClose(stock.shiftDate(date, -magnitude-1));
			head.divide(divisor);
			tail.divide(divisor);
			sum.subtract(head);
			sum.add(tail);
			SMALine.add(sum);
			date = stock.shiftDate(date, -1);
		}
		
		// TODO Auto-generated method stub
		return SMALine;
	}
*/	
	public Map<Date, BigDecimal> calculate(Map<Date, BigDecimal> map, int magnitude) {
		List<BigDecimal> prices = map.values().toList();
		List<BigDecimal> smas = calculate(prices, magnitude);
		// then put the sma list to map
	}
	
	@Override
	public List<BigDecimal> calculate(List<BigDecimal> points, int magnitude){		
		if (points.size() < magnitude - 1){
			System.out.println("Data insufficient!");
			return null;
		}
		List<BigDecimal> SMALine = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		BigDecimal divisor = new BigDecimal(magnitude);
		
		for (int i = 0; i < magnitude; i++){
			sum = sum.add(points.get(i));
		}
		sum = sum.divide(divisor);
		SMALine.add(sum);
		BigDecimal head, tail;
		/* moving the line backward, it is moving from \sum_{i=0}^{m-1} x_i/m to \sum_{i=1}^{m} x_i/m.
		   and the difference is just -x_0/m + x_m/m
		   the following is implementing this idea
		*/
		for(int i = 0; i < points.size() - magnitude + 1; i++){
			head = points.get(i);
			tail = points.get( i + magnitude);
			head = head.divide(divisor);
			head = tail.divide(divisor);
			sum = sum.subtract(head);
			sum = sum.add(tail);
			SMALine.add(sum);
		}
		return SMALine;
	}

}
