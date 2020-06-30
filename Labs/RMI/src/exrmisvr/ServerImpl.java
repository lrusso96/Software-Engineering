package exrmisvr;

import svriface.ServerInterface;

import java.rmi.*;
import java.util.*;

public class ServerImpl implements ServerInterface {

    Hashtable<Integer, ServerThread> threadPool;
    int allocatedThreads;

    public ServerImpl() {
        this.threadPool = new Hashtable<>();
        this.allocatedThreads = 0;
    }

    @Override
    public int startTask() throws RemoteException {
        System.out.println("RMI Server" + Thread.currentThread() + " startTask() has been called...");

        ServerThread st = new ServerThread();
        Thread t = new Thread(st);
        threadPool.put(allocatedThreads, st);
        t.start();
        allocatedThreads = allocatedThreads + 1;
        return (allocatedThreads - 1);
    }

    public boolean isReady(int i) throws RemoteException {
        return threadPool.get(i).isRunning();
    }

    public int[] getResults(int i) throws RemoteException {
        System.out.println("RMI Server" + Thread.currentThread() + "getResults() has been called ...");
        return threadPool.get(i).getResult();
    }
}
