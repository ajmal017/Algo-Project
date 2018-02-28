package com.algotrading.interactivebroker;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.ib.client.Bar;
import com.ib.client.CommissionReport;
import com.ib.client.Contract;
import com.ib.client.ContractDescription;
import com.ib.client.ContractDetails;
import com.ib.client.DeltaNeutralContract;
import com.ib.client.DepthMktDataDescription;
import com.ib.client.EWrapper;
import com.ib.client.Execution;
import com.ib.client.FamilyCode;
import com.ib.client.HistogramEntry;
import com.ib.client.HistoricalTick;
import com.ib.client.HistoricalTickBidAsk;
import com.ib.client.HistoricalTickLast;
import com.ib.client.NewsProvider;
import com.ib.client.OrderState;
import com.ib.client.PriceIncrement;
import com.ib.client.SoftDollarTier;
import com.ib.client.TagValue;
import com.ib.client.TickAttr;

public abstract class BaseEWrapper implements EWrapper {

	@Override
	public void securityDefinitionOptionalParameter(int reqId, String exchange, int underlyingConId,
			String tradingClass, String multiplier, Set expirations, Set strikes) {
	}

	/**
	 * updates the real time 5 seconds bars
	 * 
	 * @param reqId
	 *            the request's identifier
	 * @param time
	 *            the bar's date and time (either as a yyyymmss hh:mm:ss formatted string or as system time 
	 *            according to the request)
	 * @param open
	 *            the bar's open point
	 * @param high
	 *            the bar's high point
	 * @param low
	 *            the bar's low point
	 * @param close
	 *            the bar's closing point
	 * @param volume
	 *            the bar's traded volume (only returned for TRADES data)
	 * @param wap
	 *            the bar's Weighted Average Price rounded to minimum increment (only available for TRADES).
	 * @param count
	 *            the number of trades during the bar's timespan (only available for TRADES).
	 * @see EClientSocket::reqRealTimeBars
	 */	
	@Override
	public void realtimeBar(int reqId, long time, double open, double high, double low, double close, long volume,
			double wap, int count) {
	}

	@Override
	public void securityDefinitionOptionalParameterEnd(int reqId) {
	}

	@Override
	public void accountUpdateMulti(int reqId, String account, String modelCode, String key, String value,
			String currency) {
	}

	@Override
	public void accountUpdateMultiEnd(int reqId) {
	}

	/**
	 * provides the portfolio's open positions.
	 * 
	 * @param reqId
	 *            the id of request
	 * @param account
	 *            the account holding the position.
	 * @param modelCode
	 *            the model code holding the position.
	 * @param contract
	 *            the position's Contract
	 * @param pos
	 *            the number of positions held.
	 * @param avgCost
	 *            the average cost of the position.
	 * @see positionMultiEnd, EClientSocket::reqPositionsMulti
	 */	
	@Override
	public void positionMulti(int reqId, String account, String modelCode, Contract contract, double pos,
			double avgCost) {
	}

	/**
	 * Indicates all the positions have been transmitted.
	 * 
	 * @param reqId
	 *            the id of request
	 * @see positionMulti, EClient::reqPositionsMulti
	 */
	
	void reqHeadTimestamp(int tickerId, Contract contract, String whatToShow, int useRTH, int formatDate ){
		
	/**		
	 * 	@return the timestamp of earliest available historical data for a contract and data type.
	 * 
	 * 	@param 	tickerId	- an identifier for the request
	 * 			contract	- contract object for which head timestamp is being requested
	 * 			whatToShow	- type of data for head timestamp - "BID", "ASK", "TRADES", etc
	 *			useRTH	- use regular trading hours only, 1 for yes or 0 for no
	 *			formatDate	set to 1 to obtain the bars' time as yyyyMMdd HH:mm:ss, set to 2 to obtain 
	 *						it like system time format in seconds
	 *
	 *	@See 	headTimeStamp
	 */	
	}
	
	void reqHistogramData(int tickerId,	Contract contract, Boolean useRTH,	String period){
	/**
	 * 	@return data histogram of specified contract
	 * 
	 * 	@param	tickerId	- an identifier for the request
	 * 			contract	- Contract object for which histogram is being requested
	 * 			useRTH	- use regular trading hours only, 1 for yes or 0 for no
	 * 			period	- period of which data is being requested, e.g. "3 days"
	 * 
	 * 	@see 	histogramData	
	 */	
	}
	
	void reqHistoricalData (int tickerId, Contract contract, String endDateTime, String durationString, String barSizeSetting, String whatToShow, int useRTH, int formatDate, Boolean keepUpToDate, List< TagValue > chartOptions){
	/**
	 * Requests contracts' historical data. When requesting historical data, a finishing time and date
	 * is required along with a duration string. For example, having:
	 * 			 - endDateTime: 20130701 23:59:59 GMT
	 * 			 - durationStr: 3 D
	 * 
	 * 	@return three days of data counting backwards from July 1st 2013 at 23:59:59 GMT
	 * 			resulting in all the available bars of the last three days until the date and time specified. 
	 * 			It is possible to specify a timezone optionally. The resulting 
	 * 			bars will be returned in EWrapper::historicalData
	 * 
	 * 	@param	tickerId	the request's unique identifier.
	 * 			contract	the contract for which we want to retrieve the data.
	 * 			endDateTime	request's ending time with format yyyyMMdd HH:mm:ss {TMZ}
	 * 			durationString	the amount of time for which the data needs to be retrieved:
	 * 				" S (seconds) - " D (days)
	 * 				" W (weeks) - " M (months)
	 * 				" Y (years)
	 * 			barSizeSetting	the size of the bar:
	 * 				1 sec
	 * 				5 secs
	 * 				15 secs
	 * 				30 secs
	 * 				1 min
	 * 				2 mins
	 * 				3 mins
	 * 				5 mins
	 * 				15 mins
	 * 				30 mins
	 * 				1 hour
	 * 				1 day
	 * 			whatToShow	the kind of information being retrieved:
	 * 				TRADES
	 *				MIDPOINT
	 *				BID
	 *				ASK
	 *				BID_ASK
	 *				HISTORICAL_VOLATILITY
	 *				OPTION_IMPLIED_VOLATILITY
	 *				FEE_RATE
	 *				REBATE_RATE
	 *			useRTH			set to 0 to obtain the data which was also generated outside of the 
	 *							Regular Trading Hours, set to 1 to obtain only the RTH data
	 *			formatDate		set to 1 to obtain the bars' time as yyyyMMdd HH:mm:ss, set to 2 to obtain 
	 *							it like system time format in seconds
	 *			keepUpToDate	set to True to received continuous updates on most recent bar data.
	 *							If True, and endDateTime cannot be specified.
	 *
	 *	@see 	EWrapper::historicalData
	 */
	}

	void reqHistoricalNews (int requestId, int conId, String providerCodes, String startDateTime, String endDateTime, int totalResults, List< TagValue > historicalNewsOptions){
	/**
	 * 	Requests historical news headlines.
	 * 
	 * 	@parm	requestId
	 * 			conId	- contract id of ticker
	 *			providerCodes	- a '+'-separated list of provider codes
	 *			startDateTime	- marks the (exclusive) start of the date range. The format is yyyy-MM-dd HH:mm:ss.0
	 *			endDateTime	- marks the (inclusive) end of the date range. The format is yyyy-MM-dd HH:mm:ss.0
	 *			totalResults	- the maximum number of headlines to fetch (1 - 300)
	 *			historicalNewsOptions	reserved for internal use. Should be defined as null.
	 *
	 *	@see	EWrapper::historicalNews, EWrapper::historicalNewsEnd	
	*/		
	}
	
	void reqHistoricalTicks (int reqId, Contract contract, String startDateTime, String endDateTime, int numberOfTicks, String whatToShow, int useRth, Boolean ignoreSize, List< TagValue > miscOptions){
	/**
	 * 	Requests historical Time&Sales data for an instrument.
	 * 
	 * 	@param	reqId	id of the request
	 * 			contract	Contract object that is subject of query
	 * 			startDateTime,i.e.	"20170701 12:01:00". Uses TWS timezone specified at login.
	 * 			endDateTime,i.e.	"20170701 13:01:00". In TWS timezone. Exactly one of start time and end time has to be defined.
	 * 			numberOfTicks	Number of distinct data points. Max currently 1000 per request.
	 * 			whatToShow	(Bid_Ask, Midpoint, Trades) Type of data requested.
	 * 			useRth	Data from regular trading hours (1), or all available hours (0)
	 * 			ignoreSize	A filter only used when the source price is Bid_Ask
	 * 			miscOptions	should be defined as null, reserved for internal use
	*/		
	}

	void reqIds	(int numIds)	{
	/**
	 * 	Requests the next valid order ID at the current moment.
	 * 
	 * 	@param	numIds	deprecated- this parameter will not affect the value returned to nextValidId
	 * 
	 * 	@see	EWrapper::nextValidId	
	*/	
	}

	void reqManagedAccts(){
	/**
	 * 	Requests the accounts to which the logged user has access to.
	 * 
	 * 	@see	EWrapper::managedAccounts
	*/
	}
	
	void reqMarketDataType(int marketDataType){
	/**
	 * 	Switches data type returned from reqMktData request to "frozen", "delayed" or 
	 * 	"delayed-frozen" market data. Requires TWS/IBG v963+.
	 * 	The API can receive frozen market data from Trader Workstation. Frozen market data is the last data recorded in our system.
	 * 	During normal trading hours, the API receives real-time market data. Invoking this function with argument 
	 * 	2 requests a switch to frozen data immediately or after the close.
	 * 	When the market reopens the next data the market data type will automatically switch back to real time if available.
	 * 
	 * 	@param	marketDataType:	by default only real-time (1) market data is enabled sending 1 (real-time) 
	 * 			disables frozen, delayed and delayed-frozen market data sending 2 (frozen) enables 
	 * 			frozen market data sending 3 (delayed) enables delayed and disables delayed-frozen 
	 * 			market data sending 4 (delayed-frozen) enables delayed and delayed-frozen market data
	*/	
	}

	void reqMarketDepth (int tickerId, Contract contract, int numRows, List< TagValue > mktDepthOptions){
	/**
	 * 	Requests the contract's market depth (order book).
	 * 	This request must be direct-routed to an exchange and not smart-routed. The number of simultaneous 
	 * 	market depth requests allowed in an account is calculated based on a formula that looks at an accounts equity, 
	 * 	commissions, and quote booster packs.
	 * 
	 * 	@param	tickerId	the request's identifier
	 * 			contract	the Contract for which the depth is being requested
	 * 			numRows	the number of rows on each side of the order book
	 * 
	 * 	@see 	cancelMktDepth, EWrapper::updateMktDepth, EWrapper::updateMktDepthL2	
	*/	
	}
	
	void reqMarketRule(int marketRuleId){
	/**
	 * 	Requests details about a given market rule
	 * 
	 * 	The market rule for an instrument on a particular exchange provides details about how the minimum 
	 * 	price increment changes with price
	 * 	A list of market rule ids can be obtained by invoking reqContractDetails on a particular contract. 
	 * 	The returned market rule ID list will provide the market rule ID for the instrument in the 
	 * 	correspond valid exchange list in contractDetails.
	 * 
	 * 	@param	marketRuleId	- the id of market rule
	 * 
	 * 	@see	EWrapper::marketRule
	*/	
	}

	void reqMatchingSymbols (int reqId, String pattern){
	/**
	 * 	Requests matching stock symbols.
	 * 
	 * 	@param	reqId	id to specify the request
	 * 			pattern	- either start of ticker symbol or (for larger strings) company name
	 * 
	 *  @see	EWrapper::symbolSamples	
	*/	
	}

	void reqMktData (int tickerId, Contract contract, String genericTickList, Boolean snapshot, Boolean regulatorySnaphsot, List< TagValue > mktDataOptions){
	/**
	 * 	Requests real time market data. Returns market data for an instrument either in real time or 
	 * 	10-15 minutes delayed (depending on the market data type specified)
	 * 
	 * 	@param	tickerId	the request's identifier
	 * 			contract	the Contract for which the data is being requested
	 * 			genericTickList	comma separated ids of the available generic ticks:
	 * 				100 Option Volume (currently for stocks)
	 * 				101 Option Open Interest (currently for stocks)
	 * 				104 Historical Volatility (currently for stocks)
	 * 				105 Average Option Volume (currently for stocks)
	 * 				106 Option Implied Volatility (currently for stocks)
	 * 				162 Index Future Premium
	 * 				165 Miscellaneous Stats
	 * 				221 Mark Price (used in TWS P&L computations)
	 * 				225 Auction values (volume, price and imbalance)
	 * 				233 RTVolume - contains the last trade price, last trade size, last trade time, total volume,
	 * 					VWAP, and single trade flag.
	 * 				236 Shortable
	 * 				256 Inventory
	 * 				258 Fundamental Ratios
	 * 				411 Realtime Historical Volatility
	 * 				456 IBDividends
	 * 			snapshot	for users with corresponding real time market data subscriptions. 
	 * 						A true value will return a one-time snapshot, while a false value will provide streaming data.
	 * 			regulatory	snapshot for US stocks requests NBBO snapshots for users which have "US Securities Snapshot Bundle" 
	 * 						subscription but not corresponding Network A, B, or C subscription necessary for streaming * market data. 
	 * 						One-time snapshot of current market price that will incur a fee of 1 cent to the account per snapshot.
	 * 
	 * 	@see	cancelMktData, EWrapper::tickPrice, EWrapper::tickSize, EWrapper::tickString, EWrapper::tickEFP,
	 * 			EWrapper::tickGeneric, EWrapper::tickOptionComputation, EWrapper::tickSnapshotEnd
	*/	
	}
	
	@Override
	public void positionMultiEnd(int reqId) {
	}

	@Override
	public void bondContractDetails(int reqId, ContractDetails contractDetails) {
	}

	@Override
	public void contractDetails(int reqId, ContractDetails contractDetails) {
	}

	@Override
	public void contractDetailsEnd(int reqId) {
	}

	@Override
	public void fundamentalData(int reqId, String data) {
	}

	public void bondContractDetails(ContractDetails contractDetails) {
	}

	public void contractDetails(ContractDetails contractDetails) {
	}

	@Override
	public void currentTime(long time) {
	}

	@Override
	public void displayGroupList(int requestId, String contraftInfo) {
	}

	@Override
	public void displayGroupUpdated(int requestId, String contractInfo) {
	}

	@Override
	public void verifyAndAuthCompleted(boolean isSuccessful, String errorText) {
	}

	@Override
	public void verifyAndAuthMessageAPI(String apiData, String xyzChallange) {
	}

	@Override
	public void verifyCompleted(boolean completed, String contractInfo) {
	}

	@Override
	public void verifyMessageAPI(String message) {
	}

	@Override
	public void execDetailsEnd(int reqId) {
	}

	/**
	 * provides the portfolio's open positions.
	 * 
	 * @param account
	 *            the account holding the position.
	 * @param contract
	 *            the position's Contract
	 * @param pos
	 *            the number of positions held.
	 * @param avgCost
	 *            the average cost of the position.
	 * @see positionEnd, EClientSocket::reqPositions
	 */	
	@Override
	public void position(String account, Contract contract, double pos, double avgCost) {
	}

	public void position(String account, Contract contract, int pos, double avgCost) {
	}

	/**
	 * Indicates all the positions have been transmitted.
	 * 
	 * @see position, EClient::reqPositions
	 */	
	@Override
	public void positionEnd() {
	}

	@Override
	public void accountSummary(int reqId, String account, String tag, String value, String currency) {
	}

	@Override
	public void accountSummaryEnd(int reqId) {
	}

	/**
	 * Notifies the end of the open orders' reception.
	 * 
	 * @see orderStatus, openOrder, EClientSocket::placeOrder, EClientSocket::reqAllOpenOrders, EClientSocket::reqAutoOpenOrders
	 */	
	@Override
	public void openOrderEnd() {
	}

	public void orderStatus(int orderId, String status, double filled, double remaining, double avgFillPrice,
			int permId, int parentId, double lastFillPrice, int clientId, String whyHeld) {
	}

	public void orderStatus(int orderId, String status, int filled, int remaining, double avgFillPrice, int permId,
			int parentId, double lastFillPrice, int clientId, String whyHeld) {
	}

	@Override
	public void receiveFA(int faDataType, String xml) {
	}

	/**
	 * provides the data resulting from the market scanner request.
	 * 
	 * @param reqId
	 *            the request's identifier.
	 * @param rank
	 *            the ranking within the response of this bar.
	 * @param contractDetails
	 *            the data's ContractDetails
	 * @param distance
	 *            according to query.
	 * @param benchmark
	 *            according to query.
	 * @param projection
	 *            according to query.
	 * @param legsStr
	 *            describes the combo legs when the scanner is returning EFP
	 * @see scannerParameters, scannerDataEnd, EClientSocket::reqScannerSubscription
	 */	
	@Override
	public void scannerData(int reqId, int rank, ContractDetails contractDetails, String distance, String benchmark,
			String projection, String legsStr) {
	}

	@Override
	public void scannerDataEnd(int reqId) {
	}

	@Override
	public void scannerParameters(String xml) {
	}

	@Override
	public void tickEFP(int symbolId, int tickType, double basisPoints, String formattedBasisPoints,
			double impliedFuture, int holdDays, String futureExpiry, double dividendImpact, double dividendsToExpiry) {
	}

	@Override
	public void tickGeneric(int symbolId, int tickType, double value) {
	}

	@Override
	public void tickOptionComputation(int tickerId, int field, double impliedVol, double delta, double optPrice,
			double pvDividend, double gamma, double vega, double theta, double undPrice) {
	}

	// public void deltaNeutralValidation(int reqId, UnderComp underComp)
	@Override
	public void deltaNeutralValidation(int reqId, DeltaNeutralContract underComp) {
	}

	@Override
	public void updateMktDepth(int symbolId, int position, int operation, int side, double price, int size) {
	}

	@Override
	public void updateMktDepthL2(int symbolId, int position, String marketMaker, int operation, int side, double price,
			int size) {
	}

	/**
	 * provides IB's bulletins
	 * 
	 * @param msgId
	 *            the bulletin's identifier
	 * @param msgType
	 *            one of: 1 - Regular news bulletin 2 - Exchange no longer available for trading 
	 *            3 - Exchange is available for trading
	 * @param message
	 *            the message
	 * @param origExchange
	 *            the exchange where the message comes from.
	 */	
	@Override
	public void updateNewsBulletin(int msgId, int msgType, String message, String origExchange) {
	}

	@Override
	public void marketDataType(int reqId, int marketDataType) {
	}

	@Override
	public void tickSnapshotEnd(int tickerId) {
	}

	@Override
	public void connectionClosed() {
	}

	// Add connectAck for API version 9.72
	@Override
	public void connectAck() {
	}

	@Override
	public void error(Exception e) {
		// Print out a stack trace for the exception
		e.printStackTrace();
	}

	@Override
	public void error(String str) {
		// Print out the error message
		System.err.println(str);
	}

	@Override
	public void error(int id, int errorCode, String errorMsg) {
		// Overloaded error event (from IB) with their own error
		// codes and messages
		System.err.println("error: " + id + "," + errorCode + "," + errorMsg);
	}

	@Override
	public void tickString(int orderId, int tickType, String value) {
	}

	@Override
	public void familyCodes(FamilyCode[] arg0) {
	}

	@Override
	public void headTimestamp(int arg0, String arg1) {
	}

	@Override
	public void histogramData(int arg0, List<HistogramEntry> arg1) {
	}

	@Override
	public void historicalData(int arg0, Bar arg1) {
	}

	@Override
	public void historicalDataEnd(int arg0, String arg1, String arg2) {
	}

	@Override
	public void historicalDataUpdate(int arg0, Bar arg1) {
	}

	@Override
	public void historicalNews(int arg0, String arg1, String arg2, String arg3, String arg4) {
	}

	@Override
	public void historicalNewsEnd(int arg0, boolean arg1) {
	}

	@Override
	public void marketRule(int arg0, PriceIncrement[] arg1) {
	}
	
	/**
	 * called when receives Depth Market Data Descriptions
	 * 
	 * @param depthMktDataDescriptions
	 *            Stores a list of DepthMktDataDescriprion
	 * @see EClient::reqMktDepthExchanges
	 */	
	@Override
	public void mktDepthExchanges(DepthMktDataDescription[] depthMktDataDescriptions) {
	}

	/**
	 * called when receives News Article
	 * 
	 * @param requestId
	 *            The request ID used in the call to EClient::reqNewsArticle
	 * @param articleType
	 *            The type of news article (0 - plain text or html, 1 - binary data / pdf)
	 * @param articleText
	 *            The body of article (if articleType == 1, the binary data is encoded using the Base64 scheme)
	 * @see EClient::reqNewsArticle
	 */	
	@Override
	public void newsArticle(int requestId, int articleType, String articleText) {
	}

	/**
	 * returns array of subscribed API news providers for this user
	 * 
	 * @param newsProviders
	 *            The request ID used in the call to EClient::reqNewsArticle
	 * @see EClient::reqNewsProviders
	 */	
	@Override
	public void newsProviders(NewsProvider[] newsProviders) {
	}

	/**
	 * Feeds in currently open orders.
	 * 
	 * @param orderId
	 *            the order's unique id
	 * @param contract
	 *            the order's Contract.
	 * @param order
	 *            the currently active Order.
	 * @param orderState
	 *            the order's OrderState
	 * @see orderStatus, openOrderEnd, EClientSocket::placeOrder, EClientSocket::reqAllOpenOrders, 
	 * EClientSocket::reqAutoOpenOrders
	 */	
	@Override
	public void openOrder(int orderId, Contract contract, com.ib.client.Order order, OrderState orderState) {
	}

	/**
	 * Gives the up-to-date information of an order every time it changes. Often there are duplicate orderStatus messages.
	 * 
	 * @param orderId
	 *            the order's client id.
	 * @param status
	 *            the current status of the order. Possible values: PendingSubmit - indicates that you have transmitted 
	 *            the order, but have not yet received confirmation that it has been accepted by the order destination. 
	 *            PendingCancel - indicates that you have sent a request to cancel the order but have not yet received 
	 *            cancel confirmation from the order destination. At this point, your order is not confirmed canceled. 
	 *            It is not guaranteed that the cancellation will be successful. PreSubmitted - indicates that a simulated 
	 *            order type has been accepted by the IB system and that this order has yet to be elected. The order is 
	 *            held in the IB system until the election criteria are met. At that time the order is transmitted to 
	 *            the order destination as specified . Submitted - indicates that your order has been accepted by the 
	 *            system. ApiCanceled - after an order has been submitted and before it has been acknowledged, an API 
	 *            client client can request its cancelation, producing this state. Cancelled - indicates that the balance 
	 *            of your order has been confirmed canceled by the IB system. This could occur unexpectedly when IB or 
	 *            the destination has rejected your order. Filled - indicates that the order has been completely filled. 
	 *            Market orders executions will not always trigger a Filled status. Inactive - indicates that the order 
	 *            was received by the system but is no longer active because it was rejected or canceled.
	 * @param filled
	 *            number of filled positions.
	 * @param remaining
	 *            the remnant positions.
	 * @param avgFillPrice
	 *            average filling price.
	 * @param permId
	 *            the order's permId used by the TWS to identify orders.
	 * @param parentId
	 *           parent's id. Used for bracket and auto trailing stop orders.
	 * @param lastFillPrice
	 *           price at which the last positions were filled.
	 * @param clientId
	 *           API client which submitted the order.
	 * @param whyHeld
	 *           this field is used to identify an order held when TWS is trying to locate shares for a short sell. The 
	 *           value used to indicate this is 'locate'.
	 * @param mktCapPrice 
	 *           If an order has been capped, this indicates the current capped price. Requires TWS 967+ and API v973.04+. 
	 *           Python API specifically requires API v973.06+.
	 * @see openOrder, openOrderEnd, EClientSocket::placeOrder, EClientSocket::reqAllOpenOrders, 
	 * EClientSocket::reqAutoOpenOrders
	 */	
	@Override
	public void orderStatus(int orderId, String status, double filled, double remaining, double avgFillPrice, 
			int permId, int parentId, double lastFillPrice, int clientId, String whyHeld, double mktCapPrice ) {
	}

	@Override
	public void pnl(int arg0, double arg1, double arg2, double arg3) {
	}

	@Override
	public void pnlSingle(int arg0, int arg1, double arg2, double arg3, double arg4, double arg5) {
	}

	@Override
	public void rerouteMktDataReq(int arg0, int arg1, String arg2) {
	}

	@Override
	public void rerouteMktDepthReq(int arg0, int arg1, String arg2) {
	}

	/**
	 * bit number to exchange + exchange abbreviation dictionary
	 * 
	 * @param reqId
	 *            the request's identifier.
	 * @param theMap 
	 *            sa EClient::reqSmartComponents
	 */
	@Override
	public void smartComponents(int reqId, Map<Integer, Entry<String, Character>> theMap) {
	}

	/**
	 * called when receives Soft Dollar Tier configuration information
	 * 
	 * @param reqId
	 *            The request ID used in the call to EClient::reqSoftDollarTiers
	 * @param tiers 
	 *            Stores a list of SoftDollarTier that contains all Soft Dollar Tiers information
	 * @see EClient::reqSoftDollarTiers
	 */	
	@Override
	public void softDollarTiers(int reqId, SoftDollarTier[] tiers) {
	}

	@Override
	public void symbolSamples(int arg0, ContractDescription[] arg1) {
	}

	@Override
	public void tickNews(int arg0, long arg1, String arg2, String arg3, String arg4, String arg5) {
	}

	@Override
	public void tickReqParams(int arg0, double arg1, String arg2, int arg3) {
	}

	// EClientSocket::reqAccountUpdates

	/**
	 * Notifies when all the account's information has finished.
	 * 
	 * @param account
	 *            the account's id see EClientSocket::reqAccountUpdates
	 */
	@Override
	public void accountDownloadEnd(String accountName) {
	}

	/**
	 * Receives the last time on which the account was updated.
	 * 
	 * @param timestamp
	 *            the last update system time.
	 * @see EClientSocket::reqAccountUpdates
	 */
	@Override
	public void updateAccountTime(String timeStamp) {
	}

	/**
	 * Receives the subscribed account's information. Only one account can be
	 * subscribed at a time. After the initial callback to updateAccountValue,
	 * callbacks only occur for values which have changed. This occurs at the
	 * time of a position change, or every 3 minutes at most. This frequency
	 * cannot be adjusted.
	 * 
	 * @param key
	 *            the value being updated
	 * @param value
	 *            up-to-date value
	 * @param currency
	 *            the currency on which the value is expressed.
	 * @param accountName
	 *            the account
	 * @see https://interactivebrokers.github.io/tws-api/
	 *      interfaceIBApi_1_1EWrapper. html#ae15a34084d9f26f279abd0bdeab1b9b5
	 * @see EClientSocket::reqAccountUpdates
	 */
	@Override
	public void updateAccountValue(String key, String value, String currency, String accountName) {
	}

	/**
	 * Receives the subscribed account's portfolio. This function will receive
	 * only the portfolio of the subscribed account. If the portfolios of all
	 * managed accounts are needed, refer to EClientSocket::reqPosition After
	 * the initial callback to updatePortfolio, callbacks only occur for
	 * positions which have changed.
	 * 
	 * @param contract
	 *            the Contract for which a position is held.
	 * @param position
	 *            the number of positions held.
	 * @param marketPrice
	 *            instrument's unitary price
	 * @param marketValue
	 *            total market value of the instrument.
	 * @see EClientSocket::reqAccountUpdates
	 */
	@Override
	public void updatePortfolio(Contract contract, double position, double marketPrice, double marketValue,
			double averageCost, double unrealizedPNL, double realizedPNL, String accountName) {
	}

	// EClientSocket::reqExecutions

	/**
	 * provides the CommissionReport of an Execution see
	 * EClientSocket::reqExecutions
	 */
	@Override
	public void commissionReport(CommissionReport cr) {
	}

	/**
	 * Provides the executions which happened in the last 24 hours.
	 * 
	 * @param reqId
	 *            the request's identifier
	 * @param contract
	 *            the Contract of the Order
	 * @param execution
	 *            the Execution details.
	 * @see EClientSocket::reqExecutions
	 */
	@Override
	public void execDetails(int reqId, Contract contract, Execution execution) {
	}

	// EClient::reqHistoricalData
	/**
	 * 
	 * @param reqId
	 * @param ticks
	 *            list of HistoricalTick data
	 * @param done
	 *            flag to indicate if all historical tick data has been received
	 */
	@Override
	public void historicalTicks(int reqId, List<HistoricalTick> ticks, boolean done) {
	}

	/**
	 * @param reqId
	 * @param ticks
	 *            list of HistoricalBidAsk data
	 * @param done
	 *            flag to indicate if all historical tick data has been received
	 * 
	 */
	@Override
	public void historicalTicksBidAsk(int reqId, List<HistoricalTickBidAsk> ticks, boolean done) {
	}

	/**
	 * 
	 * @param reqId
	 * @param ticks
	 *            list of HistoricalTickLast data
	 * @param done
	 *            flag to indicate if all historical tick data has been received
	 * @see
	 */
	@Override
	public void historicalTicksLast(int reqId, List<HistoricalTickLast> ticks, boolean done) {
	}

	// EClientSocket::reqManagedAccts

	/**
	 * Receives a comma-separated string with the managed account ids. Occurs
	 * automatically on initial API client connection.
	 * 
	 * @see EClientSocket::reqManagedAccts
	 */
	@Override
	public void managedAccounts(String accountsList) {
	}

	// EClientSocket::reqMktData

	/**
	 * Market data tick size callback. Handles all size-related ticks.
	 * 
	 * @param tickerId
	 *            the request's unique identifier.
	 * @param field
	 *            the type of size being received (i.e. bid size)
	 * @param size
	 *            the actual size. US stocks have a multiplier of 100.
	 * @see EClientSocket::reqMktData
	 */
	@Override
	public void tickSize(int tickerId, int field, int size) {
	}

	/**
	 * Market data tick price callback. Handles all price related ticks. Every
	 * tickPrice callback is followed by a tickSize. A tickPrice value of -1 or
	 * 0 followed by a tickSize of 0 indicates there is no data for this field
	 * currently available, whereas a tickPrice with a positive tickSize
	 * indicates an active quote of 0 (typically for a combo contract).
	 * 
	 * @param tickerId
	 *            the request's unique identifier
	 * @param field
	 *            the type of the price being received (i.e. ask price).
	 * @param price
	 *            the actual price.
	 * @param attribs
	 *            an TickAttrib object that contains price attributes such as
	 *            TickAttrib::CanAutoExecute, TickAttrib::PastLimit and
	 *            TickAttrib::PreOpen.
	 * @see See Also TickType, tickSize, tickString, tickEFP, tickGeneric,
	 *      tickOptionComputation, tickSnapshotEnd, marketDataType,
	 *      EClientSocket::reqMktData
	 */
	@Override
	public void tickPrice(int tickerId, int field, double price, TickAttr attribs) {
	}

	/**
	 * returns "Last" or "AllLast" tick-by-tick real-time tick
	 * 
	 * @param reqId
	 *            - unique identifier of the request
	 * @param tickType
	 *            - tick-by-tick real-time tick type: "Last" or "AllLast"
	 * @param time
	 *            - tick-by-tick real-time tick timestamp
	 * @param price
	 *            - tick-by-tick real-time tick last price
	 * @param size
	 *            - tick-by-tick real-time tick last size
	 * @param attribs
	 *            - tick-by-tick real-time tick attribs (bit 0 - past limit, bit
	 *            1 - unreported)
	 * @param exchange
	 *            - tick-by-tick real-time tick exchange
	 * @param specialConditions
	 *            - tick-by-tick real-time tick special conditions
	 * @see EClient::reqTickByTickData
	 */
	@Override
	public void tickByTickAllLast(int reqId, int tickType, long time, double price, int size, TickAttr attribs,
			String exchange, String specialConditions) {
	}

	/**
	 * returns "BidAsk" tick-by-tick real-time tick
	 * 
	 * @param reqId
	 *            - unique identifier of the request
	 * @param time
	 *            - tick-by-tick real-time tick timestamp
	 * @param bidPrice
	 *            - tick-by-tick real-time tick bid price
	 * @param askPrice
	 *            - tick-by-tick real-time tick ask price
	 * @param bidSize
	 *            - tick-by-tick real-time tick bid size
	 * @param askSize
	 *            - tick-by-tick real-time tick ask size
	 * @param attribs
	 *            - tick-by-tick real-time tick attribs (bit 0 - bid past low,
	 *            bit 1 - ask past high)
	 * @see EClient::reqTickByTickData
	 */
	@Override
	public void tickByTickBidAsk(int reqId, long time, double bidPrice, double askPrice, int bidSize, int askSize, 
			TickAttr attribs) {
	}

	/**
	 * returns "MidPoint" tick-by-tick real-time tick
	 * 
	 * @param reqId
	 *            - unique identifier of the request
	 * @param time
	 *            - tick-by-tick real-time tick timestamp
	 * @param midPoint
	 *            - tick-by-tick real-time tick mid point
	 * @see EClient::reqTickByTickData
	 */
	@Override
	public void tickByTickMidPoint(int reqId, long time, double midPoint) {
	}

}
