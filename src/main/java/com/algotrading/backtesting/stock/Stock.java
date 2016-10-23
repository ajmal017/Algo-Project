package com.algotrading.backtesting.stock;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class Stock {

	private static String FILEPATH = "src/main/resources";
	private String ticker;
	private Map<Date, StockHistory> history;

	public Stock(String ticker) {
		this.ticker = ticker;
		history = new TreeMap<>();
	}

	public void read() {
		// TODO read files from ticker
		String strCsvFile = Stock.FILEPATH + "/" + this.ticker;
		String strLine = "";
		String strCvsSplitBy = ",";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		try (BufferedReader br = new BufferedReader(new FileReader(strCsvFile))) {
			while ((strLine = br.readLine()) != null) {
				// use comma as separator
				String[] strStockHistory = strLine.split(strCvsSplitBy);
				Date dtStockHistoryDate = sdf.parse(strStockHistory[0]);
				Double dbOpen = Double.parseDouble(strStockHistory[1]);
				Double dbClose = Double.parseDouble(strStockHistory[2]);
				Double dbHigh = Double.parseDouble(strStockHistory[3]);
				Double dbLow = Double.parseDouble(strStockHistory[4]);
				Double dbAdjClose = Double.parseDouble(strStockHistory[5]);
				Double dbVolume = Double.parseDouble(strStockHistory[6]);
				history.put(dtStockHistoryDate,
						new StockHistory(dtStockHistoryDate, dbOpen, dbClose, dbHigh, dbLow, dbAdjClose, dbVolume));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public String getTicker() {
		return ticker;
	}

	public Map<Date, StockHistory> getHistory() {
		return history;
	}

	public Map<Date, Double> getClosingPriceHistory() {
		Map<Date, Double> map = new TreeMap<>();
		for (Map.Entry<Date, StockHistory> entry : history.entrySet()) {
			map.put(entry.getKey(), entry.getValue()
					.getAdjClose());
		}
		return map;
	}
}
