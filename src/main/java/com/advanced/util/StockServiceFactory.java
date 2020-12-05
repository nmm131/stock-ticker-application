package com.advanced.util;

public class StockServiceFactory {
    
    public static StockService getStockService() {
    	return new DatabaseStockService(); //return a concrete implementation of the interface
    }
}