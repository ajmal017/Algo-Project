package com.algotrading.backtesting.replay;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.algotrading.backtesting.stock.Stock;

public class AvailableStocks {

	private List<Stock> stocks;

	public AvailableStocks(String fileList) throws IOException, ParseException {
		read(fileList);
	}

	public AvailableStocks(String fileList, String resourcePath) throws IOException, ParseException {
		read(fileList, resourcePath);
	}

	public AvailableStocks() {
		stocks = new ArrayList<>();
	}

	public void read(String filePath) throws IOException, ParseException {
		Path file = new File(filePath).toPath();
		Charset charset = Charset.defaultCharset();
		List<String> stringList = Files.readAllLines(file, charset);
		stocks = new ArrayList<>();
		for (String line : stringList) {
			Stock stock = new Stock(line);
			stock.read();
			stocks.add(stock);
		}
	}

	public void read(String filePath, String resourcePath) throws IOException, ParseException {
		Path file = new File(filePath).toPath();
		Charset charset = Charset.defaultCharset();
		List<String> stringList = Files.readAllLines(file, charset);
		stocks = new ArrayList<>();
		for (String line : stringList) {
			Stock stock = new Stock(line);
			stock.read(resourcePath);
			stocks.add(stock);
		}
	}

	public List<Stock> get() {
		return stocks;
	}

	public void add(Stock stock) {
		stocks.add(stock);
	}

}
