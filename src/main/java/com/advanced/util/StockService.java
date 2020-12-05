package com.advanced.util;

import java.util.Calendar;
import java.util.List;

import com.advanced.util.StockQuote.IntervalEnum;

public interface StockService {
	 /**
	 * Return the current price for a share of stock for the
	 * given symbol
	 * @param symbol the stock symbol of the company you want a
	 * quote for e.g. APPL for APPLE
	 * @return a <CODE>StockQuote</CODE> instance
	 */
	 StockQuote getQuote(String symbol) throws StockServiceException;
	 /**
	 * Get a historical list of stock quotes for the provided
	 * symbol
	 * @param symbol the stock symbol to search for
	 * @param from the date of the first stock quote
	 * @param until the date of the last stock quote
	 * @return a list of StockQuote instances.
	 * One for each day in the range specified.
	 * @throws StockServiceException 
	 */
	 List<StockQuote> getQuote(String symbol, Calendar from, Calendar
			 until, String interval) throws StockServiceException;
	 
	void addStocksToDatabase(String symbol, double price, Calendar time) throws StockServiceException; 
	
	public String getQuotes(String p_symbol, String p_timeSeries);
	
	public String getQuotes(String p_symbol, String p_timeSeries, String p_outputSize);
	
	public String getQuotes(String p_symbol, String p_timeSeries, String p_interval, String p_outputSize);
}
