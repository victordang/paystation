package paystation.domain;
import java.util.*;

import java.util.Map;

/**
 * Implementation of the pay station.
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
public class PayStationImpl implements PayStation {
    
    private int insertedSoFar;
    private int timeBought;
    
    public HashMap<Integer,Integer> coinsInserted = new HashMap<Integer,Integer>();

    @Override
    public void addPayment(int coinValue)
            throws IllegalCoinException {
        switch (coinValue) {
            case 5: break;
            case 10: break;
            case 25: break;
            default:
                throw new IllegalCoinException("Invalid coin: " + coinValue);
        }
        insertedSoFar += coinValue;
        timeBought = insertedSoFar / 5 * 2;
    }

    @Override
    public int readDisplay() {
        return timeBought;
    }
    
    @Override
    public int getInsertedSoFar() {
        return insertedSoFar;
    }

    @Override
    public Receipt buy() {
        Receipt r = new ReceiptImpl(timeBought);
        reset();
        return r;
    }

  
    
    @Override
<<<<<<< HEAD
    public int empty(){
=======
    public Map<Integer, Integer> cancel() {
            Map<Integer, Integer> retMap;
            
            return retMap;
    }
    
    @Override
    public int empty() {
>>>>>>> 2d0d9311a4f8fd7503dec1baa943495cef3bb3bf
        int payout = insertedSoFar;
        insertedSoFar = 0;
        return payout;
    }
    
    
    
    private void reset() {
        timeBought = insertedSoFar = 0;
        coinsInserted = new HashMap<Integer,Integer>();
    }
    
    
     @Override
    public HashMap<Integer, Integer> cancel() {
        HashMap <Integer,Integer> coins = coinsInserted;
        reset(); //clears the map
        return coins ;
    }
    
    
    
    
    
}
