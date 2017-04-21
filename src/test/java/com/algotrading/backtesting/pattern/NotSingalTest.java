package com.algotrading.backtesting.pattern;

import static org.mockito.MockitoAnnotations.initMocks;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mock;

import com.algotrading.backtesting.portfolio.Portfolio;
import com.algotrading.backtesting.stock.Stock;

import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class NotSingalTest {

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { true, false }, { false, true } });
	}

	@Mock
	StockSignal stockSignal;

	@Mock
	Stock stock;

	@Mock
	Date date;

	@Mock
	Portfolio portfolio;

	private final boolean signalResult;
	private final boolean notResult;

	public NotSingalTest(boolean signalResult, boolean notSignalResult) {
		this.signalResult = signalResult;
		this.notResult = notSignalResult;
	}

	@Before
	public void setUp() {
		initMocks(this);
	}

	@Test
	public void testNotSignal() throws ParseException {
		when(stockSignal.signal(stock, date, portfolio)).thenReturn(signalResult);
		StockSignal notSignal = new NotSignal(stockSignal);
		assertThat(notSignal.signal(stock, date, portfolio)).isEqualTo(notResult);
	}
}
