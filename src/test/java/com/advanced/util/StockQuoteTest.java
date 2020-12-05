package com.advanced.util;

import org.junit.Before;
import org.junit.Test;

import com.advanced.util.StockQuote;

import static junit.framework.Assert.assertEquals;
import java.util.Calendar;


/**
*
* JUNit test for StockQuote Class
*
*/

public class StockQuoteTest {
	
	private double value;
    private String tickerSymbol;
    private Calendar date;
    private StockQuote stockQuote;
	
	//Create a state
	@Before
	public void setup() {
		
		value = 1585.00; //StockQuote hard-coded value
    	tickerSymbol = "ZFZA"; //StockQuote hard-coded tickerSymbol
    	date = Calendar.getInstance(); //StockQuote hard-coded date
    	date.set(2994, 07, 02);
    }
	
	@Test
	public void testConstruction() {
		//Change the state
		stockQuote = new StockQuote(value, tickerSymbol, date);
		
		//Verify the state
		assertEquals("The value is correct.", value, stockQuote.getValue().doubleValue());
		assertEquals("The tickerSymbol is correct.", tickerSymbol, stockQuote.getTickerSymbol());
		assertEquals("The date is correct.", date, stockQuote.getDate());
	}	
}
