package services;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IStorage extends Remote{
    public abstract void store(String type, int result) throws RemoteException;
}
