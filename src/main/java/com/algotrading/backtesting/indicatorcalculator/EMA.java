package com.algotrading.backtesting.indicatorcalculator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.algotrading.backtesting.stock.Stock;

public class EMA implements IEmaCalculator{
	// Constructors
	public EMA(Stock s, Date d, int m, int l){
		this.stock = s; // the stock
		this.recent = d; // the most recent day
		this.magnitude = m; // m-day averages
		this.linelen = l; // the length of the moving averages
		this.update();
	}

	
    // Variables	
	private Stock stock; // the stock under inspection
	private Date recent; // the recent date of the indicator
	private int magnitude, linelen; // E.g. 50 points of a 10-day MA. Then magnitude = 10, linelen = 50
	private BigDecimal value; // the EMA of the recent date 
	private List<BigDecimal> line; // the EMA line, with the last element the last SMA
	private int SMA_magnitude = magnitude;
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

	public void setSMA_magnitude(int SMA_magnitude){
		if (this.SMA_magnitude != SMA_magnitude){
			this.SMA_magnitude = SMA_magnitude;
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
	
	public int getSMA_magnitude(){
		return this.SMA_magnitude;
	}
	
	private BigDecimal getAlpha(int magnitude){
		return new BigDecimal(2/(magnitude + 2));
	}

	// main functions
	
    private boolean readytocal(Date date, int magnitude, int linelen, int SMA_magnitude){ // check whether stock is sufficient for calculation
		if (this.stock == null || date == null || magnitude < 1 || linelen < 1 || SMA_magnitude < 1 ){
			System.out.println("Initialization of variables not completed yet! Cannot proceed!");
			return false;    	
		}
		if (! this.stock.checkDataexistence(date)){ // if the data on that date does not exist, shift the closest earlier day
			date = this.stock.shiftDate(date, -1);
		}
		if (date == null){
		    System.out.println("Data insufficient!"); // if that day does not exists this means data is not sufficient
			return false;
		}
		if (this.stock.shiftDate(date, -magnitude - linelen - SMA_magnitude + 3) == null){
			// check if the data is sufficient; for the reason of +3, think of a 10-day EMA with linelen =1 and SMA_magnitude = 5 
			System.out.println("The length of line is out of range!");
			return false;
		}
		return true;
    }
	
	public void forward(){ // forward one day, add the latest value of EMA to line, like it is moving forward
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
		BigDecimal divisor = new BigDecimal(SMA_magnitude);
		BigDecimal alpha = getAlpha(magnitude);
		// 1 - alpha
		BigDecimal one_minus_alpha = new BigDecimal(1).subtract(alpha);
		
		// alpha * (1 - alpha)^( magnitude - 1)
		BigDecimal constant = one_minus_alpha.pow(magnitude - 1);
		constant = constant.multiply(alpha);
		
		BigDecimal EMA_head = stock.adjClose(recent);
		BigDecimal EMA_tail = stock.adjClose(stock.shiftDate(recent, -magnitude + 1));
		BigDecimal SMA_tail = stock.adjClose(stock.shiftDate(recent, -magnitude - SMA_magnitude + 2));
		BigDecimal old_SMA = line.get(line.size() - 1);
		BigDecimal new_SMA = (old_SMA.subtract(SMA_tail.divide(divisor)).add(EMA_tail.divide(divisor)));
		line.set(line.size() - 1, new_SMA);
		value = line.get(0).subtract(constant.multiply(old_SMA));
		value = value.subtract((constant.multiply(EMA_tail)).divide(one_minus_alpha));
		value = value.add((constant.multiply(new_SMA)).divide(one_minus_alpha));
		value = value.multiply(one_minus_alpha);
		value = value.add(EMA_head.multiply(alpha));
		line.add(0, value); // add the value to the head of line
		linelen = linelen + 1;
	}
	
	public void update(){ // update line and value
		if (!readytocal(recent, magnitude, linelen, SMA_magnitude)){
			return;
		}
		List<BigDecimal> points = new ArrayList<BigDecimal>();
		BigDecimal alpha = getAlpha(magnitude);
		points = stock.getAdj_Closed_List(recent, stock.shiftDate(recent, -magnitude - linelen - SMA_magnitude + 3));
		line = calculate(points, magnitude, SMA_magnitude, alpha);
		
		if (!(line == null)){
			value = line.get(0);
			updatestatus = true;
		}
	}

	@Override
	public static List<BigDecimal> calculate(List<BigDecimal> points, int magnitude, int SMA_magnitude, BigDecimal alpha){
		
		if (points.size() < magnitude + SMA_magnitude - 2){
			System.out.println("Data insufficient!");
			return null;
		}

		List<BigDecimal> EMALine = new ArrayList<BigDecimal>();
		BigDecimal sum = new BigDecimal(0);
		BigDecimal SMA = new BigDecimal(0);
		BigDecimal divisor = new BigDecimal(magnitude);
		List<BigDecimal> EMA_SMA = new ArrayList<BigDecimal>();
		EMA_SMA = EMA_recurrence(points, magnitude, SMA_magnitude, alpha);
		sum = EMA_SMA.get(0);
		SMA = EMA_SMA.get(1);
		BigDecimal old_SMA;
		EMALine.add(sum);
		
		// 1 - alpha
		BigDecimal one_minus_alpha = new BigDecimal(1).subtract(alpha);
		
		// alpha * (1 - alpha)^( magnitude - 1)
		BigDecimal constant = one_minus_alpha.pow(magnitude - 1);
		constant = constant.multiply(alpha);
		
		BigDecimal head, tail;
		/* moving the line backward, it is moving from \sum_{i=0}^{m-1} x_i/m to \sum_{i=1}^{m} x_i/m.
		   and the difference is just -x_0/m + x_m/m
		   the following is implementing this idea
		*/
		for(int i = 0; i < points.size() - SMA_magnitude + 1; i++){
			
			head = points.get(i);
			tail = points.get( i + magnitude);
			old_SMA = SMA;
			SMA = SMA.subtract(head.divide(divisor));
			SMA = SMA.add(tail.divide(divisor));
			sum = sum.subtract(head.multiply(alpha));
			sum = sum.subtract(constant.multiply(old_SMA));
			sum = sum.divide(one_minus_alpha);
			sum = sum.add((constant.divide(one_minus_alpha)).multiply(tail));
			sum = sum.add(constant.multiply(SMA));
			EMALine.add(sum);
		}
		EMALine.add(SMA);
		return EMALine;
	}
	
	private List<BigDecimal> EMA_recurrence(List<BigDecimal> points, int magnitude, int SMA_magnitude, BigDecimal alpha){
		List<BigDecimal> current_points = points;
		BigDecimal sum = new BigDecimal(0);
		List<BigDecimal> EMA_SMA = new ArrayList<BigDecimal>();
		if (current_points.size() ==  SMA_magnitude){
			BigDecimal divisor = new BigDecimal(magnitude);
			for (int i = 0; i < SMA_magnitude; i++){
				sum.add(points.get(i));
			}
			sum.divide(divisor);
			EMA_SMA.add(sum);
			EMA_SMA.add(sum);
			
			return EMA_SMA;
		}
		sum = current_points.get(0);
		sum.multiply(alpha);
		BigDecimal unit = new BigDecimal(1);
		EMA_SMA = EMA_recurrence(current_points, magnitude, SMA_magnitude, alpha);
		BigDecimal second_sum = unit.subtract(alpha).multiply(EMA_SMA.get(0));
		sum.add(second_sum);
		EMA_SMA.set(0, sum);
		return EMA_SMA;
	}

}

	
	

