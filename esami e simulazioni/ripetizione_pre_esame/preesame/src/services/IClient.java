package services;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote{
    public abstract void clientService(String operation) throws RemoteException;
}
