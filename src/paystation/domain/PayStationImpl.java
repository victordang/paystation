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
    private HashMap<Integer,Integer> coinsInserted = new HashMap<>();

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
        
        if(coinsInserted.containsKey(coinValue)) {
            int current = coinsInserted.get(coinValue);
            coinsInserted.put(coinValue, current+=1);
        }    
        else {
            coinsInserted.put(coinValue, 1);
        } 
        

        insertedSoFar += coinValue;
        timeBought = insertedSoFar / 5 * 2;
    }

    
    public int getMap(int coinValue){
        return coinsInserted.get(coinValue);
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
    public HashMap<Integer, Integer> getCoins() {
        return coinsInserted;
    }
    
    @Override
    public HashMap<Integer, Integer> cancel(){
            HashMap<Integer, Integer> coins = coinsInserted;
            reset();
            return coins;
    }
    
    @Override
    public int empty(){
        int payout = insertedSoFar;
        insertedSoFar = 0;
        return payout;
    }
    
    private void reset() {
        timeBought = insertedSoFar = 0;
        coinsInserted = new HashMap<Integer,Integer>();
    }
  
}//end of paystation
