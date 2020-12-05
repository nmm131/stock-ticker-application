package com.advanced.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.advanced.model.Person;
import com.advanced.service.UserStockServiceException;
import com.advanced.service.UserStockServiceFactory;

public class StockQuoteApplication {

    public static void main(String[] args) throws StockServiceException, UserStockServiceException {
    	
    	String tickerSymbol = args[0];
    	String strFromDate = args[1]; //First month begins at one (1)
    	String strUntilDate = args[2]; //First month begins at one (1)
    	String strInterval = args[3];
    	Calendar fromDate = Calendar.getInstance();
    	Calendar untilDate = Calendar.getInstance();
    	List<StockQuote> stockHistory = new ArrayList<>();
    	StockQuote stockQuote = new StockQuote(14, "APPL", fromDate);
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    	
    	//Convert Strings to Dates and Enum
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
    	
    	//print arguments
    	System.out.println("Finding stocks using these arguments:" + "\nSymbol: " 
    			+ args[0] + "\nFrom: " + args[1] + "\nUntil : " 
    			+ args[2] + "\nInterval: " + args[3] + "\n");
    	
    	//getQuote(String symbol)
    	stockQuote = StockServiceFactory.getStockService().getQuote(tickerSymbol);
    	System.out.println("\nStocks using getQuote(tickerSymbol):");
    	System.out.println(stockQuote.getTickerSymbol() +
    			" " + stockQuote.getValue() +
    			" " + simpleDateFormat.format(stockQuote.getDate().getTime()));
    	
    	//getQuote(String symbol, Calendar from, Calendar until, IntervalEnum interval)
    	System.out.println("");
    	stockHistory = StockServiceFactory.getStockService().getQuote(tickerSymbol, fromDate, untilDate, strInterval);
    	System.out.println("\nStocks using getQuote(tickerSymbol, from, until, interval):");
    	
    	for (int i = 0; i < stockHistory.size(); i++) {
    		if (stockHistory.get(i).getTickerSymbol() == "NotARealTickerSymbolValue") {
    			System.out.println("\n" + args[0] + " Stock on this date: " + simpleDateFormat.format(stockHistory.get(i).getDate().getTime()));
    		} else {
    			System.out.println(stockHistory.get(i).getTickerSymbol() +
            			" " + stockHistory.get(i).getValue() +
            			" " + simpleDateFormat.format(stockHistory.get(i).getDate().getTime()));
    		}
    	}
    	
    	//getPerson
    	Person person = new Person();
    	person.setId(8);
    	Timestamp birthDate = new Timestamp(System.currentTimeMillis());
		person.setBirthDate(birthDate);
    	person.setFirstName("New");
    	person.setLastName("Person");
    	UserStockServiceFactory.getInstance().addOrUpdatePerson(person); //incorporates Person.java set methods
    	System.out.println("\nUserStocks using getPerson():\n" + UserStockServiceFactory.getInstance().getPerson());
    	System.out.println("\nAdding/Updating Person:\nId: " + person.getId());
    	System.out.println("First name: " + person.getFirstName());
    	System.out.println("Last name: " + person.getLastName());
    	System.out.println("Birthdate: " + person.getBirthDate());
    	UserStockServiceFactory.getInstance().addStockSymbolToPerson("ZAZF", person);
    	System.out.println("\nAdding stockSymbol for " + person.getFirstName() + " " + 
    			person.getLastName() + " :\n" + UserStockServiceFactory.getInstance().getStockSymbol(person));
    	System.out.println("\nUserStocks Database (includes newly added/updated person):\n" + UserStockServiceFactory.getInstance().getPerson());
    	
    }
}