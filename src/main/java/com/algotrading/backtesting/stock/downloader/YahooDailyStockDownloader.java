package com.algotrading.backtesting.stock.downloader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

public class YahooDailyStockDownloader {

	public static void main(String[] args){
		try {
			//downloader(args[0]);
			downloader("SEHK");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void downloader(String strExchange) throws IOException{
		File logFile = new File(strExchange + ".log");
		// if file doesn't exists, then create it
		if (!logFile.exists()) {
			logFile.createNewFile();
		}
		FileWriter fw = new FileWriter(logFile.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		bw.write("Started at " + dateFormat.format(Calendar.getInstance().getTime()) + "\n");
		Scanner s = new Scanner(new File(strExchange + ".csv"));
		ArrayList<String> list = new ArrayList<String>();
		while (s.hasNext()){
		    list.add(s.next());
		}
		s.close();
		for (String symbol : list) {
			Stock yahooStock = null;
			try {
				yahooStock = YahooFinance.get(symbol, true);
			} catch (Exception e) {
				bw.write(symbol + " does not exist in Yahoo finance. " + e + "\n");
	            System.out.println(symbol + " does not exist in Yahoo finance. " + e);
	            continue;
			}
			Calendar from = Calendar.getInstance();
			Calendar to	  = Calendar.getInstance();
			from.add(Calendar.YEAR, -50);
			boolean isException = false;
			int intNoOfAttempt = 0;
			List<HistoricalQuote> history = null;
			do {
				try {
					intNoOfAttempt++;
					history = yahooStock.getHistory(from, to, Interval.DAILY);
		        } catch (Exception e) {
		        	isException = true;
		    		bw.write(symbol + " data cannot be downloaded. " + e + "\n");
		            System.out.println(symbol + " data cannot be downloaded. " + e);
		        }
			} while (isException && intNoOfAttempt < 3);
			List<String> lines = new ArrayList<String>();
			for (HistoricalQuote quote : history) {
				lines.add(new SimpleDateFormat("yyyyMMdd").format(quote.getDate().getTime()) + "," +
						quote.getOpen() + "," + quote.getClose() + "," + quote.getHigh() + "," + 
						quote.getLow() + "," + quote.getAdjClose());
			}
			Path file = Paths.get("DailyPrice", strExchange + "_" + symbol.replace(".HK", "") + ".csv");
			Files.write(file, lines, Charset.forName("UTF-8"));
			//Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
		}
		bw.write("Ended at " + dateFormat.format(Calendar.getInstance().getTime()) + "\n");
		bw.close();
	}
	
}
