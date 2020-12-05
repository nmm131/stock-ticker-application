package com.advanced.util;

import org.junit.Test;

import com.advanced.service.UserStockServiceException;
import com.advanced.util.StockQuoteApplication;
import com.advanced.util.StockServiceException;

/**
*
* JUNit test for StockServiceApplication Class
*
*/

public class StockQuoteApplicationTest {
	
	@Test(expected = NullPointerException.class)
	public void testMainNegative() throws StockServiceException, UserStockServiceException {
	 StockQuoteApplication.main(null);
	}
	
	//@Test
	//public void testMainPositive() throws StockServiceException, UserStockServiceException {
	 //StockQuoteApplication.main(new String[] {"GOOG", "2015/02/08", "2015/02/15", "DAILY"});
	//}
}
