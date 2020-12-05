package com.advanced.app;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.json.JSONArray;
import org.json.JSONObject;

import com.advanced.model.Person;
import com.advanced.service.UserStockService;
import com.advanced.service.UserStockServiceException;
import com.advanced.service.UserStockServiceFactory;
import com.advanced.util.StockService;
import com.advanced.util.StockServiceException;
import com.advanced.util.StockServiceFactory;
import com.advanced.xml.Stocks;

import antlr.StringUtils;


/**
 * A simple application that shows the StockService in action. This version
 * retrieves stock data from the stock service online (Alpha Vantage).
 */
public class StockTickerApplication {

	public static void main(String[] args) throws StockServiceException, ParseException, UserStockServiceException, JAXBException {

		StockService stockService = StockServiceFactory.getStockService();
		UserStockService userStockService = UserStockServiceFactory.getInstance();
		Scanner scanner = new Scanner(System.in);
		Date personDate = null;
		String localAction = null;
		String interval = null;
		String outputSize = null;
		String jsonResultsString = null;
		String storeResults = null;
		String intervalTimeSeries = null;
		String xmlString = null;
		Calendar time = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat simpleDateFormatNoSec = new SimpleDateFormat("yyyy-MM-dd");

		// Prompt for input
		System.out.println("\nChoose a Database (Local, Online): ");
		String database = scanner.nextLine().toLowerCase().trim();
		
		if (database.contentEquals("local")) {
			System.out.println("\nWould you like to add users to the Person table (person),\nor upload an XML file containing stock quotes (xml): ");
			localAction = scanner.nextLine().toLowerCase().trim();
			
			if (localAction.contains("person")) {
				Person person = new Person();
		    	//person.setId(userStockService.getPerson().size() + 1);
				System.out.println("\nEnter First Name: ");
				String firstName = scanner.nextLine().trim();
				System.out.println("\nEnter Last Name: ");
				String lastName = scanner.nextLine().trim();
				System.out.println("\nEnter Birth Date (yyyy-MM-dd): ");
				
				try {
					personDate = simpleDateFormatNoSec.parse(scanner.nextLine().trim());
				    Timestamp birthDate = new java.sql.Timestamp(personDate.getTime());
				    person.setBirthDate(birthDate);
				} catch(Exception e) {
				}
		    	person.setFirstName(firstName);
		    	person.setLastName(lastName);
		    	
		    	userStockService.addOrUpdatePerson(person);
		    	System.out.println("\n" + userStockService.getPerson());
		    	System.out.println("\n" + firstName + " " + lastName + " " + personDate + " added to Person Table of Local Database.");
			} else if (localAction.contains("xml")) {
				System.out.println("\nPlease ensure the xml file is in src/main/resources/xml \nWhat is the file name and type? (i.e. stock_info.xml): ");
				String xmlName = scanner.nextLine().trim();
				String xmlPath = "src/main/resources/xml/" + xmlName;

		        try {

		            // default StandardCharsets.UTF_8
		            xmlString = Files.readString(Paths.get(xmlPath));

		        } catch (IOException e) {
		            e.printStackTrace();
		        }

				// here is how to go from XML to Java
		        JAXBContext jaxbContext = JAXBContext.newInstance(Stocks.class);
		        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		        Stocks stocks = (Stocks) unmarshaller.unmarshal(new StringReader(xmlString));
		        System.out.println(stocks.toString());

		        // here is how to go from Java to XML
		        JAXBContext context = JAXBContext.newInstance(Stocks.class);
		        Marshaller marshaller = context.createMarshaller();
		        //for pretty-print XML in JAXB
		        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		        marshaller.marshal(stocks, System.out);
		        
		        for (int i = 0; i < stocks.getStockQuote().size(); i++) {
		        	String symbol = stocks.getStockQuote().get(i).getSymbol();
		        	double price = stocks.getStockQuote().get(i).getPrice();
		        	Calendar xmlDate = stocks.getStockQuote().get(i).getTime();
		        	
		            StockServiceFactory.getStockService().addStocksToDatabase(symbol, price, xmlDate);
		        }
	            System.out.println("\nXML Stocks added to Local Database.");
			}
		} else if (database.contentEquals("online")) {
			//Online
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
			} else if (timeSeries.contains("daily") || timeSeries.contains("")) {
				System.out.print("\nEnter Output Size (Compact, Full):\n"
						+ "NOTE: Compact (recommended to reduce the data size of each API call) returns "
						+ "only the latest 100 stockQuotes.\n      Full returns the full-length " 
						+ timeSeries + " time series.\n");
				outputSize = scanner.nextLine().toLowerCase().trim();
			}
			
			System.out.println("");
			
			//Retrieve the data
			switch(timeSeries) {
			case "daily":
				jsonResultsString = stockService.getQuotes(symbol, timeSeries, outputSize);
				System.out.println(jsonResultsString);
				System.out.println("\nStore the results in the local database (yes, no): ");
				storeResults = scanner.nextLine().toLowerCase().trim();
				
				if (storeResults.contains("yes")) {
					System.out.println("");
					JSONObject jsonResultsObj = new JSONObject(jsonResultsString);
					//add iterate logic
					JSONArray timeSeriesData = jsonResultsObj.getJSONObject("Time Series (Daily)").names();
					for (int i = 0; i < timeSeriesData.length(); ++i) {
						String stockDate = timeSeriesData.getString(i);
						JSONObject stockPrice = jsonResultsObj.getJSONObject("Time Series (Daily)").getJSONObject(stockDate);
						double price = Double.parseDouble(stockPrice.getString("4. close"));
						time.setTime(simpleDateFormatNoSec.parse(stockDate));
						stockService.addStocksToDatabase(symbol, price, time);	  
						System.out.println("TickerSymbol: " + symbol.toUpperCase() + " Date: " + stockDate + " Price: " 
								+ stockPrice.getString("4. close"));
					}
					System.out.println("\nStock Data added to Local Database.");
				}
			    break;
			case "weekly":
				jsonResultsString = stockService.getQuotes(symbol, timeSeries);
				System.out.println(jsonResultsString);
				System.out.println("\nStore the results in the local database (yes, no): ");
				storeResults = scanner.nextLine().toLowerCase().trim();
				
				if (storeResults.contains("yes")) {
					System.out.println("");
					JSONObject jsonResultsObj = new JSONObject(jsonResultsString);
					//add iterate logic
					JSONArray timeSeriesData = jsonResultsObj.getJSONObject("Weekly Time Series").names();
					for (int i = 0; i < timeSeriesData.length(); ++i) {
						String stockDate = timeSeriesData.getString(i);
						JSONObject stockPrice = jsonResultsObj.getJSONObject("Weekly Time Series").getJSONObject(stockDate);
						double price = Double.parseDouble(stockPrice.getString("4. close"));
						time.setTime(simpleDateFormatNoSec.parse(stockDate));
						stockService.addStocksToDatabase(symbol, price, time);	  
						System.out.println("TickerSymbol: " + symbol.toUpperCase() + " Date: " + stockDate + " Price: " 
								+ stockPrice.getString("4. close"));
					}
					System.out.println("\nStock Data added to Local Database.");
				}
			    break;
			case "monthly":
				jsonResultsString = stockService.getQuotes(symbol, timeSeries);
				System.out.println(jsonResultsString);
				System.out.println("\nStore the results in the local database (yes, no): ");
				storeResults = scanner.nextLine().toLowerCase().trim();
				
				if (storeResults.contains("yes")) {
					System.out.println("");
					JSONObject jsonResultsObj = new JSONObject(jsonResultsString);
					//add iterate logic
					JSONArray timeSeriesData = jsonResultsObj.getJSONObject("Monthly Time Series").names();
					for (int i = 0; i < timeSeriesData.length(); ++i) {
						String stockDate = timeSeriesData.getString(i);
						JSONObject stockPrice = jsonResultsObj.getJSONObject("Monthly Time Series").getJSONObject(stockDate);
						double price = Double.parseDouble(stockPrice.getString("4. close"));
						time.setTime(simpleDateFormatNoSec.parse(stockDate));
						stockService.addStocksToDatabase(symbol, price, time);	  
						System.out.println("TickerSymbol: " + symbol.toUpperCase() + " Date: " + stockDate + " Price: " 
								+ stockPrice.getString("4. close"));
					}
					System.out.println("\nStock Data added to Local Database.");
				}
				break;
			case "intraday":
				jsonResultsString = stockService.getQuotes(symbol, timeSeries, interval, outputSize);
				System.out.println(jsonResultsString);
				System.out.println("\nStore the results in the local database (yes, no): ");
				storeResults = scanner.nextLine().toLowerCase().trim();
				
				if (storeResults.contains("yes")) {
					switch(interval) {
					case "1min":
						intervalTimeSeries = "Time Series (1min)";
						break;
					case "5min":
						intervalTimeSeries = "Time Series (5min)";
						break;
					case "15min":
						intervalTimeSeries = "Time Series (15min)";
						break;
					case "30min":
						intervalTimeSeries = "Time Series (30min)";
						break;
					case "60min":
						intervalTimeSeries = "Time Series (60min)";
						break;
					}
					System.out.println("");
					JSONObject jsonResultsObj = new JSONObject(jsonResultsString);
					//add iterate logic
					JSONArray timeSeriesData = jsonResultsObj.getJSONObject(intervalTimeSeries).names();
					for (int i = 0; i < timeSeriesData.length(); ++i) {
						String stockDate = timeSeriesData.getString(i);
						JSONObject stockPrice = jsonResultsObj.getJSONObject(intervalTimeSeries).getJSONObject(stockDate);
						double price = Double.parseDouble(stockPrice.getString("4. close"));
						Date dateWithSec = simpleDateFormat.parse(stockDate);
						time.setTime(dateWithSec);
						stockService.addStocksToDatabase(symbol, price, time);	  
						System.out.println("TickerSymbol: " + symbol.toUpperCase() + " Date: " + stockDate + " Price: " 
								+ stockPrice.getString("4. close"));
					}
					System.out.println("\nStock Data added to Local Database.");
				}
				break;
			case "endpoint":
				jsonResultsString = stockService.getQuotes(symbol, timeSeries);
				System.out.println(jsonResultsString);
				System.out.println("\nStore the results in the local database (yes, no): ");
				storeResults = scanner.nextLine().toLowerCase().trim();
				
				if (storeResults.contains("yes")) {
					System.out.println("");
					JSONObject jsonResultsObj = new JSONObject(jsonResultsString);
					//add iterate logic
					String stockDate = jsonResultsObj.getJSONObject("Global Quote").getString("07. latest trading day");
					double price = Double.parseDouble(jsonResultsObj.getJSONObject("Global Quote").getString("05. price"));
					time.setTime(simpleDateFormatNoSec.parse(stockDate));
					stockService.addStocksToDatabase(symbol, price, time);	  
					System.out.println("TickerSymbol: " + symbol.toUpperCase() + " Date: " + stockDate + " Price: " 
								+ price);
					System.out.println("\nStock Data added to Local Database.");
				}				
				break;
			default:
			    //Daily
				jsonResultsString = stockService.getQuotes(symbol, timeSeries, outputSize);
				System.out.println(jsonResultsString);
				System.out.println("\nStore the results in the local database (yes, no): ");
				storeResults = scanner.nextLine().toLowerCase().trim();
				
				if (storeResults.contains("yes")) {
					System.out.println("");
					JSONObject jsonResultsObj = new JSONObject(jsonResultsString);
					//add iterate logic
					JSONArray timeSeriesData = jsonResultsObj.getJSONObject("Time Series (Daily)").names();
					for (int i = 0; i < timeSeriesData.length(); ++i) {
						String stockDate = timeSeriesData.getString(i);
						JSONObject stockPrice = jsonResultsObj.getJSONObject("Time Series (Daily)").getJSONObject(stockDate);
						double price = Double.parseDouble(stockPrice.getString("4. close"));
						time.setTime(simpleDateFormatNoSec.parse(stockDate));
						stockService.addStocksToDatabase(symbol, price, time);	  
						System.out.println("TickerSymbol: " + symbol.toUpperCase() + " Date: " + stockDate + " Price: " 
								+ stockPrice.getString("4. close"));
					}
					System.out.println("\nStock Data added to Local Database.");
				}
			}
		}
		scanner.close();
		System.out.println("\nStockTickerApplication complete.");
		System.exit(0);
	}
}