/**
 * The business logic of a Parking Pay Station.
 *
 * Responsibilities:
 *
 * 1) Accept payment; 
 * 2) Calculate parking time based on payment; 
 * 3) Know earning, parking time bought; 
 * 4) Issue receipts; 
 * 5) Handle buy and cancel events.
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
import java.util.*;


public interface PayStation {

    /**
     * Insert coin into the pay station and adjust state accordingly.
     *
     * @param coinValue is an integer value representing the coin in cent. That
     * is, a quarter is coinValue=25, etc.
     * @throws IllegalCoinException in case coinValue is not a valid coin value
     */
    public void addPayment(int coinValue) throws IllegalCoinException;

    /**
     * Read the machine's display. The display shows a numerical description of
     * the amount of parking time accumulated so far based on inserted payment.
     *
     * @return the number to display on the pay station display
     */
    public int readDisplay();

    /**
     * Buy parking time. Terminate the ongoing transaction and return a parking
     * receipt. A non-null object is always returned.
     *
     * @return a valid parking receipt object.
     */
    
      /*
     * Returns the amount of money currently inserted into the Paystation between
     * empty calls.
     */
    public int getInsertedSoFar();
    
    /*
     * Returns a receipt for how much time the user purchased then resets the Paystayion.
     */
    public Receipt buy();
    
    /*
     * Returns the HashMap representing every coin inserted into the PayStation.
     */
    public HashMap<Integer, Integer> getCoins();
    
     /*
     * Returns the total amount of money collected by the paystation since the
     * empty call, and then returns it. Also resets the amount of money in the
     * Paystation.
     */
    public int empty();
    
    /**
     * Cancel the present transaction. Resets the machine for a new transaction.
     */
    public HashMap<Integer, Integer> cancel();

}//end of paystation
