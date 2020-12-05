package com.advanced.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.http.annotation.Immutable;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicStockService implements StockService {
    
	/**
	 * No-arg constructor
	 * 
	 */
	protected BasicStockService() {
	}
    
	/**
	 * Return the current price for a share of stock for the given symbol
	 * 
	 * @param tickerSymbol the stock symbol of the company you want a quote for.
	 *                     e.g. APPL for APPLE
	 *
	 * @return a <CODE>StockQuote </CODE> instance
	 */
	public StockQuote getQuote(String tickerSymbol) {
		return new StockQuote(450.23, "APPL", Calendar.getInstance());
	}

    public List<StockQuote> getQuote(@NotNull String symbol, @NotNull Calendar startDate, @NotNull Calendar endDate, @NotNull String interval) {

		// I'm calling a private helper method to create some StockQuote objects in
		// order to keep the
		// getQuote method uncluttered. When I have a real data source, I won't need the
		// helper method.
		List<StockQuote> tmpObjects = createObjects();

		// Create an empty list to store the results in
		List<StockQuote> stockHistory = new ArrayList<>();

		// Return just the StockQuote objects that fall between the start and end dates,
		// using the specified interval
		long intervalvalue = IntervalEnum.getInterval(interval);
		for (StockQuote quote : tmpObjects) {
			if ((quote.getDate().getTimeInMillis() >= startDate.getTimeInMillis())
					&& (quote.getDate().getTimeInMillis() <= endDate.getTimeInMillis())) {
				stockHistory.add(quote);
			}
		}
		return stockHistory;
	}

    /**
	 * I could have made this a separate top level enum but since it's tightly
	 * coupled to the stock service app, I decided to put it inside the stock
	 * service implementation. I also decided to keep the implementation simplh by
	 * supplying a static initialization block which will be executed once when the
	 * enum is loaded.
	 *
	 */
	enum IntervalEnum {

		DAILY, MONTHLY, YEARLY;

		private final static Map<IntervalEnum, Integer> intervalValue = new HashMap<>();

		static {
			intervalValue.put(DAILY, 1);
			intervalValue.put(MONTHLY, 30);
			intervalValue.put(YEARLY, 365);
		}

		private IntervalEnum() {
		}

		/**
		 *
		 * @return the interval value 86400000 is the number of milliseconds in a day To
		 *         find 30 days, multiply by 30 To find 1 year, multiply by 365
		 */
		public static long getInterval(String p_value) {
			IntervalEnum enumValue = IntervalEnum.valueOf(p_value);
			long value = intervalValue.get(enumValue) * 86400000;
			return value;
		}

		/**
		 * @override
		 * @return the interval string
		 */
		public String toString(IntervalEnum value) {
			return value.toString();
		}
	}

	/**
	 * Temporary private helper method to a stock history
	 * 
	 * @return List<StockQuote>
	 */
	private List<StockQuote> createObjects() {
		List<StockQuote> stockHistory = new ArrayList<>();
		int year = 2015;
		int month = 1;
		int day = 1;
		double price = 100.34;
		for (int i = year; i < 2020; i++) {
			for (int j = month; j < 12; j++) {
				for (int k = day; k < 30; k++) {
					Calendar date = new GregorianCalendar(i, j, k);
					stockHistory.add(new StockQuote(price, "APPL", date));
					price = price + 50 / 1 + 2;
				}
			}
		}

		return stockHistory;
	}
	
	public void addStocksToDatabase(String symbol, double price, Calendar time) {
    }
	
	
	public String getQuotes(String p_symbol, String p_timeSeries) {
		return "{}";
	}
	
	public String getQuotes(String p_symbol, String p_timeSeries, String p_outputSize) {
		return "{}";
	}
	
	public String getQuotes(String p_symbol, String p_timeSeries, String p_interval, String p_outputSize) {
		return "{}";
	}
}
