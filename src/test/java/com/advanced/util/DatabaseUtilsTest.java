package com.advanced.util;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import com.advanced.util.DatabaseConnectionException;
import com.advanced.util.DatabaseUtils;
import com.advanced.util.StockServiceException;


public class DatabaseUtilsTest {
	
	private String JDBC_DRIVER;
	private String DB_URL;
	private String USER;
	private String PASS;
	private Connection connection;
	private Date date;
	SimpleDateFormat simpleDateFormat;

	@Before
	public void setup() throws DatabaseConnectionException {
    	JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	    DB_URL = "jdbc:mysql://localhost:3306/stocks";
	    USER = "monty";
	    PASS = "some_pass";
	    date = Date.valueOf("2994-07-02");
	    simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
	    try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException  | SQLException e)  {
           throw new  DatabaseConnectionException("Could not connection to database." + e.getMessage(), e);
        }
	}
	
	@Test
    public void databaseInitializationTest() throws StockServiceException {
        
        try {
            Connection connection = DatabaseUtils.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO quotes (symbol,time,price) VALUES ('ZFZA','2994-07-02 00:00:01','1585.00')");
            ResultSet resultSet = statement.executeQuery("select * from quotes where symbol = 'ZFZA' order by time desc");
            
            while(resultSet.next()) {
                String symbolValue = resultSet.getString("symbol");
                Date time = resultSet.getDate("time");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(time);
                double price = resultSet.getDouble("price");
                System.out.println(time);
                assertEquals("The symbol is correct.", "ZFZA", symbolValue);
                assertTrue("The date is correct.", date.equals(time));
    	        assertEquals("The price is correct.", 1585.00, price);
            }
            statement.executeUpdate("delete from quotes where symbol = 'ZFZA'");

        } catch (DatabaseConnectionException | SQLException exception) {
            throw new StockServiceException(exception.getMessage(), exception);
        }
    }
}