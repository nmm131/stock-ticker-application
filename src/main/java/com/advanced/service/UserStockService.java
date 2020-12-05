package com.advanced.service;

import java.util.List;

import com.advanced.model.Person;

/**
 *
 */
public interface UserStockService {

    /**
     * Get a list of all people
     *
     * @return a list of Person instances
     * @throws UserStockServiceException if a service can not read or write the requested data
     *                                    or otherwise perform the requested operation.
     */
    List<Person> getPerson() throws UserStockServiceException;

    /**
     * Add a new person or update an existing Person's data
     *
     * @param person a person object to either update or create
     * @throws UserStockServiceException if a service can not read or write the requested data
     *                                    or otherwise perform the requested operation.
     */
    void addOrUpdatePerson(Person person) throws UserStockServiceException;

    /**
     * Get a list of all a person's stocks.
     *
     * @return a list of stock instances
     * @throws UserStockServiceException if a service can not read or write the requested data
     *                                    or otherwise perform the requested operation.
     */
    List<String> getStockSymbol(Person person) throws UserStockServiceException;

    /**
     * Assign a stock to a person.
     *
     * @param stock  The stock to assign
     * @param person The person to assign the stock too.
     * @throws UserStockServiceException if a service can not read or write the requested data
     *                                    or otherwise perform the requested operation.
     */
    public void addStockSymbolToPerson(String stockSymbol, Person person) throws UserStockServiceException;

}
