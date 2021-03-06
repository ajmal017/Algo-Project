package com.algotrading.persistence.mongo.dbobject;

import java.util.function.Supplier;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class TickPrice implements DBObjectable {

	public static final String NAME = "tickPrice";

	private static final String FIELD_TICKER = "ticker";
	private static final String FIELD_TIMESTAMP = "timestamp";
	private static final String FIELD_TRADING_STATUS = "tradingStatus";
	private static final String FIELD_TRANSACTION_VOLUME = "transactionVolume";
	private static final String FIELD_TRANSACTION_PRICE = "transactionPrice";
	private static final String FIELD_BID_VOLUME_1 = "bidVolume1";
	private static final String FIELD_BID_PRICE_1 = "bidPrice1";
	private static final String FIELD_BID_VOLUME_2 = "bidVolume2";
	private static final String FIELD_BID_PRICE_2 = "bidPrice2";
	private static final String FIELD_BID_VOLUME_3 = "bidVolume3";
	private static final String FIELD_BID_PRICE_3 = "bidPrice3";
	private static final String FIELD_BID_VOLUME_4 = "bidVolume4";
	private static final String FIELD_BID_PRICE_4 = "bidPrice4";
	private static final String FIELD_BID_VOLUME_5 = "bidVolume5";
	private static final String FIELD_BID_PRICE_5 = "bidPrice5";
	private static final String FIELD_ASK_VOLUME_1 = "askVolume1";
	private static final String FIELD_ASK_PRICE_1 = "askPrice1";
	private static final String FIELD_ASK_VOLUME_2 = "askVolume2";
	private static final String FIELD_ASK_PRICE_2 = "askPrice2";
	private static final String FIELD_ASK_VOLUME_3 = "askVolume3";
	private static final String FIELD_ASK_PRICE_3 = "askPrice3";
	private static final String FIELD_ASK_VOLUME_4 = "askVolume4";
	private static final String FIELD_ASK_PRICE_4 = "askPrice4";
	private static final String FIELD_ASK_VOLUME_5 = "askVolume5";
	private static final String FIELD_ASK_PRICE_5 = "askPrice5";

	private String ticker;
	private String timestamp;
	private String tradingStatus;
	private int transactionVolume;
	private double transactionPrice;
	private int bidVolume1;
	private double bidPrice1;
	private int bidVolume2;
	private double bidPrice2;
	private int bidVolume3;
	private double bidPrice3;
	private int bidVolume4;
	private double bidPrice4;
	private int bidVolume5;
	private double bidPrice5;
	private int askVolume1;
	private double askPrice1;
	private int askVolume2;
	private double askPrice2;
	private int askVolume3;
	private double askPrice3;
	private int askVolume4;
	private double askPrice4;
	private int askVolume5;
	private double askPrice5;

	public TickPrice() {

	}

	public TickPrice(String ticker, String timestamp, String tradingStatus, int transactionVolume,
			double transactionPrice, int bidVolume1, double bidPrice1, int bidVolume2, double bidPrice2, int bidVolume3,
			double bidPrice3, int bidVolume4, double bidPrice4, int bidVolume5, double bidPrice5, int askVolume1,
			double askPrice1, int askVolume2, double askPrice2, int askVolume3, double askPrice3, int askVolume4,
			double askPrice4, int askVolume5, double askPrice5) {
		super();
		this.ticker = ticker;
		this.timestamp = timestamp;
		this.tradingStatus = tradingStatus;
		this.transactionVolume = transactionVolume;
		this.transactionPrice = transactionPrice;
		this.bidVolume1 = bidVolume1;
		this.bidPrice1 = bidPrice1;
		this.bidVolume2 = bidVolume2;
		this.bidPrice2 = bidPrice2;
		this.bidVolume3 = bidVolume3;
		this.bidPrice3 = bidPrice3;
		this.bidVolume4 = bidVolume4;
		this.bidPrice4 = bidPrice4;
		this.bidVolume5 = bidVolume5;
		this.bidPrice5 = bidPrice5;
		this.askVolume1 = askVolume1;
		this.askPrice1 = askPrice1;
		this.askVolume2 = askVolume2;
		this.askPrice2 = askPrice2;
		this.askVolume3 = askVolume3;
		this.askPrice3 = askPrice3;
		this.askVolume4 = askVolume4;
		this.askPrice4 = askPrice4;
		this.askVolume5 = askVolume5;
		this.askPrice5 = askPrice5;
	}

	public String getTicker() {
		return ticker;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getTradingStatus() {
		return tradingStatus;
	}

	public int getTransactionVolume() {
		return transactionVolume;
	}

	public double getTransactionPrice() {
		return transactionPrice;
	}

	public int getBidVolume1() {
		return bidVolume1;
	}

	public double getBidPrice1() {
		return bidPrice1;
	}

	public int getBidVolume2() {
		return bidVolume2;
	}

	public double getBidPrice2() {
		return bidPrice2;
	}

	public int getBidVolume3() {
		return bidVolume3;
	}

	public double getBidPrice3() {
		return bidPrice3;
	}

	public int getBidVolume4() {
		return bidVolume4;
	}

	public double getBidPrice4() {
		return bidPrice4;
	}

	public int getBidVolume5() {
		return bidVolume5;
	}

	public double getBidPrice5() {
		return bidPrice5;
	}

	public int getAskVolume1() {
		return askVolume1;
	}

	public double getAskPrice1() {
		return askPrice1;
	}

	public int getAskVolume2() {
		return askVolume2;
	}

	public double getAskPrice2() {
		return askPrice2;
	}

	public int getAskVolume3() {
		return askVolume3;
	}

	public double getAskPrice3() {
		return askPrice3;
	}

	public int getAskVolume4() {
		return askVolume4;
	}

	public double getAskPrice4() {
		return askPrice4;
	}

	public int getAskVolume5() {
		return askVolume5;
	}

	public double getAskPrice5() {
		return askPrice5;
	}

	@Override
	public String toString() {
		return "TickPrice [ticker=" + ticker + ", timestamp=" + timestamp + ", tradingStatus=" + tradingStatus
				+ ", transactionVolume=" + transactionVolume + ", transactionPrice=" + transactionPrice
				+ ", bidVolume1=" + bidVolume1 + ", bidPrice1=" + bidPrice1 + ", bidVolume2=" + bidVolume2
				+ ", bidPrice2=" + bidPrice2 + ", bidVolume3=" + bidVolume3 + ", bidPrice3=" + bidPrice3
				+ ", bidVolume4=" + bidVolume4 + ", bidPrice4=" + bidPrice4 + ", bidVolume5=" + bidVolume5
				+ ", bidPrice5=" + bidPrice5 + ", askVolume1=" + askVolume1 + ", askPrice1=" + askPrice1
				+ ", askVolume2=" + askVolume2 + ", askPrice2=" + askPrice2 + ", askVolume3=" + askVolume3
				+ ", askPrice3=" + askPrice3 + ", askVolume4=" + askVolume4 + ", askPrice4=" + askPrice4
				+ ", askVolume5=" + askVolume5 + ", askPrice5=" + askPrice5 + "]";
	}

	@Override
	public DBObject toDBObject() {
		return new BasicDBObject("_id", getKey()).append(FIELD_TIMESTAMP, getTimestamp())
				.append(FIELD_TRADING_STATUS, getTradingStatus())
				.append(FIELD_TICKER, getTicker())
				.append(FIELD_TRANSACTION_VOLUME, getTransactionVolume())
				.append(FIELD_TRANSACTION_PRICE, getTransactionPrice())
				.append(FIELD_BID_VOLUME_1, getBidVolume1())
				.append(FIELD_BID_PRICE_1, getBidPrice1())
				.append(FIELD_BID_VOLUME_2, getBidVolume2())
				.append(FIELD_BID_PRICE_2, getBidPrice2())
				.append(FIELD_BID_VOLUME_3, getBidVolume3())
				.append(FIELD_BID_PRICE_3, getBidPrice3())
				.append(FIELD_BID_VOLUME_4, getBidVolume4())
				.append(FIELD_BID_PRICE_4, getBidPrice4())
				.append(FIELD_BID_VOLUME_5, getBidVolume5())
				.append(FIELD_BID_PRICE_5, getBidPrice5())
				.append(FIELD_ASK_VOLUME_1, getAskVolume1())
				.append(FIELD_ASK_PRICE_1, getAskPrice1())
				.append(FIELD_ASK_VOLUME_2, getAskVolume2())
				.append(FIELD_ASK_PRICE_2, getAskPrice2())
				.append(FIELD_ASK_VOLUME_3, getAskVolume3())
				.append(FIELD_ASK_PRICE_3, getAskPrice3())
				.append(FIELD_ASK_VOLUME_4, getAskVolume4())
				.append(FIELD_ASK_PRICE_4, getAskPrice4())
				.append(FIELD_ASK_VOLUME_5, getAskVolume5())
				.append(FIELD_ASK_PRICE_5, getAskPrice5());
	}

	@Override
	public void fromDBObject(DBObject dbObject) {
		ticker = (String) dbObject.get(FIELD_TICKER);
		timestamp = (String) dbObject.get(FIELD_TIMESTAMP);
		tradingStatus = (String) dbObject.get(FIELD_TRADING_STATUS);
		transactionVolume = (int) dbObject.get(FIELD_TRANSACTION_VOLUME);
		transactionPrice = (double) dbObject.get(FIELD_TRANSACTION_PRICE);
		bidVolume1 = (int) dbObject.get(FIELD_BID_VOLUME_1);
		bidPrice1 = (double) dbObject.get(FIELD_BID_PRICE_1);
		bidVolume2 = (int) dbObject.get(FIELD_BID_VOLUME_2);
		bidPrice2 = (double) dbObject.get(FIELD_BID_PRICE_2);
		bidVolume3 = (int) dbObject.get(FIELD_BID_VOLUME_3);
		bidPrice3 = (double) dbObject.get(FIELD_BID_PRICE_3);
		bidVolume4 = (int) dbObject.get(FIELD_BID_VOLUME_4);
		bidPrice4 = (double) dbObject.get(FIELD_BID_PRICE_4);
		bidVolume5 = (int) dbObject.get(FIELD_BID_VOLUME_5);
		bidPrice5 = (double) dbObject.get(FIELD_BID_PRICE_5);
		askVolume1 = (int) dbObject.get(FIELD_ASK_VOLUME_1);
		askPrice1 = (double) dbObject.get(FIELD_ASK_PRICE_1);
		askVolume2 = (int) dbObject.get(FIELD_ASK_VOLUME_2);
		askPrice2 = (double) dbObject.get(FIELD_ASK_PRICE_2);
		askVolume3 = (int) dbObject.get(FIELD_ASK_VOLUME_3);
		askPrice3 = (double) dbObject.get(FIELD_ASK_PRICE_3);
		askVolume4 = (int) dbObject.get(FIELD_ASK_VOLUME_4);
		askPrice4 = (double) dbObject.get(FIELD_ASK_PRICE_4);
		askVolume5 = (int) dbObject.get(FIELD_ASK_VOLUME_5);
		askPrice5 = (double) dbObject.get(FIELD_ASK_PRICE_5);
	}

	// @Override
	// public String getKey() {
	// return getTicker() + "_" + getTimestamp();
	// }

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public void setTradingStatus(String tradingStatus) {
		this.tradingStatus = tradingStatus;
	}

	public void setTransactionVolume(int transactionVolume) {
		this.transactionVolume = transactionVolume;
	}

	public void setTransactionPrice(double transactionPrice) {
		this.transactionPrice = transactionPrice;
	}

	public void setBidVolume1(int bidVolume1) {
		this.bidVolume1 = bidVolume1;
	}

	public void setBidPrice1(double bidPrice1) {
		this.bidPrice1 = bidPrice1;
	}

	public void setBidVolume2(int bidVolume2) {
		this.bidVolume2 = bidVolume2;
	}

	public void setBidPrice2(double bidPrice2) {
		this.bidPrice2 = bidPrice2;
	}

	public void setBidVolume3(int bidVolume3) {
		this.bidVolume3 = bidVolume3;
	}

	public void setBidPrice3(double bidPrice3) {
		this.bidPrice3 = bidPrice3;
	}

	public void setBidVolume4(int bidVolume4) {
		this.bidVolume4 = bidVolume4;
	}

	public void setBidPrice4(double bidPrice4) {
		this.bidPrice4 = bidPrice4;
	}

	public void setBidVolume5(int bidVolume5) {
		this.bidVolume5 = bidVolume5;
	}

	public void setBidPrice5(double bidPrice5) {
		this.bidPrice5 = bidPrice5;
	}

	public void setAskVolume1(int askVolume1) {
		this.askVolume1 = askVolume1;
	}

	public void setAskPrice1(double askPrice1) {
		this.askPrice1 = askPrice1;
	}

	public void setAskVolume2(int askVolume2) {
		this.askVolume2 = askVolume2;
	}

	public void setAskPrice2(double askPrice2) {
		this.askPrice2 = askPrice2;
	}

	public void setAskVolume3(int askVolume3) {
		this.askVolume3 = askVolume3;
	}

	public void setAskPrice3(double askPrice3) {
		this.askPrice3 = askPrice3;
	}

	public void setAskVolume4(int askVolume4) {
		this.askVolume4 = askVolume4;
	}

	public void setAskPrice4(double askPrice4) {
		this.askPrice4 = askPrice4;
	}

	public void setAskVolume5(int askVolume5) {
		this.askVolume5 = askVolume5;
	}

	public void setAskPrice5(double askPrice5) {
		this.askPrice5 = askPrice5;
	}

	@Override
	public String getCollectionName() {
		return NAME;
	}

	@Override
	public Supplier<? extends DBObjectable> getSupplier() {
		return TickPrice::new;
	}

}
