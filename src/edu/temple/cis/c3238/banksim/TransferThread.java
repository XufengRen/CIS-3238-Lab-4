package edu.temple.cis.c3238.banksim;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Cay Horstmann
 * @author Modified by Paul Wolfgang
 */
class TransferThread extends Thread {

    private final Bank bank;
    private final int fromAccount;
    private final int maxAmount;
    Semaphore semaphore = null;
    Semaphore signal = null;

    public TransferThread(Bank b, int from, int max, Semaphore semaphore) {
        bank = b;
        fromAccount = from;
        maxAmount = max;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
        
            try {
            semaphore.acquire(); 
            } catch (InterruptedException ex){
                //Do nothing
            }
            //System.out.println("TRANSFER THREAD ACQUIRE: " + semaphore.toString()); 
            int toAccount = (int) (bank.size() * Math.random());
            int amount = (int) (maxAmount * Math.random());
            bank.transfer(fromAccount, toAccount, amount);
            
            semaphore.release(); 
            //System.out.println("TRANSFER THREAD RELEASE: " + semaphore.toString()); 
            try {
                sleep(2);
            } catch (InterruptedException ex) {
                
            }
        
        }
        bank.closeBank();
    }

}
