package com.advanced.util;

import org.junit.Before;
import org.junit.Test;

import com.advanced.util.StockServiceException;
import com.advanced.util.StockServiceFactory;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import java.util.Calendar;


/**
*
* JUNit test for StockServiceFactory Class
*
*/

public class StockServiceFactoryTest {
	
	private double value;
    private String tickerSymbol;
    private Calendar date;
	
	//Create a state
	@Before
	public void setup() {
		
		value = 363.00; //StockQuote hard-coded value
    	tickerSymbol = "APPL"; //StockQuote hard-coded tickerSymbol
    	date = Calendar.getInstance(); //StockQuote hard-coded date
    	date.set(2000, 11, 15, 00, 00, 00);
    }
	
	@Test
	public void testGetStockService() throws StockServiceException {
		//Change the state
		StockServiceFactory.getStockService();
		
		//Verify the state
				try {
					assertEquals("The value is correct.", value, StockServiceFactory.getStockService().getQuote(tickerSymbol).getValue().doubleValue());
				} catch (StockServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				try {
					assertEquals("The tickerSymbol is correct.", tickerSymbol, StockServiceFactory.getStockService().getQuote(tickerSymbol).getTickerSymbol());
				} catch (StockServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				try {
					assertTrue("The date is correct.", ((date.getTimeInMillis() - StockServiceFactory.getStockService().getQuote(tickerSymbol).getDate().getTimeInMillis()) < 1000));
				} catch (StockServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}	
}
