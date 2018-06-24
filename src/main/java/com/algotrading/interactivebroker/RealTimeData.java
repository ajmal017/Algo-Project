package com.algotrading.interactivebroker;

import static com.algotrading.interactivebroker.util.ToStringUtil.strTickAttr;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//RealTimeData.java    
//API Version 9.72 and newer
//Version 1.3
//20170506
//R. Holowczak

//Import Java utilities and Interactive Brokers API
// http://interactivebrokers.github.io/#
// https://www.interactivebrokers.com/en/index.php?f=5041
import java.util.Vector;

import com.algotrading.interactivebroker.handler.MustBuyTickPriceHandler;
import com.algotrading.interactivebroker.test.DummyUtil;
import com.algotrading.interactivebroker.util.Logger;
import com.ib.client.CommissionReport;
import com.ib.client.Contract;
import com.ib.client.EClientSocket;
import com.ib.client.EJavaSignal;
import com.ib.client.EReader;
import com.ib.client.EWrapperMsgGenerator;
import com.ib.client.Execution;
import com.ib.client.HistoricalTick;
import com.ib.client.HistoricalTickBidAsk;
import com.ib.client.HistoricalTickLast;
import com.ib.client.Order;
import com.ib.client.TagValue;
import com.ib.client.TickAttr;
import com.ib.client.TickType;

public class RealTimeData extends BaseEWrapper {

	/** To signal a message is ready for processing in the queue. */
	private final EJavaSignal signal = new EJavaSignal();

	/**
	 * Captures incoming messages to the API client and places them into a
	 * queue.
	 */
	private final EReader reader;

	/** The class used to send messages to TWS */
	private final EClientSocket clientSocket;

	/** Keep track of the next ID */
	private int nextOrderID = 0;

	/** Keep track of prices for Moving Average */
	private double priceTotal;

	// TODO dunno what it is
	private int numberOfPrices;

	/** To log the message with timestamp */
	private Logger logger;

	/** To log the request id with the contract object */
	private final Map<Integer, Contract> marketRequestMap;

	/**
	 * To send all request to ib, wrap the clientSocket and related information
	 */
	private final Requester requester;

	public RealTimeData() {
		logger = new Logger();
		marketRequestMap = new HashMap<>();

		priceTotal = 0.0;
		numberOfPrices = 0;

		clientSocket = new EClientSocket(this, signal);
		clientSocket.eConnect(null, 7497, 0);

		// Pause here for connection to complete
		// Can also try: while (client.NextOrderId <= 0);
		try {
			while (!(clientSocket.isConnected())) {
				logger.info("Connection completed");
			}
		} catch (Exception e) {
		}

		reader = new EReader(clientSocket, signal);
		reader.start();

		requester = new Requester(this, clientSocket, marketRequestMap);
		new Thread() {
			@Override
			public void run() {

				// TODO really DU228380? what does that mean?
				// TODO move to reqXXX method below
				requester.reqAccountUpdates(true, "DU228380");
				requester.reqManagedAccts();

				// TODO delete after requester works
				// client.reqAccountUpdates(true, "DU228380");
				// client.reqManagedAccts();

				processMessages();
			}
		}.start();

		Contract contract = DummyUtil.createContract();

		// TODO remove it and have a class to determine to place order
		// placeOrder(contract);

		Vector<TagValue> mktDataOptions = new Vector<TagValue>();
		requester.reqMarketDataType(3);
		requester.reqMktData(0, contract, null, false, false, mktDataOptions);
		requester.reqHistoricalTicks(18001, contract, "20170712 21:39:33", null, 10, "TRADES", 1, true, null);

		// TODO to remove after requester works
		// client.reqMarketDataType(3);
		// client.reqMktData(0, contract, null, false, false, mktDataOptions);
		// client.reqHistoricalTicks(18001, DummyUtil.createContract(),
		// "20170712 21:39:33", null, 10, "TRADES", 1, true,
		// null);
	}

	public static void main(String args[]) {
		try {
			new RealTimeData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getNextOrderId() {
		int result = nextOrderID;
		nextOrderID++;
		return result;
	}

	@Deprecated
	private void placeOrder(Contract contract) {
		Order order = DummyUtil.createTestLimitDayOrder();
		clientSocket.placeOrder(getNextOrderId(), contract, order);
	}

	private void processMessages() {
		while (true) {
			try {
				reader.processMsgs();
			} catch (Exception e) {
				error(e);
			}
		}
	}

	/** Return the next valid OrderID */
	@Override
	public void nextValidId(int orderId) {
		nextOrderID = orderId;
	}

	@Override
	public void tickPrice(int tickerId, int field, double price, TickAttr attribs) {
		double movingAverage = 0.0;
		// 1 = bid, 2 = ask, 4 = last
		// 6 = high, 7 = low, 9 = close
		if (TickType.getField(field) == TickType.LAST.field()) {
			numberOfPrices++;
			priceTotal += price;
			movingAverage = priceTotal / numberOfPrices;
			logger.info("tickPrice: " + tickerId + "," + field + "," + price + ", " + movingAverage);
		}
		logger.info("tickPrice(): tickerId=" + tickerId + ", field=" + field + "(" + TickType.getField(field)
				+ "), price=" + price + ", tickAttr=" + strTickAttr(attribs));
		logger.info("tick price updated, MustBuyTickPriceHandler start");
		new MustBuyTickPriceHandler().handle(requester, marketRequestMap.get(tickerId), field, price, attribs);
	}

	@Override
	public void tickSize(int tickerId, int field, int size) {
		// TickType: 0=bid size, 3=ask size, 5=last size, 8=volume
		logger.info("tickSize(): orderId=" + tickerId + ", field=" + field + "(" + TickType.getField(field) + "), size="
				+ size);
	}

	@Override
	public void updateAccountValue(String key, String value, String currency, String accountName) {
		logger.info("UpdateAccountValue. Key: " + key + ", Value: " + value + ", Currency: " + currency
				+ ", AccountName: " + accountName);
	}

	@Override
	public void updatePortfolio(Contract contract, double position, double marketPrice, double marketValue,
			double averageCost, double unrealizedPNL, double realizedPNL, String accountName) {
		logger.info(new Date() + " : UpdatePortfolio. " + contract.symbol() + ", " + contract.secType() + " @ "
				+ contract.exchange() + ": Position: " + position + ", MarketPrice: " + marketPrice + ", MarketValue: "
				+ marketValue + ", AverageCost: " + averageCost + ", UnrealizedPNL: " + unrealizedPNL
				+ ", RealizedPNL: " + realizedPNL + ", AccountName: " + accountName);
		placeOrder(contract);
	}

	@Override
	public void updateAccountTime(String timeStamp) {
		logger.info("UpdateAccountTime. Time: " + timeStamp + "\n");
	}

	@Override
	public void accountDownloadEnd(String accountName) {
		logger.info("Account download finished: " + accountName + "\n");
	}

	@Override
	public void managedAccounts(String accountsList) {
		logger.info("Account list: " + accountsList);
	}

	@Override
	public void execDetails(int reqId, Contract contract, Execution execution) {
		logger.info("ExecDetails. " + reqId + " - [" + contract.symbol() + "], [" + contract.secType() + "], ["
				+ contract.currency() + "], [" + execution.execId() + "], [" + execution.orderId() + "], ["
				+ execution.shares() + "]" + ", [" + execution.lastLiquidity() + "]");
	}

	@Override
	public void commissionReport(CommissionReport commissionReport) {
		logger.info("CommissionReport. [" + commissionReport.m_execId + "] - [" + commissionReport.m_commission + "] ["
				+ commissionReport.m_currency + "] RPNL [" + commissionReport.m_realizedPNL + "]");
	}

	@Override
	public void historicalTicksLast(int reqId, List<HistoricalTickLast> ticks, boolean done) {
		for (HistoricalTickLast tick : ticks) {
			logger.info(EWrapperMsgGenerator.historicalTickLast(reqId, tick.time(), tick.mask(), tick.price(),
					tick.size(), tick.exchange(), tick.specialConditions()));
		}
	}

	@Override
	public void historicalTicksBidAsk(int reqId, List<HistoricalTickBidAsk> ticks, boolean done) {
		for (HistoricalTickBidAsk tick : ticks) {
			logger.info(EWrapperMsgGenerator.historicalTickBidAsk(reqId, tick.time(), tick.mask(), tick.priceBid(),
					tick.priceAsk(), tick.sizeBid(), tick.sizeAsk()));
		}
	}

	@Override
	public void historicalTicks(int reqId, List<HistoricalTick> ticks, boolean done) {
		for (HistoricalTick tick : ticks) {
			logger.info(EWrapperMsgGenerator.historicalTick(reqId, tick.time(), tick.price(), tick.size()));
		}
	}

}