/**
 * Testcases for the Pay Station system.
 *
 * This source code is from the book "Flexible, Reliable Software: Using
 * Patterns and Agile Development" published 2010 by CRC Press. Author: Henrik B
 * Christensen Computer Science Department Aarhus University
 *
 * This source code is provided WITHOUT ANY WARRANTY either expressed or
 * implied. You may study, use, modify, and distribute it for non-commercial
 * purposes. For any commercial use, see http://www.baerbak.com/
 */
package paystation.domain;

import java.util.HashMap;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class PayStationImplTest {

    PayStation ps;
    
    private HashMap<Integer, Integer> testMap;
    
    private static final int nickel = 5;
    private static final int dime = 10;
    private static final int quarter = 25;

    @Before
    public void setup() {
        ps = new PayStationImpl();
    }

    /**
     * Entering 5 cents should make the display report 2 minutes parking time.
     */
    @Test
    public void shouldDisplay2MinFor5Cents()
            throws IllegalCoinException {
        ps.addPayment(nickel);
        assertEquals("Should display 2 min for 5 cents",
                2, ps.readDisplay());
    }

    /**
     * Entering 25 cents should make the display report 10 minutes parking time.
     */
    @Test
    public void shouldDisplay10MinFor25Cents() throws IllegalCoinException {
        ps.addPayment(quarter);
        assertEquals("Should display 10 min for 25 cents",
                10, ps.readDisplay());
    }

    /**
     * Verify that illegal coin values are rejected.
     */
    @Test(expected = IllegalCoinException.class)
    public void shouldRejectIllegalCoin() throws IllegalCoinException {
        ps.addPayment(17);
    }

    /**
     * Entering 10 and 25 cents should be valid and return 14 minutes parking
     */
    @Test
    public void shouldDisplay14MinFor10And25Cents()
            throws IllegalCoinException {
        ps.addPayment(dime);
        ps.addPayment(quarter);
        assertEquals("Should display 14 min for 10+25 cents",
                14, ps.readDisplay());
    }

    /**
     * Buy should return a valid receipt of the proper amount of parking time
     */
    @Test
    public void shouldReturnCorrectReceiptWhenBuy()
            throws IllegalCoinException {
        ps.addPayment(nickel);
        ps.addPayment(dime);
        ps.addPayment(quarter);
        Receipt receipt;
        receipt = ps.buy();
        assertNotNull("Receipt reference cannot be null",
                receipt);
        assertEquals("Receipt value must be 16 min.",
                16, receipt.value());
    }

    /**
     * Buy for 100 cents and verify the receipt
     */
    @Test
    public void shouldReturnReceiptWhenBuy100c()
            throws IllegalCoinException {
        ps.addPayment(dime);
        ps.addPayment(dime);
        ps.addPayment(dime);
        ps.addPayment(dime);
        ps.addPayment(dime);
        ps.addPayment(quarter);
        ps.addPayment(quarter);

        Receipt receipt;
        receipt = ps.buy();
        assertEquals(40, receipt.value());
    }

    /**
     * Verify that the pay station is cleared after a buy scenario
     */
    @Test
    public void shouldClearAfterBuy()
            throws IllegalCoinException {
        ps.addPayment(quarter);
        ps.buy(); // I do not care about the result
        // verify that the display reads 0
        assertEquals("Display should have been cleared",
                0, ps.readDisplay());
        // verify that a following buy scenario behaves properly
        ps.addPayment(dime);
        ps.addPayment(quarter);
        assertEquals("Next add payment should display correct time",
                14, ps.readDisplay());
        Receipt r = ps.buy();
        assertEquals("Next buy should return valid receipt",
                14, r.value());
        assertEquals("Again, display should be cleared",
                0, ps.readDisplay());
    }

    /**
     * Verify that cancel clears the pay station
     */
    @Test
    public void shouldClearAfterCancel()
            throws IllegalCoinException {
        ps.addPayment(dime);
        ps.cancel();
        assertEquals("Cancel should clear display",
                0, ps.readDisplay());
        ps.addPayment(quarter);
        assertEquals("Insert after cancel should work",
                10, ps.readDisplay());
    }
    
    /*
     * Verify whether the empty() method returns the correct amount of money
     */
    @Test
    public void emptyReturnsAmountEntered()
            throws IllegalCoinException {
        ps.addPayment(nickel);
        ps.addPayment(dime);
        ps.addPayment(quarter);
        
        assertEquals("Empty should return the insertedSoFar",
                40, ps.empty());
    }
    
    /*
     * Verify that the empty() method resets the insertedSoFar member. 
     */
    @Test
    public void emptyResetsInsertedSoFar()
             throws IllegalCoinException {
        ps.addPayment(quarter);
        
        ps.empty();
        assertEquals("Empty should reset the insertedSoFar to 0",
                0, ps.getInsertedSoFar());
    }
    
    
    /*
     * Verify that the insertedSoFar is successfully reset after a cancel call. 
     */
    @Test
    public void cancelDoesNotAddToTotal()
            throws IllegalCoinException {
         ps.addPayment(dime);
         ps.addPayment(quarter);
         
         ps.cancel();
         assertEquals("Cancel should not add value to insertedSoFar",
                 0, ps.getInsertedSoFar());
    }
    
    /*
     * Test to make sure only one coin is returned by cancel when one coin was inserted.
     */
    @Test
    public void cancelReturnsSingleCoin()
            throws IllegalCoinException {
        ps.addPayment(dime);
        
        testMap = new HashMap<>();
        testMap.put(dime, 1);
        
        assertEquals("Cancel should return a map with one coin when only one coin has been inserted",
               testMap, ps.cancel());
    }
    
    /*
     * Test to make sure cancel will return a mixture of different coins when appropriate.
     */
    @Test
    public void cancelReturnsMixedCoins()
            throws IllegalCoinException {
        ps.addPayment(nickel);
        ps.addPayment(dime);
        ps.addPayment(quarter);
        
       testMap = new HashMap<>();
       testMap.put(nickel, 1);
       testMap.put(dime, 1);
       testMap.put(quarter, 1);
       
       assertEquals("Cancel should return a map with a mixture of coins when multiple coins have been inserted",
               testMap, ps.cancel());
    }
    
    /*
     * Test to ensure cancel does NOT return any keys for associated with coin types
     * that were not inserted.
     */
    @Test
    public void cancelDoesNotReturnFalseKeys()
            throws IllegalCoinException {
        ps.addPayment(nickel);
        ps.addPayment(quarter);
        
        testMap = new HashMap<>();
        testMap.put(nickel, 1);
        testMap.put(dime, 1);
        testMap.put(quarter, 1);
        
        assertNotEquals("Cancel should not return any keys associated with coin types that have not been inserted",
                testMap, ps.cancel());
    }
    
    @Test
    public void cancelClearsMap()
            throws IllegalCoinException {
        
    }
    
    @Test
    public void buyClearsMap() 
            throws IllegalCoinException {
        
    }
}
