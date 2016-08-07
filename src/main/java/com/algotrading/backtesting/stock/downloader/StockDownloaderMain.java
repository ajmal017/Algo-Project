package com.algotrading.backtesting.stock.downloader;

import java.io.IOException;

public class StockDownloaderMain {

	public static void main(String[] main) {
		try {
			StockDownloader yahooStockDownloader = new YahooDailyStockDownloader("SEHK");
			yahooStockDownloader.download();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
