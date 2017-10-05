/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.temple.cis.c3238.banksim;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cmala
 */
public class TestThread extends Thread {

    private final Bank bank;
    Semaphore semaphore = null;
    Semaphore signal = null;

    public TestThread(Bank b, Semaphore semaphore) {
        bank = b;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        while (bank.isOpen()) {
            //increment semaphore
            try {
                semaphore.acquire(10);
            } catch (InterruptedException ex) {
                //Do nothing
            }
            //System.out.println("TEST THREAD: " + semaphore.toString()); 
            if (bank.shouldTest()) {
                bank.test();
            }
            //decrement semaphore
            semaphore.release(10);

            try {
                sleep(1);
            } catch (InterruptedException ex) {

            }
        }
    }

}
