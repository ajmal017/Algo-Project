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
public class OrSingalTest {

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { true, true, true }, { true, false, true }, { false, true, true },
				{ false, false, false } });
	}

	@Mock
	StockSignal stockSignal1;

	@Mock
	StockSignal stockSignal2;

	@Mock
	Stock stock;

	@Mock
	Date date;

	@Mock
	Portfolio portfolio;

	private final boolean signalResult1;
	private final boolean signalResult2;
	private final boolean orSignalResult;

	public OrSingalTest(boolean signalResult1, boolean signalResult2, boolean orResult) {
		this.signalResult1 = signalResult1;
		this.signalResult2 = signalResult2;
		this.orSignalResult = orResult;
	}

	@Before
	public void setUp() {
		initMocks(this);
	}

	@Test
	public void testOrSignal() throws ParseException {
		when(stockSignal1.signal(stock, date, portfolio)).thenReturn(signalResult1);
		when(stockSignal2.signal(stock, date, portfolio)).thenReturn(signalResult2);
		StockSignal andSignal = new OrSignal(stockSignal1, stockSignal2);
		assertThat(andSignal.signal(stock, date, portfolio)).isEqualTo(orSignalResult);
	}
}
