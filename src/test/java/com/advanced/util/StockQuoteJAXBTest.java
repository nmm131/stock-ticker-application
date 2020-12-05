package com.advanced.util;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import javax.xml.bind.JAXBException;

import org.junit.Test;

import com.advanced.app.StockQuoteJAXB;
import com.advanced.service.UserStockServiceException;
import com.advanced.util.DatabaseConnectionException;
import com.advanced.util.DatabaseUtils;
import com.advanced.util.StockServiceException;

/**
*
* JUNit test for StockServiceApplication Class
*
*/

public class StockQuoteJAXBTest {
	
	@Test
	public void testMainPositive() throws StockServiceException, UserStockServiceException, JAXBException {
	 StockQuoteJAXB.main(new String[] {});
	 //Last known stock to be added
	 //<stockQuote symbol=\"OTOW\" price=\"60.41\" time=\"2015-02-10 00:00:01\"/>
	 try {
         Connection connection = DatabaseUtils.getConnection();
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery("select * from quotes");
         resultSet.last();
         assertTrue("Symbol is correct.", resultSet.getString("symbol").contentEquals("OTOW"));
         String rsPrice = resultSet.getString("price");
         String price = "60";
         assertEquals("Price is correct.", price, rsPrice);
         String rsTime = resultSet.getString("time");
         String time = "2015-02-10 00:00:00";
         assertEquals("Time is correct.", time, rsTime);
	} catch (DatabaseConnectionException | SQLException exception) {
        throw new StockServiceException(exception.getMessage(), exception);
    }
}
}
