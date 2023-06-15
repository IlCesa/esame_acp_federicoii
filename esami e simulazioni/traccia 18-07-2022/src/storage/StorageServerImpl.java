package storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import services.IStorage;

public class StorageServerImpl extends UnicastRemoteObject implements IStorage{
    private Lock lock;
    public StorageServerImpl() throws RemoteException{
        lock = new ReentrantLock();
    }

    @Override
    public void store(String type, int result) throws RemoteException {
        try {
            lock.lock();
            FileWriter f = new FileWriter(new File("results.txt"), true);
            if(type.compareTo("result") == 0){
                f.append(String.valueOf(result + "\n"));
            }
            f.close();
            lock.unlock();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
