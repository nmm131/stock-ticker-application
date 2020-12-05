package com.advanced.util;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.validation.constraints.NotNull;

public class StockQuote {

    private final BigDecimal value;
	private final String tickerSymbol;
	private final Calendar date;
    
    public enum IntervalEnum {
    	HOURLY, TWELVEHOUR, DAILY;
    }
    
    /**
	 * No-arg constructor marked private
	 * This isn't really necessary but since it was listed in the assignment
	 * instructions, I put it in.
	 */
	@SuppressWarnings("unused")
	private StockQuote() {
		this.value = null;
		this.tickerSymbol = null;
		this.date = null;
	}
    
    /**
	 * Overloaded constructor
	 * 
	 * @param p_value
	 * @param p_tickerSymbol
	 * @param p_date
	 */
	protected StockQuote(@NotNull double p_value, @NotNull String p_tickerSymbol,@NotNull Calendar p_date) {
		this.value = new BigDecimal(p_value);
		this.tickerSymbol = p_tickerSymbol;		
		this.date = p_date;
	}
    
	/**
	 * Overloaded constructor
	 * 
	  * @param p_value
	 * @param p_tickerSymbol
	 */
	protected StockQuote(@NotNull double p_value, @NotNull String p_tickerSymbol) {
		this.value = new BigDecimal(p_value);
		this.tickerSymbol = p_tickerSymbol;		
		this.date = Calendar.getInstance();
	}
    
	/**
	 * Copy constructor
	 * 
	 * @param p_stockQuote
	 */

	public StockQuote(StockQuote p_stockQuote) {
		this.value = p_stockQuote.value;
		this.tickerSymbol = p_stockQuote.tickerSymbol;
		this.date = p_stockQuote.date;
	}
    
	// Accessors
		public String getTickerSymbol() {
			return tickerSymbol;
		}

		public BigDecimal getValue() {
			return value;
		}
		
		public Calendar getDate() {
			return date;
		}
}