package com.advanced.app;

import java.util.Scanner;

import com.advanced.util.StockService;
import com.advanced.util.StockServiceFactory;


/**
 * A simple application that shows the StockService in action. This version
 * retrieves stock data from the stock service online (Alpha Vantage).
 */
public class AlphaVantageStockQuoteApplication {

	public static void main(String[] args) {

		StockService stockService = StockServiceFactory.getStockService();
		Scanner scanner = new Scanner(System.in);
		String interval = null;
		String outputSize = null;
		String jsonResultsString = null;

		// Prompt for input
		System.out.print("\nEnter Ticker Symbol: ");
		String symbol = scanner.nextLine();
		System.out.print("\nEnter Time Series (Daily, Weekly, Monthly, Intraday, Endpoint):\n"
				+ "NOTE: Default (left blank) is Daily.\n");
		String timeSeries = scanner.nextLine().toLowerCase().trim();
		if (timeSeries.contentEquals("intraday")) {
			System.out.print("\nEnter Interval (1min, 5min, 15min, 30min, 60min): ");
			interval = scanner.nextLine().toLowerCase().trim();
			System.out.print("\nEnter Output Size (Compact, Full):\n"
					+ "NOTE: Compact (recommended to reduce the data size of each API call) returns "
					+ "only the latest 100 stockQuotes.\n      Full returns the full-length " 
					+ timeSeries + " time series.\n");
			outputSize = scanner.nextLine().toLowerCase().trim();
		} else if (timeSeries.contains("daily")) {
			System.out.print("\nEnter Output Size (Compact, Full):\n"
					+ "NOTE: Compact (recommended to reduce the data size of each API call) returns "
					+ "only the latest 100 stockQuotes.\n      Full returns the full-length " 
					+ timeSeries + " time series.\n");
			outputSize = scanner.nextLine().toLowerCase().trim();
		}
		
		scanner.close();
		System.out.println("");
		
		//Retrieve the data
		switch(timeSeries) {
		case "daily":
			jsonResultsString = stockService.getQuotes(symbol, timeSeries, outputSize);
			System.out.println(jsonResultsString);
		    break;
		case "weekly":
			jsonResultsString = stockService.getQuotes(symbol, timeSeries);
			System.out.println(jsonResultsString);
		    break;
		case "monthly":
			jsonResultsString = stockService.getQuotes(symbol, timeSeries);
			System.out.println(jsonResultsString);
			break;
		case "intraday":
			jsonResultsString = stockService.getQuotes(symbol, timeSeries, interval, outputSize);
			System.out.println(jsonResultsString);
			break;
		case "endpoint":
			jsonResultsString = stockService.getQuotes(symbol, timeSeries);
			System.out.println(jsonResultsString);
			break;
		default:
		    //Daily
			jsonResultsString = stockService.getQuotes(symbol, timeSeries, outputSize);
			System.out.println(jsonResultsString);
		}
	}
}