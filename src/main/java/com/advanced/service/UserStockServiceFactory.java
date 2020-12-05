package com.advanced.service;

/**
 * A factory that returns a <CODE>StocksService</CODE> instance.
 */
public class UserStockServiceFactory {

    /**
     * Prevent instantiations
     */
    private UserStockServiceFactory() {}

    /**
     *
     * @return get a <CODE>StockService</CODE> instance
     */
    public static UserStockService getInstance() {
        return new DatabaseUserStocksService();
    }

}
