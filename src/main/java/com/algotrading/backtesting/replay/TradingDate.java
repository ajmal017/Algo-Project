package com.algotrading.backtesting.replay;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.algotrading.backtesting.util.Constants;

public class TradingDate {

	private String file;
	private List<Date> tradingDates;
	private int currentTradingDateIndex;
	private boolean isLastDay = false;

	public TradingDate() {
		tradingDates = new ArrayList<>();
	}

	public TradingDate(String file) throws IOException, ParseException {
		this.file = file;
		tradingDates = new ArrayList<>();
		read();
	}

	public void add(Date date) {
		tradingDates.add(date);
	}

	public void setCurrentDate(Date date) {
		String dateStr = Constants.DATE_FORMAT_YYYYMMDD.format(date);
		for (int i = 0; i < tradingDates.size(); i++) {
			if (date.compareTo(tradingDates.get(i)) == 0) {
				currentTradingDateIndex = i;
				break;
			}
		}
	}

	public Date currentDate() {
		return tradingDates.get(currentTradingDateIndex);
	}

	public void rollDay() {
		currentTradingDateIndex++;
	}

	public boolean isNotLastDate() {
		return currentTradingDateIndex < tradingDates.size();
	}

	public void read() throws IOException, ParseException {
		Path filePath = new File(file).toPath();
		Charset charset = Charset.defaultCharset();
		List<String> stringList = Files.readAllLines(filePath, charset);
		tradingDates = stringList.stream()
				.map(dateStr -> parseDate(dateStr))
				.sorted()
				.collect(Collectors.toList());
	}

	private Date parseDate(String dateStr) {
		try {
			return Constants.DATE_FORMAT_YYYYMMDD.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
