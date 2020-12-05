package com.advanced.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.advanced.util.StockService;

/**
 * An implementation of the StockService interface that gets
 * stock data from a database.
 */
public class DatabaseStockService implements StockService {

    /**
     * Return the current price for a share of stock  for the given symbol
     *
     * @param symbol the stock symbol of the company you want a quote for.
     *               e.g. APPL for APPLE
     * @return a  <CODE>BigDecimal</CODE> instance
     * @throws StockServiceException if using the service generates an exception.
     *                               If this happens, trying the service may work, depending on the actual cause of the
     *                               error.
     */
    @Override
    public StockQuote getQuote(String symbol) throws StockServiceException {
        // todo - this is a pretty lame implementation why?
        List<StockQuote> stockQuotes = null;
        try {
            Connection connection = DatabaseUtils.getConnection();
            Statement statement = connection.createStatement();
            String queryString = "select * from quotes where symbol = '" + symbol + "' order by time desc"; //get latest stockquote

            ResultSet resultSet = statement.executeQuery(queryString);
            stockQuotes = new ArrayList<>(resultSet.getFetchSize());
            while(resultSet.next()) {
                String symbolValue = resultSet.getString("symbol");
                Date time = resultSet.getDate("time");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(time);
                // oops!
                double price = resultSet.getDouble("price");
                stockQuotes.add(new StockQuote(price, symbolValue, calendar));
            }

        } catch (DatabaseConnectionException | SQLException exception) {
            throw new StockServiceException(exception.getMessage(), exception);
        }
        if (stockQuotes.isEmpty()) {
            throw new StockServiceException("There is no stock data for:" + symbol);
        }
        return stockQuotes.get(0);
    }

    /**
     * Get a historical list of stock quotes for the provide symbol
     *
     * @param symbol the stock symbol to search for
     * @param start   the date of the first stock quote
     * @param end  the date of the last stock quote
     * @param interval cadence
     * @return a list of StockQuote instances
     * @throws StockServiceException 
     */
    @Override
    public List<StockQuote> getQuote(String symbol, Calendar start, Calendar end, String interval) throws StockServiceException {

        List<StockQuote> stockQuotes = null;
        Date startDate = new Date(start.getTimeInMillis()); 
        Date endDate = new Date(end.getTimeInMillis());

        try {
            Connection connection = DatabaseUtils.getConnection();
            Statement statement = connection.createStatement();
            String queryString = "select * from quotes where symbol = '" + symbol + 
            		"' and time <= '" + endDate + 
            		"' and time >= '" + startDate +
            		"' order by time desc";
            
            ResultSet resultSet = statement.executeQuery(queryString);
            stockQuotes = new ArrayList<>(resultSet.getFetchSize());
            
            int intervalInDays;
            switch (interval) {
        	case "DAILY":
        		intervalInDays = 1;
        		break;
        	case "MONTHLY":
        		intervalInDays = 30;
        		break;
        	case "YEARLY":
        		intervalInDays = 365;
        		break;
    		default:
    			intervalInDays = 1;
    			break;
        	}

            	while(resultSet.relative(intervalInDays)) {
                    String symbolValue = resultSet.getString("symbol");
                    Date time = resultSet.getDate("time");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(time);            
                    double price = resultSet.getDouble("price");
                    stockQuotes.add(new StockQuote(-1987654321,
                    		"NotARealTickerSymbolValue", calendar));
                    stockQuotes.add(new StockQuote(price, symbolValue, calendar));
            }
        } catch (DatabaseConnectionException | SQLException exception) {
            throw new StockServiceException(exception.getMessage(), exception);
        }
        if (stockQuotes.isEmpty()) {
            throw new StockServiceException("There is no stock data for:" + symbol);
        }
        return stockQuotes;
    }
    
    public void addStocksToDatabase(String symbol, double price, Calendar time) throws StockServiceException {
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Connection connection = DatabaseUtils.getConnection();
            Statement statement = connection.createStatement();

            String queryString = "insert into quotes (symbol,time,price) VALUES ('" + symbol + 
            		"','" + simpleDateFormat.format(time.getTime()) + 
            		"','" + Double.toString(price) +
            		"')";
            statement.executeUpdate(queryString);
            
            
        } catch (DatabaseConnectionException | SQLException exception) {
            throw new StockServiceException(exception.getMessage(), exception);
        }
        
    }
    
    @Override
    public String getQuotes(String p_symbol, String p_timeSeries) {
    	
		String queryString = null;
		
		// Build the query string
    	if (p_timeSeries.contentEquals("endpoint")) {
    		queryString = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE";
			queryString += "&symbol=" + p_symbol;
			queryString += "&apikey=Demo";
    	} else {
			queryString = "https://www.alphavantage.co/query?function=TIME_SERIES_" + p_timeSeries.toUpperCase();
			queryString += "&symbol=" + p_symbol;
			queryString += "&apikey=Demo";
    	}
		
		//Set up the connection to the online service
        try {
            java.net.URL connection_url = new java.net.URL(queryString);
            InputStream stream = connection_url.openStream();
            
            //Stream the results
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            //Store the results in a string to return (default results are json)
            String resultsString = "";
            String line = reader.readLine();
            while (line != null) {
                resultsString += line;
                line = reader.readLine();
            }
            return resultsString;
        } catch (IOException exc) {
            return "{}";
        }
    }
    
    @Override
	public String getQuotes(String p_symbol, String p_timeSeries, String p_outputSize) {
			
			//Build the query string
			String queryString = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY";
			queryString += "&symbol=" + p_symbol;
			queryString += "&outputsize=" + p_outputSize;
			queryString += "&apikey=Demo";
			
			//Set up the connection to the online service
	        try {
	            java.net.URL connection_url = new java.net.URL(queryString);
	            InputStream stream = connection_url.openStream();
	            
	            //Stream the results
	            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
	
	            //Store the results in a string to return (default results are json)
	            String resultsString = "";
	            String line = reader.readLine();
	            while (line != null) {
	                resultsString += line;
	                line = reader.readLine();
	            }
	            return resultsString;
	        } catch (IOException exc) {
	            return "{}";
	        }
	    }
    
    @Override
	public String getQuotes(String p_symbol, String p_timeSeries, String p_interval, String p_outputSize) {
		
		//Build the query string
		String queryString = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY";
		queryString += "&symbol=" + p_symbol;
		queryString += "&interval=" + p_interval;
		queryString += "&outputsize=" + p_outputSize;
		queryString += "&apikey=Demo";
		
		//Set up the connection to the online service
	    try {
	        java.net.URL connection_url = new java.net.URL(queryString);
	        InputStream stream = connection_url.openStream();
	        
	        //Stream the results
	        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
	
	        //Store the results in a string to return (default results are json)
	        String resultsString = "";
	        String line = reader.readLine();
	        while (line != null) {
	            resultsString += line;
	            line = reader.readLine();
	        }
	        return resultsString;
	    } catch (IOException exc) {
	        return "{}";
	    }
	}
    
}
