package com.advanced.services;

import org.junit.Test;

import com.advanced.service.UserStockService;
import com.advanced.service.UserStockServiceFactory;

import static org.junit.Assert.assertNotNull;

/**
 * JUnit test for <CODE>stocksService</CODE>
 */
public class StockServiceFactoryTest {

    @Test
    public void testFactory() {
        UserStockService instance = UserStockServiceFactory.getInstance();
        assertNotNull("Make sure factory works", instance);
    }
}
