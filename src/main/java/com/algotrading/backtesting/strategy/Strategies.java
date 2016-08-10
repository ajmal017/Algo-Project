package com.algotrading.backtesting.strategy;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.algotrading.backtesting.pattern.StockSignal;
import com.algotrading.backtesting.patterninterperter.Interperter;
import com.algotrading.backtesting.portfolio.PortfolioComponent;
import com.algotrading.backtesting.stock.Stock;

public class Strategies {
	private List<Strategy> buySignal;
	private List<Strategy> sellSignal;

	public Strategies(String buyStrategiesFilePath, String sellStrategiesFilePath) throws IOException, ParseException {
		buySignal = read(buyStrategiesFilePath);
		sellSignal = read(sellStrategiesFilePath);
	}

	public List<Strategy> read(String strategiesFilePath) throws IOException, ParseException {
		Path filePath = new File(strategiesFilePath).toPath();
		Charset charset = Charset.defaultCharset();
		List<String> stringList = Files.readAllLines(filePath, charset);
		List<Strategy> strategies = new ArrayList<>();
		for (String line : stringList) {
			String[] value = line.split("\t");
			String signalStr = value[0];
			String costStr = value[1];
			StockSignal stockPattern = Interperter.toPattern(signalStr);
			double cost = Double.parseDouble(costStr);
			Strategy strategy = new Strategy(stockPattern, cost);
			strategies.add(strategy);
		}
		return strategies;
	}

	public PortfolioComponent buySellAmount(Stock stock, Date date) {
		PortfolioComponent component = new PortfolioComponent(stock, 0, 0);
		for (Strategy strategy : buySignal) {
			if (strategy.shouldPutOrder(stock, date)) {
				component.add(strategy.buySellAmount(stock, date));
				break;
			}
		}
		for (Strategy strategy : sellSignal) {
			if (strategy.shouldPutOrder(stock, date)) {
				component.add(strategy.buySellAmount(stock, date));
				break;
			}
		}
		return component;
	}
}