package services;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IManager extends Remote{

    public abstract void sendNotification(AlertNotification alertNotification) throws RemoteException;
    public abstract void subscribe(int componentID, int port) throws RemoteException;
    
}
