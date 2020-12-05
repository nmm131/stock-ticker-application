package com.advanced.model;

import org.junit.Test;

import com.advanced.model.Person;
import com.advanced.model.UserStocks;
import com.advanced.service.UserStockService;
import com.advanced.service.UserStockServiceException;
import com.advanced.service.UserStockServiceFactory;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for UserStocks class
 */
public class UserStocksTest {

    /**
     * Testing helper method for generating UserStocks test data
     *
     * @return a UserStocks object that uses Person and Stock
     * return from their respective create method.
     */
    public static UserStocks createUserStocks() {
        Person person = PersonTest.createPerson();
        String stockSymbol = "APPL"; //StockTest.createStock();
        return new UserStocks(person, stockSymbol);
    }

    @Test
    public void testPersonStocksGetterAndSetters() {
        String stockSymbol = "APPL"; //StockTest.createStock();
        Person person = PersonTest.createPerson();
        UserStocks UserStocks = new UserStocks();
        int id = 10;
        UserStocks.setId(id);
        UserStocks.setPerson(person);
        UserStocks.setStockSymbol(stockSymbol);
        assertEquals("person matches", person, UserStocks.getPerson());
        assertEquals("stockSymbol matches", stockSymbol, UserStocks.getStockSymbol());
        assertEquals("id matches", id, UserStocks.getId());
    }
    
    @Test
    public void testValidPersonStocksGetterAndSetters() {      
      //known person: id=1, firstName='Drew', lastName='Hope', + birthDate=1999-10-10 00:00:00.0
        UserStockService stockService = UserStockServiceFactory.getInstance();	
		try {
			Person person = new Person();
			UserStocks UserStocks = new UserStocks();
			UserStocks.setId(10);
	        UserStocks.setPerson(person);
	        UserStocks.setStockSymbol("APPL");
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
			
			assertEquals("person matches", person, UserStocks.getPerson());
	        assertEquals("stockSymbol matches", "APPL", UserStocks.getStockSymbol());
	        assertEquals("id matches", 10, UserStocks.getId());

		} catch (UserStockServiceException exception) {
			System.out.println(exception.getMessage());
		}
		
		//
    }

    @Test
    public void testEqualsNegativeDifferentPerson() {
        UserStocks UserStocks = createUserStocks();
        UserStocks.setId(10);
        String stockSymbol = "APPL"; //StockTest.createStock();
        Person person = new Person();
        Timestamp birthDate = new Timestamp(PersonTest.birthDayCalendar.getTimeInMillis() + 10000);
        person.setBirthDate(birthDate);
        person.setFirstName(PersonTest.firstName);
        person.setLastName(PersonTest.lastName);
        UserStocks UserStocks2 = new UserStocks(person, stockSymbol);
        assertFalse("Different person", UserStocks.equals(UserStocks2));
    }

    @Test
    public void testEquals() {
        UserStocks UserStocks = createUserStocks();
        assertTrue("Same objects are equal", UserStocks.equals(createUserStocks()));
    }

    @Test
    public void testToString() {
        UserStocks UserStocks = createUserStocks();
        assertTrue("toString has lastName", UserStocks.toString().contains(PersonTest.lastName));
        assertTrue("toString has person", UserStocks.toString().contains(PersonTest.firstName));
        //assertTrue("toString has tickerSymbol", UserStocks.toString().contains(//));
        assertTrue("toString has person birthdate", UserStocks.toString().contains(PersonTest.birthDate.toString()));
    }

}
