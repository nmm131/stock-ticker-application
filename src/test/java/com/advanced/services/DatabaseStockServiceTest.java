package com.advanced.services;

import com.advanced.model.Person;
import com.advanced.model.PersonTest;
import com.advanced.service.UserStockService;
import com.advanced.service.UserStockServiceException;
import com.advanced.service.UserStockServiceFactory;
import com.advanced.util.DatabaseUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the DatabaseStockService
 */
public class DatabaseStockServiceTest {

    private UserStockService stocksService;

    private void initDb() throws Exception {
        DatabaseUtils.initializeDatabase(DatabaseUtils.initializationFile);
    }

    // do not assume db is correct
    @Before
    public void setUp() throws Exception {
        // we could also copy db state here for later restore before initializing
        initDb();
        stocksService = UserStockServiceFactory.getInstance();
    }

    // clean up after ourselves. (this could also restore db from initial state
    @After
    public void tearDown() throws Exception {
        initDb();
    }

    @Test
    public void testGetInstance() {
        assertNotNull("Make sure stocksService is available", stocksService);
    }

    @Test
    public void testGetPerson() throws UserStockServiceException {
        List<Person> personList = stocksService.getPerson();
        assertFalse("Make sure we get some Person objects from service", personList.isEmpty());
    }

    @Test
    public void testAddOrUpdatePerson()throws UserStockServiceException {
        Person newPerson = PersonTest.createPerson();
        stocksService.addOrUpdatePerson(newPerson);
        List<Person> personList = stocksService.getPerson();
        boolean found = false;
        for (Person person : personList) {
            Timestamp returnedBirthDate = person.getBirthDate();
            Calendar returnCalendar = Calendar.getInstance();
            returnCalendar.setTimeInMillis(returnedBirthDate.getTime());
            if (returnCalendar.get(Calendar.MONTH) == PersonTest.birthDayCalendar.get(Calendar.MONTH)
                    &&
                    returnCalendar.get(Calendar.YEAR) == PersonTest.birthDayCalendar.get(Calendar.YEAR)
                    &&
                    returnCalendar.get(Calendar.DAY_OF_MONTH) == PersonTest.birthDayCalendar.get(Calendar.DAY_OF_MONTH)
                    &&
                    person.getLastName().equals(PersonTest.lastName)
                    &&
                    person.getFirstName().equals(PersonTest.firstName)) {
                found = true;
                break;
            }
        }
        assertTrue("Found the person we added", found);
    }

    @Test
    public void testGetStockSymbolsByPerson() throws UserStockServiceException {
        Person person = PersonTest.createPerson();
        List<String> stockSymbols = stocksService.getStockSymbol(person);
        // make the person have all the stockSymbols
        for (String stockSymbol : stockSymbols) {
        	stocksService.addStockSymbolToPerson(stockSymbol, person);
        }
        List<String> stockSymbolList = stocksService.getStockSymbol(person);
        for (String stockSymbol : stockSymbols) {
            boolean removed = stockSymbolList.remove(stockSymbol);
            assertTrue("Verify that the stockSymbol was found on the list", removed);
        }
        // if  stockSymbolList is empty then we know the lists matched.
        assertTrue("Verify the list of returned stockSymbols match what was expected ", stockSymbolList.isEmpty());

    }
    
    @Test
    public void testValidGetStockSymbolsByPerson() throws UserStockServiceException {
    	//known person: id=1, firstName='Drew', lastName='Hope', + birthDate=1999-10-10 00:00:00.0
        UserStockService stockService = UserStockServiceFactory.getInstance();	
		try {
			Person person = new Person();
			person.setFirstName("Drew");
			person.setLastName("Hope");
			person.setId(1);
			//person.setBirthDate(birthDate);
			
			List<String> stockSymbols = stockService.getStockSymbol(person);

			System.out.println("Stock Quotes for " + person.getFirstName() 
			+ " " + person.getLastName());
			
			for (String stock : stockSymbols) {
				System.out.println(stock.toString());
			}
			
			assertTrue("Verify the list of returned stockSymbols match what was expected ", !stockSymbols.isEmpty());

		} catch (UserStockServiceException exception) {
			System.out.println(exception.getMessage());
		}
    }
}
