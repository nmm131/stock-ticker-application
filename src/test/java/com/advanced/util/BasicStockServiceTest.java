package com.advanced.util;

import org.junit.Before;
import org.junit.Test;

import com.advanced.util.BasicStockService;
import com.advanced.util.StockQuote;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
*
* JUNit test for BasicStockService Class
*
*/

public class BasicStockServiceTest {
	
	private double value;
    private String tickerSymbol;
    private String strFromDate;
    private String strUntilDate;
    private String strInterval;
    private Calendar date;
    private Calendar date2;
    private Calendar fromDate;
    private Calendar untilDate;
    private SimpleDateFormat simpleDateFormat;
    private List<StockQuote> stockHistory = new ArrayList<>(); 
    private BasicStockService basicStockServiceExpected = new BasicStockService();
	
	//Create a state
	@Before
	public void setup() {
		
		value = 450.23; //StockQuote hard-coded value
    	tickerSymbol = "APPL"; //StockQuote hard-coded tickerSymbol
    	strFromDate = "15/01/2020"; //First month begins at one (1)
    	strUntilDate = "15/12/2020"; //First month begins at one (1)
    	strInterval = "DAILY";
    	date = Calendar.getInstance(); //StockQuote hard-coded date
    	fromDate = Calendar.getInstance();
    	untilDate = Calendar.getInstance();
    	simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    	stockHistory = basicStockServiceExpected.getQuote(tickerSymbol, fromDate, untilDate, strInterval);
    	date2 = Calendar.getInstance(); //StockQuote hard-coded date
    	date2.set(2000, 11, 15, 00, 00, 00);
    	
    	//Convert Strings to Dates
    	try {
    		//From Date 
    		Date fDate = simpleDateFormat.parse(strFromDate);
    		fromDate.setTime(fDate);
    		
    		//Until Date    		
    		Date uDate = simpleDateFormat.parse(strUntilDate);
    		untilDate.setTime(uDate);
    		
    		//Interval Enum
    		strInterval = strInterval.toUpperCase().trim();
    	} catch (ParseException e) {
    		e.printStackTrace();
    	}
	}
		
	@Test
	public void testGetQuoteSingleArg() {
		//Change the state
		BasicStockService basicStockService = new BasicStockService();
		
		//Verify the state
		System.out.println(value + " \n" + tickerSymbol + " \n" + date2.getTime() + "\n" + basicStockService.getQuote(tickerSymbol).getValue());
		assertEquals("The value is correct.", value, basicStockService.getQuote(tickerSymbol).getValue().doubleValue());
		assertEquals("The tickerSymbol is correct.", tickerSymbol, basicStockService.getQuote(tickerSymbol).getTickerSymbol());
		assertTrue("The date is correct.", ((date2.getTimeInMillis() - basicStockService.getQuote(tickerSymbol).getDate().getTimeInMillis()) < 1000));
	}

	@Test
	public void testGetQuoteFourArgs() {
		//Change the state
		BasicStockService basicStockService = new BasicStockService();
		
		//Verify the state
		for (int i = 0; i < stockHistory.size(); i++) {
			assertEquals("The StockQuote List is correct.", stockHistory, basicStockService.getQuote(tickerSymbol, fromDate, untilDate, strInterval));
			assertEquals("The tickerSymbol is correct.", tickerSymbol, stockHistory.get(i).getTickerSymbol());
		}
	}
}
