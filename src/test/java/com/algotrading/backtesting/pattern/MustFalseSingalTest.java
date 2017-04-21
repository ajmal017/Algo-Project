package com.algotrading.backtesting.pattern;

import static org.mockito.MockitoAnnotations.initMocks;

import java.text.ParseException;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.algotrading.backtesting.portfolio.Portfolio;
import com.algotrading.backtesting.stock.Stock;

import static org.assertj.core.api.Assertions.assertThat;

public class MustFalseSingalTest {

	@Mock
	Stock stock;

	@Mock
	Date date;

	@Mock
	Portfolio portfolio;

	@Before
	public void setUp() {
		initMocks(this);
	}

	@Test
	public void testMustFalseSignal() throws ParseException {
		StockSignal mustFalseSignal = new MustFalseSignal();
		assertThat(mustFalseSignal.signal(stock, date, portfolio)).isFalse();
	}
}
