package com.algotrading.backtesting.stock;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import com.algotrading.backtesting.util.Constants;

public class Stock {

	private static String FILEPATH = "src/main/resources";
	private final String ticker;
	private Map<Date, StockHistory> history;
	private int lotSize;

	public Stock(String ticker, Map<Date, StockHistory> history) {
		this(ticker, history, 1);
	}

	public Stock(String ticker) {
		this(ticker, new TreeMap<>(), 1);
	}

	public Stock(String ticker, int lotSize) {
		this(ticker, new TreeMap<>(), lotSize);
	}

	public Stock(String ticker, Map<Date, StockHistory> history, int lotSize) {
		this.ticker = ticker;
		this.history = history;
		this.lotSize = lotSize;
	}

	public void readLotSize() {
		// TODO
	}

	public void read() {
		read(false);
	}

	public void read(boolean withHeader) {
		// TODO read files from ticker
		String strCsvFile = Stock.FILEPATH + "/" + this.ticker + ".csv";
		String strLine = "";
		String strCvsSplitBy = ",";
		boolean isFirstLine = withHeader; // will skip header if true

		try (BufferedReader br = new BufferedReader(new FileReader(strCsvFile))) {
			while ((strLine = br.readLine()) != null) {
				if (isFirstLine) {
					isFirstLine = false;
				} else {
					// use comma as separator
					String[] strStockHistory = strLine.split(strCvsSplitBy);
					Date dtStockHistoryDate = Constants.DATE_FORMAT_YYYYMMDD.parse(strStockHistory[0]);
					Double dbOpen = Double.parseDouble(strStockHistory[1]);
					Double dbClose = Double.parseDouble(strStockHistory[2]);
					Double dbHigh = Double.parseDouble(strStockHistory[3]);
					Double dbLow = Double.parseDouble(strStockHistory[4]);
					Double dbAdjClose = Double.parseDouble(strStockHistory[5]);
					Double dbVolume = Double.parseDouble(strStockHistory[6]);
					history.put(dtStockHistoryDate,
							new StockHistory(dtStockHistoryDate, dbOpen, dbClose, dbHigh, dbLow, dbAdjClose, dbVolume));
				}
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	public String getTicker() {
		return ticker;
	}

	public Map<Date, StockHistory> getHistory() {
		return history;
	}

	public int getLotSize() {
		return lotSize;
	}

	@Override
	public String toString() {
		return ticker;
	}

}
