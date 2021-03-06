/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerThread implements Runnable {

    private boolean running = false;
    private int[] result;

    private synchronized void atomicAction(int i) {
        double random = Math.random();
        int j = (int) (random * 10);
        result[i] = j;
        System.out.println(Thread.currentThread() + " doing work and producing " + j);
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread() + " stars working");
        running = true;
        result = new int[10];
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            atomicAction(i);
        }

        System.out.println(Thread.currentThread() + " stops working");
        System.out.println(Thread.currentThread() + " is going to return " + Arrays.toString(result));
        running = false;
    }

    public synchronized boolean isRunning() {
        return running;
    }

    public synchronized int[] getResult() {
        System.out.println(Thread.currentThread() + " is going to return " + Arrays.toString(result));
        if (!running) return result;
        else return null;
    }

}
